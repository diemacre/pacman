package es.upm.dit.adsw.pacman2;

/**
 * Uno de los moviles sobre el terreno.
 * Un fantasma que no se mueve.
 *
 * @author jose a. manas
 * @version 19.11.2014
 */
public class Estatua
        extends Movil {
    private Casilla casilla;

    /**
     * Constructor.
     * Usa super.loadImage() para inicializar la imagen.
     */
    public Estatua() {
        setImage("patito.png");
    }

    /**
     * Getter.
     *
     * @return la casilla en la que esta en cada momento.
     */
    public Casilla getCasilla() {
        return casilla;
    }

    /**
     * Setter.
     *
     * @param casilla en que casilla me colocan.
     */
    public void setCasilla(Casilla casilla) {
        this.casilla = casilla;
    }

    /**
     * El fantasma deja de estar vivo,
     * bien porque es devorado por otro movil o porque se acaba el juego.
     *
     * @param devorado true si muere devorado por otro movil.
     */
    public void muere(boolean devorado) {
    }

    /**
     * Alguien pregunta si mpuedo moverme ahora o en el futuro en una cierta direccion.
     *
     * @param direccion direccion en la que intento moverme.
     * @return 0 = puedo moverme en esa direccion.
     *         Porque esta vacia, o porque esta el jugador y me lo como.<br/>
     *         1 = ahora mismo no puedo, pero en el futuro puede que si.
     *         Sugerencia: tropiezo con otro fantasma.<br/>
     *         2 = no puedo moverme, ni ahora ni nunca.
     */
    public int puedoMoverme(Direccion direccion) {
        return 2;
    }
}
