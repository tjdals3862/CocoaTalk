package cocoatalk.login;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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

import cocoatalk.oracle.DBCon;

public class FriendList extends JPanel implements MouseListener, ActionListener {
  // 이름 출력해주고, id 값 저장해서 버튼에 id로 다이얼로그 띄우기
  FriendProfile fp = new FriendProfile(this);
  FriendAdd fa = null;
  CocoaVO cVO = null;

  String name = null;
  String userID = null;

  Connection conn = null;
  PreparedStatement pstm = null;
  ResultSet rs = null;

  DBCon db = new DBCon();

  JList jl_frnd;
  List<String[]> fr_list;
  DefaultListModel<String> dlm_frnd;
  JPanel frnd_north;
  JTextField jtf_search;
  JButton fr_search;
  JButton fr_add;

  public FriendList(CocoaVO cVO) {
    this.cVO = cVO;
    getDB();
    InitDisplay();
  }

  public void InitDisplay() {
    dlm_frnd = new DefaultListModel<>();
    frnd_north = new JPanel();
    jtf_search = new JTextField(23);
    fr_search = new JButton("검색");
    fr_add = new JButton("추가");
    fr_add.addActionListener(this);

    for (int i = 0; i < fr_list.size(); i++) {
      int a = fr_list.size();
      String[] data = new String[a];
      data = fr_list.get(i);

      dlm_frnd.add(0, data[0]); // data[0] : name, data[1] : ID
    }

    this.setLayout(new BorderLayout());
    jl_frnd = new JList(dlm_frnd);
    jl_frnd.addMouseListener(this);
    fr_search.addActionListener(this);
    JScrollPane jsp = new JScrollPane(jl_frnd);
    jl_frnd.setFixedCellWidth(380);
    jl_frnd.setFixedCellHeight(50);
    jl_frnd.setSize(390, 200);

    this.add("North", frnd_north);
    frnd_north.add("North", jtf_search);
    frnd_north.add("North", fr_search);
    frnd_north.add("North", fr_add);

    this.add("Center", jsp);
    this.setSize(426, 380);
    this.setVisible(true);

  }

  public void searchFriend(String str) {
    fr_list = new Vector<>();
    // data = new String[1][2];
    try {
      String sql = "SELECT FR_ID, FR_NAME FROM friend where ID = '" + cVO.getId() + "'" + "&& FR_NAME = '"
          + jtf_frnd.getText();
      conn = DBCon.getConnection();
      pstm = conn.prepareStatement(sql);
      pstm.setString(1, str);
      rs = pstm.executeQuery();

      while (rs.next()) {
        String name = rs.getString("NAME");
        String ID = rs.getString("ID");
        String[] data = { name, ID };
        fr_list.add(data);
      }
    } catch (Exception e) {
    }

    for (int i = 0; i < fr_list.size(); i++) {
      int a = fr_list.size();
      String[] data = new String[a];
      data = fr_list.get(i);

      dlm_frnd.add(0, data[0]); // data[0] : name, data[1] : ID
    }
    jl_frnd = new JList(dlm_frnd);
    JScrollPane jsp = new JScrollPane(jl_frnd);
    this.add("Center", jsp);
  }

  public void getDB() {
    fr_list = new Vector<>();
    // data = new String[1][2];
    try {
      String sql = "SELECT FR_ID, FR_NAME FROM friend where ID = '" + cVO.getId() + "'";
      conn = DBCon.getConnection();
      pstm = conn.prepareStatement(sql);
      rs = pstm.executeQuery();
      while (rs.next()) {
        String name = rs.getString("FR_NAME");
        String ID = rs.getString("FR_ID");
        String[] data = { name, ID };
        fr_list.add(data);
      }
    } catch (Exception e) {
    }
    // System.out.println(nameList);
  }

  // public static void main(String[] args) {
  // new FriendList();
  // }

  @Override
  public void mouseClicked(MouseEvent e) {
    Object obj = e.getSource();
    if (obj == jl_frnd) {
      if (e.getClickCount() == 2) {
        int who = jl_frnd.locationToIndex(e.getPoint());
        String[] data = fr_list.get((fr_list.size() - 1) - who); // JList에 역순으로 들어가서
        // index가 거꾸로 잡힘
        fp.profileDisplay(true, data[0]);
      }
    }

  }

  @Override
  public void actionPerformed(ActionEvent e) {
    Object obj = e.getSource(); // 검색 버튼 클릭
    System.out.println("click");
    if (fr_search == obj) {
      searchFriend(jtf_search.getText());
      jtf_search.setText("");
      System.out.println(fr_list.toString());
    } else if (fr_add == obj) {
      fa = new FriendAdd(cVO);
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

  @Override
  public void mouseDragged(MouseEvent e) {
  }

  @Override
  public void mouseMoved(MouseEvent e) {
  }

}
