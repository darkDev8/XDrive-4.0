package org.xdrive.forms;

import org.xdrive.ext.Box;

public class DlgShowException extends javax.swing.JDialog {

    private String message;

    public DlgShowException(java.awt.Frame parent, boolean modal, String message) {
        super(parent, modal);
        initComponents();

        this.message = message;
        if (message.contains(System.lineSeparator())) {
            String [] data = message.split("\n");
            
            for (var i = 0; i < data.length; i++) {
                Box.getSwing().addToList(lstExceptionMessage, data[i]);
            }
        } else {
            Box.getSwing().addToList(lstExceptionMessage, message);
        }
        
        Box.getSwing().initializeForm(this, true, "english", Box.getFont());
        Box.getSwing().setDialogCloseEsc(this);
        Box.getSwing().addToList(lstExceptionMessage, message);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        lstExceptionMessage = new javax.swing.JList<>();
        btnOk = new javax.swing.JButton();
        btnCopyMessage = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);

        lstExceptionMessage.setFont(new java.awt.Font("Inter Medium", 0, 14)); // NOI18N
        jScrollPane1.setViewportView(lstExceptionMessage);

        btnOk.setFont(new java.awt.Font("Inter Medium", 0, 14)); // NOI18N
        btnOk.setText("Ok");
        btnOk.setFocusPainted(false);
        btnOk.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnOkActionPerformed(evt);
            }
        });

        btnCopyMessage.setFont(new java.awt.Font("Inter Medium", 0, 14)); // NOI18N
        btnCopyMessage.setText("Copy exception message");
        btnCopyMessage.setFocusPainted(false);
        btnCopyMessage.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCopyMessageActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 580, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btnCopyMessage)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnOk, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 350, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnOk)
                    .addComponent(btnCopyMessage))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnOkActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnOkActionPerformed
        dispose();
    }//GEN-LAST:event_btnOkActionPerformed

    private void btnCopyMessageActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCopyMessageActionPerformed
        var listText = new StringBuilder();
        
        for (var i = 0; i < lstExceptionMessage.getModel().getSize(); i++) {
            listText.append(lstExceptionMessage.getModel().getElementAt(i));
        }
        
        Box.getOs().copyText(listText.toString());
    }//GEN-LAST:event_btnCopyMessageActionPerformed

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                DlgShowException dialog = new DlgShowException(new javax.swing.JFrame(), true, null);
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
    private javax.swing.JButton btnCopyMessage;
    private javax.swing.JButton btnOk;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JList<String> lstExceptionMessage;
    // End of variables declaration//GEN-END:variables
}
