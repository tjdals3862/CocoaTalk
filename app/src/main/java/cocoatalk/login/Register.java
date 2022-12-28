package cocoatalk.login;

import java.awt.Color;
import java.awt.Font;
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
import javax.swing.JTextField;

import cocoatalk.oracle.DBCon;
import cocoatalk.oracle.DbFunction;

public class Register extends JFrame implements ActionListener {
  // 선언부
  LoginForm loginForm = null;
  DBCon db = null;
  Connection conn = null;
  PreparedStatement pstm = null;
  ResultSet rs = null;
  DbFunction dbf = null;

  String imgPath = "D:\\TEMP\\";
  ImageIcon imageIcon = new ImageIcon(imgPath + "wallPaper.jpg");

  JLabel jlb_name = new JLabel("이름");
  JLabel jlb_id = new JLabel("아이디");
  JLabel jlb_pw = new JLabel("비밀번호");
  JLabel jlb_pw2 = new JLabel("비밀번호확인");
  JLabel jlb_birth = new JLabel("생년월일");
  JLabel jlb_phone = new JLabel("전화번호");
  JLabel jlb_nickName = new JLabel("닉네임");
  JLabel jlb_idAvble = new JLabel("사용가능한 아이디 입니다.");
  JLabel jlb_idNotAvble = new JLabel("중복된 아이디 입니다.");
  JLabel jlb_title = new JLabel("회원가입");

  JTextField jtf_name = new JTextField(); // 이름
  JTextField jtf_id = new JTextField(); // 아이디
  JTextField jtf_pw = new JTextField(); // 비밀번호
  JTextField jtf_pw2 = new JTextField(); // 비밀번호 확인
  JTextField jtf_birth = new JTextField(); // 생년월일
  JTextField jtf_phone = new JTextField(); // 폰번호
  JTextField jtf_nickName = new JTextField(); // 닉네임

  Font font = new Font("굴림체", Font.BOLD, 13);
  Font f_join = new Font("맑은 고딕", Font.PLAIN, 25);
  Font f_label = new Font("맑은 고딕", Font.PLAIN, 12);

  ImageIcon image_join = new ImageIcon(imgPath + "bt_join.png"); // 로그인 버튼 이미지
  ImageIcon image_cancel = new ImageIcon(imgPath + "bt_join.png");
  JButton jbtn_idconfirm = new JButton("중복검사"); // 로그인 버튼
  JButton jbtn_join = new JButton("회원가입");// 회원가입 버튼
  JButton jbtn_cancel = new JButton("취소");// 취소 버튼

  // 생성자
  Register(LoginForm loginForm) {
    this.loginForm = loginForm;
    initDisplay();
  }

  // 내부클래스로 배경 이미지 처리
  class MyPanel extends JPanel {
    public void paintComponent(Graphics g) {
      g.drawImage(imageIcon.getImage(), 0, 0, null);
      setOpaque(false);
      super.paintComponent(g);
    } // end of MyPanel - 사용자 패널 정의 - LoginForm$1.class
  }

  public void set(boolean isView) {
    this.setVisible(isView);
  }

