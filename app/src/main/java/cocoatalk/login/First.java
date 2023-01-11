package cocoatalk.login;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.*;
//프로그램 실행시 첫 화면
public class First extends JFrame {

  String imgPath = "D:\\TEMP\\";
  ImageIcon imageIcon = new ImageIcon(imgPath + "loadingWalll.jpg");
  Toolkit toolkit = Toolkit.getDefaultToolkit();// 로고삽입
  Image img = toolkit.getImage(imgPath + "logo.png");// 로고삽입
  class MyPanel extends JPanel {
    // 내부클래스로 배경 이미지 처리
    public void paintComponent(Graphics g) {
      g.drawImage(imageIcon.getImage(), 0, 0, null);
      setOpaque(false);
      super.paintComponent(g);
    }
  }

  public void firstDisplay() {
    MyPanel mp = new MyPanel();
    this.add(mp);
    this.setTitle("COCOA TALK");
    this.setLocation(500, 100);
    this.setSize(410, 650);
    this.setVisible(true);
    this.setIconImage(img);// 로고삽입

  }

}
