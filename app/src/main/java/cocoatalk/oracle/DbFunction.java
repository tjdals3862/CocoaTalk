package cocoatalk.oracle;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.swing.JOptionPane;

public class DbFunction {
  DBCon db = null;
  Connection conn = null;
  PreparedStatement pstm = null;
  Statement stmt = null;
  ResultSet rs = null;
  DbFreeCon dfc = null;

  public DbFunction() {

  }

  public void insert(String query) {
    db = new DBCon();
    dfc = new DbFreeCon();

    try {
      conn = db.getConnection();
      pstm = conn.prepareStatement(query);
      int r = pstm.executeUpdate();
    } catch (SQLException sqle) {
      sqle.printStackTrace();
    }

    finally {
      try {
        dfc.freeConnection(conn, pstm);
      } catch (Exception ie) {
        throw new RuntimeException(ie.getMessage());
      }
    }
  }

  public void create(String query) {

    db = new DBCon();
    try {
      conn = db.getConnection();
      stmt = conn.createStatement();
      stmt.executeQuery(query);

    } catch (Exception se) {
      se.printStackTrace();
    } finally {
      try {
        dfc.freeConnection(conn, stmt);

      } catch (Exception ie) {
        throw new RuntimeException(ie.getMessage());
      }
    }

  }

  public void idchk(String query) {
    db = new DBCon();

    try {
      ArrayList<String> list = new ArrayList<>();
      conn = db.getConnection();
      pstm = conn.prepareStatement(query);
      rs = pstm.executeQuery();

      while (rs.next()) {
        list.add(rs.getString(1));
      }

      try {
        if (list.get(0) != null) {
          JOptionPane.showMessageDialog(null, "사용할 수 없는 id 입니다.",
              "id중복", JOptionPane.ERROR_MESSAGE);
        }
      } catch (IndexOutOfBoundsException ie) {
        JOptionPane.showMessageDialog(null, "사용가능한 id 입니다.", "정상", JOptionPane.INFORMATION_MESSAGE);

      }

    } catch (SQLException sqle) {
      System.out.println("예외 발생");
    } finally {
      try {
        dfc.freeConnection(conn, stmt, rs);
      } catch (Exception ie) {
        throw new RuntimeException(ie.getMessage());
      }
    }
  }
}
