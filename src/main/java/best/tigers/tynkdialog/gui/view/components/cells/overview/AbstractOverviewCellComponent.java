package best.tigers.tynkdialog.gui.view.components.cells.overview;

import best.tigers.tynkdialog.gui.model.page.AbstractPageModel;
import best.tigers.tynkdialog.gui.view.components.QuickPair;
import best.tigers.tynkdialog.util.Assets;
import javax.swing.BoxLayout;
import javax.swing.JPanel;

public abstract class AbstractOverviewCellComponent extends JPanel {

  private final AbstractPageModel abstractPageModel;

  public AbstractOverviewCellComponent(AbstractPageModel pageModel, boolean isSelected) {
    abstractPageModel = pageModel;
    setBorder(Assets.getDefaults().getBorder("MenuBar.border"));
    if (isSelected) {
      setBackground(Assets.getDefaults().getColor("List.selectionBackground"));
      setForeground(Assets.getDefaults().getColor("List.selectionForeground"));
    } else {
      setBackground(Assets.getDefaults().getColor("List.background"));
      setForeground(Assets.getDefaults().getColor("List.foreground"));
    }
    var layout = new BoxLayout(this, BoxLayout.Y_AXIS);
    setLayout(layout);
    add(new QuickPair("Kind", abstractPageModel.getPage().getPageKind()), TOP_ALIGNMENT);
  }

  public abstract AbstractPageModel getPageModel();
}