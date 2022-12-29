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
  JButton delete;
  JPanel pf_south;

  // ko
  // JPanel grid_panel = new JPanel();

  public FriendProfile(FriendList fl) {
    this.fl = fl;
  }

  public void profileDisplay(boolean isOpen, String who) {
    new JDialog();
    pf_south = new JPanel();

    // ko - 버튼 생성
    chat_1to1 = new JButton("1:1 채팅");
    delete = new JButton("삭제");

    jlbl_name = new JLabel(who);//
    this.setLayout(null);
    this.add(chat_1to1);
    chat_1to1.setBounds(233, 428, 80, 80);
    this.add(delete);
    delete.setBounds(133, 428, 80, 80);
    this.setResizable(false);

    // ko - JDialog 남쪽에 버튼추가
    // this.add(chat_1to1, BorderLayout.SOUTH);
    // this.pf_south.setLayout(new BorderLayout());

    // chat_1to1.addMouseListener(this);
    // chat_1to1.setSize(30, 30);

    // pf_south.add(chat_1to1);
    // add(jlbl_name);
    // add("South", pf_south);

    setSize(330, 550);
    setVisible(isOpen);//
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
