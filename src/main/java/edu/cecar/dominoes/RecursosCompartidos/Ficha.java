package edu.cecar.dominoes.RecursosCompartidos;

public class Ficha {

    String numeroIzquierda;
    String numeroDerecha;
    boolean izquierda = false;
    boolean derecha = false;
    boolean cambioPos = false;

    String rotacion;

    public boolean isIzquierda() {
        return izquierda;
    }

    public boolean isDerecha() {
        return derecha;
    }

    public void setIzquierda(boolean izquierda) {
        if (cambioPos) {
            this.derecha = izquierda;
        } else {
            this.izquierda = izquierda;
        }
    }

    public void setDerecha(boolean derecha) {
        if (cambioPos) {
            this.izquierda = derecha;
        } else {
            this.derecha = derecha;
        }
    }

    public void setCambioPos(boolean cambioPos) {
        this.cambioPos = cambioPos;
    }

    public String fichaCompleta() {
        String resultado = numeroIzquierda + "_" + numeroDerecha;

        if (cambioPos) {
            resultado = numeroDerecha + "_" + numeroIzquierda;
        }

        return resultado;
    }

    public boolean isCambioPos() {
        return cambioPos;
    }

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
        //if(cambioPos){
        //return numeroIzquierda;
        //}
        return numeroDerecha;

    }

    public void setNumeroDerecha(String numeroDerecha) {
        this.numeroDerecha = numeroDerecha;
    }

    public String getNumeroIzquierda() {
        //if(cambioPos){
        //return numeroDerecha;
        //}

        return numeroIzquierda;
    }

    public void setNumeroIzquierda(String numeroIzquierda) {
        this.numeroIzquierda = numeroIzquierda;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof Ficha){
            Ficha ficha = (Ficha) obj;
            return fichaCompleta().equals(ficha.getNumeroIzquierda() + "_" + ficha.getNumeroDerecha());
        }else{
            System.out.println("mo es instance");
            return false;
        }

      
         //To change body of generated methods, choose Tools | Templates.
    }

}
