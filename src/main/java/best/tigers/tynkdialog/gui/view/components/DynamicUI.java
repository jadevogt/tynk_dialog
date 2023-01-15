package best.tigers.tynkdialog.gui.view.components;

import best.tigers.tynkdialog.util.Assets;
import com.formdev.flatlaf.intellijthemes.FlatLightFlatIJTheme;
import com.formdev.flatlaf.intellijthemes.FlatMaterialDesignDarkIJTheme;
import com.jthemedetecor.OsThemeDetector;
import java.awt.Component;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.SwingUtilities;

public interface DynamicUI {

  default void updateUI(boolean isDark) {
    SwingUtilities.invokeLater(() -> {
      if (isDark) {
        // The OS switched to a dark theme
        FlatMaterialDesignDarkIJTheme.setup();
        SwingUtilities.updateComponentTreeUI(Assets.findWindow((Component) this));
      } else {
        // The OS switched to a light theme
        FlatLightFlatIJTheme.setup();
        SwingUtilities.updateComponentTreeUI(Assets.findWindow((Component) this));
      }
    });
  }

  default void removeListener() {
    var themeDetector = OsThemeDetector.getDetector();
    themeDetector.removeListener(this::updateUI);
  }

  default void installListeners() {
    var themeDetector = OsThemeDetector.getDetector();
    themeDetector.registerListener(this::updateUI);
    Assets.findWindow((Component) this).addWindowListener(new WindowAdapter() {
      @Override
      public void windowClosing(WindowEvent e) {
        super.windowClosing(e);
        removeListener();
      }
    });
  }
}