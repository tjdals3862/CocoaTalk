package cocoatalk.chat;

public class ChatClientThread extends Thread {
  ChatClient cc = null;

  public ChatClientThread(ChatClient cc) {
    this.cc = cc;

  }
}
