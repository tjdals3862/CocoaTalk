package cocoatalk.chat;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class ChatServer extends Thread {

  static List<Socket> socketList = new Vector<>(); // 소켓 리스트
  List<ChatServerThread> cstlist = new Vector<>(); // 채팅방 리스트

  Socket socket = null;
  ServerSocket server = null;
  ChatServerThread cst = null;

  public ChatServer() {

  }

  public void init() {

    try {
      server = new ServerSocket(3000);
      System.out.println("접속 대기중");
      while (true) {
        socket = server.accept();
        socketList.add(socket);
        cst = new ChatServerThread(this);
        cstlist.add(cst);
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