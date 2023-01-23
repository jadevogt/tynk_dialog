package best.tigers.tynkdialog.gui.view.components;

import best.tigers.tynkdialog.util.Assets;
import com.formdev.flatlaf.intellijthemes.FlatLightFlatIJTheme;
import com.formdev.flatlaf.intellijthemes.FlatMaterialDesignDarkIJTheme;
import java.awt.Component;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.SwingUtilities;

public interface DynamicUI {

  default void updateUI(boolean isDark) {

  }

  default void removeListener() {

  }

  default void installListeners() {

  }
}