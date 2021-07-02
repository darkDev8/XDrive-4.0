package org.xdrive.forms;

import com.jcraft.jsch.ChannelSftp;
import com.sdk.data.types.Characters;
import com.sdk.security.SecurityTools;
import com.sdk.tools.ExternalTools;
import java.awt.event.KeyEvent;
import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Vector;
import org.xdrive.ext.Box;

public class DlgLogin extends javax.swing.JDialog {
    
    private char echoCharacter;
    private Characters chars;
    
    public DlgLogin(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        
        chars = new Characters();
        
        Box.getSwing().initializeForm(this, true, "english", Box.getFont());
        Box.getSwing().setDialogCloseEsc(this);
        
        echoCharacter = txtPassword.getEchoChar();
        
        new Timer().scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                btnLogin.setEnabled(!txtUsername.getText().isEmpty() && !chars.arrayToString(txtPassword.getPassword(), null).isEmpty());
            }
        }, 0, 100);
    }
    
    private void login() {
        try {
            var user = Box.getXdb().getUser(txtUsername.getText().trim());
            var foundUserDirectory = false;
            
            if (Objects.isNull(user)) {
                Box.showExceptionMessage("The username is incorrect.", "Incorrect username");
            } else {
                var inputPassword = SecurityTools.hashPlainText(chars.arrayToString(txtPassword.getPassword(), null));
                var dbPassword = SecurityTools.decrypt(Box.getVariables().get("enc"), user.getLoginPassword());
                
                if (inputPassword.equals(dbPassword)) {
                    Box.getVariables().put("loginUser", user.getLoginUsername());
                    Box.getVariables().put("userHome", "/".concat(user.getLoginUsername()).concat("/"));
                    Box.getVariables().put("loginAccess", "true");
                    
                    Vector<ChannelSftp.LsEntry> serverFiles = Box.getChannelSftp().ls("/");
                    for (ChannelSftp.LsEntry item : serverFiles) {
                        if (item.getFilename().equals(Box.getVariables().get("loginUser"))) {    //check for existing user
                            foundUserDirectory = true;
                        }
                    }
                    
                    if (!foundUserDirectory) {
                        Box.getChannelSftp().mkdir(Box.getVariables().get("userHome"));
                    }
                    
                    dispose();
                } else {
                    Box.showExceptionMessage("The password is incorrect.", "Incorrect password");
                }
            }
        } catch (Exception e) {
            new DlgShowException(null, true, "Failed to detect user and login.\n" + e.getMessage() + "\n"
                    + ExternalTools.getPrintStackTrace(e)).setVisible(true);
            System.exit(-1);
        }
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        txtUsername = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        txtPassword = new javax.swing.JPasswordField();
        chkShowPassword = new javax.swing.JCheckBox();
        jPanel2 = new javax.swing.JPanel();
        btnLogin = new javax.swing.JButton();
        btnExit = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Login");
        setUndecorated(true);
        setResizable(false);

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Login", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Inter Medium", 0, 14))); // NOI18N

        jLabel1.setFont(new java.awt.Font("Inter Medium", 0, 14)); // NOI18N
        jLabel1.setText("Username");

        txtUsername.setFont(new java.awt.Font("Inter Medium", 0, 14)); // NOI18N
        txtUsername.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtUsernamePasswordKeyPressed(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Inter Medium", 0, 14)); // NOI18N
        jLabel3.setText("Password");

        txtPassword.setFont(new java.awt.Font("Inter Medium", 0, 14)); // NOI18N
        txtPassword.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtUsernamePasswordKeyPressed(evt);
            }
        });

        chkShowPassword.setFont(new java.awt.Font("Inter Medium", 0, 14)); // NOI18N
        chkShowPassword.setText("Show password");
        chkShowPassword.setFocusPainted(false);
        chkShowPassword.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkShowPasswordActionPerformed(evt);
            }
        });

        btnLogin.setFont(new java.awt.Font("Inter Medium", 0, 14)); // NOI18N
        btnLogin.setText("Login");
        btnLogin.setFocusPainted(false);
        btnLogin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLoginActionPerformed(evt);
            }
        });
        jPanel2.add(btnLogin);

        btnExit.setFont(new java.awt.Font("Inter Medium", 0, 14)); // NOI18N
        btnExit.setText("Exit");
        btnExit.setFocusPainted(false);
        btnExit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExitActionPerformed(evt);
            }
        });
        jPanel2.add(btnExit);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtUsername)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtPassword)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(chkShowPassword)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, 448, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtUsername, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtPassword, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(chkShowPassword)
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

    private void chkShowPasswordActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkShowPasswordActionPerformed
        if (chkShowPassword.isSelected()) {
            txtPassword.setEchoChar((char) 0);
        } else {
            txtPassword.setEchoChar(echoCharacter);
        }
    }//GEN-LAST:event_chkShowPasswordActionPerformed

    private void btnExitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExitActionPerformed
        System.exit(0);
    }//GEN-LAST:event_btnExitActionPerformed

    private void btnLoginActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLoginActionPerformed
        login();
    }//GEN-LAST:event_btnLoginActionPerformed

    private void txtUsernamePasswordKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtUsernamePasswordKeyPressed
        if (evt.getKeyChar() == KeyEvent.VK_ENTER) {
            if (!txtUsername.getText().isEmpty() && !chars.arrayToString(txtPassword.getPassword(), null).isEmpty()) {
                login();
            }
        }
    }//GEN-LAST:event_txtUsernamePasswordKeyPressed
    
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                DlgLogin dialog = new DlgLogin(new javax.swing.JFrame(), true);
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
    private javax.swing.JButton btnExit;
    private javax.swing.JButton btnLogin;
    private javax.swing.JCheckBox chkShowPassword;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPasswordField txtPassword;
    private javax.swing.JTextField txtUsername;
    // End of variables declaration//GEN-END:variables
}
