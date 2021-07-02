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

package org.xdrive.root;

import com.sdk.network.NetworkTools;
import com.sdk.storage.file.TextFile;
import com.xdapi.connection.XDriveConnection;
import com.xdapi.repository.XDbRepository;
import java.io.IOException;
import javax.swing.UIManager;
import org.xdrive.ext.Box;
import org.xdrive.server.ServerConnection;

public class Boot {
      public Boot loadBuildNumber() {
        try {
            var file = new TextFile("build.txt");

            if (file.exists()) {
                var firstLine = file.readFirstLine(true);

                if (Box.getStrings().isNumber(firstLine)) {
                    var number = Integer.parseInt(firstLine) + 1;
                    Box.getVariables().put("build", String.valueOf(number));

                    file.write(String.valueOf(number));
                } else {
                    Box.getVariables().put("build", "1");
                    file.write("1");
                }
            } else {
                file.create();
                file.write("1");
                Box.getVariables().put("build", "1");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return this;
    }

    public Boot loadSystemTime() {
        Box.getVariables().put("time", Box.getOs().getTime());
        return this;
    }

    public Boot loadSystemDate() {
        Box.getVariables().put("date", Box.getOs().getDate());
        return this;
    }

    public Boot loadOS() {
        Box.getVariables().put("os", Box.getOs().getOSName());
        return this;
    }

    public Boot loadEncryptionKey() {
        Box.getVariables().put("enc", "23549918*?");
        return this;
    }

    public Boot loadPath() {
        Box.getVariables().put("path", Box.getOs().getHomeUser());
        return this;
    }
    
     public Boot loadMaxUsers() {
        Box.getVariables().put("maxUsers", "10");
        return this;
    }

    public Boot loadUserSpace() {
        Box.getVariables().put("maxSpace", "104857600");
        return this;
    }

    public Boot loadInternetConnection() {
        boolean status = new NetworkTools("http://www.google.com").internetStatus();

        if (!status) {
            Box.showExceptionMessage("You need active internet connection to continue.", "No internet connection.");
            System.exit(-1);
        }

        Box.getVariables().put("eth", String.valueOf(status));
        return this;
    }
    
    public Boot loadDatabaseConnection() {
        var xdc = new XDriveConnection(3306, "host", "username", "password", "databsaeName");
        var status = xdc.connect();
        
        if (!status) {
            Box.showExceptionMessage("Unable to connect to the database.\n", "Database connection failed");
            System.exit(-1);
        }
        
        Box.setXdb(new XDbRepository(xdc));
        return this;
    }
    
    public Boot loadServerConnection() {
        var sc = new ServerConnection("ip", "username", "password", 22);
        var status = sc.connect();
        
        if (!status) {
            System.exit(-1);
        }
        
        Box.setChannelSftp(sc.getChannelSftp());
        return this;
    }

    public Boot loadUIManager() {
        UIManager.put("OptionPane.buttonFont", Box.getFont());
        UIManager.put("OptionPane.messageFont", Box.getFont());
        UIManager.put("Button.buttonFont", Box.getFont());

        return this;
    }
    
        public Boot bootAll() {
        return loadSystemDate()
                .loadSystemTime()
                .loadUIManager()
                .loadOS()
                .loadMaxUsers()
                .loadUserSpace()
                .loadPath()
                .loadEncryptionKey()
                .loadInternetConnection()
                .loadDatabaseConnection()
                .loadServerConnection();
    }
}
