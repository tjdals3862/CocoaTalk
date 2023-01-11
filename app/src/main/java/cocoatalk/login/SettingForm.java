package cocoatalk.login;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class SettingForm extends JPanel implements ActionListener {

  JPanel jp_color;
  JPanel jp_font;
  JPanel jp_logout;
  LoginForm loginForm = null;
  CocoaVO cVO = null;
  String imgPath = "D:\\TEMP\\";
  ImageIcon ig = new ImageIcon(imgPath + "setting.png");

  // 폰트 폰트
  Font f1 = new Font("고딕체", Font.PLAIN, 20);
  Font f2 = new Font("궁서", Font.PLAIN, 20);
  Font f3 = new Font("굴림", Font.PLAIN, 10);
  Font f5 = new Font("바탕", Font.PLAIN, 20);

  // 환경설정버튼1
  JButton jbtn_a = new JButton(new ImageIcon(imgPath + "backBtn.png"));// 배경화면
  // 버튼
  JButton jbtn_b = new JButton(new ImageIcon(imgPath + "fontBtn.png"));// 폰트설정
  // 버튼
  JButton jbtn_c = new JButton(new ImageIcon(imgPath + "logoutBtn.png")); //
  // 로그아웃버튼

  // 환경설정버튼2(폰트수정용)
  // JButton jbtn_a = new JButton("배 경 화 면");
  // // (new ImageIcon(imgPath +"backBtn.png"));// 배경화면 버튼
  // JButton jbtn_b = new JButton("폰 트 변 경");// 폰트설정 버튼
  // JButton jbtn_c = new JButton("로 그 아 웃"); // 로그아웃버튼
  // 하단고정버튼
  JButton jbtn_friend = new JButton(new ImageIcon(imgPath + "btnfriends.png")); // 첫번째 (사람모양-친구목록)
  JButton jbtn_chat = new JButton(new ImageIcon(imgPath + "btnmsg.png")); // 두번째 (채팅목록-하트메세지)
  JButton jbtn_search = new JButton(new ImageIcon(imgPath + "btnsearch.png")); // 세번째버튼 (검색-돋보기)
  JButton jbtn_setting = new JButton(new ImageIcon(imgPath + "btnsetting.png")); // 네번째버튼 (설정-톱니바퀴)

  // 생성자
  SettingForm(CocoaVO cVO) {
    this.cVO = cVO;
    initDisplay();
  }

  public void paintComponent(Graphics g) {
    g.drawImage(ig.getImage(), 0, 0, null);
    // setOpaque(false);
    super.paintComponent(g);
  } // end of MyPanel - 사용자 패널 정의 - LoginForm$1.class

  //////////////////////// 화면그리기
  public void initDisplay() {
    // 창
    jp_color = new JPanel();
    jp_font = new JPanel();
    jp_logout = new JPanel();

    this.add("North", jp_color);
    this.add("North", jp_font);
    this.add("North", jp_logout);
    this.setVisible(true);
    //// 환경설정 버튼
    jbtn_a.setBorderPainted(false);
    jbtn_b.setBorderPainted(false);
    jbtn_c.setBorderPainted(false);

    jbtn_a.setContentAreaFilled(false);
    jbtn_b.setContentAreaFilled(false);
    jbtn_c.setContentAreaFilled(false);

    jbtn_a.setFocusPainted(false);
    jbtn_b.setFocusPainted(false);
    jbtn_c.setFocusPainted(false);

    this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
    jp_color.add(jbtn_a);
    jbtn_a.setFont(f5);
    jp_font.add(Box.createVerticalStrut(150));
    jp_font.add(jbtn_b);
    jbtn_b.setFont(f5);
    this.setOpaque(false);
    jp_color.setOpaque(false);
    jp_font.setOpaque(false);
    jp_logout.setOpaque(false);

    jp_logout.add(jbtn_c);
    jbtn_c.setFont(f5);

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
    String[] font1 = { "나진지하다궁서체", "땡글땡글굴림체", "맑은고딩같은고딕체" };
    Object fonttype = JOptionPane.showInputDialog(null, "궁서체 좋죠 고딕체도 좋죠", "font setting",
        JOptionPane.QUESTION_MESSAGE,
        null, font1, font1[0]);
        jbtn_a.setFont(null);
  //버튼 떼기, mainform에 clearcenter 메소드처럼 
    if (fonttype.equals(font1[0])) {



      System.out.println("궁서체됐긔 changed");

    } else if (fonttype.equals(font1[1])) {
      initDisplay();
      jbtn_a.setFont(f3);
      jbtn_b.setFont(f3);
      jbtn_c.setFont(f3);
      System.out.println("굴림font changed");

    } else if (fonttype.equals(font1[2])) {
      initDisplay();
      jbtn_a.setFont(f1);
      jbtn_b.setFont(f1);
      jbtn_c.setFont(f1);
      System.out.println("고딕font changed");
    }

  } // 폰트변경 메소드 끝

  //public void clearCenter(Jpanel jp)
  

  // 배경 변경 메소드=============================작업중===============================
  public void backchange() {
    String[] back1 = { "바나나는 원래 하얀색", "치키차카 초코색" };
    Object backtype = JOptionPane.showInputDialog(null, "원하는 배경 색을 선택하세요.", "background color setting",
        JOptionPane.PLAIN_MESSAGE,
        null, back1, back1[0]);

    if (backtype.equals(back1[0])) {
      initDisplay();
      ig = new ImageIcon(imgPath + "yellowback.png");
      System.out.println("하얀색 됐긔 changed");

    } else if (backtype.equals(back1[1])) {
      initDisplay();
      ig = new ImageIcon(imgPath + "chocoback.png");
      System.out.println("초코색 됐긔font changed");

    }

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
      this.setVisible(false);

    } else if (obj == jbtn_chat) {
      MainForm mf = new MainForm(cVO);
      mf.initDisplay();
      mf.clearCenter(mf.cl);
      this.setVisible(false);

    } else if (obj == jbtn_search) {
      SearchMainForm searchMainForm = new SearchMainForm(cVO);
      searchMainForm.Search_init();
      this.setVisible(false);

    } else if (obj == jbtn_setting) {
      SettingForm settingForm = new SettingForm(cVO);
      settingForm.initDisplay();
      this.setVisible(false);

      // 환경설정 버튼 기능
    } else if (obj == jbtn_a) { // 배경화면
      SettingForm settingForm = new SettingForm(cVO);
      settingForm.backchange();

    } else if (obj == jbtn_b) { // 폰트설정
      SettingForm settingForm = new SettingForm(cVO);
      settingForm.fontchange();

    } else if (obj == jbtn_c) { // 로그아웃
      this.setVisible(false);
      loginForm = new LoginForm();
      loginForm.initDisplay();
    } // else if

  }
} // end of SettingForm