  // 화면그리기
  public void initDisplay() {

    jbtn_idconfirm.addActionListener(this);
    jbtn_join.addActionListener(this);
    jbtn_cancel.addActionListener(this);

    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.setContentPane(new MyPanel());
    this.setLayout(null);

    jbtn_idconfirm.setBorderPainted(false); // 아이디 중복검사 버튼 외곽 라인 없애기
    jbtn_idconfirm.setForeground(Color.WHITE); // 아이디 중복검사 버튼 텍스트 색깔 (흰색)
    jbtn_idconfirm.setBackground(new Color(64, 64, 64)); // 아이디 중복검사 버튼 색깔 넣기 (갈색)
    jbtn_idconfirm.setBounds(285, 145, 90, 35);
    this.add(jbtn_idconfirm);

    jbtn_join.setBorderPainted(false); // 아이디 중복검사 버튼 외곽 라인 없애기
    jbtn_join.setForeground(Color.WHITE); // 아이디 중복검사 버튼 텍스트 색깔 (흰색)
    jbtn_join.setBackground(new Color(64, 64, 64)); // 아이디 중복검사 버튼 색깔 넣기 (갈색)
    jbtn_join.setBounds(42, 450, 110, 35);
    this.add(jbtn_join);

    jbtn_cancel.setBorderPainted(false); // 아이디 중복검사 버튼 외곽 라인 없애기
    jbtn_cancel.setForeground(Color.WHITE); // 아이디 중복검사 버튼 텍스트 색깔 (흰색)
    jbtn_cancel.setBackground(new Color(64, 64, 64)); // 아이디 중복검사 버튼 색깔 넣기 (갈색)
    jbtn_cancel.setBounds(165, 450, 110, 35);
    this.add(jbtn_cancel);

    jtf_name.setBounds(95, 100, 180, 35);
    jlb_name.setBounds(57, 100, 200, 35);
    jlb_name.setFont(f_label);
    jlb_name.setForeground(Color.white);
    this.add(jtf_name);
    this.add(jlb_name);

    jtf_id.setBounds(95, 145, 180, 35);
    jlb_id.setBounds(45, 145, 200, 35);
    jlb_id.setFont(f_label);
    jlb_id.setForeground(Color.white);
    this.add(jtf_id);
    this.add(jlb_id);

    jtf_pw.setBounds(95, 210, 180, 35);
    jlb_pw.setBounds(35, 210, 200, 35);
    jlb_pw.setFont(f_label);
    jlb_pw.setForeground(Color.white);
    this.add(jtf_pw);// 비밀번호
    this.add(jlb_pw);

    jtf_pw2.setBounds(95, 255, 180, 35);
    jlb_pw2.setBounds(10, 255, 200, 35);
    jlb_pw2.setFont(f_label);
    jlb_pw2.setForeground(Color.white);
    this.add(jtf_pw2);// 비밀번호확인
    this.add(jlb_pw2);

    jtf_birth.setBounds(95, 300, 180, 35);
    jlb_birth.setBounds(35, 300, 200, 35);
    jlb_birth.setFont(f_label);
    jlb_birth.setForeground(Color.white);
    this.add(jtf_birth);// 생년월일
    this.add(jlb_birth);

    jtf_phone.setBounds(95, 345, 180, 35);
    jlb_phone.setBounds(35, 345, 200, 35);
    jlb_phone.setFont(f_label);
    jlb_phone.setForeground(Color.white);
    this.add(jtf_phone);// 전화번호
    this.add(jlb_phone);

    jtf_nickName.setBounds(95, 390, 180, 35);
    jlb_nickName.setBounds(45, 390, 200, 35);
    jlb_nickName.setForeground(Color.white);
    this.add(jtf_nickName);// 닉네임
    this.add(jlb_nickName);

    jlb_title.setFont(f_join);// 회원가입 라벨 붙이기
    jlb_title.setBounds(20, 30, 125, 45);
    this.add(jlb_title);

    this.setTitle("회원가입");
    this.setLocation(500, 100);
    this.setSize(410, 650);
    this.setVisible(false);
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    Object obj = e.getSource();

    if (obj == jbtn_idconfirm) {

    } else if (obj == jbtn_join) {

      db = new DBCon();
      dbf = new DbFunction();

      try {
        String name = jtf_name.getText();
        String id = jtf_id.getText();
        String pw = jtf_pw.getText();

        int birth = Integer.parseInt(jtf_birth.getText());
        int phone = Integer.parseInt(jtf_phone.getText());
        String nickName = jtf_nickName.getText();
        String query = "insert into member values ('" + name + "', '" + id
            + "', '" + pw + "', '" + birth + "', '" + phone + "', '" + nickName + "')";
        dbf.insert(query);

      } catch (NumberFormatException ne) {
        JOptionPane.showMessageDialog(null, "생년월일, 전화번호는 숫자만 입력하세요",
            "ERROR_MESSAGE", JOptionPane.ERROR_MESSAGE);
      } catch (Exception se) {
        JOptionPane.showMessageDialog(null, "아이디 중복검사 필요",
            "ERROR_MESSAGE", JOptionPane.ERROR_MESSAGE);
      }

    } else if (obj == jbtn_cancel) {
      this.dispose();
    }
  }
}
