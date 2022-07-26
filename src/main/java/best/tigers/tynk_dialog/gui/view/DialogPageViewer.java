package best.tigers.tynk_dialog.gui.view;

public interface DialogPageViewer {
  String getSpeaker();

  public void setSpeaker(String newSpeaker);

  String getContent();

  public void setContent(String newContent);

  public void setBlip(String newBlip);
  public String getBlip();
  public void setStyle(String newStyle);
  public String getStyle();
}
