package edu.cecar.dominoes.Server;

import com.sun.security.ntlm.Client;
import edu.cecar.dominoes.RecursosCompartidos.Ficha;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Server {

    public static void main(String[] args) {
        new Server().iniciar();
    }

    int puerto = 5050;
    int cantidadJugadores = 4;
    private ServerSocket serverSocket;
    private DataInputStream entrada;
    private DataOutputStream salida;
    private Socket clienteConexion;
    private ArrayList<Cliente> clientes = new ArrayList<>();
    private String[] fichas = {"0_0", "0_1", "0_2", "0_3", "0_4", "0_5", "0_6", "1_1", "1_2", "1_3", "1_4", "1_5", "1_6", "2_2",
        "2_3", "2_4", "2_5", "2_6", "3_3", "3_4", "3_5", "3_6", "4_4", "4_5", "4_6", "5_5", "5_6", "6_6"};
    ArrayList<String> fichasDisponibles;
    private boolean darFichas = false;

    private int tiempoDeEspera = 5;
    private int tiempoRestanteEntreEspera = tiempoDeEspera;
    private boolean inicioEspera = false;
    private boolean inicioPartida = false;
    private int turno = 0;

    ArrayList<Ficha> fichasEnMesa = new ArrayList<Ficha>();

    public Server() {
        try {
            fichasDisponibles = new ArrayList<String>();
            for (String ficha : fichas) {
                fichasDisponibles.add(ficha);
            }

            serverSocket = new ServerSocket(puerto);
        } catch (IOException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void iniciar() {

        while (true) {

            try {
                clienteConexion = serverSocket.accept();

                //System.out.println("llega el cliente");
                entrada = new DataInputStream(clienteConexion.getInputStream());
                salida = new DataOutputStream(clienteConexion.getOutputStream());

                String[] datos = entrada.readUTF().split(",");
                if (datos[1].equals("estado")) {
                    salida.writeUTF(estadoServer());
                }

                if (permitirEntradaClientes(datos[0])) {

                    switch (datos[1]) {
                        case "validarNombre":
                            validarCliente(datos[2]);
                            break;

                        case "fichas":
                            fichas(datos[0]);
                            break;
                        case "listo":
                            listo(datos[0]);
                            break;
                        case "conectados":
                            conectados();
                            break;
                        case "tiempoDeEspera":
                            tiempoDeEspera();
                            break;
                        case "jugarIzquierda":
                            jugarIzquierda(datos[2]);

                            break;
                        case "jugarDerecha":
                            jugarDerecha(datos[2]);

                            break;
                        case "actualizacionTablero":
                            actualizarMeza(datos[0], datos[2]);
                            break;
                        case "turno":
                            esMiTurno(datos[0]);
                            break;
                        case "paso":
                            paso();
                            break;
                    }
                } else {
                    salida.writeUTF("error al enviar datos");

                }

            } catch (Exception ex) {
                Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

    private void esMiTurno(String nombre) throws IOException {

        if (turno == clientes.indexOf(new Cliente(nombre))) {
            salida.writeUTF("si");
        } else {
            salida.writeUTF("no");
        }

    }
    
    private void paso(){
        siguienteTurno();
    }

    private void actualizarMeza(String nombre, String dato) throws IOException {

        String resultado = "";
        if (fichasEnMesa.size() > Integer.valueOf(dato)) {
            resultado = "1;";
            for (Ficha ficha : fichasEnMesa) {
                resultado += ficha.fichaCompleta() + "," + ficha.getRotacion() + ";";
            }

        } else {
            resultado = "0;";
        }

        int pos = clientes.indexOf(new Cliente(nombre));

        clientes.get(pos).setActivo(true);

        //System.out.println(fichasEnMesa.size());
        //System.out.println(resultado);
        salida.writeUTF(resultado);

    }

    private void jugarDerecha(String fichaCliente) throws IOException {

        String resultado = "0";
        System.out.println(fichaCliente);
        String[] partesFicha = fichaCliente.split("_");
        int posUltima = fichasEnMesa.size() - 1;

        //esta condicion es aplicada solo cuando esta puesta la primera ficha en mesa
        if (fichasEnMesa.size() <= 0) {
            System.out.println("entro");

            Ficha ficha = new Ficha(partesFicha[1], partesFicha[0]);
            ficha.setCambioPos(true);
            ficha.setRotacion("90");
            resultado = "90";
            siguienteTurno();
            fichasEnMesa.add(ficha);

            //esta condicion es aplicada solo cuando esta puesta la primera ficha en mesa
        } else if (fichasEnMesa.size() == 1) {

            //evalua la prrmera ficha en mesa por el lado derecho para saber si la ficha que envia el cliente concuerda el lado derecho
            if (fichasEnMesa.get(posUltima).getNumeroDerecha().equals(partesFicha[0])) {

                Ficha ficha = new Ficha(partesFicha[0], partesFicha[1]);

                if (fichasEnMesa.get(posUltima).isCambioPos()) {
                    fichasEnMesa.get(posUltima).setIzquierda(true);
                } else {
                    fichasEnMesa.get(posUltima).setDerecha(true);
                }

                ficha.setIzquierda(true);
                ficha.setRotacion("-90");
                resultado = "-90";
                fichasEnMesa.add(ficha);
                siguienteTurno();
                System.out.println("parte correpa: " + partesFicha[0]);

                //evalua la prrmera ficha en mesa por el lado izquierdo para saber si la ficha que envia el cliente concuerda el lado derecho
            } else if (fichasEnMesa.get(posUltima).getNumeroDerecha().equals(partesFicha[1])) {

                Ficha ficha = new Ficha(partesFicha[0], partesFicha[1]);

                if (fichasEnMesa.get(posUltima).isCambioPos()) {
                    fichasEnMesa.get(posUltima).setIzquierda(true);
                } else {
                    fichasEnMesa.get(posUltima).setDerecha(true);
                }

                ficha.setDerecha(true);
                ficha.setRotacion("90");
                resultado = "90";
                fichasEnMesa.add(ficha);
                siguienteTurno();
                System.out.println("parte correpa: " + partesFicha[1]);
            } else {
                System.out.println("ficha no se puede jugar");
            }

            //condicion para evaluar cuando ya hay mas fichas en la mesa
        } else if (fichasEnMesa.size() > 1) {

            //verifica si la ficha que esta en la mesa  tiene un lado libre
            if (!fichasEnMesa.get(posUltima).isIzquierda()) {

                //evalua la ficha en mesa por el lado izquierdo para saber si la ficha que envia el cliente concuerda el lado izquierdo
                if (fichasEnMesa.get(posUltima).getNumeroIzquierda().equals(partesFicha[0])) {
                    System.out.println("holaa 1");

                    Ficha ficha = new Ficha(partesFicha[0], partesFicha[1]);
                    ficha.setIzquierda(true);
                    ficha.setRotacion("-90");
                    resultado = "-90";
                    fichasEnMesa.add(ficha);
                    siguienteTurno();
                    System.out.println("parte correcta cuando hay mas de 1 ladi iz: " + partesFicha[0]);

                } else if (fichasEnMesa.get(posUltima).getNumeroIzquierda().equals(partesFicha[1])) {
                    System.out.println("holaa 2");
                    Ficha ficha = new Ficha(partesFicha[0], partesFicha[1]);
                    ficha.setDerecha(true);
                    ficha.setRotacion("90");
                    resultado = "90";
                    fichasEnMesa.add(ficha);
                    siguienteTurno();
                    System.out.println("parte correcta cuando hay mas de 1 lado de: " + partesFicha[1]);
                } else {
                    System.out.println("ficha no se puede jugar izquierda");
                }

            } else if (!fichasEnMesa.get(posUltima).isDerecha()) {

                //evalua la ficha en mesa por el lado izquierdo para saber si la ficha que envia el cliente concuerda el lado derecho
                if (fichasEnMesa.get(posUltima).getNumeroDerecha().equals(partesFicha[0])) {
                    System.out.println("holaa 3");
                    Ficha ficha = new Ficha(partesFicha[0], partesFicha[1]);
                    ficha.setIzquierda(true);
                    ficha.setRotacion("-90");
                    resultado = "-90";
                    fichasEnMesa.add(ficha);
                    siguienteTurno();
                    System.out.println("parte correcta cuando hay mas de 1 lado de: " + partesFicha[1]);
                } else if (fichasEnMesa.get(posUltima).getNumeroDerecha().equals(partesFicha[1])) {
                    System.out.println("holaa 4");
                    Ficha ficha = new Ficha(partesFicha[0], partesFicha[1]);
                    ficha.setDerecha(true);
                    ficha.setRotacion("90");
                    resultado = "90";
                    fichasEnMesa.add(ficha);
                    siguienteTurno();
                    System.out.println("parte correcta cuando hay mas de 1 lado de: " + partesFicha[1]);
                } else {
                    System.out.println("ficha no se puede jugar derecha");
                }

            }

        }
        /*System.out.println("vector");
        for (Ficha ficha : fichasEnMesa) {
            System.out.println(ficha.getNumeroIzquierda() + "_ " + ficha.getNumeroDerecha());
            System.out.println(ficha.isIzquierda() + " " + ficha.isDerecha());
        }
        System.out.println("fin");*/
        salida.writeUTF(resultado);
    }

    private void jugarIzquierda(String fichaCliente) throws IOException {

        String resultado = "0";
        System.out.println(fichaCliente);
        String[] partesFicha = fichaCliente.split("_");

        //esta condicion es aplicada solo cuando esta puesta la primera ficha en mesa
        if (fichasEnMesa.size() <= 0) {
            System.out.println("entro");

            Ficha ficha = new Ficha(partesFicha[0], partesFicha[1]);
            ficha.setRotacion("-90");
            resultado = "-90";
            siguienteTurno();
            fichasEnMesa.add(ficha);

            //esta condicion es aplicada solo cuando esta puesta la primera ficha en mesa
        } else if (fichasEnMesa.size() == 1) {

            //evalua la prrmera ficha en mesa por el lado izquierdo para saber si la ficha que envia el cliente concuerda el lado izquierdo
            if (fichasEnMesa.get(0).getNumeroIzquierda().equals(partesFicha[0])) {

                Ficha ficha = new Ficha(partesFicha[0], partesFicha[1]);

                if (fichasEnMesa.get(0).isCambioPos()) {
                    fichasEnMesa.get(0).setDerecha(true);
                } else {
                    fichasEnMesa.get(0).setIzquierda(true);
                }

                ficha.setIzquierda(true);
                ficha.setRotacion("90");
                resultado = "90";
                fichasEnMesa.add(0, ficha);
                siguienteTurno();
                System.out.println("parte correpa: " + partesFicha[0]);

                //evalua la prrmera ficha en mesa por el lado izquierdo para saber si la ficha que envia el cliente concuerda el lado derecho
            } else if (fichasEnMesa.get(0).getNumeroIzquierda().equals(partesFicha[1])) {

                Ficha ficha = new Ficha(partesFicha[0], partesFicha[1]);
                if (fichasEnMesa.get(0).isCambioPos()) {
                    fichasEnMesa.get(0).setDerecha(true);
                } else {
                    fichasEnMesa.get(0).setIzquierda(true);
                }

                ficha.setDerecha(true);
                ficha.setRotacion("-90");
                resultado = "-90";
                fichasEnMesa.add(0, ficha);
                siguienteTurno();
                System.out.println("parte correpa: " + partesFicha[1]);
            } else {
                System.out.println("ficha no se puede jugar");
            }

            //condicion para evaluar cuando ya hay mas fichas en la mesa
        } else if (fichasEnMesa.size() > 1) {

            //verifica si la ficha que esta en la mesa  tiene un lado libre
            if (!fichasEnMesa.get(0).isIzquierda()) {

                //evalua la ficha en mesa por el lado izquierdo para saber si la ficha que envia el cliente concuerda el lado izquierdo
                if (fichasEnMesa.get(0).getNumeroIzquierda().equals(partesFicha[0])) {
                    System.out.println("holaa 1");

                    Ficha ficha = new Ficha(partesFicha[0], partesFicha[1]);
                    ficha.setIzquierda(true);
                    ficha.setRotacion("90");
                    resultado = "90";
                    fichasEnMesa.add(0, ficha);
                    siguienteTurno();
                    System.out.println("parte correcta cuando hay mas de 1 ladi iz: " + partesFicha[0]);

                } else if (fichasEnMesa.get(0).getNumeroIzquierda().equals(partesFicha[1])) {
                    System.out.println("holaa 2");
                    Ficha ficha = new Ficha(partesFicha[0], partesFicha[1]);
                    ficha.setDerecha(true);
                    ficha.setRotacion("-90");
                    resultado = "-90";
                    fichasEnMesa.add(0, ficha);
                    siguienteTurno();
                    System.out.println("parte correcta cuando hay mas de 1 lado de: " + partesFicha[1]);
                } else {
                    System.out.println("ficha no se puede jugar izquierda");
                }

            } else if (!fichasEnMesa.get(0).isDerecha()) {

                //evalua la ficha en mesa por el lado izquierdo para saber si la ficha que envia el cliente concuerda el lado derecho
                if (fichasEnMesa.get(0).getNumeroDerecha().equals(partesFicha[0])) {
                    System.out.println("holaa 3");
                    Ficha ficha = new Ficha(partesFicha[0], partesFicha[1]);
                    ficha.setIzquierda(true);
                    ficha.setRotacion("90");
                    resultado = "90";
                    fichasEnMesa.add(0, ficha);
                    siguienteTurno();
                    System.out.println("parte correcta cuando hay mas de 1 lado de: " + partesFicha[1]);
                } else if (fichasEnMesa.get(0).getNumeroDerecha().equals(partesFicha[1])) {
                    System.out.println("holaa 4");
                    Ficha ficha = new Ficha(partesFicha[0], partesFicha[1]);
                    ficha.setDerecha(true);
                    ficha.setRotacion("-90");
                    resultado = "-90";
                    fichasEnMesa.add(0, ficha);
                    siguienteTurno();
                    System.out.println("parte correcta cuando hay mas de 1 lado de: " + partesFicha[1]);
                } else {
                    System.out.println("ficha no se puede jugar derecha");
                }

            }

        }
        System.out.println("vector");
        for (Ficha ficha : fichasEnMesa) {
            System.out.println(ficha.getNumeroIzquierda() + "_ " + ficha.getNumeroDerecha());
            System.out.println(ficha.isIzquierda() + " " + ficha.isDerecha());
        }
        System.out.println("fin");
        salida.writeUTF(resultado);
    }

    private boolean permitirEntradaClientes(String nombre) {
        boolean resultado = false;

        if (clientes.size() <= cantidadJugadores || clientes.contains(new Cliente(nombre))) {

            resultado = true;
        }

        return resultado;

    }

    private String estadoServer() {
        String resultado = "";
        if (clientes.size() <= cantidadJugadores && !inicioPartida) {
            resultado = "1,ok";
        } else {
            resultado = "0,";
            if (clientes.size() > cantidadJugadores) {
                resultado += "sala de jugadores completa";
            }
            if (inicioPartida) {
                resultado += " ya inicio la partida";
            }
        }
        return resultado;
    }

    private void fichas(String nombre) throws IOException {

        int posCliente = clientes.indexOf(new Cliente(nombre));
        String fichas = "";
        Random r = new Random();

        for (int i = 0; i < 7; i++) {
            int numero = r.nextInt(fichasDisponibles.size());
            clientes.get(posCliente).addFicha(fichasDisponibles.get(numero));
            fichas += fichasDisponibles.get(numero) + ",";
            System.out.println(nombre + " - " + fichasDisponibles.get(numero));
            fichasDisponibles.remove(numero);
        }
        //System.out.println("cantidad fichas: " + fichasDisponibles.size());

        salida.writeUTF(fichas);

    }

    private void validarCliente(String nombre) throws IOException {
        if (!clientes.contains(new Cliente(nombre))) {
            clientes.add(new Cliente(nombre));
            salida.writeUTF("ok");
        } else {
            salida.writeUTF("false");
        }
    }

    private void listo(String nombre) {
        //System.out.println("nmbre: "+nombre);
        int pos = clientes.indexOf(new Cliente(nombre));
        clientes.get(pos).setListo(true);
        int numeroListos = getNumeroClientesYaListos();
        if (numeroListos >= 3 && numeroListos <= 4 && !inicioEspera) {
            System.out.println("Tiempo espera iniciado");
            iniciarEspaeraJugadores();
        }
    }

    private void conectados() throws IOException {
        String resultado = "";
        for (Cliente cliente : clientes) {
            resultado += cliente.nombre + ",";

        }
        salida.writeUTF(resultado);
    }

    private void iniciarEspaeraJugadores() {

        inicioEspera = true;
        new Thread(() -> {

            for (int i = tiempoDeEspera; i > 0; i--) {

                tiempoRestanteEntreEspera--;

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException ex) {
                    Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
            inicioPartida = true;
            tiempoRestanteEntreEspera = tiempoDeEspera;
            inicioEspera = false;

            System.out.println("Tiempo espera fin");

        }).start();

    }

    private Integer getNumeroClientesYaListos() {
        int resultado = 0;
        for (Cliente cliente : clientes) {
            if (cliente.listo) {
                resultado += 1;
            }
        }
        System.out.println("numero listos: " + resultado);
        return resultado;
    }

    private void tiempoDeEspera() throws IOException {
        if (!inicioPartida) {
            salida.writeUTF("" + tiempoRestanteEntreEspera);
        } else {
            salida.writeUTF("-2");
        }
    }

    private void siguienteTurno() {

        if (turno >= clientes.size() - 1) {
            turno = 0;
        } else {
            turno++;
        }
    }

}
