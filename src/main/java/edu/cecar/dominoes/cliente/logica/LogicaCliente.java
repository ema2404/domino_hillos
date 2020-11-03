/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.cecar.dominoes.cliente.logica;

import edu.cecar.dominoes.cliente.vista.Ganador;
import edu.cecar.dominoes.cliente.vista.NotificarTurno;
import java.awt.BorderLayout;
import java.awt.Frame;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;

public class LogicaCliente {

    String ipServer = "127.0.0.1";
    int puerto = 5050;
    String nombre = "";
    boolean fueNotificado = false;
    boolean fueNotificadoGanador=false;
    ArrayList<JLabel> labels = new ArrayList<JLabel>();
    ArrayList<String> fichas = new ArrayList<String>();
    private JLabel fichaSeleccionada;
    private JLabel fichaABorar;
    boolean juegoGanado=false;

    Socket conexion;
    private int numMaxBloqueo =7;

    private boolean mandarMensaje(String nombre, String peticion, String mensaje) {
        boolean resultado = false;
        try {
            conexion = new Socket(ipServer, puerto);
            DataOutputStream salida = new DataOutputStream(conexion.getOutputStream());
            salida.writeUTF(nombre + "," + peticion + "," + mensaje);
            resultado = true;
        } catch (IOException ex) {
            Logger.getLogger(LogicaCliente.class.getName()).log(Level.SEVERE, null, ex);
        }

        return resultado;
    }

    private String recivirMensaje() {
        String resultado = "";

        try {
            DataInputStream entrada;
            entrada = new DataInputStream(conexion.getInputStream());
            resultado = entrada.readUTF();
        } catch (Exception ex) {
            Logger.getLogger(LogicaCliente.class.getName()).log(Level.SEVERE, null, ex);
            resultado = "error al enviar datos al server 1";
        }

        return resultado;

    }

    public void esMiTurno(NotificarTurno notificarTurno, JButton btn1, JButton btn2, JButton btn3) {
        mandarMensaje(nombre, "turno", "");
        if (recivirMensaje().equals("si") && !notificarTurno.isVisible() && !fueNotificado) {

            notificarTurno.setLocationRelativeTo(null);
            notificarTurno.setVisible(true);
            fueNotificado = true;

            btn1.setEnabled(true);
            btn2.setEnabled(true);
            btn3.setEnabled(true);
        }
        System.out.println(notificarTurno.isVisible());
    }

    public String validarNombre(String n, JFrame principal) {

        mandarMensaje(n, "validarNombre", n);
        String respuesta = recivirMensaje();

        if (respuesta.equals("ok")) {
            this.nombre = n;
            principal.setTitle("Jugador: " + n);
            return "ok";
        } else {
            return respuesta;
        }

    }

    public String estadoServer(String nombre) {
        mandarMensaje(nombre, "estado", "");
        return recivirMensaje();
    }

    public void pedirFichas(JPanel tableroMisFichas, JPanel fichaAjugar) {
        mandarMensaje(nombre, "fichas", "");

        String[] fichasString = recivirMensaje().split(",");

        for (int i = 0; i < numMaxBloqueo; i++) {
            JLabel label = new JLabel();
            label.setSize(80, 150);
            //label.setLocation(0, 0);
            label.setName(fichasString[i]);
            //label.setPreferredSize(new Dimension(80, 150));
            fichas.add(fichasString[i]);

            ImageIcon image = new ImageIcon("src/main/java/edu/cecar/dominoes/cliente/recursosGraficos/" + fichasString[i] + ".jpg");
            Icon icon = new ImageIcon(image.getImage().getScaledInstance(label.getWidth(), label.getHeight(), Image.SCALE_DEFAULT));

            //label.setIcon(new ImageIcon("src/main/java/edu/cecar/dominoes/cliente/recursosGraficos/" + fichasString[i] + ".jpg"));
            label.setIcon(icon);

            label.addMouseListener(new MouseAdapter() {
                public void mouseClicked(MouseEvent ev) {

                    JLabel fichaJugada = new JLabel();
                    fichaJugada.setSize(80, 130);
                    fichaJugada.setName(label.getName());
                    System.out.println(label.getName());

                    fichaAjugar.removeAll();
                    ImageIcon image = new ImageIcon("src/main/java/edu/cecar/dominoes/cliente/recursosGraficos/" + label.getName() + ".jpg");
                    Icon icon = new ImageIcon(image.getImage().getScaledInstance(fichaJugada.getWidth(), fichaJugada.getHeight(), Image.SCALE_DEFAULT));

                    fichaJugada.setIcon(icon);
                    fichaAjugar.add(fichaJugada);
                    fichaAjugar.revalidate();
                    fichaAjugar.repaint();

                    fichaSeleccionada = fichaJugada;
                    fichaABorar = label;

                }

            });

            tableroMisFichas.add(label, BorderLayout.CENTER);
            //label.repaint();

        }

    }

