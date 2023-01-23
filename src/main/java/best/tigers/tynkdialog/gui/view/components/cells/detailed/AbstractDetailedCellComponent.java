package best.tigers.tynkdialog.gui.view.components.cells.detailed;

import best.tigers.tynkdialog.gui.model.page.AbstractPageModel;
import best.tigers.tynkdialog.util.Assets;
import javax.swing.JPanel;

public abstract class AbstractDetailedCellComponent extends JPanel {

  private final AbstractPageModel abstractPageModel;

  public AbstractDetailedCellComponent(AbstractPageModel pageModel, boolean isSelected) {
    this.abstractPageModel = pageModel;
    setBorder(Assets.getDefaults().getBorder("MenuBar.border"));
    if (isSelected) {
      setBackground(Assets.getDefaults().getColor("List.selectionBackground"));
      setForeground(Assets.getDefaults().getColor("List.selectionForeground"));
    } else {
      setBackground(Assets.getDefaults().getColor("List.background"));
      setForeground(Assets.getDefaults().getColor("List.foreground"));
    }
  }

}
