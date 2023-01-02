package cocoatalk.login;

import cocoatalk.oracle.DBCon;
import java.util.List;
import java.util.Vector;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.event.MouseInputListener;

import java.awt.event.MouseEvent;
import java.awt.GridLayout;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class FriendList extends JPanel implements MouseInputListener {
  // 이름 출력해주고, id 값 저장해서 버튼에 id로 다이얼로그 띄우기
  FriendProfile fp = new FriendProfile(this);
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

  public FriendList(CocoaVO cVO) {
    this.cVO = cVO;
    InitDisplay();
  }

  public void InitDisplay() {
    dlm_frnd = new DefaultListModel<>();
    getDB();
    for (int i = 0; i < fr_list.size(); i++) {
      int a = fr_list.size();
      String[] data = new String[a];
      data = fr_list.get(i);

      dlm_frnd.add(0, data[0]); // data[0] : name, data[1] : ID
    }
    jl_frnd = new JList(dlm_frnd);
    jl_frnd.addMouseListener(this);
    JScrollPane jsp = new JScrollPane(jl_frnd);
    jl_frnd.setFixedCellWidth(390);
    jl_frnd.setFixedCellHeight(50);
    jl_frnd.setSize(390, 200);
    this.add(jsp);
    this.setSize(426, 380);
    this.setVisible(true);

  }

  public void searchFriend(String str) {
    fr_list = new Vector<>();
    // data = new String[1][2];
    try {
      String sql = "SELECT * FROM MEMBER WHERE ID = ?;";
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
    jl_frnd = (JList) e.getSource();
    if (e.getClickCount() == 2) {
      int who = jl_frnd.locationToIndex(e.getPoint());
      String[] data = fr_list.get((fr_list.size() - 1) - who); // JList에 역순으로 들어가서
      // index가 거꾸로 잡힘
      fp.profileDisplay(true, data[0]);
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
