package cocoatalk.main;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Vector;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import cocoatalk.dialog.ChatAdd;
import cocoatalk.login.CocoaVO;
import cocoatalk.oracle.DBCon;

public class ChatList extends JPanel implements MouseListener, ActionListener {
  // 채팅방 목록 출력해주고,
  // 추가 누르면 id값으로 검색해서 체크박스 >> 대화방 생성
  DBCon dbcon = new DBCon();

  ChatAdd ca = null;
  CocoaVO cVO = null;

  List<String> chat_list;
  DefaultListModel<String> dlm_chat;
  JScrollPane jsp;
  JList jl_chat;

  String name = null;
  String userID = null;

  Connection conn = null;
  PreparedStatement pstm = null;
  ResultSet rs = null;

  DBCon db = new DBCon();

  JPanel chat_north;
  JTextField jtf_search;
  JButton chat_search;
  JButton chat_add;

  public ChatList(CocoaVO cVO) {
    this.cVO = cVO;
    InitDisplay();
  }

  public void InitDisplay() {
    dlm_chat = new DefaultListModel<>();
    getRoom();

    chat_north = new JPanel();
    jtf_search = new JTextField(23);
    chat_search = new JButton("검색");
    chat_add = new JButton("추가");
    chat_add.addActionListener(this);

    jl_chat = new JList(dlm_chat);
    jsp = new JScrollPane(jl_chat);

    this.setLayout(new BorderLayout());

    chat_search.addActionListener(this);

    jl_chat.addMouseListener(this);
    jl_chat.setFixedCellWidth(380);
    jl_chat.setFixedCellHeight(50);
    jl_chat.setVisibleRowCount(6);
    jl_chat.setSize(390, 200);

    chat_north.add("North", jtf_search);
    chat_north.add("North", chat_search);
    chat_north.add("North", chat_add);

    this.add("North", chat_north);
    this.add("Center", jsp);

    this.setSize(426, 380);
    this.setVisible(true);

  }

  public void searchChatList(String str) {
    // dlm_chat.clear();
    // chat_list = new Vector<>();

    // try {
    // String sql = String.format("SELECT * FROM friend WHERE id = '%s' AND fr_name
    // like '%s'", cVO.getId(),
    // str);
    // conn = DBCon.getConnection();
    // pstm = conn.prepareStatement(sql);
    // rs = pstm.executeQuery();

    // while (rs.next()) {
    // String room = rs.getString("ROOM")
    // // String[] data = { name, ID };
    // chat_list.add(room);
    // }

    // for (int i = 0; i < chat_list.size(); i++) {
    // int a = chat_list.size();
    // // String[] data = new String[a];

    // data = chat_list.get(i);

    // dlm_chat.add(0, data[0]); // data[0] : name, data[1] : ID
    // }
    // } catch (SQLException e) {
    // e.printStackTrace();
    // }
  }

  public void addChatList() {

  }

  private void getRoom() {
    dlm_chat.clear();
    chat_list = new Vector<>();

    try {
      conn = dbcon.getConnection();
      String sql = String.format("SELECT room FROM room_mem WHERE id = '%s'",
          cVO.getId());
      // String sql = "SELECT * FROM room_mem WHERE id = '?'";
      // pstm.setString(1, cVO.getId());
      // System.out.println(cVO.getId());
      pstm = conn.prepareStatement(sql);
      rs = pstm.executeQuery();

      while (rs.next()) {
        String room = rs.getString("room");
        chat_list.add(room);
      }
      for (int i = 0; i < chat_list.size(); i++) {
        String room = chat_list.get(i);
        dlm_chat.addElement(room); // data[0] : name, data[1] : ID
      }
    } catch (Exception e) {
    }
  }

  @Override
  public void mouseClicked(MouseEvent e) {
    Object obj = e.getSource();
    if (obj == jl_chat) {
      if (e.getClickCount() == 2) {
        int who = jl_chat.locationToIndex(e.getPoint());
        // String[] data = chat_list.get((chat_list.size() - 1) - who); // JList에 역순으로
        // 들어가서
        // // index가 거꾸로 잡힘
        // fp.profileDisplay(true, data[0]);
      }
    }

  }

  @Override
  public void actionPerformed(ActionEvent e) {
    Object obj = e.getSource();
    if (chat_search == obj) { // 검색 버튼 클릭
      if (jtf_search.getText() == null) {
        getRoom();
        InitDisplay();
      } else {
        // roomSearch(jtf_search.getText());
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
