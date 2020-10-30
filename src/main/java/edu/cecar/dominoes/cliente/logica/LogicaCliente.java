/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.cecar.dominoes.cliente.logica;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Label;
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
import javax.swing.JLabel;
import javax.swing.JPanel;

public class LogicaCliente {

    String ipServer = "127.0.0.1";
    int puerto = 5050;
    String nombre;
    ArrayList<JLabel> labels = new ArrayList<JLabel>();
    ArrayList<String> fichas = new ArrayList<String>();

    Socket conexion;

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

    public boolean validarNombre(String n) {

        mandarMensaje(n, "validarNombre", n);

        if (recivirMensaje().equals("ok")) {
            this.nombre = n;
            return true;
        } else {
            return false;
        }

    }

    public String estadoServer(String nombre) {
        mandarMensaje(nombre, "estado", "");
        return recivirMensaje();
    }

    public void pedirFichas(JPanel tableroMisFichas, JPanel fichaAjugar) {
        mandarMensaje(nombre, "fichas", "");

        String[] fichasString = recivirMensaje().split(",");

        for (int i = 0; i < 7; i++) {
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
                    ImageIcon image = new ImageIcon("src/main/java/edu/cecar/dominoes/cliente/recursosGraficos/" +label.getName()+ ".jpg");
                    Icon icon = new ImageIcon(image.getImage().getScaledInstance(fichaJugada.getWidth(), fichaJugada.getHeight(), Image.SCALE_DEFAULT));

                    //label.setIcon(new ImageIcon("src/main/java/edu/cecar/dominoes/cliente/recursosGraficos/" + fichasString[i] + ".jpg"));
                    fichaJugada.setIcon(icon);
                    fichaAjugar.add(fichaJugada);
                    fichaAjugar.revalidate();
                    fichaAjugar.repaint();

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

    public void estoyListo() {
        System.out.println("Listo: " + nombre);
        mandarMensaje(nombre, "listo", "ok");
    }

    public int tiempoEsperaultimoJugador() {
        mandarMensaje(nombre, "tiempoDeEspera", "");

        int resultado = Integer.valueOf(recivirMensaje());

        return resultado;
    }
    
    

}
