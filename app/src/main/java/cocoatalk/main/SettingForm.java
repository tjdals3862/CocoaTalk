package cocoatalk.main;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Dimension;
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
  String imgPath = "app\\src\\main\\java\\cocoatalk\\images\\";
  ImageIcon ig = new ImageIcon(imgPath + "setting.png");
  SettingForm sf = null;

  // 폰트 폰트
  Font f1;
  Font f2;
  Font f3;
  Font f4;

  // 환경설정버튼1
  JButton jbtn_a = new JButton(new ImageIcon(imgPath + "themeBtn.png"));// 배경화면
  // // 버튼
  JButton jbtn_b = new JButton(new ImageIcon(imgPath + "fontBtn.png"));// 폰트설정
  // // 버튼
  JButton jbtn_c = new JButton(new ImageIcon(imgPath + "logoutBtn.png")); //
  // 로그아웃버튼

  
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

    // 환경설정 버튼
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

    jp_setting.add(Box.createRigidArea(new Dimension(0, 50)));
    jp_setting.add(Box.createHorizontalGlue());

    jp_setting.add(Box.createHorizontalGlue());
    jp_setting.add(jbtn_b);
    this.setOpaque(false);

    jp_setting.add(Box.createRigidArea(new Dimension(0, 50)));
    jp_setting.add(Box.createHorizontalGlue());
    jp_setting.setOpaque(false);
    jp_setting.add(jbtn_c);

    // 버튼들 액션리스너

    jbtn_a.addActionListener(this);
    jbtn_b.addActionListener(this);
    jbtn_c.addActionListener(this);


  }////////////// 화면그리기 / initDisplay 끝//////////////////////////////

  // 폰트변경메소드
  public void fontchange() {
    String[] font1 = { "나진지하다궁서체", "땡글땡글굴림체", "마라탕먹고싶은바탕" };
    Object fonttype = JOptionPane.showInputDialog(null, "궁서체 좋죠 고딕체도 좋죠", "font setting",
        JOptionPane.QUESTION_MESSAGE,
        null, font1, font1[0]);
    if (fonttype.equals(font1[0])) {
      f2 = new Font("궁서", Font.PLAIN, 13);
      cVO.setFontc(f2);
      System.out.println("궁서체됐긔 changed");

    } else if (fonttype.equals(font1[1])) {
      f3 = new Font("굴림", Font.ITALIC, 13);
      cVO.setFontc(f3);
      System.out.println("굴림font changed");

    } else if (fonttype.equals(font1[2])) {
      f4 = new Font("바탕", Font.PLAIN, 13);
      cVO.setFontc(f4);
      System.out.println("바탕font changed");
    } else {
      System.out.println("설정안함");
    }
  }
  // 폰트변경 메소드 끝

  // 테마 변경 메소드=============================작업중===============================
  public void backchange() {
    String[] theme1 = { "바나나는 원래 하얀색", "민트 초코색" };
    Object backtype = JOptionPane.showInputDialog(null, "원하는 테마를 선택하세요.", "theme setting",
        JOptionPane.PLAIN_MESSAGE,
        null, theme1, theme1[0]);

    if (backtype.equals(theme1[0])) {
      ig = new ImageIcon(imgPath + "yellowback.png");
    
      cVO.setTheme(ig);
      System.out.println("하얀색 됐긔 changed");

    } else if (backtype.equals(theme1[1])) {

      ig = new ImageIcon(imgPath + "chocoback.png");
      cVO.setTheme(ig);
      System.out.println("초코색 됐긔font changed");

    }

  }

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
