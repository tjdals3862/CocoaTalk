package cocoatalk.main;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
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
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JFrame;
import java.awt.Font;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import cocoatalk.dialog.FriendAdd;
import cocoatalk.dialog.FriendProfile;
import cocoatalk.login.CocoaVO;
import cocoatalk.oracle.DBCon;

public class ChatFriendList extends JFrame implements MouseListener, ActionListener {

  FriendAdd fa = null;
  MainForm mf = null;
  CocoaVO cVO = null;
  FriendProfile fp = null;

  List<String[]> fr_list;
  DefaultListModel<String> dlm_frnd;
  JScrollPane jsp;
  JList jl_frnd;

  String name = null;
  String kkkkk = null;
  String userID = null;

  Connection conn = null;
  PreparedStatement pstm = null;
  ResultSet rs = null;

  DBCon db = new DBCon();

  JPanel frnd_north;
  JTextField jtf_search;
  JButton fr_search;
  JButton fr_invite;
  Font font;

  // 생성자
  public ChatFriendList(CocoaVO cVO) {
    this.cVO = cVO;
    // InitDisplay();
  }

  public void InitDisplay() {
    dlm_frnd = new DefaultListModel<>();
    getDB();
    frnd_north = new JPanel();
    add(frnd_north);
    jtf_search = new JTextField(23);
    fr_search = new JButton("검색");
    fr_invite = new JButton("초대");
    jl_frnd = new JList(dlm_frnd);
    font = cVO.getFontc();

    fr_search.addActionListener(this);
    frnd_north.setLayout(new FlowLayout());

    jsp = new JScrollPane(jl_frnd);

    jl_frnd.addMouseListener(this);
    jl_frnd.setFixedCellWidth(380);
    jl_frnd.setFixedCellHeight(50);
    jl_frnd.setVisibleRowCount(6);
    jl_frnd.setSize(390, 200);
    jl_frnd.setFont(font);
    jl_frnd.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);

    frnd_north.add(jtf_search);
    frnd_north.add(fr_search);
    frnd_north.add(fr_invite);

    this.add("North", frnd_north);
    this.add("Center", jsp);

    this.setSize(426, 380);
    this.setVisible(true);

    fr_invite.addActionListener(new ActionListener() {

      @Override
      public void actionPerformed(ActionEvent ae) {
        System.out.println("선택완료");
        ChatFriendList ch = new ChatFriendList(cVO);
        ch.searchFriend(kkkkk);

        int[] a = jl_frnd.getSelectedIndices();
        for (int i = 0; i < a.length; i++) {
          System.out.println("여긴어ㅏ기러ㅏ니우리낭리ㅏ눌이ㅜㄹㅇ" + a[i]);
        }
      }
    });

  }

  public void searchFriend(String str) {
    // dlm_frnd.clear();
    fr_list = new Vector<>();

    try {
      String sql = String.format("SELECT * FROM frlist_%s WHERE id = '%s' AND fr_name like '%%%s%%' ",
          cVO.getId(),
          cVO.getId(),
          str);
      conn = db.getConnection();
      pstm = conn.prepareStatement(sql);
      rs = pstm.executeQuery();

      while (rs.next()) {
        String name = rs.getString("FR_NAME");
        String ID = rs.getString("FR_ID");
        String[] data = { name, ID };
        fr_list.add(data);
      }

      for (int i = 0; i < fr_list.size(); i++) {
        int a = fr_list.size();
        String[] data = new String[a];
        data = fr_list.get(i);

        dlm_frnd.add(0, data[0]); // data[0] : name, data[1] : ID
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  public void getDB() {
    // dlm_frnd.clear();
    fr_list = new Vector<>();

    try {
      String sql = "SELECT FR_ID, FR_NAME FROM frlist_" + cVO.getId() + " where ID = '" + cVO.getId() + "'";
      conn = db.getConnection();
      pstm = conn.prepareStatement(sql);
      rs = pstm.executeQuery();
      while (rs.next()) {
        String name = rs.getString("FR_NAME");
        String ID = rs.getString("FR_ID");
        String[] data = { name, ID };
        fr_list.add(data);
      }

      for (int i = 0; i < fr_list.size(); i++) {
        int a = fr_list.size();
        String[] data = new String[a];
        data = fr_list.get(i);

        dlm_frnd.add(0, data[0]); // data[0] : name, data[1] : ID
      }
    } catch (Exception e) {
    }
  }

  @Override
  public void mouseClicked(MouseEvent e) {
    Object obj = e.getSource();
    /*
     * if (obj == jl_frnd) {
     * if (e.getClickCount() == 2) {
     * int who = jl_frnd.locationToIndex(e.getPoint());
     * String[] data = fr_list.get((fr_list.size() - 1) - who); // JList에 역순으로 들어가서
     * // index가 거꾸로 잡힘
     * // fp = new FriendProfile(this);
     * fp.profileDisplay(true, data[0]);
     * }
     * }
     */
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    Object obj = e.getSource();

    if (fr_search == obj) { // 검색 버튼 클릭
      if (jtf_search.getText() == null) {
        System.out.println(jtf_search.getText());
        getDB();

      } else {
        searchFriend(jtf_search.getText());
        jtf_search.setText("");
      }

      // } else if (fr_invite == obj) { // 초대 버튼 클릭

      // System.out.println("받아온값넣어보쟈");
      // int[] a = jl_frnd.getSelectedIndices();

      // for (int i = 0; i < a.length; i++) {
      // System.out.println("ㅎㅇㅎㅇㅎㅇㅎㅇ" + a[i]);
      // }
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
