package cocoatalk.chat;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

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
  }

  // ------------------------------------
  // ArrayList에서 클라이언트 소켓 제거
  // 접속 후 나가버리는 경우 클릉 쓸 때 오류가 발생
  // ------------------------------------
  public void remove(Socket socketList) {
    for (Socket s : ChatServer.socketList) {
      if (client == s) {
        ChatServer.socketList.remove(client);
        break;
      }
    }
  }

  @Override
  public void run() {

    try {
      System.out.println(client + " : 연결됨");
      oos = new ObjectOutputStream(client.getOutputStream());// 말하기
      ois = new ObjectInputStream(client.getInputStream());// 듣기

      String msg = (String) ois.readObject(); // 채팅메세지
      chatName = "tomato"; // 채팅 이름 본인 name 호출
      System.out.println(chatName + "님이 입장");
      System.out.println(msg);
      // 채팅메세지를 client에 전달
      for (ChatServerThread cst : cs.cstlist) {
        System.out.println(cst);
        room.oos.writeObject(msg);
      }

    } catch (Exception e) {
    } finally {
      try {
        if (client != null) {
          client.close();
          // 접속 후 나가버린 클라이언트인 경우 ArrayList에서 제거
          remove(client);
        }
        ois = null;
        oos = null;
      } catch (IOException ex) {
      }
    }

  }

}
