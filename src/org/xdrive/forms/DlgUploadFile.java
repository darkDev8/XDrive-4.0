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

package org.xdrive.forms;

import com.sdk.storage.base.FileOperation;
import com.sdk.swingui.dialogs.FileDialog;
import java.awt.FlowLayout;
import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import org.xdrive.ext.Box;
import org.xdrive.threads.UploadFileThread;

public class DlgUploadFile extends javax.swing.JDialog {

    private Map<String, String> files;
    private Thread uploadFileThread;

    public DlgUploadFile(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();

        files = new HashMap<>();

        setFormItems();
    }

    private void setFormItems() {
        Box.getSwing().initializeForm(this, true, "english", Box.getFont());
        Box.getSwing().setDialogCloseEsc(this);
        tbOptions.setLayout((new FlowLayout(FlowLayout.CENTER)));

        Box.getSwing().setComponentIcon(btnClear, "/org/xdrive/img/clear.png",
                Box.TOOLBAR_BUTTON_SIZE, Box.TOOLBAR_BUTTON_SIZE);

        Box.getSwing().setComponentIcon(btnDelete, "/org/xdrive/img/multiply.png",
                Box.TOOLBAR_BUTTON_SIZE, Box.TOOLBAR_BUTTON_SIZE);

        Box.getSwing().setComponentIcon(btnAdd, "/org/xdrive/img/add.png",
                Box.TOOLBAR_BUTTON_SIZE, Box.TOOLBAR_BUTTON_SIZE);
    }
    
