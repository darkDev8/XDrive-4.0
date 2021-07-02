package org.xdrive.forms;

import com.sdk.swingui.dialogs.FileDialog;
import java.io.File;
import java.util.List;
import java.util.Objects;
import org.xdrive.ext.Box;
import org.xdrive.threads.DownloadFileThread;

public class DlgDownload extends javax.swing.JDialog {

    private List<String> values;

    public DlgDownload(java.awt.Frame parent, boolean modal, List<String> values) {
        super(parent, modal);
        initComponents();

        this.values = values;
        Box.getSwing().initializeForm(this, true, "english", Box.getFont());
        Box.getSwing().setDialogCloseEsc(this);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        lblDownloadStatus = new javax.swing.JLabel();
        txtDownloadPath = new javax.swing.JTextField();
        btnBrowse = new javax.swing.JButton();
        pbDownloadProgress = new javax.swing.JProgressBar();
        jPanel2 = new javax.swing.JPanel();
        btnDownload = new javax.swing.JButton();
        btnCancel = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Download files");
        setUndecorated(true);
        setResizable(false);

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Download files", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Inter Medium", 0, 14))); // NOI18N

        lblDownloadStatus.setFont(new java.awt.Font("Inter Medium", 0, 14)); // NOI18N
        lblDownloadStatus.setText("Waiting to start download...");

        txtDownloadPath.setEditable(false);
        txtDownloadPath.setFont(new java.awt.Font("Inter Medium", 0, 14)); // NOI18N

        btnBrowse.setFont(new java.awt.Font("Inter Medium", 0, 14)); // NOI18N
        btnBrowse.setText("Browse");
        btnBrowse.setFocusPainted(false);
        btnBrowse.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBrowseActionPerformed(evt);
            }
        });

        btnDownload.setFont(new java.awt.Font("Inter Medium", 0, 14)); // NOI18N
        btnDownload.setText("Download");
        btnDownload.setFocusPainted(false);
        btnDownload.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDownloadActionPerformed(evt);
            }
        });
        jPanel2.add(btnDownload);

        btnCancel.setFont(new java.awt.Font("Inter Medium", 0, 14)); // NOI18N
        btnCancel.setText("Cancel");
        btnCancel.setFocusPainted(false);
        btnCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelActionPerformed(evt);
            }
        });
        jPanel2.add(btnCancel);

        jLabel2.setFont(new java.awt.Font("Inter Medium", 0, 14)); // NOI18N
        jLabel2.setText("Location");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(txtDownloadPath)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnBrowse))
                    .addComponent(pbDownloadProgress, javax.swing.GroupLayout.DEFAULT_SIZE, 448, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addComponent(lblDownloadStatus))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtDownloadPath, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnBrowse))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(pbDownloadProgress, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lblDownloadStatus)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
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

    private void btnBrowseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBrowseActionPerformed
        File file = new FileDialog("Select download path", Box.getVariables().get("path"), "d").selectDirectory();

        if (!Objects.isNull(file)) {
            txtDownloadPath.setText(file.getAbsolutePath());
        }
    }//GEN-LAST:event_btnBrowseActionPerformed

    private void btnDownloadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDownloadActionPerformed
        new Thread(new DownloadFileThread(this, values, pbDownloadProgress, lblDownloadStatus, txtDownloadPath.getText())).start();
    }//GEN-LAST:event_btnDownloadActionPerformed

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                DlgDownload dialog = new DlgDownload(new javax.swing.JFrame(), true, null);
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
    private javax.swing.JButton btnBrowse;
    private javax.swing.JButton btnCancel;
    private javax.swing.JButton btnDownload;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JLabel lblDownloadStatus;
    private javax.swing.JProgressBar pbDownloadProgress;
    private javax.swing.JTextField txtDownloadPath;
    // End of variables declaration//GEN-END:variables
}
