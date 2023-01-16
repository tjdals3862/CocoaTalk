package cocoatalk.main;

import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.Vector;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import cocoatalk.client.ChatClient;
import cocoatalk.client.Room;
import cocoatalk.dialog.FriendAdd;
import cocoatalk.dialog.FriendProfile;
import cocoatalk.login.CocoaVO;
import cocoatalk.oracle.DBCon;
import cocoatalk.oracle.DbFreeCon;

public class ChatFriendList extends JFrame implements MouseListener, ActionListener {

  FriendAdd fa = null;
  MainForm mf = null;
  CocoaVO cVO = null;
  FriendProfile fp = null;
  ChatClient cc = null;

  List<String[]> fr_list;
  Set<String> friendlist = new HashSet<>();

  DefaultListModel<String> dlm_frnd;
  JScrollPane jsp;
  JList jl_frnd;

  String name = null;
  String kkkkk = null;
  String userID = null;

  Connection conn = null;
  PreparedStatement pstm = null;
  ResultSet rs = null;

  DBCon db = new DBCon();
  DbFreeCon dfc = null;

  JPanel frnd_north;
  JTextField jtf_search;
  JButton fr_search;
  JButton fr_invite;
  Font font;

  // 생성자
  public ChatFriendList(CocoaVO cVO) {
    this.cVO = cVO;
    // InitDisplay();
  }

  public void InitDisplay() {
    dlm_frnd = new DefaultListModel<>();
    getDB();
    frnd_north = new JPanel();
    add(frnd_north);
    jtf_search = new JTextField(23);
    fr_search = new JButton("검색");
    fr_invite = new JButton("초대");
    jl_frnd = new JList(dlm_frnd);
    font = cVO.getFontc();

    fr_search.addActionListener(this);
    fr_invite.addActionListener(this);
    frnd_north.setLayout(new FlowLayout());

    jsp = new JScrollPane(jl_frnd);

    jl_frnd.addMouseListener(this);
    jl_frnd.setFixedCellWidth(380);
    jl_frnd.setFixedCellHeight(50);
    jl_frnd.setVisibleRowCount(6);
    jl_frnd.setSize(390, 200);
    jl_frnd.setFont(font);
    jl_frnd.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);

    frnd_north.add(jtf_search);
    frnd_north.add(fr_search);
    frnd_north.add(fr_invite);

    this.add("North", frnd_north);
    this.add("Center", jsp);

    this.setSize(426, 380);
    this.setVisible(true);

