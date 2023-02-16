package best.tigers.tynkdialog.gui.view.components.cells.detailed;

import best.tigers.tynkdialog.game.Constants;
import best.tigers.tynkdialog.gui.model.page.AbstractPageModel;
import best.tigers.tynkdialog.gui.model.page.TalkPageModel;
import best.tigers.tynkdialog.supertext.SuperTextEditorKit;
import best.tigers.tynkdialog.util.Assets;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Insets;
import javax.swing.JEditorPane;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.plaf.ComponentUI;

public class TalkDetailedCellComponent extends AbstractDetailedCellComponent {

  public TalkDetailedCellComponent(AbstractPageModel pageModel,
      boolean isSelected) {
    super(pageModel, isSelected);
    var contentField = new NoWrapEditorPane();
    var talkPageModel = (TalkPageModel) pageModel;
    var scrollPane = new JScrollPane();

    scrollPane.setViewportView(contentField);
    scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
    scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);

    contentField.setMargin(new Insets(0, 0, 0, 0));
    contentField.setFont(Assets.getInstance().getTerminus());
    contentField.setForeground(Constants.TextColor.WHITE.toAWT());
    contentField.setBackground(Constants.TextColor.BACKGROUND.toAWT());
    contentField.setContentType("text/supertext");
    contentField.setEditorKit(new SuperTextEditorKit());
    contentField.setPreferredSize(new Dimension(600, 100));

    contentField.setText(talkPageModel.getContent());
    add(scrollPane);
  }

  static class NoWrapEditorPane extends JEditorPane {

    @Override
    public boolean getScrollableTracksViewportWidth() {
      Component parent = getParent();
      ComponentUI ui = getUI();
      return parent == null || (ui.getPreferredSize(this).width <= parent.getSize().width);
    }
  }
}
