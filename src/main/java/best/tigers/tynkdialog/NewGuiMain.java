package best.tigers.tynkdialog;

import best.tigers.tynkdialog.gui.controller.PrimaryListController;
import best.tigers.tynkdialog.newgui.model.DialogBean;
import best.tigers.tynkdialog.newgui.model.FlatPageBean;
import best.tigers.tynkdialog.newgui.view.DialogBeanView;
import best.tigers.tynkdialog.util.Assets;
import com.formdev.flatlaf.intellijthemes.FlatMaterialDesignDarkIJTheme;

import javax.swing.*;
import java.awt.*;

public class NewGuiMain {
    public static void main(String[] args) {
        Assets.getInstance().refreshAssets();
        FlatMaterialDesignDarkIJTheme.setup();
        Assets.runIntegrations();
        JEditorPane.registerEditorKitForContentType("text/supertext",
                "best.tigers.tynkdialog.supertext.SuperTextEditorKit"
        );
        var bean = new DialogBean();
        var pageBean = new FlatPageBean();
        pageBean.setFlat("testFlat");
        EventQueue.invokeLater(() -> {
            var frame1 = new JFrame();
            var view1 = new DialogBeanView(bean);
            var frame2 = new JFrame();
            var view2 = new DialogBeanView(bean);
            frame1.add(view1.getPanel());
            frame2.add(view2.getPanel());

            frame1.setVisible(true);
            frame2.setVisible(true);
            frame1.pack();
            frame2.pack();
            bean.addPage(pageBean);
        });
    }
}
