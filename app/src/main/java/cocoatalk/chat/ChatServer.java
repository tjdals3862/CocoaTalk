package cocoatalk.chat;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

public class ChatServer extends Thread {

  // static List<Socket> socketList = new Vector<>(); // 소켓 리스트
  // List<ChatServerThread> cstlist = null; // 채팅방 리스트
  List<Map<ChatServerThread, String>> cstlist = new Vector<>();
  Map<ChatServerThread, String> cstMap = new HashMap<>();;
  Socket socket = null;
  ServerSocket server = null;
  ChatServerThread cst = null;

  public ChatServer() {

  }

  @Override
  public void run() {

    // cstlist = new Vector<>();
    try {
      server = new ServerSocket(3000);
      System.out.println("접속 대기중");
      while (true) {
        socket = server.accept();
        // socketList.add(socket);
        ChatServerThread cst = new ChatServerThread(this, socket);
        cstMap.put(cst, "id");
        cstlist.add(cstMap);
        cst.start();

      }

    } catch (Exception e) {
      e.printStackTrace();
    }

  }

  public static void main(String[] args) {
    ChatServer cs = new ChatServer();
    Thread th = new Thread(cs);
    th.start();
  }

}