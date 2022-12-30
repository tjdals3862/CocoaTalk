package cocoatalk.login;

import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class SettingForm extends JFrame implements ActionListener {
  String imgPath = "D:\\TEMP\\";
  ImageIcon ig = new ImageIcon(imgPath + "wallPaper.jpg");

  // 환경설정버튼
  JButton jbtn_a = new JButton("로그아웃이긔 >_<"); // (new ImageIcon(imgPath + "Logout.png"));// 로그아웃 버튼
  JButton jbtn_b = new JButton("폰트 바꿀 사람 >_o"); // (new ImageIcon(imgPath + "FontButton.png"));// 폰트설정 버튼
  JButton jbtn_c = new JButton("배경 바꾸고 싶니? 3 _ 3"); // (new ImageIcon(imgPath + "wallButton.png"));// 배경화면 버튼

  // 하단고정버튼
  JButton jbtn_friend = new JButton(
      new ImageIcon(imgPath + "btnheart.png"));// 하트버튼
  JButton jbtn_search = new JButton(
      new ImageIcon(imgPath + "btnmsg.png"));// 하트버튼2
  JButton jbtn_home = new JButton(
      new ImageIcon(imgPath + "btnhome.png"));// 홈 버튼
  JButton jbtn_setting = new JButton(
      new ImageIcon(imgPath + "btnsetting.png"));// 메시지 버튼

  // 생성자
  SettingForm() {

  }

  class MyPanel extends JPanel {
    public void paintComponent(Graphics g) {
      g.drawImage(ig.getImage(), 0, 0, null);
      setOpaque(false);
      super.paintComponent(g);
    } // end of MyPanel - 사용자 패널 정의 - LoginForm$1.class
  }

  //////////////////////// 화면그리기
  public void initDisplay() {
    // 창
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.setContentPane(new MyPanel());
    this.setLayout(null);
    this.setLocation(500, 100);
    this.setResizable(false);

    //// 환경설정 버튼
    jbtn_a.setBounds(10, 80, 390, 150);
    this.add(jbtn_a);
    jbtn_b.setBounds(10, 240, 390, 150);
    this.add(jbtn_b);
    jbtn_c.setBounds(10, 400, 390, 150);
    this.add(jbtn_c);

    // 하단 고정 버튼 4개

    jbtn_friend.setBounds(15, 565, 80, 80);
    this.add(jbtn_friend);
    jbtn_search.setBounds(110, 565, 80, 80);
    this.add(jbtn_search);
    jbtn_home.setBounds(205, 565, 80, 80);
    this.add(jbtn_home);
    jbtn_setting.setBounds(300, 565, 80, 80);
    this.add(jbtn_setting);

    jbtn_friend.setBorderPainted(false);
    jbtn_friend.setContentAreaFilled(false);
    jbtn_search.setBorderPainted(false);
    jbtn_search.setContentAreaFilled(false);
    jbtn_home.setBorderPainted(false);
    jbtn_home.setContentAreaFilled(false);
    jbtn_setting.setBorderPainted(false);
    jbtn_setting.setContentAreaFilled(false);

    this.setTitle("Setting");
    this.setLocation(500, 100);
    this.setSize(426, 688);
    this.setVisible(true);

    // 버튼들 액션리스너

    jbtn_a.addActionListener(this);
    jbtn_b.addActionListener(this);
    jbtn_c.addActionListener(this);

    jbtn_friend.addActionListener(this);
    jbtn_search.addActionListener(this);
    jbtn_home.addActionListener(this);
    jbtn_setting.addActionListener(this);

  }////////////////////////////////////////////////////////////////////////////////// 화면그리기

  public static void main(String[] args) {
    SettingForm settingForm = new SettingForm();
    settingForm.initDisplay();

  }

  @Override
  public void actionPerformed(ActionEvent e) {
    Object obj = e.getSource();

    if (obj == jbtn_friend) {
      TalkMain talkMain = new TalkMain();
      talkMain.initDisplay();

    } else if (obj == jbtn_search) {
      SearchMainForm searchMainForm = new SearchMainForm();
      searchMainForm.Search_init();
      setVisible(false);

    } else if (obj == jbtn_home) {
      MainForm mainForm = new MainForm();
      mainForm.initDisplay();
      setVisible(false);

    } else if (obj == jbtn_setting) {
      SettingForm settingForm = new SettingForm();
      settingForm.initDisplay();
      setVisible(true);
    } // else if

  }
} // end of SettingForm
