package cocoatalk.login;

import cocoatalk.oracle.DBCon;
import java.util.List;
import java.util.Vector;

import javax.swing.DefaultListModel;
import javax.swing.JDialog;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.ListModel;

import org.w3c.dom.NameList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class FriendList extends JDialog {
  // 이름 출력해주고, id 값 저장해서 버튼에 id로 다이얼로그 띄우기
  String name = null;
  String userID = null;

  // List<Friends> list = new Vector<>();

  Connection conn = null;
  PreparedStatement pstm = null;
  ResultSet rs = null;

  DBCon db = new DBCon();

  JList jl;
  List<String> fr_list;

  public FriendList() {
    getDB();
    InitDisplay();
  }

  public void InitDisplay() {
    DefaultListModel<String> dlm = new DefaultListModel<String>();
    for (int i = 0; i < fr_list.size(); i++) {
      dlm.add(0, fr_list.get(i));
    }
    jl = new JList(dlm);
    JScrollPane jsp = new JScrollPane(jl);
    this.add(jsp);
    this.setSize(410, 650);
    this.setVisible(true);
  }

  public void getDB() {
    fr_list = new Vector<>();

    try {
      String sql = "SELECT * FROM MEMBER";
      conn = DBCon.getConnection();
      pstm = conn.prepareStatement(sql);
      rs = pstm.executeQuery();
      while (rs.next()) {
        String name = rs.getString(1);
        String ID = rs.getString(2);
        // data={name,ID};
        fr_list.add(0, name);
        fr_list.add(1, ID);
      }
    } catch (Exception e) {
    }
    // System.out.println(nameList);
  }

  public static void main(String[] args) {
    new FriendList();
  }
}
