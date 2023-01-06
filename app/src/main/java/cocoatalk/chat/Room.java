package cocoatalk.chat;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import cocoatalk.oracle.DBCon;

public class Room {

  Connection conn = null;
  PreparedStatement pstmt = null;
  ResultSet rs = null;

  // room search
  public Room() {

  }

  public void roomSearch() {
    try {
      conn = DBCon.getConnection();
      String sql = String.format("");
      pstmt = conn.prepareStatement(sql);
      rs = pstmt.executeQuery();
    } catch (Exception e) {
      e.printStackTrace();
    }
    for (;;) {
      if (1 == 1) { // 채팅방이 존재하면

      } else { // 존재하지 않으면
        roomCreate();
      }
    }
  }

  // room create
  public void roomCreate() {
    try {
      conn = DBCon.getConnection();
      StringBuilder sql = new StringBuilder();
      sql.append("insert into room_mem values ((                          ");
      sql.append("     select /*+index_desc(m room_idx)*/              ");
      sql.append("            room+1                                   ");
      sql.append("       from room_mem m                               ");
      sql.append("      where (room between 1 and 1000) AND rownum = 1)");
      sql.append("            ,?,?)                                    ");
      pstmt.setString(1, null); // id
      pstmt.setString(2, null); // name
      pstmt = conn.prepareStatement(sql.toString());
      rs = pstmt.executeQuery();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

}