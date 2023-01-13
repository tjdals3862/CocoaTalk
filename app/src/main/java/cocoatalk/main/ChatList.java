package cocoatalk.main;

import java.awt.BorderLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;
import java.util.Vector;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import cocoatalk.client.Room;
import cocoatalk.dialog.ChatAdd;
import cocoatalk.login.CocoaVO;
import cocoatalk.oracle.DBCon;

public class ChatList extends JPanel implements MouseListener {
  // 채팅방 목록 출력해주고,
  // 추가 누르면 id값으로 검색해서 체크박스 >> 대화방 생성
  DBCon dbcon = new DBCon();

  ChatAdd ca = null;
  CocoaVO cVO = null;
  Room r = new Room();

  List<String> chat_list;
  DefaultListModel<String> dlm_chat;
  JScrollPane jsp;
  JList jl_chat;

  String name = null;
  String myID = null;

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
    myID = cVO.getId();
    System.out.println(myID);
    dlm_chat = new DefaultListModel<>();
    getRoomList();

    chat_north = new JPanel();
    jtf_search = new JTextField(23);
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

    chat_north.add("North", jtf_search);
    chat_north.add("North", chat_search);
    chat_north.add("North", chat_add);

    this.add("North", chat_north);
    this.add("Center", jsp);

    this.setSize(426, 380);
    this.setVisible(true);

  }

  private void getRoomList() {
    dlm_chat.clear();
    chat_list = new Vector<>();

    try {
      conn = dbcon.getConnection();
      String sql = String.format("SELECT room FROM room_mem WHERE id = '%s'",
          myID);
      pstm = conn.prepareStatement(sql);
      rs = pstm.executeQuery();

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
    }
  }

  private void loadRoomList(int room) {
    dlm_chat.clear();
    chat_list = new Vector<>();
    List<String> l = r.getMember(myID, room);

    for (int i = 0; i < chat_list.size(); i++) {
      // room = chat_list.get(i);
      // dlm_chat.addElement(room); // data[0] : name, data[1] : ID
    }

  }

  @Override
  public void mouseClicked(MouseEvent e) {
    Object obj = e.getSource();
    Room r = new Room();
    if (obj == jl_chat) {
      if (e.getClickCount() == 2) {
        int room = jl_chat.locationToIndex(e.getPoint());
        r.getMember(myID, room); // return List<String> 1:다 채팅방 멤버 뽑기
      }
    } else if (chat_search == obj) { // 검색 버튼 클릭
      if (jtf_search.getText() == null) {
        getRoomList();
        InitDisplay();
      } else {
        loadRoomList(r.searchRoomList(myID, jtf_search.getText()));
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