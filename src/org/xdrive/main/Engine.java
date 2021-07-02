/*
XDrive 4.0 is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with this program.  If not, see <https://www.gnu.org/licenses/>.
*/

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