    jl_frnd.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);

    jl_frnd.addListSelectionListener(new ListSelectionListener() {
      public void valueChanged(ListSelectionEvent e) {
        if (e.getValueIsAdjusting()) {
          System.out.println("선택 : " + jl_frnd.getSelectedValue());

          StringTokenizer st = new StringTokenizer((String) jl_frnd.getSelectedValue(), "#");
          String name = st.nextToken();
          String id = st.nextToken();

          friendlist.add(id);
          System.out.println("친구목록 : " + friendlist);
        }
      }
    });
  }

  public void searchFriend(String str) {
    dlm_frnd.clear();
    fr_list = new Vector<>();

    try {
      String sql = String.format("SELECT * FROM frlist_%s WHERE id = '%s' AND fr_name like '%%%s%%' ",
          cVO.getId(),
          cVO.getId(),
          str);
      conn = db.getConnection();
      pstm = conn.prepareStatement(sql);
      rs = pstm.executeQuery();

      while (rs.next()) {
        String name = rs.getString("FR_NAME");
        String ID = rs.getString("FR_ID");
        String[] data = { name, ID };
        fr_list.add(data);
      }

      for (int i = 0; i < fr_list.size(); i++) {
        int a = fr_list.size();
        String[] data = new String[a];
        data = fr_list.get(i);

        dlm_frnd.add(0, data[0] + "#" + data[1]); // data[0] : name, data[1] : ID
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  public void getDB() {
    dlm_frnd.clear();
    fr_list = new Vector<>();

    try {
      String sql = "SELECT FR_ID, FR_NAME FROM frlist_" + cVO.getId() + " where ID = '" + cVO.getId() + "'";
      conn = db.getConnection();
      pstm = conn.prepareStatement(sql);
      rs = pstm.executeQuery();
      while (rs.next()) {
        String name = rs.getString("FR_NAME");
        String ID = rs.getString("FR_ID");
        String[] data = { name, ID };
        fr_list.add(data);
      }

      for (int i = 0; i < fr_list.size(); i++) {
        int a = fr_list.size();
        String[] data = new String[a];
        data = fr_list.get(i);

        dlm_frnd.add(0, data[0] + "#" + data[1]); // data[0] : name, data[1] : ID
      }
    } catch (Exception e) {
    }
  }

  @Override
  public void mouseClicked(MouseEvent e) {

  }

  @Override
  public void actionPerformed(ActionEvent e) {
    Object obj = e.getSource();

    if (fr_search == obj) { // 검색 버튼 클릭
      if (jtf_search.getText() == null) {
        System.out.println(jtf_search.getText());
        getDB();

      } else {
        searchFriend(jtf_search.getText());
        jtf_search.setText("");
      }

    } else if (fr_invite == obj) {
      System.out.println("친구추가했긔");
      System.out.println("추가할 친구 몇명?  : " + friendlist.size());
      if (friendlist.size() == 0) {
        System.out.println("선택한 친구가 없음");
        String msg = "친구를 선택하세요";
        JOptionPane.showMessageDialog(this, msg, "ERROR",
            JOptionPane.INFORMATION_MESSAGE);
      } else {
        Iterator it = friendlist.iterator();
        ArrayList<String> list = new ArrayList<>(); // 친구 id 리스트
        ArrayList<Integer> room = new ArrayList<>(); // 방번호 리스트

        while (it.hasNext()) {

          // System.out.println(it.next());
          // id 값으로 room방이 있는지 체크
          list.add((String) it.next());
        }
        System.out.println("친구id리스트 추가 완료");
        try {
          DBCon dbcon = new DBCon();
          conn = dbcon.getConnection();
          StringBuilder sql = new StringBuilder();
          dfc = new DbFreeCon();
          sql.append(" select room from room_mem ");
          sql.append(" group by room ");
          sql.append(" having count(*) = " + list.size() + "+1 ");
          System.out.println("리스트 사이즈 : " + list.size());
          PreparedStatement pstmt = conn.prepareStatement(sql.toString());
          ResultSet rs = pstmt.executeQuery();
          while (rs.next()) {
            room.add((Integer) rs.getInt("room"));
            System.out.println(rs.getInt("room"));
          }
        } catch (Exception se) {
          se.printStackTrace();
        } finally {
          try {
            dfc.freeConnection(conn, pstm, rs);
          } catch (Exception ie) {
            throw new RuntimeException(ie.getMessage());
          }
        }

        System.out.println("카톡방 있는지 확인 시작");
        boolean isTrue = true;
        boolean isChk = true;
        System.out.println("room 사이즈 : " + room.size());
        int a = list.size() + 1;
        while (isTrue) {

          run_stop: for (int i = 0; i < room.size(); i++) {
            System.out.println(i + "번째 테스트 진행");
            try {
              DBCon dbcon = new DBCon();
              conn = dbcon.getConnection();
              StringBuilder sql = new StringBuilder();
              dfc = new DbFreeCon();
              sql.append(" select id from room_mem ");
              sql.append(" where room = " + room.get(i) + " ");
              PreparedStatement pstmt = conn.prepareStatement(sql.toString());
              ResultSet rs = pstmt.executeQuery();

              while (rs.next()) {
                run_start: for (int j = 0; j < list.size(); j++) {
                  System.out.println("========================");
                  System.out.println("fr id는 : " + rs.getString("id"));
                  System.out.println("id는 : " + cVO.getId());
                  System.out.println("list id는 : " + list.get(j));

                  if (rs.getString("id").equals(list.get(j))
                      || cVO.getId().equals(list.get(j))) {

                    System.out.println("break run start ID 동일");
                    a--;
                    break run_start;
                  }
                }
                System.out.println("a는 : " + a);
                // 해당 방이 있으면 isChk false
                if (a == 0) {
                  isChk = false;
                  isTrue = false;
                  System.out.println("break run stop");
                  break run_stop;
                }
              }
            } catch (Exception se) {
              se.printStackTrace();
            } finally {
              dfc.freeConnection(conn, pstm, rs);
            }
          }
          // for문이 다돌아도 체크되지 않으면 while 종료 후 채팅방생성
          isTrue = false;
        }

        // 채팅방생성
        if (isChk) {

          JOptionPane.showMessageDialog(this, "채팅방 생성 완료했습니다.", "info",
              JOptionPane.INFORMATION_MESSAGE);
          Room r = new Room();
          r.roomCreate(cVO.getId(), friendlist);
          this.dispose();
          friendlist.clear();
        } else {
          JOptionPane.showMessageDialog(this, "이미 존재하는 채팅방 있습니다.", "info",
              JOptionPane.INFORMATION_MESSAGE);
          friendlist.clear();
        }

      }

    }

  }

  @Override
  public void mousePressed(MouseEvent e) {
  }

  @Override
  public void mouseReleased(MouseEvent e) {
  }

  @Override
  public void mouseEntered(MouseEvent e) {
  }

  @Override
  public void mouseExited(MouseEvent e) {
  }

}
