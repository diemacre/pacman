package es.upm.dit.adsw.pacman2;

/**
 * Orientaciones geograficas para mover los moviles por el terreno.
 *
 * @author jose a. manas
 * @version 14.11.2014
 */

public enum Direccion {
    NORTE, SUR, ESTE, OESTE;

    /**
     * Dada una direccion, devuelve la opuesta.
     * Por ejemplo, lo opuesto al NORTE es el SUR.
     *
     * @return direccion opuesta.
     */
    public Direccion opuesta() {
        switch (this) {
            case NORTE:
                return SUR;
            case SUR:
                return NORTE;
            case ESTE:
                return OESTE;
            case OESTE:
                return ESTE;
        }
        return this;
    }
}
