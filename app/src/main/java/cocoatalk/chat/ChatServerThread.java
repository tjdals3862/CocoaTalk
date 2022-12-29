package cocoatalk.chat;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.StringTokenizer;

public class ChatServerThread extends Thread {

  ChatServer cs = null;
  Socket client = null;
  ObjectOutputStream oos = null;
  ObjectInputStream ois = null;

  // 현재 서버에 입장한 클라이언트 스레드의 닉네임 저장
  String chatName = null;

  public ChatServerThread(ChatServer cs) {
    this.cs = cs;
    this.client = cs.socket;

    try {
      oos = new ObjectOutputStream(client.getOutputStream());// 말하기
      ois = new ObjectInputStream(client.getInputStream());// 듣기

      String msg = (String) ois.readObject();
      StringTokenizer st = new StringTokenizer(msg);
      chatName = st.nextToken();
      System.out.println(chatName + "님이 입장");
      for (ChatServerThread cst : cs.Chatlist) {
        this.send(cst.chatName);
      }

      cs.Chatlist.add(this);
      this.broadCasting(msg);
    } catch (Exception e) {
    }

  }

  // 현재 입장해 있는 친구들 모두에게 메시지 전송하기 구현
  public void broadCasting(String msg) {
    for (ChatServerThread cst : cs.Chatlist) {
      cst.send(msg);
    }
  }

  // 클라이언트에게 말하기 구현
  public void send(String msg) {
    try {
      oos.writeObject(msg);
    } catch (Exception e) {
      e.printStackTrace();// stack에 쌓여 있는 에러메시지 이력 출력함
    }
  }

  @Override
  public void run() {

    String msg = null;
    boolean isStop = false;
    try {
      while (!isStop) {
        msg = (String) ois.readObject();
        if (msg != null) {

        }
      }
    } catch (Exception e) {

    }

  }

}
