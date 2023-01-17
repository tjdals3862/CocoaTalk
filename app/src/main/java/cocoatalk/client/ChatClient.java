package cocoatalk.client;
// 클라이언트 채팅방

import java.awt.AlphaComposite;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.JViewport;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.StyleContext;
import javax.swing.text.StyledDocument;

import cocoatalk.oracle.DBCon;

public class ChatClient extends JFrame implements ActionListener, Runnable {
  //////////////// 통신과 관련한 전역변수 추가 시작//////////////
  Socket socket = null;
  ObjectOutputStream oos = null;// 말 하고 싶을 때
  ObjectInputStream ois = null;// 듣기 할 때
  String nickName = null;// 닉네임 등록
  String id = null; // id
  String frid = null; // 친구 id
  int room_num; // 채팅방 번호

  ChatClientThread cct = null;
  Room r = new Room();

  // 선언
  String imgPath = "app\\src\\main\\java\\cocoatalk\\images\\"; // 배경
  ImageIcon imgbgIcon = new ImageIcon(imgPath + "winter.gif"); //
  Toolkit toolkit = Toolkit.getDefaultToolkit();// 로고삽입
  Image img = toolkit.getImage(imgPath + "logo.png");// 로고삽입
  JLabel jlb_bgLabel = new JLabel(imgbgIcon); //
  JPanel jp = new JPanel(); // 텍스트 메인 페널
  JPanel jp_send = new JPanel(); // 텍스트 전송 페널
  JButton jbtn_send = new JButton(new ImageIcon(imgPath + "sendbtn.png")); // 텍스트 전송 버튼
  JTextField jtf_message = new JTextField(); // 텍스트 입력
  JTextArea jta_display = new JTextArea();

  StyledDocument sd_display = new DefaultStyledDocument(new StyleContext());
  JTextPane jtp_chatDisplay = new JTextPane(sd_display);

  // 배경 화면 이미지 삽입
  JViewport viewport = new JViewport() {
    public void paintComponent(Graphics g) {
      Image img = imgbgIcon.getImage(); //
      setOpaque(false);
      Graphics2D gd = (Graphics2D) g;
      gd.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f));
      g.drawImage(img, 0, 0, this);
      super.paintComponent(g);
    }
  };

  Font font = new Font("Paryrus", Font.BOLD, 20);
  JScrollPane jsp = new JScrollPane(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
      JScrollPane.HORIZONTAL_SCROLLBAR_NEVER); //

  public void ChatClient() {

  }

  // 화면 initDisplay 메소드
  public void initDisplay() {
    this.setIconImage(img);// 로고삽입
    jsp.setOpaque(true);
    jsp.setViewport(viewport);
    jtp_chatDisplay.setOpaque(false);
    jsp.setViewportView(jtp_chatDisplay);
    // 메인 페널
    jp.setLayout(new BorderLayout());
    jp.add("Center", jsp);
    jp.add("South", jp_send);
    // 전송 페널
    jp_send.setLayout(new BorderLayout());
    jp_send.add("Center", jtf_message);
    jp_send.add("East", jbtn_send);
    // 버튼 크기
    jbtn_send.setPreferredSize(new Dimension(65, 40));
    // 전송 화면
    // jta_display 폰트색상
    jtp_chatDisplay.setForeground(Color.ORANGE);
    jtp_chatDisplay.setEditable(false); // 텍스트 필드 입력 불가
    // 폰트
    jtp_chatDisplay.setFont(font);
    jtf_message.setFont(font);
    // 액션
    jtf_message.addActionListener(this);
    jbtn_send.addActionListener(this);
    // 보내기 버튼 크기 조절
    jbtn_send.setPreferredSize(new Dimension(100, 80));
    // 채팅장 사이즈 고정
    this.setResizable(false);
    this.setLayout(new GridLayout(1, 2));
    this.add(jp);
    this.setTitle("Cacao TALK");
    this.setVisible(true);
    this.setSize(400, 600);
    this.setDefaultCloseOperation(DISPOSE_ON_CLOSE); // X 클릭 자동 종료
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    Object obj = e.getSource();

    String message = id + "#" + room_num + "#" + jtf_message.getText();

    // 채팅 이벤트 처리
    if (obj == jbtn_send || obj == jtf_message) {
      // 입력된 메시지 얻기
      try {
        oos.writeObject(message);
        jtf_message.setText("");
      } catch (Exception e2) {

      }
    }
  }

  // 소켓 관련 초기화
  public void init() {
    Thread th = new Thread(this);
    th.start();
  }

  public void chatting() {

    try {
      DBCon dbcon = new DBCon();
      Connection conn = dbcon.getConnection();
      Room room = new Room();

      // 최근 10개 채팅 추출
      StringBuilder sql = new StringBuilder();
      sql.append(" SELECT id, chat FROM                                   ");
      sql.append(" (select id,chat,TIME from room_chat                   ");
      sql.append(" where room = " + room_num + " order by  TIME DESC ");
      sql.append(" ) WHERE ROWNUM <= 10 order by TIME                  ");

      PreparedStatement pstm = conn.prepareStatement(sql.toString());
      ResultSet rs = pstm.executeQuery();

      // 가져온 10개의 채팅 jtextarea에 업로드
      while (rs.next()) {
        sd_display.insertString(sd_display.getLength(),
            room.getName(rs.getString("id")) + " : " + rs.getString("chat") + "\n", null);
      }

    } catch (Exception e) {
      // TODO: handle exception
    }
  }

  // 1대1 채팅방 호출시의 메소드
  public void chatOpen(String myID, String frID) {
    id = myID;
    frid = frID;
    room_num = r.roomSearch(myID, frID);
    initDisplay();
    chatting();
    init();
  }

  // 채팅방 리스트에서 호출되는 메소드(method overloading)
  public void chatOpen(String myID, int room) {
    id = myID;
    room_num = room;
    initDisplay();
    chatting();
    init();
  }

  @Override
  public void run() {
    try {
      // 서버측의 ip주소 작성하기
      socket = new Socket("192.168.10.74", 3000);
      oos = new ObjectOutputStream(socket.getOutputStream());
      ois = new ObjectInputStream(socket.getInputStream());
      // initDisplay에서 닉네임이 결정된 후 init메소드가 호출되므로
      // 서버에게 내가 입장한 사실을 알린다.(말하기)
      cct = new ChatClientThread(this);
      // 클라이언트 쓰레드 시작
      cct.start();
    } catch (Exception e) {

    }
  }
}