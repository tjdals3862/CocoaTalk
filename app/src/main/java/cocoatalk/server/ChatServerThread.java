package cocoatalk.server;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
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
  String room_num = null;
  List<ChatServerThread> keyList = null;
  List<ChatServerThread> memlist = null;

  Iterator<ChatServerThread> it = null;

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
      // roomNum = st.nextToken(); // 룸 번호
      room_num = st.nextToken();
      msg = st.nextToken();
      System.out.println(chatName + "님이 입장");
      System.out.println(room_num + "방 채팅방에 입장");

      cs.cstMap.put(this, room_num);
      cs.cstlist.add(cs.cstMap);
      this.send(msg);

    } catch (

    Exception e) {
    }
  }

  // 현재 입장해 있는 친구들 모두에게 메시지 전송하기 구현
  public void broadCasting(String msg) {

    for (ChatServerThread cst : keyList) {
      for (int i = 0; i < memlist.size(); i++) {
        if (cst.equals(memlist.get(i))) {
          cst.send(msg);
        }
      }
    }
  }

  // 클라이언트에게 말하기 구현
  public void send(String msg) {
    try {
      String message = chatName + "#" + room_num + "#" + msg;
      oos.writeObject(message);
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
        // Map에 key, value를 가져온다.
        keyList = new Vector<>();
        memlist = new Vector<>();

        Map<ChatServerThread, String> map = new HashMap<>();
        map = cs.cstMap;

        Iterator<ChatServerThread> it = map.keySet().iterator();

        while (it.hasNext()) {
          ChatServerThread key = it.next();
          String value = cs.cstMap.get(key);
          keyList.add(key);
          System.out.println(room_num + " 의 value : " + value + "추가되어야하는 id :  " + frName);

          if (value.equals(room_num)) {
            memlist.add(key);
            System.out.println("추가되는 : " + value);
          }
        }
        System.out.println(room_num + "의 리스트 사이즈 : " + memlist.size());
        // this.broadCasting(msg);

        msg = (String) ois.readObject();
        StringTokenizer st = null;
        if (msg != null) {
          st = new StringTokenizer(msg, "#");
        }
        String nickName = st.nextToken();
        String room_num = st.nextToken();
        String message = st.nextToken();

        System.out.println("run : " + message);
        broadCasting(message);

      }
    } catch (Exception e) {
      // TODO: handle exception
    }

  }

}