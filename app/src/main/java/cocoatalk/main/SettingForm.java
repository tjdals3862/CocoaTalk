package cocoatalk.main;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Container;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import cocoatalk.login.CocoaVO;
import cocoatalk.login.LoginForm;

public class SettingForm extends JPanel implements ActionListener {

  JPanel jp_setting;
  JPanel jp_font1;
  JPanel jp_font2;
  JPanel jp_font3;

  LoginForm loginForm = null;
  CocoaVO cVO = null;
  String imgPath = "D:\\TEMP\\";
  ImageIcon ig = new ImageIcon(imgPath + "setting.png");
  SettingForm sf = null;

  // 폰트 폰트
  Font f1;
  Font f2;
  Font f3;
  Font f4;

  // 환경설정버튼1
  JButton jbtn_a = new JButton(new ImageIcon(imgPath + "backBtn.png"));// 배경화면
  // // 버튼
  JButton jbtn_b = new JButton(new ImageIcon(imgPath + "fontBtn.png"));// 폰트설정
  // // 버튼
  JButton jbtn_c = new JButton(new ImageIcon(imgPath + "logoutBtn.png")); //
  // 로그아웃버튼

  // 환경설정버튼2(폰트수정용)
  // JButton jbtn_a = new JButton("배 경 화 면");
  // // (new ImageIcon(imgPath +"backBtn.png"));// 배경화면 버튼
  // JButton jbtn_b = new JButton("폰 트 변 경");// 폰트설정 버튼
  // JButton jbtn_c = new JButton("로 그 아 웃"); // 로그아웃버튼

  // JButton jbtn_a = null;
  // (new ImageIcon(imgPath +"backBtn.png"));// 배경화면 버튼
  // JButton jbtn_b = null;// 폰트설정 버튼
  // JButton jbtn_c = null; // 로그아웃버튼
  // 하단고정버튼
  JButton jbtn_friend = new JButton(new ImageIcon(imgPath + "btnfriends.png")); // 첫번째 (사람모양-친구목록)
  JButton jbtn_chat = new JButton(new ImageIcon(imgPath + "btnmsg.png")); // 두번째 (채팅목록-하트메세지)
  JButton jbtn_search = new JButton(new ImageIcon(imgPath + "btnsearch.png")); // 세번째버튼 (검색-돋보기)
  JButton jbtn_setting = new JButton(new ImageIcon(imgPath + "btnsetting.png")); // 네번째버튼 (설정-톱니바퀴)
  Container con = null;

  // 생성자
  SettingForm(CocoaVO cVO, Container con) {
    this.cVO = cVO;
    this.con = con;
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
    jp_setting = new JPanel();
    this.add("North", jp_setting);

    // this.setVisible(true);
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

    jp_setting.setLayout(new BoxLayout(jp_setting, BoxLayout.Y_AXIS));
    jp_setting.add(jbtn_a);
    jbtn_b.add(Box.createVerticalStrut(150));
    jp_setting.add(jbtn_b);
    this.setOpaque(false);
    jp_setting.setOpaque(false);
    jp_setting.add(jbtn_c);

    // 버튼들 액션리스너

    jbtn_a.addActionListener(this);
    jbtn_b.addActionListener(this);
    jbtn_c.addActionListener(this);

    jbtn_friend.addActionListener(this);
    jbtn_search.addActionListener(this);
    jbtn_chat.addActionListener(this);
    jbtn_setting.addActionListener(this);

  }////////////// 화면그리기 / initDisplay 끝//////////////////////////////

  // public void clear(JPanel jp){

  // jp_setting.revalidate();
  // jp_setting.repaint();

  // jp_setting.add(jbtn_a);
  // jp_setting.add(jbtn_b);
  // jp_setting.add(jbtn_c);
  // jbtn_a.setFont(f3);
  // jbtn_b.setFont(f3);
  // jbtn_c.setFont(f3);

  // System.out.println("왔니");
  // }
  // 폰트변경메소드 =============================작업중===============================
  public void fontchange() {
    String[] font1 = { "나진지하다궁서체", "땡글땡글굴림체", "맑은고딩같은고딕체" };
    Object fonttype = JOptionPane.showInputDialog(null, "궁서체 좋죠 고딕체도 좋죠", "font setting",
        JOptionPane.QUESTION_MESSAGE,
        null, font1, font1[0]);
    // 버튼 떼기, mainform에 clearcenter 메소드처럼
    if (fonttype.equals(font1[0])) {

      f2 = new Font("궁서", Font.PLAIN, 13);
      cVO.setFontc(f2);
      System.out.println("궁서체됐긔 changed");

    } else if (fonttype.equals(font1[1])) {
      f3 = new Font("굴림", Font.PLAIN, 13);
      cVO.setFontc(f3);
      System.out.println("굴림font changed");

    } else if (fonttype.equals(font1[2])) {
      f4 = new Font("바탕", Font.PLAIN, 13);
      cVO.setFontc(f4);
      System.out.println("바탕font changed");
    }
  }
  // 폰트타입에 값넣고 버튼 붙이기 revalidate, repaint
  // 폰트변경 메소드 끝

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

    // 환경설정 버튼 기능
    if (obj == jbtn_a) { // 배경화면
      SettingForm settingForm = new SettingForm(cVO, con);
      settingForm.backchange();

    } else if (obj == jbtn_b) { // 폰트설정
      SettingForm settingForm = new SettingForm(cVO, con);
      settingForm.fontchange();

    } else if (obj == jbtn_c) { // 로그아웃
      this.setVisible(false);
      loginForm = new LoginForm();
      loginForm.initDisplay();
    } // else if

  }
} // end of SettingForm
