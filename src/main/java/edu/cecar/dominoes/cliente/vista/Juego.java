/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.cecar.dominoes.cliente.vista;

import edu.cecar.dominoes.cliente.logica.LogicaCliente;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JViewport;
import javax.swing.Timer;

public class Juego extends javax.swing.JFrame {

    LogicaCliente logicaCliente = new LogicaCliente();
    NotificarTurno notificarTurno;
    Ganador ganador;

    Juego a = this;

    Timer timerActualizar = new Timer(2000, new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            logicaCliente.ganador(a, ganador);
            if (!logicaCliente.isJuegoGanado()) {
                
                logicaCliente.actualizacionJugada(jpJuego);
                logicaCliente.esMiTurno(notificarTurno, btnDerecha, btnPaso, btnIzquierda);
            }else{
                timerActualizar.stop();
                logicaCliente.setJuegoGanado(false);
            }

        }
    });
    Timer timerPreguntarTurno = new Timer(2000, new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {

        }
    });

    /**
     * Creates new form Main
     */
    public Juego() {

        initComponents();
        jScrollPane.getViewport().setScrollMode(JViewport.SIMPLE_SCROLL_MODE);

        this.revalidate();
        this.repaint();

    }

    public static void main(String args[]) {

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {

                Juego main = new Juego();

                main.setLocationRelativeTo(null);
                main.setVisible(true);

            }
        });

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane = new javax.swing.JScrollPane();
        jpJuego = new javax.swing.JPanel();
        jpFichasDisponibles = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        btnIzquierda = new javax.swing.JButton();
        btnDerecha = new javax.swing.JButton();
        btnPaso = new javax.swing.JButton();
        jpFichaParaJugar = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(97, 87, 87));
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                ventanaAvierta(evt);
            }
        });

        jpJuego.setBackground(new java.awt.Color(14, 188, 3));
        jpJuego.setLayout(new java.awt.GridBagLayout());
        jScrollPane.setViewportView(jpJuego);

        jpFichasDisponibles.setBackground(new java.awt.Color(0, 0, 0));
        jpFichasDisponibles.setBorder(javax.swing.BorderFactory.createCompoundBorder(new javax.swing.border.MatteBorder(null), new javax.swing.border.LineBorder(new java.awt.Color(0, 255, 255), 2, true)));
        jpFichasDisponibles.setAlignmentX(1.0F);
        jpFichasDisponibles.setAutoscrolls(true);

        jPanel1.setBackground(new java.awt.Color(51, 204, 255));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 176, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        jPanel2.setBackground(new java.awt.Color(0, 0, 0));
        jPanel2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255), 2));

        btnIzquierda.setText("Izquierda");
        btnIzquierda.setEnabled(false);
        btnIzquierda.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnIzquierdaActionPerformed(evt);
            }
        });

        btnDerecha.setText("derecha");
        btnDerecha.setEnabled(false);
        btnDerecha.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDerechaActionPerformed(evt);
            }
        });

        btnPaso.setText("Paso");
        btnPaso.setEnabled(false);
        btnPaso.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPasoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnIzquierda)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnPaso)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnDerecha)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnDerecha)
                    .addComponent(btnIzquierda)
                    .addComponent(btnPaso))
                .addContainerGap())
        );

        jpFichaParaJugar.setBackground(new java.awt.Color(0, 0, 0));
        jpFichaParaJugar.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(102, 255, 102)));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane)
                        .addContainerGap())
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jpFichasDisponibles, javax.swing.GroupLayout.PREFERRED_SIZE, 701, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(73, 73, 73)
                                .addComponent(jpFichaParaJugar, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 24, Short.MAX_VALUE)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 457, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jpFichasDisponibles, javax.swing.GroupLayout.PREFERRED_SIZE, 184, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jpFichaParaJugar, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void ventanaAvierta(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_ventanaAvierta

        timerActualizar.setRepeats(true);
        timerPreguntarTurno.setRepeats(true);

        PedirNombre nombre = new PedirNombre(this, rootPaneCheckingEnabled, logicaCliente);
        nombre.setLocationRelativeTo(this);
        nombre.setVisible(true);

        Listo listo = new Listo(this, rootPaneCheckingEnabled, logicaCliente);
        listo.setLocationRelativeTo(this);
        listo.setVisible(true);
        listo.pararCiclo();

        TiempoEspera espera = new TiempoEspera(this, rootPaneCheckingEnabled, logicaCliente);
        espera.setLocationRelativeTo(null);
        espera.setVisible(true);

        if (espera.getTiempo() == 0) {
            logicaCliente.pedirFichas(jpFichasDisponibles, jpFichaParaJugar);
            timerActualizar.start();
            timerPreguntarTurno.start();
            notificarTurno = new NotificarTurno(this, true, logicaCliente.getNombre());

        } else {
            JOptionPane.showMessageDialog(rootPane, " Vuelva a entrar");
            Juego juego = new Juego();
            juego.setLocationRelativeTo(null);
            juego.setVisible(true);
            this.dispose();
        }

        pack();


    }//GEN-LAST:event_ventanaAvierta

    private void btnIzquierdaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnIzquierdaActionPerformed
        // TODO add your handling code here:

        if (jpFichaParaJugar.getComponents().length > 0) {
            JLabel label = (JLabel) jpFichaParaJugar.getComponent(0);
            logicaCliente.mandarFichaIzquierda(label.getName(), jpJuego, btnDerecha, btnPaso, btnIzquierda);
            jpFichaParaJugar.removeAll();
        }
        //logicaCliente.mandarFichaIzquierda("0_0",jpJuego);        
        //logicaCliente.mandarFichaIzquierda("2_3");
        //logicaCliente.mandarFichaIzquierda("2_4");  


    }//GEN-LAST:event_btnIzquierdaActionPerformed

    private void btnDerechaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDerechaActionPerformed
        // TODO add your handling code here:
        if (jpFichaParaJugar.getComponents().length > 0) {
            JLabel label = (JLabel) jpFichaParaJugar.getComponent(0);
            logicaCliente.mandarFichaDerecha(label.getName(), jpJuego, btnDerecha, btnPaso, btnIzquierda);
            jpFichaParaJugar.removeAll();
        }
    }//GEN-LAST:event_btnDerechaActionPerformed

    private void btnPasoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPasoActionPerformed
        // TODO add your handling code here:
        logicaCliente.paso(btnIzquierda, btnPaso, btnDerecha);
    }//GEN-LAST:event_btnPasoActionPerformed

    /**
     * @param args the command line arguments
     */

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnDerecha;
    private javax.swing.JButton btnIzquierda;
    private javax.swing.JButton btnPaso;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane;
    private javax.swing.JPanel jpFichaParaJugar;
    private javax.swing.JPanel jpFichasDisponibles;
    private javax.swing.JPanel jpJuego;
    // End of variables declaration//GEN-END:variables
}
