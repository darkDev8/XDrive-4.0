package org.xdrive.main;

import org.xdrive.ext.Box;
import org.xdrive.forms.FrmMain;
import org.xdrive.root.Boot;

public class Engine {

    public static void main(String[] args) {
        try {
            new Boot().bootAll().loadBuildNumber();
            javax.swing.UIManager
                    .setLookAndFeel("com.formdev.flatlaf.intellijthemes.FlatNordIJTheme");
            
            java.awt.EventQueue.invokeLater(() -> {
                new FrmMain().setVisible(true);
            });
        } catch (Exception e) {
            Box.showExceptionMessage("Theme configuration failed.\n" + e.getMessage(), "Theme load failed");
            System.exit(-1);
        }
    }
}
