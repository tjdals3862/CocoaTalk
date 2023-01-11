package cocoatalk.chat;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

import cocoatalk.oracle.DBCon;
import cocoatalk.oracle.DbFreeCon;

public class Room {
  DbFreeCon dfc = new DbFreeCon();

  public int roomSearch(String myID, String frID) { // 1:1 채팅방 찾기 (1000~1999번)
    int i = 0;
    try {
      DBCon dbcon = new DBCon();
      Connection conn = dbcon.getConnection();
      StringBuilder sql = new StringBuilder();
      sql.append("select room                        "); // param 1 : frID, param 2 : room
      sql.append("  from room_mem                    ");
      sql.append(" where room in (select room        ");
      sql.append("                  from room_mem    ");
      sql.append("                 where id = ?)     ");
      sql.append("   and ID = ?                      ");
      sql.append("   and (room between 1000 and 1999)");
      sql.append(" group by room                     ");
      PreparedStatement pstmt = conn.prepareStatement(sql.toString());
      pstmt.setString(1, myID);
      pstmt.setString(2, frID);
      ResultSet rs = pstmt.executeQuery();

      // rs.next();
      if (rs.next()) {
        i = rs.getInt("room");
        dfc.freeConnection(conn, pstmt, rs);
      } else {
        roomCreate(myID);
        roomCreate(frID);
      }

    } catch (Exception e) {
      e.printStackTrace();
    }

    return i;
  }

  // public void roomSearch(Integer room) {
  // try {
  // Connection conn = DBCon.getConnection();
  // StringBuilder sql = new StringBuilder();
  // sql.append("");
  // } catch (Exception e) {
  // e.printStackTrace();
  // }
  // }

  // 1:1 채팅방 개설
  public void roomCreate(String ID) { // 채팅방 개설
    try {
      DBCon dbcon = new DBCon();
      Connection conn = dbcon.getConnection();
      StringBuilder sql = new StringBuilder();
      sql.append("insert into room_mem values ((                          ");
      sql.append("     select /*+index_desc(m room_idx)*/                 ");
      sql.append("            room+1                                      ");
      sql.append("       from room_mem m                                  ");
      sql.append("      where (room between 1000 and 1999) AND rownum = 1)");
      sql.append("            ,?,?)                                       ");
      PreparedStatement pstmt = conn.prepareStatement(sql.toString());
      pstmt.setString(1, ID); // id
      pstmt.setString(2, getName(ID)); // name
      ResultSet rs = pstmt.executeQuery();

      dfc.freeConnection(conn, pstmt, rs);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  String name = null;

  public String getName(String ID) {
    try {
      DBCon dbcon = new DBCon();
      Connection conn = dbcon.getConnection();
      StringBuilder sql = new StringBuilder();
      sql.append("select NAME from MEMBER where ID = ?");
      PreparedStatement pstmt = conn.prepareStatement(sql.toString());
      pstmt = conn.prepareStatement(sql.toString());
      pstmt.setString(1, ID);
      ResultSet rs = pstmt.executeQuery();
      rs.next();
      name = rs.getString("NAME");

      dfc.freeConnection(conn, pstmt, rs);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return name;
  }

  public int getRoom(String myID, String frID) { // 1:1 채팅방 번호 조회
    int room = 0;
    try {
      DBCon dbcon = new DBCon();
      Connection conn = dbcon.getConnection();
      StringBuilder sql = new StringBuilder();
      sql.append("select room                       ");
      sql.append("  from room_mem                   ");
      sql.append(" where room between 1000 and 1999 ");
      sql.append("   and id = ?                     ");
      sql.append("   and room in (select room       ");
      sql.append("  from room_mem                   ");
      sql.append(" where id = ?     )               ");
      PreparedStatement pstmt = conn.prepareStatement(sql.toString());
      pstmt.setString(1, myID);
      pstmt.setString(2, frID);
      ResultSet rs = pstmt.executeQuery();
      rs.next();
      room = rs.getInt("ROOM");

      dfc.freeConnection(conn, pstmt, rs);
    } catch (Exception e) {
      e.printStackTrace();
    }

    return room;
  }

  public static void main(String[] args) {
    Room r = new Room();
    // System.out.println(r.getName("852"));

    // String[] id = { "1", "2", "3" };
    // for (int i = 0; i < id.length; i++)
    // r.roomCreate("test", id[i]);

    System.out.println(r.roomSearch("123", "이승현"));
  }
}