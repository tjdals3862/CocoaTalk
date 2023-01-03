package cocoatalk.login;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.Color;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.event.MouseInputListener;

import cocoatalk.chat.ChatClient;

public class FriendProfile extends JDialog implements MouseInputListener, ActionListener {
  FriendList fl = null;
  ChatClient cc = null;
  String imgPath = "D:\\TEMP\\";
 // ImageIcon imageIcon = new ImageIcon(imgPath + "join.jpg");
  
  ///////////////////////////////////////////////////////////////////// 이미지 삽입

  JButton jbtn_del = new JButton(new ImageIcon(imgPath + "delbtn.png"));// 삭제버튼
  JButton jbtn_chat = new JButton(new ImageIcon(imgPath + "1on1btn.png"));// 1:1채팅버튼
  /////////////////////////////////////////////////////////////////////////////버튼이미지  
  JLabel jlbl_name;
  JPanel pf_south;

  public FriendProfile(FriendList fl) {
    this.fl = fl;
  }
  public void profileDisplay(boolean isOpen, String who) {
    new JDialog();
    JPanel jp = new JPanel();//패널 객체생성
    jlbl_name = new JLabel(who);
    
    jp.setLayout(null);
    this.add(jbtn_chat);
    this.add(jbtn_del);
    this.add(jp);//패널추가 
    jp.setBackground(new Color(55,38,30));// 패널 배경 
    jbtn_chat.setBounds(160, 440, 80, 40);// 1:1채팅버튼 
    jbtn_del.setBounds(70, 440, 80, 40);//삭제버튼 
    jbtn_chat.addActionListener(this); // 1:1 버튼 실행시
    jbtn_del.addActionListener(this); // 삭제 버튼 실행시 
    
    setResizable(false); //창크기 수정불가
    setSize(330, 550); //화면사이즈 
    setVisible(isOpen);
    setTitle(who);// 채팅창 이름(사용자 별로 다름) 
    
  }
 
      
    

  @Override
  public void mouseClicked(MouseEvent e) { //마우스이벤트
    Object obj = e.getSource();
    if (obj == jbtn_chat) {
      dispose();
    } else if (obj == jbtn_del) {

    }

  }

  @Override
  public void mousePressed(MouseEvent e) {
  }

  @Override
  public void mouseReleased(MouseEvent e) {
  }

  @Override
  public void mouseEntered(MouseEvent e) {
  }

  @Override
  public void mouseExited(MouseEvent e) {
  }

  @Override
  public void mouseDragged(MouseEvent e) {
  }

  @Override
  public void mouseMoved(MouseEvent e) {
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    Object obj = e.getSource();
    if (jbtn_chat == obj) {
      cc = new ChatClient();
      cc.initDisplay();
    } else if (jbtn_del == obj) {

    }
  }
}