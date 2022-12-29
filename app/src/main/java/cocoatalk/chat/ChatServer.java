package cocoatalk.chat;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class ChatServer {

  List<ServerSocket> serverList = new Vector<ServerSocket>(); // 채팅서버 리스트
  List<String> roomtList = new ArrayList<String>(); // 방이름
  List<Integer> port = new ArrayList<>(); // 포트번호

  Socket socket = null;
  ServerSocket server = null;
  ChatServerThread cst = null;
  List<ChatServerThread> Chatlist = null;

  public ChatServer() {

  }

  public void init() {
    boolean isStop = false;
    Chatlist = new Vector<>();

    try {
      server = new ServerSocket(3000);

      List<Socket> list = new Vector<>();
      while (!isStop) {
        System.out.println("접속 대기중");
        socket = server.accept();
        list.add(socket);
        ChatServerThread cst = new ChatServerThread(this);
        cst.start();
      }

    } catch (Exception e) {
      e.printStackTrace();
    }

  }

  public static void main(String[] args) {
    ChatServer cs = new ChatServer();
    cs.init();
  }

}
