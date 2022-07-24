package best.tigers.tynk_dialog.gui.old;

public interface Observable {
  void attachListener(Observer observer);
  void removeListener(Observer observer);
  void notifyListeners();
}
