package cocoatalk.chat;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

import cocoatalk.oracle.DBCon;

public class Room {

  Connection conn = null;
  PreparedStatement pstmt = null;
  ResultSet rs = null;

  // room search
  public Room() {

  }

  public void roomSearch(String myID, String frID) {
    try {
      conn = DBCon.getConnection();
      StringBuilder sql = new StringBuilder();
      sql.append("select id, room                     ");
      sql.append("  from room_mem                     ");
      sql.append(" where room in (select room         ");
      sql.append("                  from room_mem     ");
      sql.append("                 where id = ?)      ");
      sql.append("   and (room between 1000 and 1999)");
      pstmt = conn.prepareStatement(sql.toString());
      pstmt.setString(1, myID);
      rs = pstmt.executeQuery();
      int i = 0;
      while (rs.next()) {
        if (frID == rs.getString("id")) {
          // enter(rs.getString("room"))
          i++;
        }
      }
      if (i == 0) {
        roomCreate(myID, frID);
      }

    } catch (Exception e) {
      e.printStackTrace();
    }

  }

  // room create
  public void roomCreate(String myID, String frID) {
    try {
      conn = DBCon.getConnection();
      StringBuilder sql = new StringBuilder();
      sql.append("insert into room_mem values ((                       ");
      sql.append("     select /*+index_desc(m room_idx)*/              ");
      sql.append("            room+1                                   ");
      sql.append("       from room_mem m                               ");
      sql.append("      where (room between 1 and 1000) AND rownum = 1)");
      sql.append("            ,?,?)                                    ");
      pstmt = conn.prepareStatement(sql.toString());
      pstmt.setString(1, myID); // id
      pstmt.setString(2, frID); // name
      rs = pstmt.executeQuery();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

}