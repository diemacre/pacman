package es.upm.dit.adsw.pacman2;

/**
 * Recopila la informacion pertinente a una casilla del terreno.
 * Es basicamente un almacen de informacion, sin comportamiento.
 *
 * @author jose a. manas
 * @version 14.11.2014
 */
public class Casilla {
    private final Terreno terreno;
    private final int x;
    private final int y;

    private Movil movil = null;

    // atributos de la casilla.
    private boolean objetivo;
    private boolean trampa;
    private boolean llave;

    /**
     * Constructor.
     *
     * @param x abscisa.
     * @param y ordenada.
     */
    public Casilla(Terreno terreno, int x, int y) {
        this.terreno = terreno;
        this.x = x;
        this.y = y;
    }

    /**
     * Getter.
     *
     * @return posicion en el eje X.
     */
    public int getX() {
        return x;
    }

    /**
     * Getter.
     *
     * @return posicion en el eje Y.
     */
    public int getY() {
        return y;
    }

    /**
     * Getter.
     *
     * @return movil en la casilla; null si está vacia.
     */
    public Movil getMovil() {
        return movil;
    }

    /**
     * Pon un movil en la casilla.
     * Si ponemos null, se interpreta que la casilla queda vacia.
     *
     * @param movil movil que queremos colocar en esta casilla.
     */
    public void setMovil(Movil movil) {
        this.movil = movil;
    }

    /**
     * Getter.
     *
     * @return true si la casilla es objetivo.
     */
    public boolean isObjetivo() {
        return objetivo;
    }

    /**
     * Setter.
     *
     * @param objetivo valor a cargar.
     */
    public void setObjetivo(boolean objetivo) {
        this.objetivo = objetivo;
    }

    /**
     * Representacion grafica para trazas.
     *
     * @return devuelve la posicion en formato (x, y).
     */
    @Override
    public String toString() {
        return String.format("(%d, %d)", x, y);
    }

    /**
     * Compara una casilla con otra.
     *
     * @param o objeto con el que queremos comparar.
     * @return true si tienen la misma X y la misma Y.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Casilla casilla = (Casilla) o;
        return x == casilla.x && y == casilla.y;
    }

    /**
     * Test previo de igualdad.
     * Si dos objetos difieren en el hashcode, no pueden ser iguales.
     * Do objetos diferentes pueden tener el mismo hashcode (colision).
     *
     * @return hashcode
     */
    @Override
    public int hashCode() {
        return 31 * x + y;
    }

    /**
     * Casilla anexa en una cierta direccion-.
     *
     * @param direccion direccion de la siguiente casilla.
     * @return casilla anexa en la dirección dada o null ni es el borde.
     */
    public Casilla siguiente(Direccion direccion) {
        try {
            switch (direccion) {
                case NORTE:
                    return terreno.getCasilla(x, y + 1);
                case SUR:
                    return terreno.getCasilla(x, y - 1);
                case ESTE:
                    return terreno.getCasilla(x + 1, y);
                case OESTE:
                    return terreno.getCasilla(x - 1, y);
            }
        } catch (Exception ignored) {
        }
        return null;
    }

    /**
     * Averigua en que direccion tengo que moverme desde esta casilla para pasar a la proxima casilla c2.
     * This y c2 son casillas adyacentes.
     * No se tiene en cuenta si entre esta casilla y la otra hay o no una pared.
     *
     * @param c2 casilla de llegada.
     * @return direccion para ir de this a c2.
     */
    public Direccion getDireccion(Casilla c2) {
        if (c2.getX() == x && c2.getY() == y + 1)
            return Direccion.NORTE;
        if (c2.getX() == x && c2.getY() == y - 1)
            return Direccion.SUR;
        if (c2.getX() == x + 1 && c2.getY() == y)
            return Direccion.ESTE;
        if (c2.getX() == x - 1 && c2.getY() == y)
            return Direccion.OESTE;
        return null;
    }

    /**
     * Setter.
     *
     * @param trampa si es trampa o no.
     */
    public void setTrampa(boolean trampa) {
        this.trampa = trampa;
    }

    /**
     * Getter.
     *
     * @return si es trampa o no.
     */
    public boolean isTrampa() {
        return trampa;
    }

    /**
     * Setter.
     *
     * @param llave si es llave o no.
     */
    public void setLlave(boolean llave) {
        this.llave = llave;
    }

    /**
     * Getter.
     *
     * @return si es llave o no.
     */
    public boolean isLlave() {
        return llave;
    }
}
