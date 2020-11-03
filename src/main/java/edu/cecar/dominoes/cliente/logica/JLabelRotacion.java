package edu.cecar.dominoes.cliente.logica;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class JLabelRotacion extends JLabel {

    String ficha;
    Double angulos;

    public JLabelRotacion(String ficha, String angulos) {
        //setSize(130,80);
        setPreferredSize(new Dimension(150, 150));
       
        //this.ficha = ficha;
        

        Image image = new ImageIcon("src/main/java/edu/cecar/dominoes/cliente/recursosGraficos/" + ficha + ".jpg").getImage();
        
        ImageIcon icon = new ImageIcon(image.getScaledInstance(80,130,  Image.SCALE_DEFAULT));
        
        setIcon(icon);

        this.angulos = Double.valueOf(angulos);
    }

    @Override
    protected void paintComponent(Graphics graphics) {

        Graphics2D graficoNuevo = (Graphics2D) graphics;

        graficoNuevo.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        

        AffineTransform at = graficoNuevo.getTransform();
        Shape figura = graficoNuevo.getClip();

        double X = getWidth() / 2.0;
        //System.out.println("x: "+ X);
        double Y = getHeight() / 2.0;
        //System.out.println("y: "+Y);

        at.rotate(Math.toRadians(angulos), X, Y);

        graficoNuevo.setTransform(at);
        graficoNuevo.setClip(figura);

        super.paintComponent(graphics);

    }

}
