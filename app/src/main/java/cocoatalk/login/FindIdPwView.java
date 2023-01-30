package cocoatalk.login;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.ImageIcon;
import cocoatalk.oracle.DBCon;
import cocoatalk.oracle.DbFunction;
import java.awt.Image;
import java.awt.Toolkit;

public class FindIdPwView extends JFrame {
   String imgPath = "app\\src\\main\\java\\cocoatalk\\images\\";
  ImageIcon imageIcon = new ImageIcon(imgPath + "profileback.png");
  Toolkit toolkit = Toolkit.getDefaultToolkit();// 로고삽입
  Image img = toolkit.getImage(imgPath + "logo.png");// 로고삽입
   String id_data = null;
   String mid = null;
   JDialog jdl_infomiss = new JDialog();// 아이디/비밀번호찾기 프레임
   JPanel jp_idmiss = new JPanel(); // 1.첫 화면 도화지
   JPanel jp_idfind = new JPanel(); // 2.아이디 찾기 성공했을 시 도화지
   JPanel jp_pwmiss = new JPanel(); // 3.비밀번호찾기 탭 눌렀을 때 도화지
   JPanel jp_pwfind = new JPanel(); // 4.비밀번호찾기 성공했을 시 도화지
   JPanel jp_north = new JPanel();
   JLabel jlb_idfind = new JLabel();// 2번 도화지에 붙이는 JLabel
   JLabel jlb_name = new JLabel("이름"); // 1번 도화지에 붙는 JLabel
   JLabel jlb_phone = new JLabel("전화번호"); // 1번 도화지에 붙는 JLabel
   JLabel jlb_id = new JLabel("아이디"); // 3번 도화지에 붙는 JLabel
   JLabel jlb_name2 = new JLabel("이름"); // 3번 도화지에 붙는 JLabel
   JLabel jlb_pwupdate = new JLabel("비밀번호");
   JLabel jlb_pwupdate2 = new JLabel("비밀번호확인");
   JButton jbtn_pwupdate = new JButton("비밀번호 변경");// 3번 도화지에 붙이는 비밀번호 찾기 버튼 (성공시 4번 도화지로 전환)
   JButton jbtn_pwsearch = new JButton("비밀번호 찾기");// 3번 도화지에 붙이는 비밀번호 찾기 버튼 (성공시 4번 도화지로 전환)
   JButton jbtn_idsearch = new JButton("아이디찾기");// 아이디 찾기 검색버튼 (성공지 2번도화지로 전환)
   JButton jbtn_idmiss = new JButton("아이디찾기");// 아이디 찾기 상단메뉴(JPanel-1번도화지 전환용)
   JButton jbtn_pwmiss = new JButton("비밀번호찾기"); // 비밀번호 찾기 상단메뉴(JPanel-3번도화지 전환용)
   // 1번도화지에 붙는 JTextField , 이름/전화번호 입력
   JPasswordField jtf_pwupdate2 = new JPasswordField() {
      @Override
      public void setBorder(Border border) {

      }
   };
   JPasswordField jtf_pwupdate = new JPasswordField() {
      @Override
      public void setBorder(Border border) {

      }
   };
   JTextField jtf_name = new JTextField() {
      @Override
      public void setBorder(Border border) {

      }
   };
   JTextField jtf_phone = new JTextField() {
      @Override
      public void setBorder(Border border) {

      }
   };
   JTextField jtf_name2 = new JTextField() {
      @Override
      public void setBorder(Border border) {

      }
   };
   JTextField jtf_id = new JTextField() {
      @Override
      public void setBorder(Border border) {

      }
   };
   FindIdPwView infomiss = null;
   Connection conn = null;
   PreparedStatement pstm1 = null;
   PreparedStatement pstm2 = null;
   ResultSet rs1 = null;
   ResultSet rs2 = null;

   Font f = new Font("맑은 고딕", Font.PLAIN, 13);

