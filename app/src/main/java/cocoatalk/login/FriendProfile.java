package cocoatalk.login;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.event.MouseInputListener;

import cocoatalk.chat.ChatClient;
import cocoatalk.oracle.DBCon;
import cocoatalk.oracle.DbFunction;

public class FriendProfile extends JDialog implements MouseInputListener, ActionListener {
  FriendList fl = null;
  CocoaVO cVO = null;
  ChatClient cc = null;
  MainForm mf = null;
  JLabel jlbl_name;
  JButton jbtn_chat = new JButton("1:1 채팅");
  JButton jbtn_del = new JButton("삭제");
  JPanel pf_south;
  String name = null;

  DBCon db = null;
  DbFunction dbf = null;

  // ko
  // JPanel grid_panel = new JPanel();

  public FriendProfile(FriendList fl) {
    this.fl = fl;
  }

  public void profileDisplay(boolean isOpen, String who) {
    new JDialog();
    pf_south = new JPanel();
    jlbl_name = new JLabel(who);//
    name = who;

    this.setLayout(null);
    this.add(jbtn_chat);
    this.add(jbtn_del);

    jbtn_chat.setBounds(233, 428, 80, 80);
    jbtn_del.setBounds(133, 428, 80, 80);
    jbtn_chat.addActionListener(this);
    jbtn_del.addActionListener(this);

    this.setResizable(false);
    this.setTitle(who);

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

  @Override
  public void actionPerformed(ActionEvent e) {
    Object obj = e.getSource();
    if (jbtn_chat == obj) {
      cc = new ChatClient();
      cc.initDisplay();
    } else if (jbtn_del == obj) {

      String query = "delete from friend where fr_name = '" + name + "'";
      db = new DBCon();
      dbf = new DbFunction();
      dbf.insert(query);
      JOptionPane.showMessageDialog(null, "삭제되었습니다.",
          "INFO_MESSAGE", JOptionPane.INFORMATION_MESSAGE);

      this.dispose();
    }
  }
}