    public void getConectados(JLabel label) {
        mandarMensaje(nombre, "conectados", "ok");
        String[] respuesta = recivirMensaje().split(",");

        label.setText("Numero de conectados: " + respuesta.length);
        label.repaint();
    }

    public void estadisticas(JTextArea area){
        mandarMensaje(nombre, "estadisticas", "");
        area.setText(recivirMensaje());
    }
    
    public void estoyListo() {
        System.out.println("Listo: " + nombre);
        mandarMensaje(nombre, "listo", "ok");
    }

    public int tiempoEsperaultimoJugador() {
        mandarMensaje(nombre, "tiempoDeEspera", "");

        int resultado = Integer.valueOf(recivirMensaje());

        return resultado;
    }

    public void mandarFichaIzquierda(String ficha, JPanel tablero, JButton btn1, JButton btn2, JButton btn3) {
        mandarMensaje(nombre, "jugarIzquierda", fichaSeleccionada.getName());
        //mandarMensaje(nombre, "jugarIzquierda", ficha);
        String radios = recivirMensaje();
        if (!radios.equals("0")) {

            fueNotificado = false;
            fichaSeleccionada.setVisible(false);
            fichaABorar.setVisible(false);

            JLabel labelGuardar = new JLabelRotacion(fichaSeleccionada.getName(), radios);
            System.out.println(labelGuardar.getSize().toString());
            //labelGuardar.setSize(80, 130);
            tablero.add(labelGuardar, 0);
            //tablero.add(new JLabel("axczfvd"));
            tablero.revalidate();
            tablero.repaint();

            btn1.setEnabled(false);
            btn2.setEnabled(false);
            btn3.setEnabled(false);
        } else {
            System.out.println("ficha no posible");
            JOptionPane.showMessageDialog(btn3, "jugada no permitida");
        }

    }

    public void mandarFichaDerecha(String ficha, JPanel tablero, JButton btn1, JButton btn2, JButton btn3) {
        mandarMensaje(nombre, "jugarDerecha", fichaSeleccionada.getName());
        //mandarMensaje(nombre, "jugarIzquierda", ficha);
        String radios = recivirMensaje();
        if (!radios.equals("0")) {
            fueNotificado = false;
            fichaSeleccionada.setVisible(false);
            fichaABorar.setVisible(false);

            JLabel labelGuardar = new JLabelRotacion(fichaSeleccionada.getName(), radios);
            //System.out.println(labelGuardar.getSize().toString());
            //labelGuardar.setSize(80, 130);
            tablero.add(labelGuardar);
            //tablero.add(new JLabel("axczfvd"));
            tablero.revalidate();
            tablero.repaint();

            btn1.setEnabled(false);
            btn2.setEnabled(false);
            btn3.setEnabled(false);
        } else {
            System.out.println("ficha no posible");
            JOptionPane.showMessageDialog(btn3, "jugada no permitida");
        }

    }

    public void actualizacionJugada(JPanel panel) {
        //System.out.println("Actualizar nombre:" + nombre + ":");
        mandarMensaje(nombre, "actualizacionTablero", "" + panel.getComponents().length);
        String[] fichas = recivirMensaje().split(";");
        if (fichas[0].equals("1")) {
            panel.removeAll();
            for (int i = 1; i < fichas.length; i++) {
                String[] ficha = fichas[i].split(",");
                JLabel labelGuardar = new JLabelRotacion(ficha[0], ficha[1]);
                panel.add(labelGuardar);
            }

            panel.revalidate();
            panel.repaint();
            System.out.println("actualizacion");
        } else {
            System.out.println("no actualizada");
        }

    }

    public String getNombre() {
        return nombre;
    }

    public void paso(JButton btn1, JButton btn2, JButton btn3) {
        mandarMensaje(nombre, "paso", "");
        btn1.setEnabled(false);
        btn2.setEnabled(false);
        btn3.setEnabled(false);
        
        fueNotificado=false;
    }
    
    public void ganador(Frame f, Ganador ganador){
        mandarMensaje(nombre, "ganador", "");
        String[] mensaje= recivirMensaje().split(",");
        
        if (mensaje[0].equals("1") && !fueNotificadoGanador) {
             ganador = new Ganador(f, true,mensaje[1]);
            

            ganador.setLocationRelativeTo(null);
            ganador.setVisible(true);
            fueNotificadoGanador = true;
            juegoGanado=true;
 

            
        }else{
            System.out.println("no hay ganador");
        }

    }

    public boolean isJuegoGanado() {
        return juegoGanado;
    }

    public void setJuegoGanado(boolean juegoGanado) {
        this.juegoGanado = juegoGanado;
    }
    
    
}
