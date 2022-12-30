package cocoatalk.chat;

public class ChatClientThread extends Thread {

  ChatClient cc = null;

  public ChatClientThread(ChatClient cc) {
    this.cc = cc;
  }

  @Override
  public void run() {

    while (true) {
      try {
        String message = "";
        message = (String) cc.ois.readObject();
        System.out.println("서버에서 전송된 message : " + message);
        cc.jta_display.append(message + "\n");
      } catch (Exception e) {

      }
    }
  }
}
