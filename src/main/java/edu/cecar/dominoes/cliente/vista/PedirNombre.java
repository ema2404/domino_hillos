/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.cecar.dominoes.cliente.vista;

import edu.cecar.dominoes.cliente.logica.LogicaCliente;
import javax.swing.JOptionPane;

public class PedirNombre extends javax.swing.JDialog {
    String nombre;
    LogicaCliente logicaCliente;
    

    /**
     * Creates new form PedirNombre
     */
    public PedirNombre(java.awt.Frame parent, boolean modal,LogicaCliente logicaCliente) {
        super(parent, modal);
        this.logicaCliente = logicaCliente;
        initComponents();
    }
    boolean ciclo = true;
    public void romperEstadoServer(){
        this.ciclo= false;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        txtNombre = new javax.swing.JTextField();
        jbGuardar = new javax.swing.JButton();
        jlEstadoSErver = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                estadoServer(evt);
            }
        });

        txtNombre.setEditable(false);

        jbGuardar.setEnabled(false);
        jbGuardar.setLabel("Guardar");
        jbGuardar.setName(""); // NOI18N
        jbGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbGuardarActionPerformed(evt);
            }
        });

        jlEstadoSErver.setText("Conectando Server....");

        jButton1.setText("Salir");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(138, 138, 138)
                        .addComponent(txtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jlEstadoSErver))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(73, 73, 73)
                        .addComponent(jbGuardar)
                        .addGap(57, 57, 57)
                        .addComponent(jButton1)))
                .addContainerGap(138, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addComponent(jlEstadoSErver)
                .addGap(33, 33, 33)
                .addComponent(txtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(29, 29, 29)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jbGuardar)
                    .addComponent(jButton1))
                .addContainerGap(140, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jbGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbGuardarActionPerformed
      
        

        if (logicaCliente.validarNombre(txtNombre.getText())) {
            JOptionPane.showMessageDialog(rootPane, "Nombre gurdado");
            nombre = txtNombre.getText();           
            this.setVisible(false);
        } else {
            JOptionPane.showMessageDialog(rootPane, "Nombre ya esta en uso");
        }


    }//GEN-LAST:event_jbGuardarActionPerformed

    private void estadoServer(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_estadoServer

        new Thread(() -> {

            while (ciclo) {
                try {

                    LogicaCliente logicaCliente = new LogicaCliente();

                    String[] mensajes = logicaCliente.estadoServer("").split(",");
                   

                    if (mensajes[0].equals("1")) {
                        jlEstadoSErver.setText("Estado del Server: Listo");
                        jlEstadoSErver.repaint();
                        jbGuardar.setEnabled(true);                       
                        txtNombre.setEditable(true); 
                    } else {  
                        jlEstadoSErver.setText("Estado del Server: "+mensajes[1]);
                        jbGuardar.setEnabled(false);                        
                        txtNombre.setEditable(false);
                        jlEstadoSErver.repaint();
                                              
                    }

                    Thread.sleep(3000);

                } catch (Exception e) {
                    e.printStackTrace();
                    System.out.println("Conectando....");
                }
                
            

            }
            this.dispose();
           

        }).start();


    }//GEN-LAST:event_estadoServer

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        System.exit(0);
        
    }//GEN-LAST:event_jButton1ActionPerformed

    public String getNombre() {
        romperEstadoServer();
        return nombre;
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jbGuardar;
    private javax.swing.JLabel jlEstadoSErver;
    private javax.swing.JTextField txtNombre;
    // End of variables declaration//GEN-END:variables
}