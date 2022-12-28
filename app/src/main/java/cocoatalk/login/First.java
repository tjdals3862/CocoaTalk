package cocoatalk.login;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Graphics;

public class First extends JFrame {

  String imgPath = "D:\\TEMP\\";
  ImageIcon imageIcon = new ImageIcon(imgPath + "first.jpg");

  class MyPanel extends JPanel {
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

  }

}
