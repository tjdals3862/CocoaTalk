package cocoatalk.login;

import java.awt.Color;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.awt.Graphics;
import java.awt.Font;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class SearchMainForm extends JFrame implements ActionListener {
  public static final String content = null;
  // 선언
  CocoaVO cVO = null;
  FriendList fl = new FriendList(cVO);
  ChatList cl = new ChatList(cVO);
  NaverConnect nc = new NaverConnect();

  String imgPath = "D:\\TEMP\\";
  ImageIcon ig = new ImageIcon(imgPath + "wallPaper.jpg");
  Toolkit toolkit = Toolkit.getDefaultToolkit();// 로고삽입
  Image img = toolkit.getImage(imgPath + "logo.png");// 로고삽입

  MainForm MainForm = new MainForm(cVO);
  JPanel jpanel = new JPanel() {
    public void paintComponent(Graphics g) {
      g.drawImage(ig.getImage(), 0, 0, null);
      setOpaque(false);
      super.paintComponent(g);
    } // end of MyPanel - 사용자 패널 정의 -
    // LoginForm$1.class

  };
  JScrollPane jsp = null;
  JTextField searchTextField = new JTextField();
  JTextArea searchTextArea = new JTextArea();
  JButton searchBtn;
  // JLabel label = new JLabel("검색 : ");

  JButton jbtn_friend = new JButton(new ImageIcon(imgPath + "btnfriends.png")); // 첫번째 (사람모양-친구목록)
  JButton jbtn_chat = new JButton(new ImageIcon(imgPath + "btnmsg.png")); // 두번째 (채팅목록-하트메세지)
  JButton jbtn_search = new JButton(new ImageIcon(imgPath + "btnsearch.png")); // 세번째버튼 (검색-돋보기)
  JButton jbtn_setting = new JButton(new ImageIcon(imgPath + "btnsetting.png")); // 네번째버튼 (설정-톱니바퀴)

  // 생성자
  SearchMainForm(CocoaVO cVO) {
    this.cVO = cVO;
  }

  /*
   * // 내부클래스로 배경 이미지 처리
   * class MyPanel extends JPanel {
   * public void paintComponent(Graphics g) {
   * g.drawImage(ig.getImage(), 0, 0, null);
   * setOpaque(false);
   * super.paintComponent(g);
   * } // end of MyPanel - 사용자 패널 정의 - LoginForm$1.class
   * }
   */

  public void Search_init() {
    // 창
    this.setTitle("SearchPanel");
    this.setIconImage(img);// 로고삽입
    this.setBounds(50, 50, 410, 670);
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.setVisible(true);
    this.setSize(426, 688);
    this.setLocation(500, 100);
    this.setResizable(false);
    jpanel.setLayout(null);
    this.add(jpanel);
    jpanel.setBackground(new Color(51, 0, 0, 120));

    // 검색창
    searchTextField.setBounds(25, 100, 295, 30);
    jpanel.add(searchTextField);
    // label.setBounds(150, 100, 70, 30);
    // jpanel.add(label);

    jsp = new JScrollPane(searchTextArea);
    jsp.setBounds(25, 145, 360, 380);
    jpanel.add(jsp);

    // 검색버튼
    jpanel.add(searchBtn = new JButton("검색"));
    searchBtn.setBounds(320, 100, 70, 31);
    searchBtn.setBackground(Color.pink);

    // 메뉴버튼들

    jbtn_friend.setBounds(15, 575, 80, 80);
    jpanel.add(jbtn_friend);
    jbtn_chat.setBounds(110, 575, 80, 80);
    jpanel.add(jbtn_chat);
    jbtn_search.setBounds(205, 575, 80, 80);
    jpanel.add(jbtn_search);
    jbtn_setting.setBounds(300, 575, 80, 80);
    jpanel.add(jbtn_setting);

    jbtn_friend.setBorderPainted(false);
    jbtn_friend.setContentAreaFilled(false);
    jbtn_search.setBorderPainted(false);
    jbtn_search.setContentAreaFilled(false);
    jbtn_chat.setBorderPainted(false);
    jbtn_chat.setContentAreaFilled(false);
    jbtn_setting.setBorderPainted(false);
    jbtn_setting.setContentAreaFilled(false);

    jbtn_friend.addActionListener(this);
    jbtn_search.addActionListener(this);
    jbtn_chat.addActionListener(this);
    jbtn_setting.addActionListener(this);
    searchBtn.addActionListener(this);

    //

  }

  // public static void main(String[] args) {
  // SearchMainForm serchPanel1 = new SearchMainForm(cVO);
  // serchPanel1.Search_init();

  // }

  @Override
  public void actionPerformed(ActionEvent e) {
    Object obj = e.getSource();

    // if (obj == jbtn_friend) {
    // TalkMain talkMain = new TalkMain();
    // talkMain.initDisplay();

    // } else if (obj == jbtn_search) {
    // SearchMainForm searchMainForm = new SearchMainForm();
    // searchMainForm.Search_init();
    // setVisible(true);

    // } else if (obj == jbtn_home) {
    // MainForm mainForm = new MainForm();
    // mainForm.initDisplay();
    // setVisible(false);

    // } else if (obj == jbtn_setting) {
    // SettingForm settingForm = new SettingForm();
    // settingForm.initDisplay();
    // setVisible(false);
    // } // else if

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
    } else if (obj == searchBtn) {
      String msg = searchTextField.getText();

      if (msg.equals("")) {
        System.out.println("빈값입니다.");
      } else {
        nc.search(msg);
        searchTextArea.setText(null);

        for (int i = 0; i < nc.titlelist.size(); i++) {
          searchTextArea.append(nc.titlelist.get(i) + "\n");
          searchTextArea.append(nc.catelist.get(i) + "\n");
          searchTextArea.append(nc.addlist.get(i) + "\n");
          searchTextArea.append("================================================" + "\n");
        }
      }

    }
  } // 이벤트
} // cls SearchPanel2