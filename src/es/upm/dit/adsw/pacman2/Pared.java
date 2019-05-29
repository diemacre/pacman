package es.upm.dit.adsw.pacman2;

/**
 * Una pared entre 2 celdas.
 *
 * @author jose a. manas
 * @version 14.11.2014
 */
public class Pared {
    private final Casilla casilla1;
    private final Casilla casilla2;

    /**
     * Constructor.
     *
     * @param casilla1 una de las casillas.
     * @param casilla2 la otra casilla.
     */
    public Pared(Casilla casilla1, Casilla casilla2) {
        this.casilla1 = casilla1;
        this.casilla2 = casilla2;
    }

    /**
     * Constructor.
     *
     * @param casilla1  una de las casillas.
     * @param direccion de la otra casilla.
     */
    public Pared(Casilla casilla1, Direccion direccion) {
        this.casilla1 = casilla1;
        this.casilla2 = casilla1.siguiente(direccion);
        if (casilla2 == null)
            throw new IllegalArgumentException();
    }

    /**
     * Getter.
     *
     * @return casilla.
     */
    public Casilla getCasilla1() {
        return casilla1;
    }

    /**
     * Getter.
     *
     * @return casilla.
     */
    public Casilla getCasilla2() {
        return casilla2;
    }

    /**
     * Compara paredes.
     *
     * @param o con lo que nos comparamos.
     * @return true si es la misma casilla y la misma direccion.
     */
    @SuppressWarnings("RedundantIfStatement")
    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;

        Pared pared = (Pared) o;
        Casilla pc1 = pared.getCasilla1();
        Casilla pc2 = pared.getCasilla2();
        if (casilla1.equals(pc1) && casilla2.equals(pc2))
            return true;
        if (casilla1.equals(pc2) && casilla2.equals(pc1))
            return true;
        return false;
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
        return casilla1.hashCode() + casilla2.hashCode();
    }
}
