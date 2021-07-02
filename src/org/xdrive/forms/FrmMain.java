package org.xdrive.forms;

import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.SftpException;
import com.sdk.security.SecurityTools;
import com.sdk.storage.base.FileOperation;
import com.sdk.tools.ExternalTools;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JTable;
import javax.swing.RowFilter;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import org.apache.commons.io.FilenameUtils;
import org.xdrive.ext.Box;
import org.xdrive.root.Boot;

public class FrmMain extends javax.swing.JFrame {

    private JPopupMenu popup;

    private JMenuItem miDelete = null;
    private JMenuItem miDownload = null;
    private JMenuItem miFileProperties = null;
    private JMenuItem miRename = null;
    private JMenuItem miShowContent = null;
    private JMenuItem miCopyName = null;

    public FrmMain() {
        initComponents();
        login();

        miDelete = new JMenuItem("Delete");
        miDownload = new JMenuItem("Download");
        miFileProperties = new JMenuItem("File properties");
        miRename = new JMenuItem("Rename");
        miShowContent = new JMenuItem("Show content");
        miCopyName = new JMenuItem("Copy name");

        setFormItems();
        loadFilesInformation();
        setTablePopupMenu();
        loadTimer();
    }

    private void setFormItems() {
        Box.getSwing().initializeForm(this, true, "english", Box.getFont());

        Box.getSwing().setComponentIcon(lblDashboardIcon, "/org/xdrive/img/dashboard.png", 74, 74);
        Box.getSwing().setComponentIcon(btnRefreshTable, "/org/xdrive/img/refresh.png",
                Box.TOOLBAR_BUTTON_SIZE, Box.TOOLBAR_BUTTON_SIZE);

        Box.getSwing().setComponentIcon(btnCheckServerConnection, "/org/xdrive/img/connect.png",
                Box.TOOLBAR_BUTTON_SIZE, Box.TOOLBAR_BUTTON_SIZE);
        lblBuild.setText("Build " + Box.getVariables().get("build"));

        Box.getSwing().setComponentIcon(miUploadFile, "/org/xdrive/img/upload.png", 16, 16);
        Box.getSwing().setComponentIcon(miPrintFiles, "/org/xdrive/img/print.png", 16, 16);
        Box.getSwing().setComponentIcon(miLogout, "/org/xdrive/img/logout.png", 16, 16);
        Box.getSwing().setComponentIcon(miPreferences, "/org/xdrive/img/preferences.png", 16, 16);
        Box.getSwing().setComponentIcon(miAnalyzeStorage, "/org/xdrive/img/analyze_storage.png", 16, 16);
        Box.getSwing().setComponentIcon(miHelp, "/org/xdrive/img/help.png", 16, 16);
    }

    private void loadFilesInformation() {
        try {
            Box.getSwing().disableTableEdit(tblFiles);
            var userDirectory = Box.getVariables().get("userHome");

            Box.getChannelSftp().cd(userDirectory);
            Box.getSwing().clearTable(tblFiles, false);

            Vector<ChannelSftp.LsEntry> items = Box.getChannelSftp().ls(userDirectory);
            var model = (DefaultTableModel) tblFiles.getModel();

            for (var i = 0; i < items.size(); i++) {
                if (items.get(i).getFilename().equals(".") || items.get(i).getFilename().equals("..")) {
                    i++;
                } else {
                    model.addRow(new Object[]{items.get(i).getAttrs().getPermissionsString(), items.get(i).getAttrs().getMtimeString(),
                        ExternalTools.toReadableSize(items.get(i).getAttrs().getSize()), items.get(i).getFilename()});
                }
            }

            tblFiles.getTableHeader().setFont(Box.getFont());
            Box.setTableColumnHeaderPosition(tblFiles, "left");

            tblFiles.getColumnModel().getColumn(0).setMaxWidth(100);
            tblFiles.getColumnModel().getColumn(0).setMinWidth(100);

            tblFiles.getColumnModel().getColumn(1).setMaxWidth(250);
            tblFiles.getColumnModel().getColumn(1).setMinWidth(250);

            tblFiles.getColumnModel().getColumn(2).setMaxWidth(130);
            tblFiles.getColumnModel().getColumn(2).setMinWidth(130);

            Box.getSwing().sortTable(tblFiles, "asc", 3);
            lblFiles.setText("Files " + (items.size() - 2));
        } catch (Exception e) {
            new DlgShowException(this, true, "Failed to load files from server.\n" + e.getMessage() + "\n"
                    + ExternalTools.getPrintStackTrace(e)).setVisible(true);
        }
    }

