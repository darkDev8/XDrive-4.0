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

package org.xdrive.threads;

import java.util.List;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JProgressBar;
import org.xdrive.ext.Box;

public class DownloadFileThread implements Runnable {

    private List<String> values;
    private String downloadPath;

    private JProgressBar bar;
    private JDialog dialog;
    private JLabel label;

    public DownloadFileThread(JDialog dialog, List<String> values, JProgressBar bar, JLabel label, String downloadPath) {
        this.values = values;
        this.downloadPath = downloadPath;

        this.bar = bar;
        this.dialog = dialog;
        this.label = label;
    }

    @Override
    public void run() {
        try {
            var percent = 0.0f;
            var uploadCount = 0;
            
            for (var value : values) {
                var path = Box.getVariables().get("userHome").concat(value);
                
                Box.getChannelSftp().get(path, downloadPath.concat("/").concat(value));
                uploadCount++;
                
                percent = (uploadCount * 100) / values.size();
                bar.setValue((int) percent);
                
                label.setText("Remaining files " + (values.size() - uploadCount));
            }
            
            if (bar.getValue() != 100) {
                bar.setValue(bar.getValue() + (100 - bar.getValue()));
            }
            
            Box.showMessageBox("Files downloaded", "All the files downloaded successfully.", "info", null);
            dialog.dispose();
        } catch (Exception e) {
            Box.showExceptionMessage("Failed to download files from server.\n" + e.getMessage(), "Download failed");
        }
    }

}
