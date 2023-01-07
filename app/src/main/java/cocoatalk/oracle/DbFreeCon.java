package cocoatalk.oracle;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class DbFreeCon {

    // 사용한 자원 반납하기 - INSERT, UPDATE, DELETE
    public void freeConnection(Connection con, Statement stmt) {
        if (stmt != null) {
            try {
                stmt.close();
            } catch (Exception e) {

            }
        }
        if (con != null) {
            try {
                con.close();
            } catch (Exception e) {

            }
        }
    }// end of freeConnection
    public void freeConnection(Connection con, PreparedStatement pstmt) {
        if (pstmt != null) {
            try {
                pstmt.close();
            } catch (Exception e) {

            }
        }
        if (con != null) {
            try {
                con.close();
            } catch (Exception e) {

            }
        }
    }// end of freeConnection

    // 사용한 자원 반납하기 - SELECT
    public void freeConnection(Connection con, Statement stmt, ResultSet rs) {
        if (rs != null) {
            try {
                rs.close();
            } catch (Exception e) {
                // TODO: handle exception
            }
        }
        if (stmt != null) {
            try {
                stmt.close();
            } catch (Exception e) {
                // TODO: handle exception
            }
        }
        if (con != null) {
            try {
                con.close();
            } catch (Exception e) {
                // TODO: handle exception
            }
        }
    }// end of freeConnection
    public void freeConnection(Connection con, PreparedStatement pstmt, ResultSet rs) {
        if (rs != null) {
            try {
                rs.close();
            } catch (Exception e) {
                // TODO: handle exception
            }
        }
        if (pstmt != null) {
            try {
                pstmt.close();
            } catch (Exception e) {
                // TODO: handle exception
            }
        }
        if (con != null) {
            try {
                con.close();
            } catch (Exception e) {
                // TODO: handle exception
            }
        }
    }// end of freeConnection
}
