package cocoatalk.dialog;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.awt.Color;
import java.awt.*;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.event.MouseInputListener;

import cocoatalk.client.ChatClient;
import cocoatalk.login.CocoaVO;
import cocoatalk.main.ChatFriendList;
import cocoatalk.main.FriendList;
import cocoatalk.oracle.DBCon;
import cocoatalk.oracle.DbFreeCon;
import cocoatalk.oracle.DbFunction;


public class FriendProfile extends JDialog implements ActionListener {
  FriendList fl = null;
  ChatClient cc = null;
  CocoaVO cVO = null;
  String imgPath = "D:\\TEMP\\";
  Toolkit toolkit = Toolkit.getDefaultToolkit();// 로고삽입
  Image img = toolkit.getImage(imgPath + "logo.png");// 로고삽입
  // ImageIcon imageIcon = new ImageIcon(imgPath + "join.jpg");

  ///////////////////////////////////////////////////////////////////// 이미지 삽입

  JButton jbtn_del = new JButton(new ImageIcon(imgPath + "delbtn.png"));// 삭제버튼
  JButton jbtn_chat = new JButton(new ImageIcon(imgPath + "1on1btn.png"));// 1:1채팅버튼
  ///////////////////////////////////////////////////////////////////////////// 버튼이미지
  JLabel jlbl_name;
  JPanel pf_south;

  DBCon db = null;
  Connection conn = null;
  PreparedStatement pstm = null;
  Statement stmt = null;
  ResultSet rs = null;
  DbFreeCon dfc = null;
  
  
  
  public FriendProfile(FriendList fl) {
    this.fl = fl;
  }
  
  public FriendProfile(CocoaVO cVO) {
    this.cVO = cVO;
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
    jbtn_chat.setBounds(160, 50, 80, 40);// 1:1채팅버튼
    jbtn_del.setBounds(70, 50, 80, 40);// 삭제버튼
    jbtn_chat.addActionListener(this); // 1:1 버튼 실행시
    jbtn_del.addActionListener(this); // 삭제 버튼 실행시

    setResizable(false); // 창크기 수정불가
    setSize(330, 180); // 화면사이즈
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

     DbFunction dbf = new DbFunction();
      db = new DBCon();
      dbf = new DbFunction();
      

      String query = "delete from frlist_"+fl.cVO.getId() + " where fr_id = '" + jlbl_name.getText() + "'";
    
      dbf.delete(query);

      System.out.println("삭제완료");


    }
  }
}