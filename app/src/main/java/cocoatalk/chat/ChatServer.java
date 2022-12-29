package cocoatalk.chat;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class ChatServer extends JFrame implements Runnable, ActionListener {

  ChatServer cst = null;
  List<ChatServerThread> globalList = null;
  ServerSocket server = null;
  Socket socket = null;
  JTextArea jta_log = new JTextArea(10, 30);
  JScrollPane jsp_log = new JScrollPane(jta_log, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
      JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

  public ChatServer() {

  }

  // 화면그리기
  public void initDisplay() {

    this.add("Center", jsp_log);
    this.setSize(500, 400);
    this.setVisible(true);
  }

  public static void main(String[] args) {
    ChatServer cs = new ChatServer();
    cs.initDisplay();
    Thread th = new Thread(cs);
    th.start(); // run() 호출 - 지연발생함 - 클라이언트가 접속할때까지 기다림..
  }

  @Override
  public void run() {
    // TODO Auto-generated method stub

  }

  @Override
  public void actionPerformed(ActionEvent e) {
    // TODO Auto-generated method stub

  }

}
