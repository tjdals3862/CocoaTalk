package cocoatalk.client;

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
        cc.sd_display.insertString(cc.sd_display.getLength(), message + "\n", null);
        // cc.jta_display.append(message + "\n");
      } catch (Exception e) {

      }
    }
  }
}
