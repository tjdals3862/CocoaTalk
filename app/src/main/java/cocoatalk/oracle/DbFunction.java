package cocoatalk.oracle;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JOptionPane;

public class DbFunction {
  DBCon db = null;
  Connection conn = null;
  PreparedStatement pstm = null;
  ResultSet rs = null;

  public DbFunction() {

  }

  public void insert(String query) {
    db = new DBCon();

    try {
      conn = db.getConnection();
      pstm = conn.prepareStatement(query);
      int r = pstm.executeUpdate();
    } catch (SQLException sqle) {
      sqle.printStackTrace();
    }

    finally {
      try {
        if (rs != null) {
          rs.close();
        }
        if (pstm != null) {
          pstm.close();
        }
        if (conn != null) {
          conn.close();
        }
      } catch (Exception ie) {
        throw new RuntimeException(ie.getMessage());
      }
    }

  }
}
