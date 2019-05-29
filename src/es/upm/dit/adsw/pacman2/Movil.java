package es.upm.dit.adsw.pacman2;

import javax.swing.*;
import java.awt.*;
import java.net.URL;

/**
 * Modela jugadores y fantasmas.
 *
 * @author Jose A. Manas
 * @version 14.11.2014
 */
public abstract class Movil {
    private Image image;

    /**
     * Proporciona como quiere ser presentado graficamente.
     *
     * @return una imagen adecuada.
     */
    public Image getImagen() {
        return image;
    }

    /**
     * Cara la imagen con la que quiere ser presentado en pantalla.
     *
     * @param filename fichero del que se carga la imagen.
     */
    public void setImage(String filename) {
        Class<Movil> root = Movil.class;
        String path = "imgs/" + filename;
        try {
            URL url = root.getResource(path);
            ImageIcon icon = new ImageIcon(url);
            image = icon.getImage();
        } catch (Exception e) {
            System.err.println("no se puede cargar "
                    + root.getPackage().getName()
                    + System.getProperty("file.separator")
                    + path);
            image = null;
        }
    }

    /**
     * Getter.
     *
     * @return en que casilla me encuentro.
     */
    public abstract Casilla getCasilla();

    /**
     * Setter.
     *
     * @param casilla en que casilla me colocan.
     */
    public abstract void setCasilla(Casilla casilla);

    /**
     * El movil deja de estar vivo,
     * bien porque es devorado por otro movil o porque se acaba el juego.
     *
     * @param devorado true si muere devorado por otro movil.
     */
    public abstract void muere(boolean devorado);

    /**
     * Alguien pregunta si mpuedo moverme ahora o en el futuro en una cierta direccion.
     *
     * @param direccion direccion en la que intento moverme.
     * @return 0 - si puedo moverme en esa direccion.<br/>
     *         1 - si ahora mismo no puedo, pero en el futuro puede que si.<br/>
     *         2 - si no puedo moverme, ni ahora ni nunca mas.
     */
    public abstract int puedoMoverme(Direccion direccion);

}
