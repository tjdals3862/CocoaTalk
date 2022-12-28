package cocoatalk.login;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class LoginForm extends JFrame implements ActionListener {
  // 선언부
  Register register = new Register(this);
  String imgPath = "D:\\TEMP\\";
  ImageIcon imageIcon = new ImageIcon(imgPath + "login.jpg");
  JLabel jlb_id = new JLabel("아이디");
  JTextField jtf_id = new JTextField();
  JLabel jlb_pw = new JLabel("비밀번호");
  JPasswordField jpf_pw = new JPasswordField();
  Font font = new Font("굴림체", Font.BOLD, 13);

  JButton jbtn_join = new JButton(new ImageIcon(imgPath + "confirm.png"));
  JButton jbtn_login = new JButton(new ImageIcon(imgPath + "loginbutton.png"));

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
      MainForm MainFormcopy = new MainForm();
      MainFormcopy.initDisplay();
      this.setVisible(false);
    } else if (obj == jbtn_join) {
      register.set(true);
    }
  }
}
