package cocoatalk.login;

import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JOptionPane;

public class SettingForm extends JFrame implements ActionListener {
  LoginForm loginForm = null;
  CocoaVO cVO = null;
  String imgPath = "D:\\TEMP\\";
  ImageIcon ig = new ImageIcon(imgPath + "wallPaper.jpg");

  // 환경설정버튼
  JButton jbtn_a = new JButton(new ImageIcon(imgPath + "backBtn.png"));// 배경화면 버튼
  JButton jbtn_b = new JButton(new ImageIcon(imgPath + "fontBtn.png"));// 폰트설정 버튼
  JButton jbtn_c = new JButton(new ImageIcon(imgPath + "logoutBtn.png")); // 로그아웃버튼

  // 하단고정버튼
  JButton jbtn_friend = new JButton(new ImageIcon(imgPath + "btnfriends.png")); // 첫번째 (사람모양-친구목록)
  JButton jbtn_chat = new JButton(new ImageIcon(imgPath + "btnmsg.png")); // 두번째 (채팅목록-하트메세지)
  JButton jbtn_search = new JButton(new ImageIcon(imgPath + "btnsearch.png")); // 세번째버튼 (검색-돋보기)
  JButton jbtn_setting = new JButton(new ImageIcon(imgPath + "btnsetting.png")); // 네번째버튼 (설정-톱니바퀴)

  // 생성자
  SettingForm(CocoaVO cVO) {
    this.cVO = cVO;
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
    this.setTitle("Setting");
    this.setLocation(500, 100);
    this.setSize(426, 688);
    this.setVisible(true);

    //// 환경설정 버튼
    jbtn_a.setBounds(65, 120, 280, 60);
    this.add(jbtn_a);
    jbtn_b.setBounds(65, 240, 280, 60);
    this.add(jbtn_b);
    jbtn_c.setBounds(65, 360, 280, 60);
    this.add(jbtn_c);

    jbtn_a.setBorderPainted(true);
    jbtn_a.setContentAreaFilled(false);
    jbtn_b.setBorderPainted(false);
    jbtn_b.setContentAreaFilled(false);
    jbtn_c.setBorderPainted(false);
    jbtn_c.setContentAreaFilled(false);

    // 하단 고정 버튼 4개

    jbtn_friend.setBounds(15, 565, 80, 80);
    this.add(jbtn_friend);
    jbtn_chat.setBounds(110, 565, 80, 80);
    this.add(jbtn_chat);
    jbtn_search.setBounds(205, 565, 80, 80);
    this.add(jbtn_search);
    jbtn_setting.setBounds(300, 565, 80, 80);
    this.add(jbtn_setting);

    jbtn_friend.setBorderPainted(false);
    jbtn_friend.setContentAreaFilled(false);
    jbtn_search.setBorderPainted(false);
    jbtn_search.setContentAreaFilled(false);
    jbtn_chat.setBorderPainted(false);
    jbtn_chat.setContentAreaFilled(false);
    jbtn_setting.setBorderPainted(false);
    jbtn_setting.setContentAreaFilled(false);

    // 버튼들 액션리스너

    jbtn_a.addActionListener(this);
    jbtn_b.addActionListener(this);
    jbtn_c.addActionListener(this);

    jbtn_friend.addActionListener(this);
    jbtn_search.addActionListener(this);
    jbtn_chat.addActionListener(this);
    jbtn_setting.addActionListener(this);

  }////////////// 화면그리기 / initDisplay 끝//////////////////////////////

  // 폰트변경메소드 =============================작업중===============================
  public void fontchange() {
    String[] font1 = { "땡글땡글굴림체", "맑은고딩같은고딕체", "나진지하다궁서체" };
    JOptionPane.showInputDialog(null, "궁서체 좋죠 고딕체도 좋죠", "font setting", JOptionPane.QUESTION_MESSAGE, null, font1, "c");
    // if(){}

  } // 폰트변경 메소드 끝

  // 배경 변경 메소드=============================작업중===============================
  public void backchange() {
    String[] back1 = { "바나나는 원래 하얀색", "치키차카 초코색" };
    JOptionPane.showInputDialog(null, "원하는 배경 색을 선택하세요.", "background color setting", JOptionPane.PLAIN_MESSAGE,
        null, back1, "c");
    // if(){}

  }

  // 메인메소드
  // public static void main(String[] args) {
  // SettingForm settingForm = new SettingForm();
  // settingForm.initDisplay();

  // }

  @Override
  public void actionPerformed(ActionEvent e) {
    Object obj = e.getSource();

    // 하단고정버튼
    if (obj == jbtn_friend) {
      MainForm mf = new MainForm(cVO);
      mf.initDisplay();
      this.dispose();

    } else if (obj == jbtn_chat) {
      MainForm mf = new MainForm(cVO);
      mf.initDisplay();
      mf.clearCenter(mf.cl);
      this.dispose();

    } else if (obj == jbtn_search) {
      SearchMainForm searchMainForm = new SearchMainForm(cVO);
      searchMainForm.Search_init();
      this.dispose();

    } else if (obj == jbtn_setting) {
      SettingForm settingForm = new SettingForm(cVO);
      settingForm.initDisplay();
      this.dispose();

      // 환경설정 버튼 기능
    } else if (obj == jbtn_a) { // 배경화면
      SettingForm settingForm = new SettingForm(cVO);
      settingForm.backchange();

    } else if (obj == jbtn_b) { // 폰트설정
      SettingForm settingForm = new SettingForm(cVO);
      settingForm.fontchange();

    } else if (obj == jbtn_c) { // 로그아웃
      this.dispose();
      loginForm = new LoginForm();
      loginForm.initDisplay();
    } // else if

  }
} // end of SettingForm
