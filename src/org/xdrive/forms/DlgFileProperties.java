package org.xdrive.forms;

import java.util.Objects;
import javax.swing.table.DefaultTableModel;
import org.xdrive.ext.Box;

public class DlgFileProperties extends javax.swing.JDialog {

    private Object[] data;

    public DlgFileProperties(java.awt.Frame parent, boolean modal, Object[] data) {
        super(parent, modal);
        initComponents();

        this.data = data;

        if (Objects.isNull(data)) {
            dispose();
        }

        loadData();

        Box.getSwing().initializeForm(this, true, "english", Box.getFont());
        Box.getSwing().setDialogCloseEsc(this);
    }

    private void loadData() {
        var model = (DefaultTableModel) tblProperties.getModel();
        String[] keys = {"Permission", "Modify time", "Size", "File name"};

        for (var i = 0; i < data.length; i++) {
            model.addRow(new Object[]{keys[i], data[i]});
        }

        Box.getSwing().disableTableEdit(tblProperties);
        tblProperties.getTableHeader().setFont(Box.getFont());
        Box.setTableColumnHeaderPosition(tblProperties, "center");

        tblProperties.getColumnModel().getColumn(0).setMaxWidth(110);
        tblProperties.getColumnModel().getColumn(0).setMinWidth(110);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblProperties = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();
        btnCopy = new javax.swing.JButton();
        btnOk = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("File properties");
        setUndecorated(true);
        setResizable(false);

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "File properties", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Inter Medium", 0, 14))); // NOI18N

        tblProperties.setFont(new java.awt.Font("Inter Medium", 0, 14)); // NOI18N
        tblProperties.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Key", "Value"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(tblProperties);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 500, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        btnCopy.setFont(new java.awt.Font("Inter Medium", 0, 14)); // NOI18N
        btnCopy.setText("Copy");
        btnCopy.setFocusPainted(false);
        btnCopy.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCopyActionPerformed(evt);
            }
        });
        jPanel2.add(btnCopy);

        btnOk.setFont(new java.awt.Font("Inter Medium", 0, 14)); // NOI18N
        btnOk.setText("Ok");
        btnOk.setFocusPainted(false);
        btnOk.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnOkActionPerformed(evt);
            }
        });
        jPanel2.add(btnOk);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnOkActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnOkActionPerformed
        dispose();
    }//GEN-LAST:event_btnOkActionPerformed

    private void btnCopyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCopyActionPerformed
        var message = new StringBuilder();

        for (var i = 0; i < Box.getSwing().countTableRows(tblProperties); i++) {
            message.append(tblProperties.getValueAt(i, 0))
                    .append("   ").append(tblProperties.getValueAt(i, 1)).append("\n");
        }

        Box.getOs().copyText(message.toString());
    }//GEN-LAST:event_btnCopyActionPerformed

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                DlgFileProperties dialog = new DlgFileProperties(new javax.swing.JFrame(), true, null);
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
    private javax.swing.JButton btnCopy;
    private javax.swing.JButton btnOk;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tblProperties;
    // End of variables declaration//GEN-END:variables
}
