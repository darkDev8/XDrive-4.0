package org.xdrive.server;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import java.util.Properties;
import org.xdrive.ext.Box;

public class ServerConnection {

    private String ip;
    private String username;
    private String password;

    private int port;
    private ChannelSftp channelSftp;

    public ServerConnection(String ip, String username, String password, int port) {
        this.ip = ip;
        this.username = username;
        this.password = password;
        this.port = port;
    }
    
    public ServerConnection() {
        
    }

    public boolean connect() {
        var jSch = new JSch();
        Session session = null;
        Channel channel = null;

        try {
            session = jSch.getSession(username, ip, port);
            session.setPassword(password);

            var config = new Properties();
            config.put("StrictHostKeyChecking", "no");
            session.setConfig(config);
            session.setTimeout(10000);
            session.connect();

            channel = session.openChannel("sftp");
            channel.connect();

            channelSftp = (ChannelSftp) channel;
            return true;
        } catch (Exception e) {
            Box.showExceptionMessage("Failed to connect to the server.\n" + e.getMessage(), "Server connection failed");
            return false;
        }
    }

    public ChannelSftp getChannelSftp() {
        return channelSftp;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }
    
    
}
