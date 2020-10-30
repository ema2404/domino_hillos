

package edu.cecar.dominoes.cliente.logica;


public class Ficha {

    String numeroFicha;

    public String getNumeroFicha() {
        return numeroFicha;
    }

    public void setNumeroFicha(String numeroFicha) {
        this.numeroFicha = numeroFicha;
    }

    public Ficha(String numeroFicha) {
        this.numeroFicha = numeroFicha;
    }
    
    

    @Override
    public String toString() {
        return "Ficha{" + "numeroFicha=" + numeroFicha + '}';
    }
    
    
    
}
