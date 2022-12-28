package cocoatalk.login;

import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import java.awt.BorderLayout;

public class TalkMain extends JFrame implements ActionListener {
  // 선언부
  JButton jbtn_send = new JButton("보내기"); // 보내기 버튼
  JTextField jtf_mesaage = new JTextField(20); // JTextField 입력창 크기
  JTextArea jTextArea_display = new JTextArea(15, 38); // 대화창
  String cols[] = { "대화명" };
  String data[][] = new String[0][1];
  DefaultTableModel dtm = new DefaultTableModel(data, cols);
  JTable jtb = new JTable(dtm);
  JScrollPane jsp = new JScrollPane(jtb, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
      JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

  // 생성자
  TalkMain() {
  }

  public void initDisplay() {
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);// X버튼 누르면 종료
    // this.setContentPane(new MyPanel());
    this.setLayout(null);// 이거 없으면 보내기로만 꽊참
    jbtn_send.setBounds(280, 520, 100, 80);// 보내기 버튼 위치, 크기
    this.add(jtf_mesaage);
    jtf_mesaage.setBounds(10, 520, 260, 80);// JTextField 입력창 위치, 크기
    this.add(jbtn_send);// 보내기 붙이기
    this.add(jTextArea_display);
    this.setTitle("COCOA TALK");
    this.setLocation(500, 100);// 창 호출 위치
    this.setSize(410, 650);// 창 크기
    this.setVisible(true);// 화면 띄우기
  }

  // // 메인메소드
  // public static void main(String[] args) {
  // TalkMain MainForm = new TalkMain();
  // MainForm.initDisplay();

  // }

  @Override
  public void actionPerformed(ActionEvent e) {
    Object obj = e.getSource();

    // 채팅 이벤트 처리
    if (obj == jbtn_send || obj == jtf_mesaage) {
      // 입력된 메시지 얻기
      String message = jtf_mesaage.getText();

      // jTextArea_display 텍스트 영역에 출력
      // jTextArea_display.append(message + "\n");

      // jTextField_mesaage 텍스트 필드 초기화
      jtf_mesaage.setText("");
    }
  }
}
