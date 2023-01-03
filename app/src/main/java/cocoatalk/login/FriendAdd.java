package cocoatalk.login;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import cocoatalk.oracle.DBCon;
import cocoatalk.oracle.DbFunction;

public class FriendAdd extends JFrame implements ActionListener {

  JLabel jlb_id = new JLabel("아이디");
  JTextField jtf_id = new JTextField("");
  JButton jbtn_add = new JButton("추가");// 친구 추가 버튼
  Font f_label = new Font("맑은 고딕", Font.PLAIN, 12);

  String imgPath = "D:\\TEMP\\";
  ImageIcon imageIcon = new ImageIcon(imgPath + "wallPaper.jpg");

  CocoaVO cVO = null;
  DBCon db = null;
  DbFunction dbf = null;
  Connection conn = null;
  PreparedStatement pstm = null;
  ResultSet rs = null;

  public FriendAdd(CocoaVO cVO) {
    this.cVO = cVO;
    initDisplay();
  }

  class MyPanel extends JPanel {
    public void paintComponent(Graphics g) {
      g.drawImage(imageIcon.getImage(), 0, 0, null);
      setOpaque(false);
      super.paintComponent(g);
    } // end of MyPanel - 사용자 패널 정의 - LoginForm$1.class
  }

  public void initDisplay() {
    jbtn_add.addActionListener(this);
    this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
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

  // public static void main(String[] args) {
  // new FriendAdd();
  // }

  @Override
  public void actionPerformed(ActionEvent e) {
    Object obj = e.getSource();

    if (obj == jbtn_add) {

      String id = cVO.getId();
      System.out.println(id);
      String fr_id = jtf_id.getText();

      // DBCon db = new DBCon();

      if (fr_id.equals("")) {
        JOptionPane.showMessageDialog(null, "id를 확인해주세요",
            "ERROR_MESSAGE", JOptionPane.ERROR_MESSAGE);
      } else {

        try {
          String query = "select fr_name from friend where id = '" + id + "' and fr_id = '" + fr_id + "' ";
          db = new DBCon();
          dbf = new DbFunction();
          conn = db.getConnection();
          pstm = conn.prepareStatement(query);
          rs = pstm.executeQuery();

          String name = "";

          while (rs.next()) {
            name = rs.getString("fr_name");
          }

          if (!name.equals("")) {
            JOptionPane.showMessageDialog(null, "이미 추가 된 친구 입니다.",
                "id중복", JOptionPane.ERROR_MESSAGE);
          } else {

            String query2 = "select name from member where id = '" + fr_id + "'";
            pstm = conn.prepareStatement(query2);
            rs = pstm.executeQuery();
            String fr_name = "";
            while (rs.next()) {
              fr_name = rs.getString("name");
            }

            String query3 = "insert into friend values ('" + id + "','" + fr_id + "','" + fr_name + "')";
            dbf.insert(query3);
            JOptionPane.showMessageDialog(null, "추가 완료",
                "Success", JOptionPane.INFORMATION_MESSAGE);
            jtf_id.setText("");
          }

        } catch (SQLException ie) {
        } finally {
          try {
            if (rs != null) {
              rs.close();
            }
            if (pstm != null) {
              pstm.close();
            }
            if (conn != null) {
              conn.close();
            }
          } catch (Exception ie) {
            throw new RuntimeException(ie.getMessage());
          }
        }
      }

    }
  }
}
