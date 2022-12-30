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

public class ChatList extends JPanel implements MouseInputListener {
  // 이름 출력해주고, id 값 저장해서 버튼에 id로 다이얼로그 띄우기

  String name = null;
  String userID = null;

  Connection conn = null;
  PreparedStatement pstm = null;
  ResultSet rs = null;

  DBCon db = new DBCon();

  JList jl;
  List<String[]> fr_list;

  public ChatList() {
    // getDB();
    InitDisplay();
  }

  public void InitDisplay() {
    DefaultListModel<String> dlm = new DefaultListModel<String>();
    getDB();
    for (int i = 0; i < fr_list.size(); i++) {
      int a = fr_list.size();
      String[] data = new String[a];
      data = fr_list.get(i);

      dlm.add(0, data[0]); // data[0] : name, data[1] : ID
    }
    jl = new JList(dlm);
    jl.addMouseListener(this);
    JScrollPane jsp = new JScrollPane(jl);
    jl.setFixedCellWidth(390);
    jl.setFixedCellHeight(50);
    jl.setSize(380, 200);
    this.add(jsp);
    this.setSize(390, 200);
    this.setVisible(true);

  }

  public void getDB() {
    fr_list = new Vector<>();
    // data = new String[1][2];
    try {
      String sql = "SELECT * FROM MEMBER";
      conn = DBCon.getConnection();
      pstm = conn.prepareStatement(sql);
      rs = pstm.executeQuery();
      while (rs.next()) {
        String name = rs.getString("NAME");
        String ID = rs.getString("ID");
        String[] data = { name, ID };
        fr_list.add(data);
      }
    } catch (Exception e) {
    }
    // System.out.println(nameList);
  }

  public static void main(String[] args) {
    new ChatList();
  }

  @Override
  public void mouseClicked(MouseEvent e) {
    jl = (JList) e.getSource();
    if (e.getClickCount() == 2) {
      // int who = jl.locationToIndex(e.getPoint());
      // String[] data = fr_list.get((fr_list.size() - 1) - who); // JList에 역순으로 들어가서
      // index가 거꾸로 잡힘
      // fp.profileDisplay(true, data[0]);
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
