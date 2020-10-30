/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.cecar.dominoes.cliente.vista;

import edu.cecar.dominoes.cliente.logica.LogicaCliente;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TiempoEspera extends javax.swing.JDialog {

    LogicaCliente logicaCliente;
    int tiempo = -1;

    public int getTiempo() {
        return tiempo;
    }
    
    
    

    /**
     * Creates new form TiempoEspera
     */
    public TiempoEspera(java.awt.Frame parent, boolean modal, LogicaCliente logicaCliente) {
        super(parent, modal);
        initComponents();
        this.logicaCliente = logicaCliente;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lbTiempo = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                finCarga(evt);
            }
        });
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lbTiempo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbTiempo.setText("jLabel1");
        getContentPane().add(lbTiempo, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 140, 370, 40));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void finCarga(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_finCarga

        new Thread(() -> {

            while (tiempo != 0) {
                tiempo = logicaCliente.tiempoEsperaultimoJugador();
                if (tiempo == -2) {
                    lbTiempo.setText("partida ya en curso, no es posible unirse");                    
                    lbTiempo.repaint();
                    try {
                        Thread.sleep(5000);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(TiempoEspera.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    break;
                } else {
                    System.out.println("tiempo: " + tiempo);
                    lbTiempo.setText("" + tiempo);
                    lbTiempo.repaint();
                }

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException ex) {
                    Logger.getLogger(TiempoEspera.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
            System.out.println("fin conteo");

            this.dispose();

        }).start();


    }//GEN-LAST:event_finCarga

    /**
     * @param args the command line arguments
     */
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel lbTiempo;
    // End of variables declaration//GEN-END:variables
}