   public void initDisplay() {

      // 상단 메뉴바의 도화지에 버튼 두 개 붙이기+버튼정의
      jdl_infomiss.setIconImage(img);
      jbtn_pwmiss.setBorderPainted(false);
      jbtn_pwmiss.setBackground(Color.white);
      jbtn_pwmiss.setFont(f);
      jbtn_idmiss.setBorderPainted(false);
      jbtn_idmiss.setBackground(new Color(155, 138, 124));
      jbtn_idmiss.setFont(f);
      jp_north.add(jbtn_idmiss);
      jp_north.add(jbtn_pwmiss);
      this.setIconImage(img);// 로고삽입
      
      // 상단 메뉴바를 위한 도화지 붙이기
      jp_north.setLayout(new GridLayout());
      jdl_infomiss.add("North", jp_north);

      // 4번도화지에 컨텐츠 정의
      jlb_pwupdate.setBounds(40, 78, 80, 40);
      jlb_pwupdate.setFont(f);
      jlb_pwupdate2.setBounds(15, 128, 80, 40);
      jlb_pwupdate2.setFont(f);
      jtf_pwupdate.setBounds(100, 78, 250, 40);
      jtf_pwupdate2.setBounds(100, 128, 250, 40);
      jbtn_pwupdate.setBorderPainted(false);
      jbtn_pwupdate.setForeground(Color.WHITE);
      jbtn_pwupdate.setBackground(new Color(64, 64, 64));
      jbtn_pwupdate.setBounds(100, 193, 180, 40);
      jbtn_pwupdate.setFont(f);
      jp_pwfind.add(jlb_pwupdate);
      jp_pwfind.add(jlb_pwupdate2);
      jp_pwfind.add(jtf_pwupdate);
      jp_pwfind.add(jtf_pwupdate2);
      jp_pwfind.add(jbtn_pwupdate);
      jp_pwfind.setLayout(new BorderLayout());

      // 3번 도화지에 컨텐츠 정의
      jbtn_pwsearch.setBorderPainted(false);
      jbtn_pwsearch.setForeground(Color.WHITE);
      jbtn_pwsearch.setBackground(new Color(64, 64, 64));
      jbtn_pwsearch.setBounds(100, 193, 180, 40);
      jbtn_pwsearch.setFont(f);
      jp_pwmiss.add(jbtn_pwsearch);
      jlb_id.setBounds(45, 128, 80, 40);
      jlb_id.setFont(f);
      jlb_name2.setBounds(52, 78, 80, 40);
      jlb_name2.setFont(f);
      jp_pwmiss.add(jlb_name2);
      jp_pwmiss.add(jlb_id);
      jtf_name2.setBounds(100, 78, 250, 40);
      jtf_id.setBounds(100, 128, 250, 40);
      jp_pwmiss.add(jtf_id);
      jp_pwmiss.add(jtf_name2);
      jp_pwmiss.setLayout(new BorderLayout());

      jlb_idfind.setBounds(95, 90, 300, 35);
      jlb_idfind.setFont(new Font("맑은 고딕", Font.BOLD, 13));
      // jp_idfind.add(jbtn_gotopwmiss);
      jp_idfind.add(jlb_idfind);
      jp_idfind.setLayout(new BorderLayout());

      // 1번 도화지에 컨텐츠 정의
      jlb_name.setBounds(50, 50, 40, 40);
      jlb_name.setFont(f);
      jlb_phone.setBounds(30, 100, 80, 40);
      jlb_phone.setFont(f);
      jp_idmiss.add(jlb_name);
      jp_idmiss.add(jlb_phone);
      jtf_name.setBounds(100, 50, 250, 40);
      jtf_phone.setBounds(100, 100, 250, 40);
      jp_idmiss.add(jtf_phone);
      jp_idmiss.add(jtf_name);
      jbtn_idsearch.setBorderPainted(false);
      jbtn_idsearch.setForeground(Color.WHITE);
      jbtn_idsearch.setBackground(new Color(64, 64, 64));
      jbtn_idsearch.setBounds(100, 165, 180, 40);
      jbtn_idsearch.setFont(f);
      jp_idmiss.add(jbtn_idsearch);
      jp_idmiss.setLayout(new BorderLayout());

      // JDialog, 메인 프레임 정의
      jp_pwfind.setSize(400, 300);
      jp_pwmiss.setSize(400, 300);
      jp_idfind.setSize(400, 300);
      jp_idmiss.setSize(400, 300);
      jdl_infomiss.add("Center", jp_pwfind);// 4번도화지 orange
      jdl_infomiss.add("Center", jp_pwmiss);// 3번도화지 orange
      jdl_infomiss.add("Center", jp_idfind);// 2번도화지 gray
      jdl_infomiss.add("Center", jp_idmiss);// 1번도화지
      jp_pwfind.setVisible(false);
      jp_pwmiss.setVisible(false);
      jp_idfind.setVisible(false);
      jp_idmiss.setVisible(true);
      jdl_infomiss.setTitle("아이디/비밀번호 찾기");
      jdl_infomiss.setSize(400, 300);
      jdl_infomiss.setVisible(true);
      infomiss = new FindIdPwView();

      // 상단 메뉴 "아이디찾기"버튼
      jbtn_idmiss.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {

            jp_pwfind.setVisible(false);
            jp_pwmiss.setVisible(false);
            jp_idfind.setVisible(false);
            jp_idmiss.setVisible(true);
            jbtn_pwmiss.setBackground(Color.white);
            jbtn_idmiss.setBackground(new Color(155, 138, 124));
         }
      });
      // 상단메뉴 "비밀번호 찾기" 버튼
      jbtn_pwmiss.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
            jp_idmiss.setVisible(false);
            jp_idfind.setVisible(false);
            jp_pwfind.setVisible(false);
            jp_pwmiss.setVisible(true);
            jbtn_pwmiss.setBackground(new Color(155, 138, 124));// 비밀번호 찾기 버튼 색깔
            jbtn_idmiss.setBackground(Color.white);
         }
      });
      jbtn_idsearch.addActionListener(new ActionListener() {// 아이디 찾기 눌렀을때 나오는 화면의 밑에있는 아이디찾기 버튼 눌렀을때 이벤트 처리
         public void actionPerformed(ActionEvent e) {

            String name_data = jtf_name.getText(); // 사용자가 name textfield에 입력한 값을 name_data에 저장
            String phone_data = jtf_phone.getText(); // 사용자가 phone textfield에 입력한 값을 phone_data에 저장

            String query1 = String.format("SELECT id FROM member WHERE name = '%s' AND phone = '%s'", name_data, // 사용자가
                                                                                                                 // 입력한
                                                                                                                 // name과
                                                                                                                 // phone의
                                                                                                                 // 값과
                                                                                                                 // 같은
                                                                                                                 // 데이터를
                                                                                                                 // 가진
                                                                                                                 // id
                                                                                                                 // 값을
                                                                                                                 // member
                                                                                                                 // 테이블에서
                                                                                                                 // 가져오는
                                                                                                                 // 쿼리문
                                                                                                                 // 1
                  phone_data);
            String query2 = String.format("SELECT name FROM member WHERE name = '%s' AND phone = '%s'", name_data, // 사용자가
                                                                                                                   // 입력한
                                                                                                                   // name과
                                                                                                                   // phone의
                                                                                                                   // 값과
                                                                                                                   // 같은
                                                                                                                   // 데이터를
                                                                                                                   // 가진
                                                                                                                   // name
                                                                                                                   // 값을
                                                                                                                   // member
                                                                                                                   // 테이블에서
                                                                                                                   // 가져오는
                                                                                                                   // 쿼리문
                                                                                                                   // 2
                  phone_data);
            DBCon db = new DBCon();

            try {

               conn = db.getConnection();
               pstm1 = conn.prepareStatement(query1);
               pstm2 = conn.prepareStatement(query2);
               rs1 = pstm1.executeQuery();
               rs2 = pstm2.executeQuery();

               String id = "";
               String name = "";

               while (rs1.next() && rs2.next()) { // 쿼리문에서 가져온 id와 name값을 각각 id와 name 변수에 저장
                  id = rs1.getString("id");
                  name = rs2.getString("name");
               }

               if (!name.equals("") && name.equals(name_data)) {// name의 값이 null이아니고, 입력한 name_data와 같을때 그에 해당하는 id값을
                                                                // 팝업으로 출력
                  JOptionPane.showMessageDialog(null, "회원님의 아이디는 " + id + " 입니다.", "아이디 찾기", 1);

               } else {// 그 외에는 찾기 실패
                  JOptionPane.showMessageDialog(null, "일치하는 정보가 없습니다", "아이디 찾기 실패", 1);
                  //System.out.println(name);
               }

            } catch (SQLException ex) {
               JOptionPane.showMessageDialog(null, "일치하는 정보가 없습니다", "아이디 찾기 실패", 1);
               //System.out.println("SQLException" + ex);

            } finally {
               try {
                  if (rs1 != null) {
                     rs1.close();
                  }
                  if (rs2 != null) {
                     rs2.close();
                  }
                  if (pstm1 != null) {
                     pstm1.close();
                  }
                  if (pstm2 != null) {
                     pstm2.close();
                  }
                  if (conn != null) {
                     conn.close();
                  }
               } catch (Exception ie) {
                  throw new RuntimeException(ie.getMessage());
               }

            }
         }
      });
      // 3번도화지 "비밀번호 찾기"버튼
      jbtn_pwsearch.addActionListener(new ActionListener() {// 비밀번호 찾기 상단버튼 눌렀을때 나오는 화면 하단에 있는 비밀번호 찾기를 눌렀을때의 이벤트처리
         public void actionPerformed(ActionEvent e) {

            id_data = jtf_id.getText(); // 사용자가 id textfield에 입력한 값을 전역변수 id_data에 저장
            String name_data = jtf_name2.getText(); // 사용자가 name textfield에 입력한 값을 name_data에 저장

            String query = String.format("SELECT id FROM member WHERE id = '%s' AND name = '%s'", id_data,
                  name_data);

            DBCon db = new DBCon();

            try {

               conn = db.getConnection();
               pstm1 = conn.prepareStatement(query);

               rs1 = pstm1.executeQuery();
               String id = "";

               while (rs1.next()) {
                  id = rs1.getString("id");
               }

               if (!id.equals("") && id.equals(id_data)) {
                  jp_pwfind.setVisible(true);
               } else {
                  JOptionPane.showMessageDialog(null, "일치하는 정보가 없습니다", "비밀번호 찾기 실패", 1);
               }

            } catch (SQLException ex) {
               JOptionPane.showMessageDialog(null, "일치하는 정보가 없습니다", "비밀번호 찾기 실패", 1);
               //System.out.println("SQLException" + ex);

            } finally {
               try {
                  if (rs1 != null) {
                     rs1.close();
                  }
                  if (pstm1 != null) {
                     pstm1.close();
                  }
                  if (conn != null) {
                     conn.close();
                  }
               } catch (Exception ie) {
                  throw new RuntimeException(ie.getMessage());
               }

            }
         }
      });
      // 4번도화지 "비밀번호 변경"버튼
      jbtn_pwupdate.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
            DBCon db = new DBCon();
            DbFunction dbf = new DbFunction();

            try {
               String pw = jtf_pwupdate.getText();
               String pw2 = jtf_pwupdate2.getText();

               String query = String.format("UPDATE member SET password='%s' WHERE id='%s'", pw, id_data);
               if (pw.equals(pw2)) {
                  if (pw.equals("")) {
                     JOptionPane.showMessageDialog(null, "공백 확인 하세요",
                           "ERROR_MESSAGE", JOptionPane.ERROR_MESSAGE);
                  } else {
                     dbf.insert(query);
                     JOptionPane.showMessageDialog(null, "비밀번호가 변경되었습니다",
                           "CHANGE_SUCCESS", JOptionPane.ERROR_MESSAGE);
                     jdl_infomiss.dispose();

                  }
               } else {
                  JOptionPane.showMessageDialog(null, "비밀번호를 확인하세요",
                        "ERROR_MESSAGE", JOptionPane.ERROR_MESSAGE);
               }

            } catch (Exception ae) {

            } finally {
               try {
                  if (rs1 != null) {
                     rs1.close();
                  }
                  if (pstm1 != null) {
                     pstm1.close();
                  }
                  if (conn != null) {
                     conn.close();
                  }
               } catch (Exception ie) {
                  throw new RuntimeException(ie.getMessage());
               }

            }
         }
      });
   }
}