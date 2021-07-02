package org.xdrive.forms;

import com.sdk.data.types.Characters;
import com.sdk.security.SecurityTools;
import com.sdk.swingui.dialogs.FileDialog;
import com.sdk.tools.ExternalTools;
import com.xdapi.entity.User;
import java.io.File;
import java.util.Arrays;
import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import org.xdrive.ext.Box;

public class DlgPereferences extends javax.swing.JDialog {

    private char echoCharacter;
    private boolean saveStatus;

    private User user;

    public DlgPereferences(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();

        Box.getSwing().initializeForm(this, true, "english", Box.getFont());
        Box.getSwing().setDialogCloseEsc(this);

        loadPreferences();
        echoCharacter = txtPassword.getEchoChar();

        new Timer().scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                btnApply.setEnabled(!saveStatus);
            }
        }, 0, 100);
    }

    private void loadPreferences() {
        user = Box.getXdb().getUser(Box.getVariables().get("loginUser"));

        if (Objects.isNull(user)) {
            new DlgShowException(null, true, "Failed to detect user and load preferences").setVisible(true);
        }

        txtUsername.setText(user.getLoginUsername());
        txtPasswordHint.setText(user.getPasswordHint());
        txtWorkingDirectory.setText(user.getWorkingDirectory());
        cmbTheme.setSelectedIndex(user.getTheme());
    }

    private boolean savePreferences() {
        try {
            user.setLoginUsername(txtUsername.getText());

            var password = new Characters().arrayToString(txtPassword.getPassword(), null);
            if (!Box.getStrings().isNullOrEmpty(password)) {
                user.setLoginPassword(SecurityTools.encrypt(Box.getVariables().get("enc"), SecurityTools.hashPlainText(password)));
            }

            user.setPasswordHint(txtPasswordHint.getText());

            user.setWorkingDirectory(txtWorkingDirectory.getText());
            user.setTheme(cmbTheme.getSelectedIndex());

            switch (cmbTheme.getSelectedIndex()) {
                case 0:
                    UIManager.setLookAndFeel("com.formdev.flatlaf.intellijthemes.FlatArcIJTheme");
                    break;

                case 1:
                    UIManager.setLookAndFeel("com.formdev.flatlaf.intellijthemes.FlatMaterialDesignDarkIJTheme");
                    break;

                case 2:
                    UIManager.setLookAndFeel("com.formdev.flatlaf.intellijthemes.FlatArcOrangeIJTheme");
                    break;
                    
                case 3:
                    UIManager.setLookAndFeel("com.formdev.flatlaf.intellijthemes.FlatNordIJTheme");
                    break;

                case 4:
                    UIManager.setLookAndFeel("com.formdev.flatlaf.intellijthemes.FlatSolarizedLightIJTheme");
                    break;

                case 5:
                    UIManager.setLookAndFeel("com.formdev.flatlaf.intellijthemes.materialthemeuilite.FlatMaterialOceanicContrastIJTheme");
                    break;

                case 6:
                    UIManager.setLookAndFeel("com.formdev.flatlaf.intellijthemes.FlatCobalt2IJTheme");
                    break;

                case 7:
                    UIManager.setLookAndFeel("com.formdev.flatlaf.intellijthemes.FlatGradiantoNatureGreenIJTheme");
                    break;

            }

            SwingUtilities.updateComponentTreeUI(super.getParent());
            SwingUtilities.updateComponentTreeUI(this);
            saveStatus = Box.getXdb().updateUser(user, user.getPkid());

            return saveStatus;
        } catch (Exception e) {
            new DlgShowException(null, true, "Failed to update user pereferences.\n"
                    + e.getMessage() + "\n" + ExternalTools.getPrintStackTrace(e)).setVisible(true);
            return false;
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel2 = new javax.swing.JPanel();
        lblLoginUser1 = new javax.swing.JLabel();
        lblLoginUser2 = new javax.swing.JLabel();
        txtUsername = new javax.swing.JTextField();
        lblLoginUser4 = new javax.swing.JLabel();
        chkShowPassword = new javax.swing.JCheckBox();
        txtPasswordHint = new javax.swing.JTextField();
        txtPassword = new javax.swing.JPasswordField();
        jPanel3 = new javax.swing.JPanel();
        lblLoginUser5 = new javax.swing.JLabel();
        txtWorkingDirectory = new javax.swing.JTextField();
        btnSelectWorkingDirectory = new javax.swing.JButton();
        lblLoginUser6 = new javax.swing.JLabel();
        cmbTheme = new javax.swing.JComboBox<>();
        btnResetDefault = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        btnApply = new javax.swing.JButton();
        btnOk = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Preferences");
        setUndecorated(true);
        setResizable(false);

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Pereferences", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Inter Medium", 0, 14))); // NOI18N

        jTabbedPane1.setTabLayoutPolicy(javax.swing.JTabbedPane.SCROLL_TAB_LAYOUT);
        jTabbedPane1.setTabPlacement(javax.swing.JTabbedPane.LEFT);
        jTabbedPane1.setFont(new java.awt.Font("Inter Medium", 0, 14)); // NOI18N

        lblLoginUser1.setFont(new java.awt.Font("Inter Medium", 0, 14)); // NOI18N
        lblLoginUser1.setText("<html>\nThe username must be at least 4 characters\n</html>\n");

        lblLoginUser2.setFont(new java.awt.Font("Inter Medium", 0, 14)); // NOI18N
        lblLoginUser2.setText("<html>\nThe password must be at least 3 chracters and contains<br>\nboth numbers and characters.\n</html>");

        txtUsername.setFont(new java.awt.Font("Inter Medium", 0, 14)); // NOI18N
        txtUsername.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtPereferencesTextEditKeyPressed(evt);
            }
        });

        lblLoginUser4.setFont(new java.awt.Font("Inter Medium", 0, 14)); // NOI18N
        lblLoginUser4.setText("Pssword hint can't be empty.");

        chkShowPassword.setFont(new java.awt.Font("Inter Medium", 0, 14)); // NOI18N
        chkShowPassword.setText("Show password");
        chkShowPassword.setFocusPainted(false);
        chkShowPassword.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkShowPasswordActionPerformed(evt);
            }
        });

        txtPasswordHint.setFont(new java.awt.Font("Inter Medium", 0, 14)); // NOI18N
        txtPasswordHint.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtPereferencesTextEditKeyPressed(evt);
            }
        });

        txtPassword.setFont(new java.awt.Font("Inter Medium", 0, 14)); // NOI18N
        txtPassword.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtPereferencesTextEditKeyPressed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(lblLoginUser2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addComponent(chkShowPassword)
                        .addGap(267, 267, 267))
                    .addComponent(lblLoginUser1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblLoginUser4)
                    .addComponent(txtPasswordHint)
                    .addComponent(txtUsername)
                    .addComponent(txtPassword))
                .addContainerGap(48, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblLoginUser1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtUsername, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(lblLoginUser2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtPassword, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(7, 7, 7)
                .addComponent(chkShowPassword)
                .addGap(18, 18, 18)
                .addComponent(lblLoginUser4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtPasswordHint, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(153, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("User configuration", jPanel2);

        lblLoginUser5.setFont(new java.awt.Font("Inter Medium", 0, 14)); // NOI18N
        lblLoginUser5.setText("Working directory path");

        txtWorkingDirectory.setEditable(false);
        txtWorkingDirectory.setFont(new java.awt.Font("Inter Medium", 0, 14)); // NOI18N

        btnSelectWorkingDirectory.setFont(new java.awt.Font("Inter Medium", 0, 14)); // NOI18N
        btnSelectWorkingDirectory.setText("Browse");
        btnSelectWorkingDirectory.setFocusPainted(false);
        btnSelectWorkingDirectory.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSelectWorkingDirectoryActionPerformed(evt);
            }
        });

        lblLoginUser6.setFont(new java.awt.Font("Inter Medium", 0, 14)); // NOI18N
        lblLoginUser6.setText("Application theme");

        cmbTheme.setFont(new java.awt.Font("Inter Medium", 0, 14)); // NOI18N
        cmbTheme.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Arc Light", "Material Dark", "Arc Orange", "Flat Nord", "Solarized Light", "Material Oceanic Constrast", "Flat Colbat2", "Gradiant Nature Green" }));
        cmbTheme.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cmbThemeItemStateChanged(evt);
            }
        });

        btnResetDefault.setFont(new java.awt.Font("Inter Medium", 0, 14)); // NOI18N
        btnResetDefault.setText("Reset default");
        btnResetDefault.setFocusPainted(false);
        btnResetDefault.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnResetDefaultActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(txtWorkingDirectory)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnSelectWorkingDirectory))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblLoginUser5)
                            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(cmbTheme, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(lblLoginUser6, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addGap(0, 228, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btnResetDefault)))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblLoginUser5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtWorkingDirectory, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnSelectWorkingDirectory))
                .addGap(18, 18, 18)
                .addComponent(lblLoginUser6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cmbTheme, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 231, Short.MAX_VALUE)
                .addComponent(btnResetDefault)
                .addContainerGap())
        );

        jTabbedPane1.addTab("Application", jPanel3);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 600, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 400, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        btnApply.setFont(new java.awt.Font("Inter Medium", 0, 14)); // NOI18N
        btnApply.setText("Apply");
        btnApply.setEnabled(false);
        btnApply.setFocusPainted(false);
        btnApply.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnApplyActionPerformed(evt);
            }
        });
        jPanel4.add(btnApply);

        btnOk.setFont(new java.awt.Font("Inter Medium", 0, 14)); // NOI18N
        btnOk.setText("Ok");
        btnOk.setFocusPainted(false);
        btnOk.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnOkActionPerformed(evt);
            }
        });
        jPanel4.add(btnOk);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnApplyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnApplyActionPerformed
        savePreferences();
    }//GEN-LAST:event_btnApplyActionPerformed

    private void btnOkActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnOkActionPerformed
        if (savePreferences()) {
            dispose();
        }
    }//GEN-LAST:event_btnOkActionPerformed

    private void btnSelectWorkingDirectoryActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSelectWorkingDirectoryActionPerformed
        var directory = new FileDialog("Select working directory", user.getWorkingDirectory(), "d").selectDirectory();

        if (!Objects.isNull(directory)) {
            txtWorkingDirectory.setText(directory.getAbsolutePath());
        }
    }//GEN-LAST:event_btnSelectWorkingDirectoryActionPerformed

    private void chkShowPasswordActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkShowPasswordActionPerformed
        if (chkShowPassword.isSelected()) {
            txtPassword.setEchoChar((char) 0);
        } else {
            txtPassword.setEchoChar(echoCharacter);
        }
    }//GEN-LAST:event_chkShowPasswordActionPerformed

    private void txtPereferencesTextEditKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPereferencesTextEditKeyPressed
        saveStatus = false;
    }//GEN-LAST:event_txtPereferencesTextEditKeyPressed

    private void cmbThemeItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cmbThemeItemStateChanged
        saveStatus = false;
    }//GEN-LAST:event_cmbThemeItemStateChanged

    private void btnResetDefaultActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnResetDefaultActionPerformed

    }//GEN-LAST:event_btnResetDefaultActionPerformed

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                DlgPereferences dialog = new DlgPereferences(new javax.swing.JFrame(), true);
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
    private javax.swing.JButton btnApply;
    private javax.swing.JButton btnOk;
    private javax.swing.JButton btnResetDefault;
    private javax.swing.JButton btnSelectWorkingDirectory;
    private javax.swing.JCheckBox chkShowPassword;
    private javax.swing.JComboBox<String> cmbTheme;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JLabel lblLoginUser1;
    private javax.swing.JLabel lblLoginUser2;
    private javax.swing.JLabel lblLoginUser4;
    private javax.swing.JLabel lblLoginUser5;
    private javax.swing.JLabel lblLoginUser6;
    private javax.swing.JPasswordField txtPassword;
    private javax.swing.JTextField txtPasswordHint;
    private javax.swing.JTextField txtUsername;
    private javax.swing.JTextField txtWorkingDirectory;
    // End of variables declaration//GEN-END:variables
}
