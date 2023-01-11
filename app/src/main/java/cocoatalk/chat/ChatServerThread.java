package cocoatalk.chat;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;
import java.util.Vector;

public class ChatServerThread extends Thread {
  ChatServer cs = null;
  Socket client = null;
  ObjectOutputStream oos = null;
  ObjectInputStream ois = null;
  // 현재 서버에 입장한 클라이언트 스레드의 닉네임 저장
  String chatName = null;
  String frName = null;
  String nickname = null;
  List<ChatServerThread> keyList = null;
  List<ChatServerThread> memlist = null;

  public ChatServerThread() {

  }

  public ChatServerThread(ChatServer cs, Socket socket) {
    this.cs = cs;
    this.client = socket;
  }

  public void con() {
    try {
      System.out.println(client + " : 연결됨");
      oos = new ObjectOutputStream(client.getOutputStream());// 말하기
      ois = new ObjectInputStream(client.getInputStream());// 듣기

      String msg = (String) ois.readObject(); // 채팅메세지
      System.out.println(msg);
      StringTokenizer st = new StringTokenizer(msg, "#");
      chatName = st.nextToken();
      frName = st.nextToken();
      msg = st.nextToken();
      System.out.println(chatName + "님이 입장");
      System.out.println(frName + "님과 채팅방에 입장");

      // Map에 key, value를 가져온다.
      keyList = new Vector<>();
      memlist = new Vector<>();
      Iterator<ChatServerThread> it = cs.cstMap.keySet().iterator();

      while (it.hasNext()) {
        ChatServerThread key = it.next();
        String value = cs.cstMap.get(key);
        keyList.add(key);

        if (value.equals(chatName)) {
          memlist.add(key);
        }
      }
      // 입장용 테스트 메세지 ( id 가 입장했습니다.)
      this.broadCasting(msg);

    } catch (

    Exception e) {
    }
  }

  // 현재 입장해 있는 친구들 모두에게 메시지 전송하기 구현
  public void broadCasting(String msg) {
    for (ChatServerThread cst : keyList) {

      cst.send(msg);
    }
  }

  // 클라이언트에게 말하기 구현
  public void send(String msg) {
    try {
      oos.writeObject(msg);
      System.out.println("send : " + msg);
    } catch (Exception e) {
      e.printStackTrace();// stack에 쌓여 있는 에러메시지 이력 출력함
    }
  }

  @Override
  public void run() {
    con();
    String msg = null;

    try {
      while (true) {
        msg = (String) ois.readObject();
        StringTokenizer st = null;
        if (msg != null) {
          st = new StringTokenizer(msg, "#");
        }
        String nickName = st.nextToken();
        String frName = st.nextToken();
        String message = st.nextToken();

        System.out.println("run : " + message);
        broadCasting(message);

      }
    } catch (Exception e) {
      // TODO: handle exception
    }

  }

}