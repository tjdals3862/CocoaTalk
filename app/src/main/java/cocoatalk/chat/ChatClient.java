package cocoatalk.chat;

import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import java.awt.Font;
import java.awt.Dimension;

public class ChatClient extends JFrame implements ActionListener {
  //////////////// 통신과 관련한 전역변수 추가 시작//////////////
  Socket socket = null;
  ObjectOutputStream oos = null;// 말 하고 싶을 때
  ObjectInputStream ois = null;// 듣기 할 때
  String nickName = null;// 닉네임 등록

  // 선언부
  // 이미지 경로 선언
  String imgPath = "D:\\TEMP\\";
  ImageIcon imgIcon = new ImageIcon(imgPath + "wallPaper.jpg");
  JTable jtb = new JTable();
  JScrollPane jsp = new JScrollPane(jtb);
  JPanel jp = new JPanel();
  JPanel jp_behind = new JPanel();
  JButton jbtn_send = new JButton("보내기");
  JTextField jtf_message = new JTextField();
  JTextArea jta_display = new JTextArea() {
    public void paintComponent(Graphics g) {
      g.drawImage(imgIcon.getImage(), 0, 0, null);
      setOpaque(false);
      super.paintComponent(g);
    }
  };
  JScrollPane jsp_display = new JScrollPane(jta_display, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
      JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
  Font f = new Font("Paryrus", Font.BOLD, 40);
  // Font f = new Font("Arial", Font.BOLD, 40);
  // Font f = new Font("휴먼매직체", Font.BOLD, 40);
  // Font f = new Font("굴림체", Font.BOLD, 40);

  public void ChatClient() {

  }

  public void initDisplay() {
    // 폰트 설정
    jtf_message.setFont(f);// 입력창 폰트
    jta_display.setFont(f);// 대화창 폰트

    // 이벤트 처리
    jbtn_send.addActionListener(this);
    jtf_message.addActionListener(this);

    // 종료 버튼 누리면 꺼짐
    this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

    // 레이아웃 설정
    this.setLayout(new BorderLayout());
    jp.setLayout(new BorderLayout());

    // 버튼과 텍스트 필드 추가
    jp.add(jtf_message, BorderLayout.CENTER);
    jp.add(jbtn_send, BorderLayout.EAST);

    // 텍스트 영역의 테투리
    jta_display.setLineWrap(true);

    // 스크롤 추가
    this.add(jsp_display, BorderLayout.CENTER);
    this.add(jp, BorderLayout.SOUTH);

    // 프레임 설정
    this.setTitle("COCOA TALK");
    this.setSize(410, 650);
    this.setVisible(true);

    jbtn_send.setPreferredSize(new Dimension(100, 80));
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    Object obj = e.getSource();
    // ChatClient chat = new ChatClient();

    // 채팅 이벤트 처리
    if (obj == jbtn_send || obj == jtf_message) {
      // 입력된 메시지 얻기
      String message = jtf_message.getText();

      // jTextArea_display 텍스트 영역에 출력
      jta_display.append(message + "\n");
      try {
        oos.writeObject(message);
        // jTextField_mesaage 텍스트 필드 초기화
        jtf_message.setText("");
      } catch (Exception e2) {

      }

    }
  }

  // 소켓 관련 초기화
  public void init() {
    try {
      // 서버측의 ip주소 작성하기
      // socket = new Socket("192.168.0.244",3000);
      // new ServerSocket(3000)이 받아서 accept()통해서 client소켓에 저장됨.
      socket = new Socket("192.168.10.74", 3000);
      oos = new ObjectOutputStream(socket.getOutputStream());
      ois = new ObjectInputStream(socket.getInputStream());
      // initDisplay에서 닉네임이 결정된 후 init메소드가 호출되므로
      // 서버에게 내가 입장한 사실을 알린다.(말하기)
      // String message = jtf_message.getText();
      // oos.writeObject(message);
      ChatClientThread cct = new ChatClientThread(this);
      cct.start();
    } catch (Exception e) {
      // 예외가 발생했을 때 직접적인 원인되는 클래스명 출력하기
      System.out.println(e.toString());
    }
  }

  public static void main(String[] args) {
    ChatClient cc = new ChatClient();
    cc.initDisplay();
    cc.init();

  }
}
