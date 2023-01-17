package cocoatalk.dialog;

import java.awt.Color;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;

import cocoatalk.client.ChatClient;
import cocoatalk.main.FriendList;

public class FriendProfile extends JDialog implements ActionListener {
  FriendList fl = null;
  ChatClient cc = null;
  String imgPath = "app\\src\\main\\java\\cocoatalk\\images\\";
  Toolkit toolkit = Toolkit.getDefaultToolkit();// 로고삽입
  Image img = toolkit.getImage(imgPath + "logo.png");// 로고삽입
  // ImageIcon imageIcon = new ImageIcon(imgPath + "join.jpg");

  ///////////////////////////////////////////////////////////////////// 이미지 삽입

  JButton jbtn_del = new JButton(new ImageIcon(imgPath + "delbtn.png"));// 삭제버튼
  JButton jbtn_chat = new JButton(new ImageIcon(imgPath + "1on1btn.png"));// 1:1채팅버튼
  ///////////////////////////////////////////////////////////////////////////// 버튼이미지
  JLabel jlbl_name;
  JPanel pf_south;

  public FriendProfile(FriendList fl) {
    this.fl = fl;
  }

  public void profileDisplay(boolean isOpen, String who) {
    new JDialog();
    JPanel jp = new JPanel();// 패널 객체생성
    jlbl_name = new JLabel(who);
    this.setIconImage(img);// 로고삽입
    jp.setLayout(null);
    this.add(jbtn_chat);
    this.add(jbtn_del);
    this.add(jp);// 패널추가
    jp.setBackground(new Color(55, 38, 30));// 패널 배경
    jbtn_chat.setBounds(160, 440, 80, 40);// 1:1채팅버튼
    jbtn_del.setBounds(70, 440, 80, 40);// 삭제버튼
    jbtn_chat.addActionListener(this); // 1:1 버튼 실행시
    jbtn_del.addActionListener(this); // 삭제 버튼 실행시

    setResizable(false); // 창크기 수정불가
    setSize(330, 550); // 화면사이즈
    setVisible(isOpen);
    setTitle(who);// 채팅창 이름(사용자 별로 다름)
    jbtn_del.setBorderPainted(false);// 버튼 테두리 변경
    jbtn_del.setContentAreaFilled(false);
    jbtn_chat.setBorderPainted(false);
    jbtn_chat.setContentAreaFilled(false);

  }

  @Override
  public void actionPerformed(ActionEvent e) {
    Object obj = e.getSource();
    if (obj == jbtn_chat) {
      cc = new ChatClient();
      cc.chatOpen(fl.cVO.getId(), jlbl_name.getText());
      this.dispose();
    } else if (obj == jbtn_del) {

    }
  }
}