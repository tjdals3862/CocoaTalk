package cocoatalk.login;

import java.awt.Font;
import javax.swing.ImageIcon;

public class CocoaVO {

  String id;
  String name;

  public String getName() {
    return this.name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getId() {
    return this.id;
  }

  public void setId(String id) {
    this.id = id;
  }

  Font fontc;

  public Font getFontc() {
    return this.fontc;
  }

  public void setFontc(Font fontc) {
    this.fontc = fontc;
  }

  ImageIcon theme;

  public ImageIcon getTheme() {
    return this.theme;
  }

  public void setTheme(ImageIcon theme) {
    this.theme = theme;
  }

}
