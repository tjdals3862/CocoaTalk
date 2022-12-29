package cocoatalk.chat;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

class ServerHandler extends Thread {
  Socket sock = null;

  public ServerHandler(Socket sock) {
    this.sock = sock;
  }

  public void run() {
    InputStream fromServer = null;

    try {
      fromServer = sock.getInputStream();

      byte[] buf = new byte[1024];
      int count;

      while ((count = fromServer.read(buf)) != -1) {
        System.out.write(buf, 0, count);
      }
    } catch (IOException ie) {
      System.out.println(" 연결 종료");
    } finally {
      try {
        if (fromServer != null) {
          fromServer.close();
        }
        if (sock != null) {
          sock.close();
        }
      } catch (IOException e) {
      }
    }
  }
}

public class ChatClient2 {

  public static void main(String[] args) {
    Socket sock = null;

    try {
      sock = new Socket("localhost", 3000);
      System.out.println(sock + " : 연결됨");

      OutputStream toServer = sock.getOutputStream();

      // 서버에서 보내오는 값을 받기위한 쓰레드
      ServerHandler chandler = new ServerHandler(sock);
      chandler.start();

      byte[] buf = new byte[1024];
      int count;
      while ((count = System.in.read(buf)) != -1) {
        toServer.write(buf, 0, count);
        toServer.flush();
      }
    } catch (IOException e) {
      System.out.println("연결 종료");
    } finally {
      try {
        if (sock != null)
          sock.close();
      } catch (IOException e) {

      }
    }
  }
}
