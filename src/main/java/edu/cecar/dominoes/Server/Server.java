package edu.cecar.dominoes.Server;

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

    private int tiempoDeEspera = 15;
    private int tiempoRestanteEntreEspera = tiempoDeEspera;
    private boolean inicioEspera = false;
    private boolean inicioPartida = false;

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
                    }
                } else {
                    salida.writeUTF("error al enviar datos");

                }

            } catch (Exception ex) {
                Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

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
            if(inicioPartida){
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

    //private int getTurno(){
    //}
}
