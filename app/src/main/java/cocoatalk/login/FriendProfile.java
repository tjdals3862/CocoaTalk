package cocoatalk.login;

import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.event.MouseInputListener;

public class FriendProfile extends JDialog implements MouseInputListener {
  FriendList fl = null;

  JLabel jlbl_name;
  JButton chat_1to1;
  JPanel pf_north;

  public FriendProfile(FriendList fl) {
    this.fl = fl;
  }

  public void profileDisplay(boolean isOpen, String who) {
    new JDialog();
    pf_north = new JPanel();

    jlbl_name = new JLabel(who);
    chat_1to1 = new JButton();

    chat_1to1.addMouseListener(this);
    chat_1to1.setSize(30, 30);

    pf_north.add(chat_1to1);

    add(jlbl_name);
    add(pf_north);

    setSize(330, 550);
    setVisible(isOpen);
  }

  @Override
  public void mouseClicked(MouseEvent e) {
    Object obj = e.getSource();
    if (obj == chat_1to1) {
      dispose();
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
}