    private void loadTimer() {
        new Timer().scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                new Boot().loadSystemTime();
                String[] time = Box.getVariables().get("time").split(":");
                for (var i = 0; i < time.length; i++) {
                    time[i] = time[i].trim();
                }

                lblTime.setText(Box.getVariables().get("time"));

                if (Integer.parseInt(time[0]) > 12) {
                    lblTime.setText(lblTime.getText().concat(" AM"));
                } else {
                    lblTime.setText(lblTime.getText().concat(" PM"));
                }

                if (Box.getVariables().containsKey("uploadStatus")) {
                    var status = Box.getVariables().get("uploadStatus");
                    Box.getVariables().remove("uploadStatus", status);

                    if (status.equals("true")) {
                        loadFilesInformation();
                    }
                }
            }
        }, 0, 100);
    }

    private void login() {
        this.setVisible(false);
        new DlgLogin(this, true).setVisible(true);

        if (!Box.getVariables().containsKey("loginAccess")) {
            System.exit(0);
        } else {
            if (Box.getVariables().get("loginAccess").equals("true")) {
                this.setVisible(true);
            } else {
                System.exit(0);
            }
        }
    }

    private void setTablePopupMenu() {
        popup = new JPopupMenu();

        miDelete.setFont(Box.getFont());
        miDownload.setFont(Box.getFont());
        miFileProperties.setFont(Box.getFont());
        miRename.setFont(Box.getFont());
        miShowContent.setFont(Box.getFont());
        miCopyName.setFont(Box.getFont());

        Box.getSwing().setComponentIcon(miDelete, "/org/xdrive/img/delete.png");
        Box.getSwing().setComponentIcon(miDownload, "/org/xdrive/img/download.png");
        Box.getSwing().setComponentIcon(miFileProperties, "/org/xdrive/img/properties.png");
        Box.getSwing().setComponentIcon(miRename, "/org/xdrive/img/edit.png");
        Box.getSwing().setComponentIcon(miShowContent, "/org/xdrive/img/eye.png");
        Box.getSwing().setComponentIcon(miCopyName, "/org/xdrive/img/copy.png");

        miDelete.addActionListener((ActionEvent ae) -> {
            try {
                int[] selectedRows = tblFiles.getSelectedRows();
                var values = new ArrayList<String>();

                for (var row : selectedRows) {
                    values.add(tblFiles.getValueAt(row, 3).toString());
                }

                for (var value : values) {
                    Box.getChannelSftp().rm(Box.getVariables().get("userHome").concat(value));
                    loadFilesInformation();
                }
            } catch (SftpException e) {
                new DlgShowException(this, true, "Failed to delete files from server.\n" + e.getMessage() + "\n"
                        + ExternalTools.getPrintStackTrace(e)).setVisible(true);
            }
        });

        miDownload.addActionListener((ActionEvent ae) -> {
            int[] selectedRows = tblFiles.getSelectedRows();
            var values = new ArrayList<String>();

            for (var row : selectedRows) {
                values.add(tblFiles.getValueAt(row, 3).toString());
            }

            new DlgDownload(this, rootPaneCheckingEnabled, values).setVisible(true);
        });

        miFileProperties.addActionListener((e) -> {
            var data = new ArrayList<Object>();
            for (int i = 0; i < 4; i++) {
                data.add(tblFiles.getValueAt(tblFiles.getSelectedRow(), i));
            }

            new DlgFileProperties(this, true, data.toArray()).setVisible(true);
        });

        miRename.addActionListener((ActionEvent ae) -> {
            try {
                new DlgAsk(this, true, "New name", "n").setVisible(true);

                if (Box.getVariables().containsKey("askDialog")) {
                    var newName = Box.getVariables().get("askDialog");
                    var currentName = tblFiles.getValueAt(tblFiles.getSelectedRow(), 3).toString();

                    var currentNameExtension = FilenameUtils.getExtension(currentName);
                    var newNameExtension = FilenameUtils.getExtension(newName);

                    if (Box.getStrings().isNullOrEmpty(newNameExtension)) {
                        if (!Box.getStrings().isNullOrEmpty(currentNameExtension)) {
                            newName = newName.concat(".").concat(currentNameExtension);
                        }
                    }

                    Box.getChannelSftp().rename(Box.getVariables().get("userHome").concat(currentName),
                            Box.getVariables().get("userHome").concat(newName));

                    loadFilesInformation();
                    Box.getVariables().remove("askDialog");
                }
            } catch (SftpException | IllegalArgumentException e) {
                new DlgShowException(this, true, "Failed to rename the file.\n" + e.getMessage() + "\n"
                        + ExternalTools.getPrintStackTrace(e)).setVisible(true);
            }
        });

        miCopyName.addActionListener((ActionEvent ae) -> {
            Box.getOs().copyText(tblFiles.getValueAt(tblFiles.getSelectedRow(), 3).toString());
        });

        popup.removeAll();

        popup.add(miDelete);
        popup.add(miDownload);
        popup.add(miFileProperties);
        popup.add(miRename);
        popup.add(miShowContent);
        popup.add(miCopyName);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jToolBar1 = new javax.swing.JToolBar();
        btnRefreshTable = new javax.swing.JButton();
        btnCheckServerConnection = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblFiles = new javax.swing.JTable();
        txtSearch = new javax.swing.JTextField();
        cmbFileType = new javax.swing.JComboBox<>();
        lblFiles = new javax.swing.JLabel();
        lblSearch = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        lblBuild = new javax.swing.JLabel();
        lblTime = new javax.swing.JLabel();
        lblDashboardIcon = new javax.swing.JLabel();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        miUploadFile = new javax.swing.JMenuItem();
        miPrintFiles = new javax.swing.JMenuItem();
        miDeleteAllFiles = new javax.swing.JMenuItem();
        miLogout = new javax.swing.JMenuItem();
        miExit = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();
        miPreferences = new javax.swing.JMenuItem();
        miAnalyzeStorage = new javax.swing.JMenuItem();
        jMenu3 = new javax.swing.JMenu();
        miSortAscending = new javax.swing.JCheckBoxMenuItem();
        miSortDescending = new javax.swing.JCheckBoxMenuItem();
        jMenu4 = new javax.swing.JMenu();
        miHelp = new javax.swing.JMenuItem();
        miAboutXDrive = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("XDrive 4.0");
        setFocusCycleRoot(false);
        setFont(new java.awt.Font("Inter Medium", 0, 14)); // NOI18N
        setMinimumSize(new java.awt.Dimension(800, 500));

        jToolBar1.setFloatable(false);
        jToolBar1.setRollover(true);

        btnRefreshTable.setToolTipText("Refresh table");
        btnRefreshTable.setFocusable(false);
        btnRefreshTable.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnRefreshTable.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnRefreshTable.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRefreshTableActionPerformed(evt);
            }
        });
        jToolBar1.add(btnRefreshTable);

        btnCheckServerConnection.setToolTipText("Check server connection");
        btnCheckServerConnection.setFocusable(false);
        btnCheckServerConnection.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnCheckServerConnection.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnCheckServerConnection.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCheckServerConnectionActionPerformed(evt);
            }
        });
        jToolBar1.add(btnCheckServerConnection);

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Manage files", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Inter Medium", 0, 14))); // NOI18N

        tblFiles.setFont(new java.awt.Font("Inter Medium", 0, 14)); // NOI18N
        tblFiles.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Permissions", "Modify time", "Size", "File name"
            }
        ));
        tblFiles.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                tblFilesMouseReleased(evt);
            }
        });
        jScrollPane1.setViewportView(tblFiles);

        txtSearch.setFont(new java.awt.Font("Inter Medium", 0, 14)); // NOI18N
        txtSearch.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtSearchKeyPressed(evt);
            }
        });

        cmbFileType.setFont(new java.awt.Font("Inter Medium", 0, 14)); // NOI18N
        cmbFileType.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "All files", "Musics", "Pictures", "Videos", "Documents", "Executables", "Others" }));
        cmbFileType.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cmbFileTypeItemStateChanged(evt);
            }
        });

        lblFiles.setFont(new java.awt.Font("Inter Medium", 0, 14)); // NOI18N
        lblFiles.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblFiles.setText("Files 0");

        lblSearch.setFont(new java.awt.Font("Inter Medium", 0, 14)); // NOI18N
        lblSearch.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblSearch.setText("Search");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblSearch, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 1148, Short.MAX_VALUE)
                    .addComponent(txtSearch, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addComponent(lblFiles)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(cmbFileType, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(lblSearch)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtSearch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cmbFileType, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblFiles))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 417, Short.MAX_VALUE)
                .addContainerGap())
        );

        jLabel4.setFont(new java.awt.Font("Inter Medium", 0, 14)); // NOI18N
        jLabel4.setText("Created by mahdiDedsec");

        jLabel1.setFont(new java.awt.Font("Inter Medium", 0, 22)); // NOI18N
        jLabel1.setText("XDrive 4.0");

        lblBuild.setFont(new java.awt.Font("Inter Medium", 0, 14)); // NOI18N
        lblBuild.setText("Build 0");

        lblTime.setFont(new java.awt.Font("Inter Medium", 0, 14)); // NOI18N
        lblTime.setText("00 : 00 : 00 AM");

        lblDashboardIcon.setFont(new java.awt.Font("Inter Medium", 0, 14)); // NOI18N

        jMenu1.setText("File");
        jMenu1.setFont(new java.awt.Font("Inter Medium", 0, 14)); // NOI18N

        miUploadFile.setFont(new java.awt.Font("Inter Medium", 0, 14)); // NOI18N
        miUploadFile.setText("Upload file");
        miUploadFile.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                miUploadFileActionPerformed(evt);
            }
        });
        jMenu1.add(miUploadFile);

        miPrintFiles.setFont(new java.awt.Font("Inter Medium", 0, 14)); // NOI18N
        miPrintFiles.setText("Print files");
        miPrintFiles.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                miPrintFilesActionPerformed(evt);
            }
        });
        jMenu1.add(miPrintFiles);

        miDeleteAllFiles.setFont(new java.awt.Font("Inter Medium", 0, 14)); // NOI18N
        miDeleteAllFiles.setText("Delete all files");
        miDeleteAllFiles.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                miDeleteAllFilesActionPerformed(evt);
            }
        });
        jMenu1.add(miDeleteAllFiles);

        miLogout.setFont(new java.awt.Font("Inter Medium", 0, 14)); // NOI18N
        miLogout.setText("Logout");
        miLogout.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                miLogoutActionPerformed(evt);
            }
        });
        jMenu1.add(miLogout);

        miExit.setFont(new java.awt.Font("Inter Medium", 0, 14)); // NOI18N
        miExit.setText("Exit");
        miExit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                miExitActionPerformed(evt);
            }
        });
        jMenu1.add(miExit);

        jMenuBar1.add(jMenu1);

        jMenu2.setText("Edit");
        jMenu2.setFont(new java.awt.Font("Inter Medium", 0, 14)); // NOI18N

        miPreferences.setFont(new java.awt.Font("Inter Medium", 0, 14)); // NOI18N
        miPreferences.setText("Preferences");
        miPreferences.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                miPreferencesActionPerformed(evt);
            }
        });
        jMenu2.add(miPreferences);

        miAnalyzeStorage.setFont(new java.awt.Font("Inter Medium", 0, 14)); // NOI18N
        miAnalyzeStorage.setText("Analyze storage");
        jMenu2.add(miAnalyzeStorage);

        jMenu3.setText("Sort");
        jMenu3.setFont(new java.awt.Font("Inter Medium", 0, 14)); // NOI18N

        miSortAscending.setFont(new java.awt.Font("Inter Medium", 0, 14)); // NOI18N
        miSortAscending.setSelected(true);
        miSortAscending.setText("Ascending");
        miSortAscending.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                miSortAscendingActionPerformed(evt);
            }
        });
        jMenu3.add(miSortAscending);

        miSortDescending.setFont(new java.awt.Font("Inter Medium", 0, 14)); // NOI18N
        miSortDescending.setText("Descending");
        miSortDescending.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                miSortDescendingActionPerformed(evt);
            }
        });
        jMenu3.add(miSortDescending);

        jMenu2.add(jMenu3);

        jMenuBar1.add(jMenu2);

        jMenu4.setText("Help");
        jMenu4.setFont(new java.awt.Font("Inter Medium", 0, 14)); // NOI18N

        miHelp.setFont(new java.awt.Font("Inter Medium", 0, 14)); // NOI18N
        miHelp.setText("View help");
        jMenu4.add(miHelp);

        miAboutXDrive.setFont(new java.awt.Font("Inter Medium", 0, 14)); // NOI18N
        miAboutXDrive.setText("About XDrive");
        miAboutXDrive.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                miAboutXDriveActionPerformed(evt);
            }
        });
        jMenu4.add(miAboutXDrive);

        jMenuBar1.add(jMenu4);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lblDashboardIcon, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel4)
                                    .addComponent(lblTime))
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lblBuild)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblDashboardIcon, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel1)
                                .addComponent(lblBuild))
                            .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblTime)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void miExitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_miExitActionPerformed
        System.exit(0);
    }//GEN-LAST:event_miExitActionPerformed

    private void miUploadFileActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_miUploadFileActionPerformed
        new DlgUploadFile(this, true).setVisible(true);
    }//GEN-LAST:event_miUploadFileActionPerformed

    private void btnRefreshTableActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRefreshTableActionPerformed
        loadFilesInformation();
    }//GEN-LAST:event_btnRefreshTableActionPerformed

    private void btnCheckServerConnectionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCheckServerConnectionActionPerformed
        new Boot().loadServerConnection();
        Box.showMessageBox("Server connection", "The server connection was successful.", "info", null);
    }//GEN-LAST:event_btnCheckServerConnectionActionPerformed

    private void miPrintFilesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_miPrintFilesActionPerformed
        if (Box.getSwing().printTable(tblFiles, "Files information", null)) {
            Box.showMessageBox("Print", "The files printed successfully.", "info", null);
        } else {
            Box.showExceptionMessage("Failed to print the files.", "Print failed");
        }
    }//GEN-LAST:event_miPrintFilesActionPerformed

    private void miDeleteAllFilesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_miDeleteAllFilesActionPerformed
        if (Box.showMessageBox("Delete all files", "Are you sure you want to delete all files?\nChanges can't be undo.",
                "warning", new String[]{"Yes", "No"}) == 0) {
            try {
                Box.getChannelSftp().rm(Box.getVariables().get("userHome").concat("*"));
                loadFilesInformation();

                Box.showMessageBox("Files deleted", "All files deleted successfully.", "info", null);
            } catch (SftpException e) {
                new DlgShowException(this, true, "Failed to delete all files from server.\n" + e.getMessage() + "\n"
                        + ExternalTools.getPrintStackTrace(e)).setVisible(true);
            }
        }
    }//GEN-LAST:event_miDeleteAllFilesActionPerformed

    private void miSortAscendingActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_miSortAscendingActionPerformed
        miSortAscending.setSelected(true);
        miSortDescending.setSelected(false);

        Box.getSwing().sortTable(tblFiles, "asc", 3);
    }//GEN-LAST:event_miSortAscendingActionPerformed

    private void miSortDescendingActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_miSortDescendingActionPerformed
        miSortAscending.setSelected(false);
        miSortDescending.setSelected(true);

        Box.getSwing().sortTable(tblFiles, "des", 3);
    }//GEN-LAST:event_miSortDescendingActionPerformed

    private void miLogoutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_miLogoutActionPerformed
        if (Box.showMessageBox("Logout", "Are you sure you want to logout?", "warning", new String[]{"Yes", "No"}) == 0) {
            Box.getVariables().remove("loginAccess", "true");
            login();
        }
    }//GEN-LAST:event_miLogoutActionPerformed

    private void tblFilesMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblFilesMouseReleased
        if (SwingUtilities.isRightMouseButton(evt)) {
            JTable source = (JTable) evt.getSource();

            var row = source.rowAtPoint(evt.getPoint());
            var column = source.columnAtPoint(evt.getPoint());

            if (!source.isRowSelected(row)) {
                source.changeSelection(row, column, false, false);
            }

            if (tblFiles.getSelectedRows().length > 1) {
                popup.remove(miFileProperties);
                popup.remove(miRename);
                popup.remove(miCopyName);
            } else {
                popup.add(miFileProperties);
                popup.add(miRename);
                popup.add(miCopyName);
            }

            popup.show(evt.getComponent(), evt.getX(), evt.getY());
        }
    }//GEN-LAST:event_tblFilesMouseReleased

    private void txtSearchKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSearchKeyPressed
        var Model = (DefaultTableModel) tblFiles.getModel();
        var tr = new TableRowSorter<>(Model);

        tblFiles.setRowSorter(tr);
        tr.setRowFilter(RowFilter.regexFilter(txtSearch.getText().trim()));
    }//GEN-LAST:event_txtSearchKeyPressed

    private void miAboutXDriveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_miAboutXDriveActionPerformed
        new DlgAboutXDrive(this, true).setVisible(true);
    }//GEN-LAST:event_miAboutXDriveActionPerformed

    private void cmbFileTypeItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cmbFileTypeItemStateChanged
