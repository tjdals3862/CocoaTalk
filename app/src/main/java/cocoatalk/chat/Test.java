package cocoatalk.chat;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import cocoatalk.oracle.DBCon;
import cocoatalk.oracle.DbFreeCon;

public class Test {
  Connection conn = null;
  PreparedStatement pstmt = null;
  ResultSet rs = null;
  DbFreeCon dfc = new DbFreeCon();

  public void roomSearch(String myID, String frID) {
    try {
      conn = DBCon.getConnection();
      StringBuilder sql = new StringBuilder();
      sql.append("select room                     ");
      sql.append("  from room_mem                     ");
      sql.append(" where room in (select room         ");
      sql.append("                  from room_mem     ");
      sql.append("                 where id = ?)      ");
      sql.append("   and (room between 1000 and 1999)");
      sql.append("   and id = ? group by room ");
      pstmt = conn.prepareStatement(sql.toString());
      pstmt.setString(1, myID);
      pstmt.setString(2, frID);
      rs = pstmt.executeQuery();
      int i = 0;

      while (rs.next()) {
        System.out.println(rs.getString("room"));
        // if (frID.equals(rs.getString("id"))) {
        // // enter(rs.getString("room"))
        // i++;
        // }
        // System.out.println(i);
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    dfc.freeConnection(conn, pstmt, rs);
  }

  public static void main(String[] args) {
    Test test = new Test();
    test.roomSearch("123", "1234");

  }
}
