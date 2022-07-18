package best.tigers.tynk_dialog;

import best.tigers.tynk_dialog.game.Dialog;
import best.tigers.tynk_dialog.game.DialogPage;

public class Main {
  public static void main(String... args) {
    var test = new DialogPage();
    var testTwo = new DialogPage();
    var testThree = new DialogPage();
    var testContainerThree = new Dialog();
    var testContainerTwo = new Dialog();
    var testContainer = new Dialog();
    testContainer.addPage(test);
    testContainer.addPage(testTwo);
    testContainer.addPage(testThree);
    System.out.println(testContainerThree.serialize().toString());
    System.out.println(testContainerTwo.serialize().toString());
    System.out.println(testContainer.serialize().toString());
  }
}
