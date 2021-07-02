package org.xdrive.ext;

import com.jcraft.jsch.ChannelSftp;
import com.sdk.swingui.Swing;
import com.sdk.swingui.dialogs.MessageBox;
import com.sdk.tools.OperatingSystem;
import java.awt.Font;
import com.sdk.console.Console;
import com.sdk.data.types.Strings;
import com.xdapi.repository.XDbRepository;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;

public class Box {

    private static Map<String, String> variables;
    private static Font font;

    private static Strings strings;
    private static OperatingSystem os;
    private static Console console;
    private static Swing swing;
    private static MessageBox msg;
    private static XDbRepository xdb;
    private static ChannelSftp channelSftp;

    public static final int TOOLBAR_BUTTON_SIZE = 18;

    static {
        variables = new HashMap<>();
        font = new Font("Inter Medium", Font.PLAIN, 14);

        strings = new Strings();
        os = new OperatingSystem();
        console = new Console(2, 40, false);
        swing = new Swing();

        msg = new MessageBox(new String[]{"Ok"}, "null");
        msg.setMessageFont(font);
        msg.setButtonFont(font);
        msg.setMessageType(JOptionPane.ERROR_MESSAGE);
    }

    public static Map<String, String> getVariables() {
        return variables;
    }

    public static Strings getStrings() {
        return strings;
    }

    public static OperatingSystem getOs() {
        return os;
    }

    public static Console getConsole() {
        return console;
    }

    public static Swing getSwing() {
        return swing;
    }

    public static Font getFont() {
        return font;
    }

    public static MessageBox getMsg() {
        return msg;
    }

    public static XDbRepository getXdb() {
        return xdb;
    }

    public static void setXdb(XDbRepository xdb) {
        Box.xdb = xdb;
    }

    public static ChannelSftp getChannelSftp() {
        return channelSftp;
    }

    public static void setChannelSftp(ChannelSftp channelSftp) {
        Box.channelSftp = channelSftp;
    }

    public static int showMessageBox(String title, String message, String type, String[] buttons) {
        msg.setTitle(title);
        msg.setMessage(message);

        if (strings.isNullOrEmpty(type)) {
            msg.setMessageType(JOptionPane.WARNING_MESSAGE);
        } else {
            if (strings.isNullOrEmpty(type)) {
                msg.setMessageType(JOptionPane.ERROR_MESSAGE);
            } else {
                switch (type.toLowerCase()) {
                    case "error":
                        msg.setMessageType(JOptionPane.ERROR_MESSAGE);
                        break;

                    case "warning":
                        msg.setMessageType(JOptionPane.WARNING_MESSAGE);
                        break;

                    case "info":
                        msg.setMessageType(JOptionPane.INFORMATION_MESSAGE);
                        break;

                    case "none":
                        msg.setMessageType(JOptionPane.NO_OPTION);
                        break;

                }
            }
        }

        if (strings.isNullOrEmpty(buttons)) {
            msg.setButtons(new String[]{"Ok"});
        } else {
            msg.setButtons(buttons);
        }

        return msg.show();
    }

    public static int showExceptionMessage(String message, String title) {
        return showMessageBox(title, message, "error", null);
    }

    public static void setTableRowCenter(JTable table) {
        var renderer = (DefaultTableCellRenderer) table.getDefaultRenderer(Object.class);
        renderer.setHorizontalAlignment(SwingConstants.CENTER);
    }

    public static void setTableColumnHeaderPosition(JTable table, String position) {
        switch (position.toLowerCase()) {
            case "right":
                ((DefaultTableCellRenderer) table.getTableHeader().getDefaultRenderer())
                        .setHorizontalAlignment(JLabel.RIGHT);
                break;

            case "left":
                ((DefaultTableCellRenderer) table.getTableHeader().getDefaultRenderer())
                        .setHorizontalAlignment(JLabel.LEFT);
                break;

            case "center":
                ((DefaultTableCellRenderer) table.getTableHeader().getDefaultRenderer())
                        .setHorizontalAlignment(JLabel.CENTER);
                break;
        }
    }
}
