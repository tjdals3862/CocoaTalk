package cocoatalk.dialog;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import cocoatalk.login.CocoaVO;
import cocoatalk.oracle.DBCon;
import cocoatalk.oracle.DbFunction;

public class ChatAdd extends JDialog implements ActionListener {

  JLabel jlb_id = new JLabel("아이디");
  JTextField jtf_id = new JTextField("");
  JButton jbtn_add = new JButton("추가");// 친구 추가 버튼
  Font f_label = new Font("맑은 고딕", Font.PLAIN, 12);

  String imgPath = "images\\";
  ImageIcon imageIcon = new ImageIcon(imgPath + "wallPaper.jpg");

  CocoaVO cVO = null;
  DBCon db = null;
  DbFunction dbf = null;
  Connection conn = null;
  PreparedStatement pstm = null;
  ResultSet rs = null;

  public ChatAdd(CocoaVO cVO) {
    this.cVO = cVO;
    initDisplay();
  }

  // 배경 이미지 삽입
  class MyPanel extends JPanel {
    public void paintComponent(Graphics g) {
      g.drawImage(imageIcon.getImage(), 0, 0, null);
      setOpaque(false);
      super.paintComponent(g);
    } // end of MyPanel - 사용자 패널 정의 - LoginForm$1.class
  }

  // 화면 initDisplay 메소드
  public void initDisplay() {
    jbtn_add.addActionListener(this);
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.setContentPane(new MyPanel());
    this.setLayout(null);

    jbtn_add.setBorderPainted(false); // 아이디 중복검사 버튼 외곽 라인 없애기
    jbtn_add.setForeground(Color.WHITE); // 아이디 중복검사 버튼 텍스트 색깔 (흰색)
    jbtn_add.setBackground(new Color(64, 64, 64)); // 아이디 중복검사 버튼 색깔 넣기 (갈색)
    jbtn_add.setBounds(290, 170, 90, 35);
    this.add(jbtn_add);

    jtf_id.setBounds(95, 105, 180, 35);
    jlb_id.setBounds(45, 105, 200, 35);
    jlb_id.setFont(f_label);
    jlb_id.setForeground(Color.white);
    this.add(jtf_id);
    this.add(jlb_id);

    this.setTitle("친구추가");
    this.setLocation(500, 100);
    this.setSize(410, 250);
    this.setVisible(true);
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    Object obj = e.getSource();

    if (obj == jbtn_add) {

      String id = cVO.getId();
      String fr_id = jtf_id.getText();

    }
  }
}
