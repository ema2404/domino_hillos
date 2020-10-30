package edu.cecar.dominoes.Server;

import java.util.ArrayList;

public class Cliente {

    String nombre;
    ArrayList<String> fichas = new ArrayList<String>();
    boolean listo = false;

    public Cliente(String nombre) {
        this.nombre = nombre;
    }

    public void addFicha(String ficha) {
        fichas.add(ficha);
    }

    public void setListo(boolean listo) {
        this.listo = listo;
    }

    public String getNombre() {
        return nombre;
    }

    public boolean isListo() {
        return listo;
    }

    @Override
    public boolean equals(Object obj) {

        if (obj instanceof Cliente) {
            return nombre.equals(((Cliente) obj).getNombre());
        }

        return false;//To change body of generated methods, choose Tools | Templates.
    }

}
