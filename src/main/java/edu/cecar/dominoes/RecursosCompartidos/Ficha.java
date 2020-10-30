

package edu.cecar.dominoes.RecursosCompartidos;


public class Ficha {

    String numeroIzquierda;
    String numeroDerecha;
    String rotacion;

    public String getRotacion() {
        return rotacion;
    }

    public void setRotacion(String rotacion) {
        this.rotacion = rotacion;
    }
    

    public Ficha(String numeroIzquierda, String numeroDerecha) {
        this.numeroIzquierda = numeroIzquierda;
        this.numeroDerecha = numeroDerecha;
    }

    public String getNumeroDerecha() {
        return numeroDerecha;
    }

    public void setNumeroDerecha(String numeroDerecha) {
        this.numeroDerecha = numeroDerecha;
    }

    public String getNumeroIzquierda() {
        return numeroIzquierda;
    }

    public void setNumeroIzquierda(String numeroIzquierda) {
        this.numeroIzquierda = numeroIzquierda;
    }

    @Override
    public boolean equals(Object obj) {
        
        
        Ficha ficha =(Ficha)obj;
        return (numeroIzquierda+"_"+numeroDerecha).equals(ficha.getNumeroIzquierda()+"_"+ficha.getNumeroDerecha()); //To change body of generated methods, choose Tools | Templates.
    }



    
}
