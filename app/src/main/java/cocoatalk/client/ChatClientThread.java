package cocoatalk.client;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
// 채팅 클라이언트 쓰레드
import java.util.StringTokenizer;

import cocoatalk.oracle.DbFunction;

public class ChatClientThread extends Thread {

  ChatClient cc = null;
  String id = null;
  String fr_id = null;
  int room_num;
  LocalDate now = null;
  SimpleDateFormat dateFormat = null;
  DbFunction dbf = null;

  public ChatClientThread(ChatClient cc) {
    this.cc = cc;
  }

  @Override
  public void run() {

    while (true) {
      try {
        String message = "";
        message = (String) cc.ois.readObject();

        // 서버로 받은 메세지를 잘라 id, 친구id, 메세지로 구분
        StringTokenizer st = new StringTokenizer(message, "#");
        String msg = null;
        id = st.nextToken();
        room_num = Integer.parseInt(st.nextToken());
        msg = st.nextToken();

        Room room = new Room();
        // 받아온 id와 친구id로 채팅방 번호를 가져온다.
        // int room_num = room.getRoom(id, fr_id);

        // 현재 시간 구하기
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String strdate = formatter.format(date);

        // 메세지를 db에 전송
        String query = "insert into room_chat values ( " + room_num + " , '" + id + "', '" +
            msg + "', TO_DATE( '" + strdate + "','YYYY-MM-DD HH24:MI:SS' ))";
        dbf = new DbFunction();
        dbf.insert(query);

        // 서버로 받은 메세지 채팅창에 올린다.
        cc.sd_display.insertString(cc.sd_display.getLength(), msg + "\n", null);

      } catch (Exception e) {

      }
    }
  }
}