    private void setButtonsEnableStatus(boolean status) {
        btnAdd.setEnabled(status);
        btnClear.setEnabled(status);
        btnDelete.setEnabled(status);
        btnCancel.setEnabled(status);
    }
 
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        lstFiles = new javax.swing.JList<>();
        tbOptions = new javax.swing.JToolBar();
        btnClear = new javax.swing.JButton();
        btnDelete = new javax.swing.JButton();
        btnAdd = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        s = new javax.swing.JLabel();
        pbRemainSpace = new javax.swing.JProgressBar();
        pbUploadProgress = new javax.swing.JProgressBar();
        jLabel2 = new javax.swing.JLabel();
        lblUploadStatus = new javax.swing.JLabel();
        chkReplaceFiles = new javax.swing.JCheckBox();
        jPanel3 = new javax.swing.JPanel();
        btnUpload = new javax.swing.JButton();
        btnStop = new javax.swing.JButton();
        btnCancel = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Upload files");
        setUndecorated(true);
        setResizable(false);

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Files", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Inter Medium", 0, 14))); // NOI18N

        lstFiles.setFont(new java.awt.Font("Inter Medium", 0, 14)); // NOI18N
        lstFiles.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        jScrollPane1.setViewportView(lstFiles);

        tbOptions.setFloatable(false);
        tbOptions.setRollover(true);

        btnClear.setFont(new java.awt.Font("Inter Medium", 0, 14)); // NOI18N
        btnClear.setToolTipText("Clear the selected files");
        btnClear.setFocusable(false);
        btnClear.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnClear.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnClear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnClearActionPerformed(evt);
            }
        });
        tbOptions.add(btnClear);

        btnDelete.setFont(new java.awt.Font("Inter Medium", 0, 14)); // NOI18N
        btnDelete.setToolTipText("Delete files from list");
        btnDelete.setFocusable(false);
        btnDelete.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnDelete.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteActionPerformed(evt);
            }
        });
        tbOptions.add(btnDelete);

        btnAdd.setFont(new java.awt.Font("Inter Medium", 0, 14)); // NOI18N
        btnAdd.setToolTipText("Add new files");
        btnAdd.setFocusable(false);
        btnAdd.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnAdd.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddActionPerformed(evt);
            }
        });
        tbOptions.add(btnAdd);

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Upload status", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Inter Medium", 0, 14))); // NOI18N

        s.setFont(new java.awt.Font("Inter Medium", 0, 14)); // NOI18N
        s.setText("Uploaded");

        jLabel2.setFont(new java.awt.Font("Inter Medium", 0, 14)); // NOI18N
        jLabel2.setText("Remain space");

        lblUploadStatus.setFont(new java.awt.Font("Inter Medium", 0, 14)); // NOI18N
        lblUploadStatus.setText("Waiting to start upload...");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(pbRemainSpace, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pbUploadProgress, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addComponent(s)
                            .addComponent(lblUploadStatus))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pbRemainSpace, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(s)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pbUploadProgress, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblUploadStatus)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        chkReplaceFiles.setFont(new java.awt.Font("Inter Medium", 0, 14)); // NOI18N
        chkReplaceFiles.setText("Replace files");
        chkReplaceFiles.setFocusPainted(false);

        btnUpload.setFont(new java.awt.Font("Inter Medium", 0, 14)); // NOI18N
        btnUpload.setText("Upload");
        btnUpload.setFocusPainted(false);
        btnUpload.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUploadActionPerformed(evt);
            }
        });
        jPanel3.add(btnUpload);

        btnStop.setFont(new java.awt.Font("Inter Medium", 0, 14)); // NOI18N
        btnStop.setText("Stop");
        btnStop.setFocusPainted(false);
        btnStop.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnStopActionPerformed(evt);
            }
        });
        jPanel3.add(btnStop);

        btnCancel.setFont(new java.awt.Font("Inter Medium", 0, 14)); // NOI18N
        btnCancel.setText("Cancel");
        btnCancel.setFocusPainted(false);
        btnCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelActionPerformed(evt);
            }
        });
        jPanel3.add(btnCancel);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 398, Short.MAX_VALUE)
                    .addComponent(tbOptions, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(chkReplaceFiles)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(tbOptions, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(chkReplaceFiles)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelActionPerformed
        dispose();
    }//GEN-LAST:event_btnCancelActionPerformed

    private void btnAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddActionPerformed
        var selectedFiles = new FileDialog("Select files", Box.getVariables().get("path"), "f").showMultipleOpenDialog();

        if (!Objects.isNull(selectedFiles)) {
            for (var file : selectedFiles) {
                var fp = new FileOperation(file.getAbsolutePath());

                files.put(fp.getName(), fp.getPath());
                Box.getSwing().addToList(lstFiles, fp.getName());
            }
        }
    }//GEN-LAST:event_btnAddActionPerformed

    private void btnUploadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUploadActionPerformed
        if (files.isEmpty()) {
            Box.showMessageBox("No files", "Add files to start download.", "warning", null);
        } else {
            uploadFileThread = new Thread(new UploadFileThread(files, chkReplaceFiles.isSelected(), pbUploadProgress, lstFiles, lblUploadStatus, this));
            uploadFileThread.start();
            
            setButtonsEnableStatus(false);
        }
    }//GEN-LAST:event_btnUploadActionPerformed

    private void btnStopActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnStopActionPerformed
        if (!Objects.isNull(uploadFileThread)) {
            uploadFileThread.stop();
            setButtonsEnableStatus(true);
        }
    }//GEN-LAST:event_btnStopActionPerformed

    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed
        var index = lstFiles.getSelectedIndex();

        if (index != -1) {
            var value = lstFiles.getSelectedValue();

            if (files.containsKey(value)) {
                files.values().removeIf(v -> v.equals(value));
                Box.getSwing().removeFromList(lstFiles, index);
            }
        }
    }//GEN-LAST:event_btnDeleteActionPerformed

    private void btnClearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnClearActionPerformed
        Box.getSwing().clearList(lstFiles);
        files.clear();
    }//GEN-LAST:event_btnClearActionPerformed

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                DlgUploadFile dialog = new DlgUploadFile(new javax.swing.JFrame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAdd;
    private javax.swing.JButton btnCancel;
    private javax.swing.JButton btnClear;
    private javax.swing.JButton btnDelete;
    private javax.swing.JButton btnStop;
    private javax.swing.JButton btnUpload;
    private javax.swing.JCheckBox chkReplaceFiles;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblUploadStatus;
    private javax.swing.JList<String> lstFiles;
    private javax.swing.JProgressBar pbRemainSpace;
    private javax.swing.JProgressBar pbUploadProgress;
    private javax.swing.JLabel s;
    private javax.swing.JToolBar tbOptions;
    // End of variables declaration//GEN-END:variables
}
