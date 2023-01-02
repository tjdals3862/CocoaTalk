package cocoatalk.login;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class MainForm extends JFrame implements ActionListener {
  // 선언부
  CocoaVO cVO = null;
  FriendList fl = null;
  ChatList cl = new ChatList();
  LoginForm lf = new LoginForm();

  String id = null;
  String imgPath = "D:\\TEMP\\";
  ImageIcon imageIcon = new ImageIcon(imgPath + "cocoatalkmain.jpg");
  JPanel centerPanel = new JPanel();
  JPanel btnPanel = new JPanel();
  JLabel jlb_id = null;
  // JLabel jlb_id = new JLabel("아이디");
  Font font = new Font("굴림체", Font.BOLD, 13);

  JButton jbtn_friend = new JButton(new ImageIcon(imgPath + "btnheart.png"));
  JButton jbtn_search = new JButton(new ImageIcon(imgPath + "btnmsg.png"));
  JButton jbtn_chat = new JButton(new ImageIcon(imgPath + "btnhome.png"));
  JButton jbtn_setting = new JButton(new ImageIcon(imgPath + "btnsetting.png"));

  // 생성자
  MainForm(CocoaVO cVO) {
    this.cVO = cVO;
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

    fl = new FriendList(cVO);
    clearCenter(fl);
    // 창
    this.setTitle("COCOA TALK");
    this.setLocation(500, 100);
    this.setSize(426, 688);
    this.setVisible(true);
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.setResizable(false);
    this.setContentPane(new MyPanel());
    this.setLayout(null);

    // frinedPanel

    ////// 센터패널
    this.add("Center", centerPanel);
    centerPanel.setBounds(-10, 95, 426, 380);
    centerPanel.setBackground(new Color(0, 0, 0, 0)); // 투명

    ///// 버튼
    jbtn_friend.setBounds(10, 470, 80, 80);
    this.add(jbtn_friend);
    jbtn_chat.setBounds(100, 470, 80, 80);
    this.add(jbtn_chat);
    jbtn_search.setBounds(200, 470, 80, 80);
    this.add(jbtn_search);
    jbtn_setting.setBounds(290, 470, 80, 80);
    this.add(jbtn_setting);

    jbtn_friend.setBorderPainted(false);
    jbtn_friend.setContentAreaFilled(false);
    jbtn_search.setBorderPainted(false);
    jbtn_search.setContentAreaFilled(false);
    jbtn_chat.setBorderPainted(false);
    jbtn_chat.setContentAreaFilled(false);
    jbtn_setting.setBorderPainted(false);
    jbtn_setting.setContentAreaFilled(false);

    // id 라벨
    jlb_id = new JLabel("ID : " + cVO.getId());
    jlb_id.setBounds(330, 60, 80, 40);
    jlb_id.setBackground(Color.BLACK);
    jlb_id.setForeground(Color.WHITE);
    this.add(jlb_id);

    // 버튼액션
    jbtn_friend.addActionListener(this);
    jbtn_chat.addActionListener(this);
    jbtn_setting.addActionListener(this);
    jbtn_search.addActionListener(this);
  }

  public void clearCenter(JPanel jp) {
    Container cont = this.getContentPane();
    if (centerPanel != null) {
      cont.remove(centerPanel);
    }
    centerPanel = new JPanel();
    this.add("Center", centerPanel);
    centerPanel.setBounds(-10, 90, 426, 380);
    centerPanel.setBackground(new Color(0, 0, 0, 0));
    cont.revalidate();
    cont.setLayout(new BorderLayout());
    centerPanel.add(jp);

  }

  // 메인메소드
  // public static void main(String[] args) {
  // MainForm MainFormcopy = new MainForm();
  // MainFormcopy.initDisplay();
  // }

  @Override
  public void actionPerformed(ActionEvent e) {
    Object obj = e.getSource();

    if (obj == jbtn_friend) {
      fl = new FriendList(cVO);
      clearCenter(fl);

    } else if (obj == jbtn_chat) {
      clearCenter(cl);

    } else if (obj == jbtn_search) {
      SearchMainForm searchMainForm = new SearchMainForm(cVO);
      searchMainForm.Search_init();
      this.setVisible(false);

    } else if (obj == jbtn_setting) {
      SettingForm settingForm = new SettingForm(cVO);
      settingForm.initDisplay();
      setVisible(false);
    }
  }
}