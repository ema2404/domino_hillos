package edu.cecar.dominoes.Server;

import edu.cecar.dominoes.RecursosCompartidos.Ficha;
import java.io.Serializable;
import java.util.ArrayList;

public class Cliente implements Serializable{

    String nombre;
    ArrayList<Ficha> fichas = new ArrayList<Ficha>();
    boolean listo = false;
    boolean activo=false;
    
    int ganadas=0;
    int perdidas=0;

    public ArrayList<Ficha> getFichas() {
        return fichas;
    }

    public void setFichas(ArrayList<Ficha> fichas) {
        this.fichas = fichas;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }
    
    public Cliente(String nombre) {
        this.nombre = nombre;
    }

    public void addFicha(Ficha ficha) {
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

    public int getGanadas() {
        return ganadas;
    }

    public void setGanadas(int ganadas) {
        this.ganadas = ganadas;
    }

    public int getPerdidas() {
        return perdidas;
    }

    public void setPerdidas(int perdidas) {
        this.perdidas = perdidas;
    }

    
    
    
    
    @Override
    public boolean equals(Object obj) {

        if (obj instanceof Cliente) {
            return nombre.equals(((Cliente) obj).getNombre());
        }

        return false;//To change body of generated methods, choose Tools | Templates.
    }

}
