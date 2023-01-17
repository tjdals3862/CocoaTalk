package cocoatalk.main;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;
import java.util.StringTokenizer;
import java.util.Vector;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import cocoatalk.client.ChatClient;
import cocoatalk.client.Room;
import cocoatalk.dialog.ChatAdd;
import cocoatalk.login.CocoaVO;
import cocoatalk.oracle.DBCon;
import cocoatalk.oracle.DbFreeCon;

public class ChatList extends JPanel implements MouseListener {
  // 채팅방 목록 출력해주고,
  // 추가 누르면 id값으로 검색해서 체크박스 >> 대화방 생성
  ChatAdd ca = null;
  CocoaVO cVO = null;
  ChatClient cc = null;
  Room r = new Room();

  DefaultListModel<String> dlm_chat = new DefaultListModel<>();
  JScrollPane jsp;
  JList jl_chat;

  String name = null;
  String myID = null;

  DBCon db = new DBCon();
  DbFreeCon dfc = new DbFreeCon();

  JPanel chat_north;
  JTextField jtf_search;
  JButton chat_search;
  JButton chat_add;
  Font font;

  public ChatList(CocoaVO cVO) {
    this.cVO = cVO;
    InitDisplay();
  }

  public void InitDisplay() {
    myID = cVO.getId();
    System.out.println(myID);

    getRoomList();

    font = cVO.getFontc();
    chat_north = new JPanel();
    jtf_search = new JTextField("", 23);
    chat_search = new JButton("검색");
    chat_add = new JButton("추가");
    chat_add.addMouseListener(this);

    jl_chat = new JList(dlm_chat);
    jsp = new JScrollPane(jl_chat);

    this.setLayout(new BorderLayout());

    chat_search.addMouseListener(this);

    jl_chat.addMouseListener(this);
    jl_chat.setFixedCellWidth(380);
    jl_chat.setFixedCellHeight(50);
    jl_chat.setVisibleRowCount(6);
    jl_chat.setSize(390, 200);
    jl_chat.setFont(font);

    chat_north.add("North", jtf_search);
    chat_north.add("North", chat_search);
    chat_north.add("North", chat_add);

    this.add("North", chat_north);
    this.add("Center", jsp);

    this.setSize(426, 380);
    this.setVisible(true);

  }

  private void getRoomList() {
    DBCon dbcon = new DBCon();
    dlm_chat.clear();
    List<String> chat_list = new Vector<>();

    try {
      Connection conn = dbcon.getConnection();
      String sql = String.format("SELECT room FROM room_mem WHERE id = '%s'",
          myID);
      PreparedStatement pstmt = conn.prepareStatement(sql);
      ResultSet rs = pstmt.executeQuery();

      while (rs.next()) {
        int room = rs.getInt("room");
        List<String> ls = r.getMember(myID, room);
        StringBuilder nameList = new StringBuilder();
        for (int i = 0; i < ls.size() - 1; i++) {
          nameList.append(ls.get(i) + ", ");
        }
        nameList.append(ls.get(ls.size() - 1));

        chat_list.add(nameList + "/" + room);
        System.out.println(chat_list.toString());
      }
      for (int i = 0; i < chat_list.size(); i++) {
        String chatRoom = chat_list.get(i);
        dlm_chat.addElement(chatRoom);
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  private void searchRoomList(String myID, String fr) { // 친구 이름으로 검색
    DBCon dbcon = new DBCon();
    dlm_chat.clear();
    List<String> chat_list = new Vector<>();
    // List<String> l = r.getMember(myID, room);
    try {
      Connection conn = dbcon.getConnection();
      // String sql = String.format("SELECT room FROM room_mem WHERE id = '%s'",
      // myID);
      StringBuilder sql = new StringBuilder();
      sql.append("select room                                            "); // 조회 쿼리 테이블 조인********
      sql.append("  from room_mem                                        ");
      sql.append(" where room in (select room from room_mem where id = ?)");
      sql.append("   and (id like ? or name like ?)                      ");
      PreparedStatement pstmt = conn.prepareStatement(sql.toString());
      pstmt.setString(1, myID);
      pstmt.setString(2, fr);
      pstmt.setString(3, fr);
      ResultSet rs = pstmt.executeQuery();
      System.out.println(myID);
      while (rs.next()) {
        int room = rs.getInt("room");
        List<String> ls = r.getMember(myID, room);
        StringBuilder nameList = new StringBuilder();
        for (int i = 0; i < ls.size() - 1; i++) {
          nameList.append(ls.get(i) + ", ");
        }
        nameList.append(ls.get(ls.size() - 1));

        chat_list.add(nameList + "/" + room);
      }
      for (int i = 0; i < chat_list.size(); i++) {
        String memID = chat_list.get(i);
        dlm_chat.addElement(memID);
      }
    } catch (Exception e) {
      e.printStackTrace();
    }

  }

  @Override
  public void mouseClicked(MouseEvent e) {
    Object obj = e.getSource();
    Room r = new Room();
    if (obj == jl_chat) {
      if (e.getClickCount() == 2) {
        String data = jl_chat.getSelectedValue().toString();
        StringTokenizer st = new StringTokenizer(data, "/");
        String a = st.nextToken();
        int room = Integer.parseInt(st.nextToken());

        cc = new ChatClient();
        cc.chatOpen(cVO.getId(), room);

      }
    } else if (chat_search == obj) { // 검색 버튼 클릭
      if (jtf_search.getText() == "") {
        getRoomList();
        InitDisplay();
        System.out.println(jtf_search.getText());
      } else {
        searchRoomList(myID, jtf_search.getText());
        jtf_search.setText("");
      }
    } else if (chat_add == obj) { // 추가 버튼 클릭
      ChatFriendList cfl = new ChatFriendList(cVO);
      cfl.InitDisplay();
    }
  }

  @Override
  public void mousePressed(MouseEvent e) {
  }

  @Override
  public void mouseReleased(MouseEvent e) {
  }

  @Override
  public void mouseEntered(MouseEvent e) {
  }

  @Override
  public void mouseExited(MouseEvent e) {
  }
}