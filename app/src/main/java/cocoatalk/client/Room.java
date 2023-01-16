package cocoatalk.client;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import cocoatalk.oracle.DBCon;
import cocoatalk.oracle.DbFreeCon;
import cocoatalk.oracle.DbFunction;

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
        i = roomCreate(myID, frID);
      }

    } catch (Exception e) {
      e.printStackTrace();
    }

    return i;
  }

  public int roomCreate(String myID, String frID) { // 1:1 채팅방 개설
    int room = 0;
    try {
      DBCon dbcon = new DBCon();
      Connection conn = dbcon.getConnection();
      StringBuilder sql = new StringBuilder();
      sql.append("select /*+index_desc(m room_idx)*/             ");
      sql.append("       room+1                                  ");
      sql.append("  from room_mem m                              ");
      sql.append(" where (room between 1 and 1999) AND rownum = 1");
      PreparedStatement pstmt = conn.prepareStatement(sql.toString());
      ResultSet rs = pstmt.executeQuery();
      rs.next();
      room = rs.getInt("room+1");
      dfc.freeConnection(conn, pstmt, rs);

      DbFunction df = new DbFunction();
      String query = "insert into room_mem values (" + room + ", '" + myID + "', '" + getName(myID) + "')";
      df.insert(query);
      dfc.freeConnection(conn, pstmt, rs);

      DbFunction df2 = new DbFunction();
      String query2 = "insert into room_mem values (" + room + ", '" + frID + "', '" + getName(frID) + "')";
      df2.insert(query2);
      dfc.freeConnection(conn, pstmt, rs);

    } catch (Exception e) {
      e.printStackTrace();
    }
    return room;
  }

  public int roomCreate(String myID, Set<String> frIDs) { // 1:다 채팅방 개설 >> 구현해야함************
    int room = 0;
    try {
      DBCon dbcon = new DBCon();
      Connection conn = dbcon.getConnection();
      StringBuilder sql = new StringBuilder();
      sql.append("select /*+index_desc(m room_idx)*/                ");
      sql.append("       room+1                                     ");
      sql.append("  from room_mem m                                 ");
      sql.append(" where (room between 2000 and 3999) AND rownum = 1");
      PreparedStatement pstmt = conn.prepareStatement(sql.toString());
      ResultSet rs = pstmt.executeQuery();
      rs.next();
      room = rs.getInt("room+1");
      dfc.freeConnection(conn, pstmt, rs);

      DbFunction df = new DbFunction();
      String query = "insert into room_mem values (" + room + ", '" + myID + "', '" + getName(myID) + "')";
      df.insert(query);
      dfc.freeConnection(conn, pstmt, rs);

      Iterator<String> iter = frIDs.iterator();

      while (iter.hasNext()) {
        String frID = iter.next();
        df = new DbFunction();
        String query2 = "insert into room_mem values (" + room + ", '" + frID + "', '" + getName(frID) + "')";
        df.insert(query2);
      }
      dfc.freeConnection(conn, pstmt, rs);
    } catch (

    Exception e) {
      e.printStackTrace();
    }
    return room;
  }

  String name = null;

  public String getName(String ID) { // id에 해당하는 name, getter()
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
      sql.append(" where room between 1 and 1999    ");
      sql.append("   and id = ?                     ");
      sql.append("   and room in (select room       ");
      sql.append("                from room_mem     ");
      sql.append("                where id = ? )    ");

      // String[] frIDs = frID;
      // for (int i = 0; i < frID.length; i++) {
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

  public int searchRoomList(String myID, String frID) { // 채팅방 번호 조회
    int room = 0;
    try {
      DBCon dbcon = new DBCon();
      Connection conn = dbcon.getConnection();
      StringBuilder sql = new StringBuilder();
      sql.append("select room                       ");
      sql.append("  from room_mem                   ");
      sql.append(" where room id = ?                ");
      sql.append("   and room in (select room       ");
      sql.append("                from room_mem     ");
      sql.append("                where id = ? )    ");

      // String[] frIDs = frID;
      // for (int i = 0; i < frID.length; i++) {
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

  public List<String> getMember(String myID, int room) { // 방번호로 참여자 목록 반환~> List<String> frIDs : 서버에서 쓸거
    List<String> frIDs = new ArrayList();
    try {
      DBCon dbcon = new DBCon();
      Connection conn = dbcon.getConnection();
      StringBuilder sql = new StringBuilder();
      sql.append("select id from room_mem where room = ?");

      PreparedStatement pstmt = conn.prepareStatement(sql.toString());
      pstmt.setInt(1, room);
      ResultSet rs = pstmt.executeQuery();

      while (rs.next()) {
        frIDs.add(rs.getString("ID"));
      }
      dfc.freeConnection(conn, pstmt, rs);
    } catch (Exception e) {
      e.printStackTrace();
    }

    return frIDs;
  }

  // public static void main(String[] args) {
  // Room r = new Room();
  // // System.out.println(r.getName("852"));

  // // String[] id = { "1", "2", "3" };
  // // for (int i = 0; i < id.length; i++)
  // // r.roomCreate("test", id[i]);
  // System.out.println(r.frID);
  // System.out.println(r.roomSearch("ko", "lsh"));
  // }

}