//        var items = new ArrayList<String>();
//
//        for (var i = 0; i < Box.getSwing().countTableRows(tblFiles); i++) {
//            items.add(tblFiles.getValueAt(i, 3).toString());
//        }
//
//        for (var i = 0; i < items.size(); i++) {
//            switch (cmbFileType.getSelectedIndex()) {
//                case 1: {
//                    if (!new FileOperation(items.get(i)).getType().equalsIgnoreCase("music file")) {
//                        ((DefaultTableModel) tblFiles.getModel())
//                    }
//                }
//
//                break;
//            }
//        }
    }//GEN-LAST:event_cmbFileTypeItemStateChanged

    private void miPreferencesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_miPreferencesActionPerformed
        new DlgPereferences(this, true).setVisible(true);
    }//GEN-LAST:event_miPreferencesActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCheckServerConnection;
    private javax.swing.JButton btnRefreshTable;
    private javax.swing.JComboBox<String> cmbFileType;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenu jMenu4;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JLabel lblBuild;
    private javax.swing.JLabel lblDashboardIcon;
    private javax.swing.JLabel lblFiles;
    private javax.swing.JLabel lblSearch;
    private javax.swing.JLabel lblTime;
    private javax.swing.JMenuItem miAboutXDrive;
    private javax.swing.JMenuItem miAnalyzeStorage;
    private javax.swing.JMenuItem miDeleteAllFiles;
    private javax.swing.JMenuItem miExit;
    private javax.swing.JMenuItem miHelp;
    private javax.swing.JMenuItem miLogout;
    private javax.swing.JMenuItem miPreferences;
    private javax.swing.JMenuItem miPrintFiles;
    private javax.swing.JCheckBoxMenuItem miSortAscending;
    private javax.swing.JCheckBoxMenuItem miSortDescending;
    private javax.swing.JMenuItem miUploadFile;
    private javax.swing.JTable tblFiles;
    private javax.swing.JTextField txtSearch;
    // End of variables declaration//GEN-END:variables
}
