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
  JButton jbtn_chat = new JButton("1:1 채팅");
  JButton jbtn_del = new JButton("삭제");
  JPanel pf_south;

  // ko
  // JPanel grid_panel = new JPanel();

  public FriendProfile(FriendList fl) {
    this.fl = fl;
  }

  public void profileDisplay(boolean isOpen, String who) {
    new JDialog();
    pf_south = new JPanel();
    jlbl_name = new JLabel(who);//

    this.setLayout(null);
    this.add(jbtn_chat);
    this.add(jbtn_del);

    jbtn_chat.setBounds(233, 428, 80, 80);
    jbtn_del.setBounds(133, 428, 80, 80);

    this.setResizable(false);

    setSize(330, 550);
    setVisible(isOpen);//
  }

  @Override
  public void mouseClicked(MouseEvent e) {
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
}
