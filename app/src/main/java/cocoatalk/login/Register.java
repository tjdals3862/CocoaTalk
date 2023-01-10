package cocoatalk.login;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import cocoatalk.oracle.DBCon;
import cocoatalk.oracle.DbFunction;

public class Register extends JFrame implements ActionListener, ItemListener {
  // 선언부
  LoginForm loginForm = null;
  DBCon db = null;
  Connection conn = null;
  PreparedStatement pstm = null;
  ResultSet rs = null;
  DbFunction dbf = null;
  boolean isTrue = false;
  boolean check = false;

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
  JLabel jlb_title = new JLabel();

  JTextField jtf_name = null; // 이름
  JTextField jtf_id = null; // 아이디
  JPasswordField jtf_pw = null; // 비밀번호
  JPasswordField jtf_pw2 = null; // 비밀번호 확인
  JTextField jtf_birth = null; // 생년월일
  JTextField jtf_phone = null; // 폰번호
  JTextField jtf_nickName = null; // 닉네임

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

    jtf_name = new JTextField(""); // 이름
    jtf_id = new JTextField(""); // 아이디
    jtf_pw = new JPasswordField(""); // 비밀번호
    jtf_pw2 = new JPasswordField(""); // 비밀번호 확인
    jtf_birth = new JTextField(""); // 생년월일
    jtf_phone = new JTextField(""); // 폰번호
    jtf_nickName = new JTextField(""); // 닉네임

    jbtn_idconfirm.addActionListener(this);
    jbtn_join.addActionListener(this);
    jbtn_cancel.addActionListener(this);

    this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
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
    jbtn_join.setBounds(80, 500, 110, 35);
    this.add(jbtn_join);

    jbtn_cancel.setBorderPainted(false); // 아이디 중복검사 버튼 외곽 라인 없애기
    jbtn_cancel.setForeground(Color.WHITE); // 아이디 중복검사 버튼 텍스트 색깔 (흰색)
    jbtn_cancel.setBackground(new Color(64, 64, 64)); // 아이디 중복검사 버튼 색깔 넣기 (갈색)
    jbtn_cancel.setBounds(205, 500, 110, 35);
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
    this.setResizable(false);

    JCheckBox cb = new JCheckBox(" 개인정보 수집을 동의하십니까? ");// 개인정보 활용 동의
    this.add(cb);// 개인정보동의 체크박스 추가
    cb.setBounds(90, 445, 218, 40);// 개인정보동의 체크박스 위치 및 크기 //웅식
    cb.addItemListener(this);
  }

  @Override
  public void itemStateChanged(ItemEvent e) {
    if (e.getStateChange() == ItemEvent.SELECTED) {
      check = true;
      System.out.println("체크");
    } else {
      check = false;
      System.out.println("체크해제");
    }
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    Object obj = e.getSource();

    if (obj == jbtn_idconfirm) {
      db = new DBCon();
      dbf = new DbFunction();
      String id = jtf_id.getText();
      String query = "select id from member where id = '" + id + "'";
      dbf.idchk(query);

    } else if (obj == jbtn_join) {

      db = new DBCon();
      dbf = new DbFunction();

      try {

        while (true) {
          String name = jtf_name.getText();
          String id = jtf_id.getText();
          String pw = jtf_pw.getText();
          String pw2 = jtf_pw2.getText();
          int birth = Integer.parseInt(jtf_birth.getText());
          int phone = Integer.parseInt(jtf_phone.getText());
          String nickName = jtf_nickName.getText();
          String query = "insert into member values ('" + name + "', '" + id
              + "', '" + pw + "', '" + birth + "', '" + phone + "', '" + nickName + "')";

          // name, id, pw 가 공백인지 체크
          if (name.equals("") || id.equals("") || pw.equals("")) {
            JOptionPane.showMessageDialog(null, "공백 확인 하세요",
                "ERROR_MESSAGE", JOptionPane.ERROR_MESSAGE);
            break;
          }

          // id 중복 체크
          StringBuilder sql2 = new StringBuilder();
          sql2.append("select id from member where id ='" + id + "'");
          String result = dbf.idchk2(sql2.toString());
          if (id.equals(result)) {
            JOptionPane.showMessageDialog(null, "id 중복 확인 하세요",
                "ERROR_MESSAGE", JOptionPane.ERROR_MESSAGE);
            break;
          }

          // 패스워드 확인 - 2가지가 같은지 체크
          if (!pw.equals(pw2)) {
            JOptionPane.showMessageDialog(null, "비밀번호를 확인하세요",
                "ERROR_MESSAGE", JOptionPane.ERROR_MESSAGE);
            break;
          }
          // 체크박스 체크 유무
          if (check == false) {
            JOptionPane.showMessageDialog(null, "개인정보 이용에 동의해주세요.",
                "ERROR_MESSAGE", JOptionPane.ERROR_MESSAGE);
            break;
          }

          StringBuilder sql = new StringBuilder();
          sql.append("CREATE TABLE frlist_" + id + "   ( ");
          sql.append("id      VARCHAR2(10) NOT NULL, ");
          sql.append("fr_id   VARCHAR2(30) primary key, ");
          sql.append("fr_name VARCHAR2(30) NOT NULL) ");

          dbf.create(sql.toString());
          System.out.println("테이블 생성 완료");

          dbf.insert(query);

          jtf_name.setText("");
          jtf_id.setText("");
          jtf_pw.setText("");
          jtf_pw2.setText("");
          jtf_birth.setText("");
          jtf_phone.setText("");
          jtf_nickName.setText("");
          JOptionPane.showMessageDialog(null, "회원가입 완료했습니다.",
              "SUCCESS_MESSAGE", JOptionPane.INFORMATION_MESSAGE);
          this.dispose();
          break;
        }

        // dbf.insert(query);

      } catch (NumberFormatException ne) {
        JOptionPane.showMessageDialog(null, "생년월일, 전화번호는 숫자만 입력하세요",
            "ERROR_MESSAGE", JOptionPane.ERROR_MESSAGE);
      }

    } else if (obj == jbtn_cancel) {
      this.dispose();
    }
  }

}
