package cocoatalk.login;

import java.awt.Color;
import java.awt.Font;
import java.awt.*;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import cocoatalk.oracle.DBCon;

public class LoginForm extends JFrame implements ActionListener {
  // 선언부
  CocoaVO cVO = new CocoaVO();
  Register register = new Register(this);
  String imgPath = "D:\\TEMP\\";
  ImageIcon imageIcon = new ImageIcon(imgPath + "login.jpg");
  Toolkit toolkit = Toolkit.getDefaultToolkit();// 로고삽입
  Image img = toolkit.getImage(imgPath + "logo.png");// 로고삽입

  JLabel jlb_id = new JLabel("아이디");
  JTextField jtf_id = new JTextField();
  JLabel jlb_pw = new JLabel("비밀번호");
  JPasswordField jpf_pw = new JPasswordField();
  Font font = new Font("굴림체", Font.BOLD, 13);
  JButton jbtn_join = new JButton(new ImageIcon(imgPath + "confirm.png"));
  JButton jbtn_login = new JButton(new ImageIcon(imgPath + "loginbutton.jpg"));
  JLabel jlb_find = new JLabel();
  Font f_join = new Font("맑은 고딕", Font.PLAIN, 12);
  Connection conn = null;
  PreparedStatement pstm = null;
  ResultSet rs = null;

  // 생성자
  LoginForm() {
  }

  // 내부클래스로 배경 이미지 처리
  class MyPanel extends JPanel {
    public void paintComponent(Graphics g) {
      g.drawImage(imageIcon.getImage(), 0, 0, null);
      setOpaque(false);
      super.paintComponent(g);
    } // end of MyPanel - 사용자 패널 정의 - LoginForm$1.class
  }

  // 화면그리기
  public void initDisplay() {
    this.setIconImage(img);// 로고삽입
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.setContentPane(new MyPanel());
    this.setLayout(null);
    jlb_id.setBounds(70, 360, 80, 40);
    jlb_id.setFont(font);
    jlb_id.setForeground(Color.white);
    jtf_id.setBounds(135, 360, 185, 40);
    this.add(jlb_id);
    this.add(jtf_id);
    jlb_pw.setBounds(70, 400, 80, 40);
    jlb_pw.setFont(font);
    jbtn_join.setBorderPainted(false);// 버튼 테두리 변경
    jbtn_join.setContentAreaFilled(false);
    jbtn_login.setBorderPainted(false);
    jbtn_login.setContentAreaFilled(false);
    jlb_pw.setForeground(Color.white);
    jpf_pw.setBounds(135, 400, 185, 40);
    this.add(jlb_pw);
    this.add(jpf_pw);
    jbtn_login.setBounds(70, 470, 120, 40);
    this.add(jbtn_login);
    jbtn_join.setBounds(200, 470, 120, 40);
    this.add(jbtn_join);

    this.setTitle("COCOA TALK");
    this.setLocation(500, 100);
    this.setSize(410, 650);
    this.setVisible(true);

    jbtn_join.addActionListener(this);
    jbtn_login.addActionListener(this);

    this.add(jlb_find); //아이디/패스워드 찾기 추가
    jlb_find.setText("아이디/비밀번호 찾기");
    jlb_find.setForeground(Color.WHITE);
    jlb_find.setFont(f_join);
    jlb_find.setBounds(140, 520, 200, 20);
    jlb_find.addMouseListener(new MouseAdapter() {
      @Override
      public void mousePressed(MouseEvent e) {
        // TODO Auto-generated method stub
        super.mousePressed(e);
        FindIdPwView fipv = new FindIdPwView();
        fipv.initDisplay();
      }

    });
  }

  // 메인메소드
  public static void main(String[] args) {

    First first = new First();
    first.firstDisplay();

    try {
      Thread.sleep(500);
      first.dispose();
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    LoginForm loginForm = new LoginForm();
    loginForm.initDisplay();

  }

  @Override
  public void actionPerformed(ActionEvent e) {
    Object obj = e.getSource();

    if (obj == jbtn_login) {
      String id_data = jtf_id.getText();
      String pw_data = jpf_pw.getText();

      String query = String.format("SELECT password FROM member WHERE id = '%s' AND password ='%s'", id_data, pw_data);
      DBCon db = new DBCon();

      try {

        conn = db.getConnection();
        pstm = conn.prepareStatement(query);
        rs = pstm.executeQuery();

        String password = "";

        while (rs.next()) {
          password = rs.getString("password");
        }

        if (!password.equals("") && password.equals(pw_data)) {
          MainForm MainFormcopy = new MainForm(cVO);
          cVO.setId(id_data);
          MainFormcopy.initDisplay();
          this.dispose();
        } else {
          JOptionPane.showMessageDialog(this, "Login Failed", "로그인 실패", 1);
        }

      } catch (SQLException ex) {

        JOptionPane.showMessageDialog(this, "Login Failed", "로그인 실패", 1);
        System.out.println("SQLException" + ex);

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
        } catch (Exception ie) {
          throw new RuntimeException(ie.getMessage());
        }
      }

    } else if (obj == jbtn_join) {
      register.set(true);
    }
  }
}