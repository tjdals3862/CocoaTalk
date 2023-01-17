package cocoatalk.main;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Font;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import cocoatalk.login.CocoaVO;

public class SearchMainForm extends JPanel implements ActionListener {
  public static final String content = null;
  // 선언
  CocoaVO cVO = null;
  // FriendList fl = new FriendList(cVO);
  // ChatList cl = new ChatList(cVO);
  NaverConnect nc = new NaverConnect();
  // MainForm MainForm = new MainForm(cVO);

  String imgPath = "app\\src\\main\\java\\cocoatalk\\images\\";
  ImageIcon ig = new ImageIcon(imgPath + "setting.jpg");

  public void paintComponent(Graphics g) {
    g.drawImage(ig.getImage(), 0, 0, null);
    setOpaque(false);
    super.paintComponent(g);
  } // end of MyPanel

  JScrollPane jsp;
  JPanel jp_search;
  JTextField searchTextField;
  JTextArea searchTextArea = new JTextArea();
  JButton searchBtn;
  Font font;

  // 생성자
  SearchMainForm(CocoaVO cVO) {
    this.cVO = cVO;
    Search_init();

  }

  public void Search_init() {

    font = cVO.getFontc();
    jp_search = new JPanel();
    searchBtn = new JButton("검색");
    searchTextField = new JTextField(28);
    jsp = new JScrollPane(searchTextArea);

    jp_search.add(searchTextField);
    this.setLayout(new BorderLayout());
    this.add("North", jp_search);
    jp_search.add(searchBtn = new JButton("검색"));
    jp_search.add("North", searchTextField);
    jp_search.add("North", searchBtn);
    this.add("Center", jsp);
    searchTextArea.setFont(font);

    // 검색버튼
    searchBtn.setBounds(320, 100, 70, 31);
    searchBtn.setBackground(Color.pink);
    searchBtn.addActionListener(this);
    jp_search.setOpaque(false);

    // 검색 결과창
    jsp.setPreferredSize(new Dimension(390, 315));

    this.setSize(426, 500);
    this.setVisible(true);

  }

  @Override
  public void actionPerformed(ActionEvent e) {
    Object obj = e.getSource();
    if (obj == searchBtn) {
      String msg = searchTextField.getText();
      if (msg.equals("")) {
        System.out.println("빈값입니다.");
      } else {
        nc.search(msg);
        searchTextArea.setText(null);

        for (int i = 0; i < nc.titlelist.size(); i++) {
          searchTextArea.append(nc.titlelist.get(i) + "\n");
          searchTextArea.append(nc.catelist.get(i) + "\n");
          searchTextArea.append(nc.addlist.get(i) + "\n");
          searchTextArea.append(nc.linklist.get(i) + "\n");
          searchTextArea.append("================================================" + "\n");
        }
      }

    }
  } // 이벤트
} // cls SearchPanel2