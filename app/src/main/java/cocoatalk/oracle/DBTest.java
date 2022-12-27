package cocoatalk.oracle;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class DBTest {

  public static void main(String args[]) {
    Connection conn = null;
    PreparedStatement pstm = null;
    ResultSet rs = null;

    // Scanner scan = new Scanner(System.in);

    // System.out.println("사원이름 : ");
    // name = scan.nextLine();
    // System.out.println("거리 : ");
    // street = scan.nextLine();
    // System.out.println("도시 : ");
    // city = scan.nextLine();

    String name = "test";
    String id = "test";
    String password = "1234";
    String email = "abcd";
    int birth = 20221226;
    int phone = 01012344567;
    String nickname = "tiger";

    try {
      String query = "insert into member values ('" + name + "', '" + id
          + "', '" + password + "', '" + email + "', '" + birth + "', '" + phone + "', '" + nickname + "')";

      conn = DBCon.getConnection();
      pstm = conn.prepareStatement(query);
      int r = pstm.executeUpdate();

      // 본문에서는 insert 문을 사용했지만 select 문을 사용하려면
      // rs = pstm.executeQuery(); 를 사용하면 됩니다.
      // executeUpdate문은 insert, delete, update문을 사용할 수 있으며
      // executeQuery문은 select 문에서만 사용됩니다.

      System.out.println("============================================");
      // System.out.println("추가되었습니다. -> ('" + name + "', '" + street + "', '" + city
      // + "')");

    } catch (SQLException sqle) {
      System.out.println("예외 발생");
      sqle.printStackTrace();
    } finally {
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
      } catch (Exception e) {
        throw new RuntimeException(e.getMessage());
      }
    }
  }

}
