package org.xdrive.threads;

import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.SftpException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Map;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JProgressBar;
import org.xdrive.ext.Box;

public class UploadFileThread implements Runnable {

    private Map<String, String> files;
    private boolean replaceFiles;

    private JProgressBar bar;
    private JList list;
    private JDialog dialog;
    private JLabel label;

    public UploadFileThread(Map<String, String> files, boolean replaceFiles, JProgressBar bar, JList list, JLabel label, JDialog dialog) {
        this.files = files;
        this.replaceFiles = replaceFiles;
        this.bar = bar;
        this.list = list;
        this.label = label;
        this.dialog = dialog;
    }

    @Override
    public void run() {
        try {
            var foundUserDirectory = false;
            var filePath = "/";
            var uploaded = 0;

            label.setText("Setting user directory...");
            Vector<ChannelSftp.LsEntry> serverFiles = Box.getChannelSftp().ls(filePath);    //enter root directory
            for (ChannelSftp.LsEntry item : serverFiles) {
                if (item.getFilename().equals(Box.getVariables().get("loginUser"))) {    //check for existing user
                    foundUserDirectory = true;
                }
            }

            filePath = "/".concat(Box.getVariables().get("loginUser")).concat("/");
            if (!foundUserDirectory) {
                label.setText("Creating new directory...");

                Box.getChannelSftp().mkdir(filePath);     //create user directory if not exist.
            }

            label.setText("Setting upload path...");
            Box.getChannelSftp().cd(filePath);      //etner user directory

            label.setText("Checking files...");
            serverFiles = Box.getChannelSftp().ls(filePath);
            if (replaceFiles) {
                for (ChannelSftp.LsEntry item : serverFiles) {
                    for (Map.Entry<String, String> fileItem : files.entrySet()) {
                        if (fileItem.getKey().equals(item.getFilename())) {
                            Box.getChannelSftp().rm(filePath.concat(item.getFilename()));       //remove files from server if exists
                        }
                    }
                }
            } else {
                for (ChannelSftp.LsEntry item : serverFiles) {
                    files.entrySet().removeIf(e -> e.getKey().equals(item.getFilename()));      //search for existing files and remove them
                }
            }

            label.setText("Starting upload...");
            for (Map.Entry<String, String> uploadItems : files.entrySet()) {
                var currentFile = new File(uploadItems.getValue());
                var fis = new FileInputStream(currentFile);     //create FileInputStream object from file object

                Box.getChannelSftp().put(fis, uploadItems.getKey());        //upload file to the server
                uploaded++;     //increase upload counts

                var percent = (uploaded * 100) / files.size();
                bar.setValue(percent);      //set progressbar value

                var uploadSize = (files.size() - uploaded);

                if (uploadSize == 0) {
                    label.setText("Upload finished successfully.");
                } else {
                    label.setText("Uploading (" + uploadSize + " file remains)");
                }
            }

            bar.setValue(bar.getValue() + (100 - bar.getValue()));      //fill remain progressbar
            Box.getVariables().put("uploadStatus", "true");
            Box.showMessageBox("Files uploaded", "All the files uploaded successfully.", "info", null);
            dialog.dispose();
        } catch (Exception e) {
            Box.showExceptionMessage("Failed to upload all files to the server.\n" + e.getMessage(), "Upload failed");
        }
    }

}
