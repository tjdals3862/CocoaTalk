package cocoatalk.login;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.awt.Graphics;

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
  String imgPath = "D:\\TEMP\\";
  ImageIcon imageIcon = new ImageIcon();

  MainForm MainForm = new MainForm();
  TalkMain talkMain = new TalkMain();

  JFrame jframe = new JFrame();
  JPanel jpanel = new JPanel();
  JTextField searchTextField = new JTextField();
  JTextArea searchTextArea = new JTextArea();
  JButton searchBtn;
  // JLabel label = new JLabel("검색 : ");

  JButton jbtn_friend = new JButton(new ImageIcon(imgPath + "btnheart.png"));
  JButton jbtn_search = new JButton(new ImageIcon(imgPath + "btnmsg.png"));
  JButton jbtn_home = new JButton(new ImageIcon(imgPath + "btnhome.png"));
  JButton jbtn_setting = new JButton(new ImageIcon(imgPath + "btnsetting.png"));

  // 생성자
  SearchMainForm() {
    Search_init();
  }

  // 내부클래스로 배경 이미지 처리
  class MyPanel extends JPanel {
    public void paintComponent(Graphics g) {
      g.drawImage(imageIcon.getImage(), 0, 0, null);
      setOpaque(false);
      super.paintComponent(g);
    } // end of MyPanel - 사용자 패널 정의 - LoginForm$1.class
  }

  public void Search_init() {
    // 창
    jframe.setTitle("CocoaTalk_SearchPanel");
    jframe.setBounds(50, 50, 410, 670);
    jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    jframe.setVisible(true);
    jpanel.setLayout(null);
    jframe.add(jpanel);
    jpanel.setBackground(new Color(51, 0, 0, 150));

    // 검색창
    searchTextField.setBounds(80, 100, 220, 30);
    jpanel.add(searchTextField);
    // label.setBounds(150, 100, 70, 30);
    // jpanel.add(label);

    JScrollPane jsp = new JScrollPane(searchTextArea);
    jsp.setBounds(10, 145, 360, 250);
    jpanel.add(jsp);

    // 검색버튼
    jpanel.add(searchBtn = new JButton("검색"));
    searchBtn.setBounds(300, 100, 70, 31);
    searchBtn.setBackground(Color.pink);

    // 메뉴버튼들
    jbtn_friend.setBounds(10, 520, 80, 80);
    jpanel.add(jbtn_friend);
    jbtn_search.setBounds(100, 520, 80, 80);
    jpanel.add(jbtn_search);
    jbtn_home.setBounds(200, 520, 80, 80);
    jpanel.add(jbtn_home);
    jbtn_setting.setBounds(290, 520, 80, 80);
    jpanel.add(jbtn_setting);

    jbtn_friend.setBorderPainted(false);
    jbtn_friend.setContentAreaFilled(false);
    jbtn_search.setBorderPainted(false);
    jbtn_search.setContentAreaFilled(false);
    jbtn_home.setBorderPainted(false);
    jbtn_home.setContentAreaFilled(false);
    jbtn_setting.setBorderPainted(false);
    jbtn_setting.setContentAreaFilled(false);

    jbtn_friend.addActionListener(this);
    jbtn_search.addActionListener(this);
    jbtn_home.addActionListener(this);
    jbtn_setting.addActionListener(this);
    searchBtn.addActionListener(this);

    //

  }

  public static void main(String[] args) {
    SearchMainForm serchMainForm = new SearchMainForm();
    serchMainForm.Search_init();

  }

  @Override
  public void actionPerformed(ActionEvent e) {
    Object obj = e.getSource();

    if (obj == jbtn_home) {
      MainForm.initDisplay();
      jframe.setVisible(false);

    } else if (obj == jbtn_friend) {
      talkMain.initDisplay();

    } else if (obj == jbtn_setting) {

    } else if (obj == jbtn_search) {

    } else if (obj == searchBtn) {

    }
  } // 이벤트
} // cls SearchPanel2
