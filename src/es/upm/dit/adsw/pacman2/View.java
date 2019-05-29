package es.upm.dit.adsw.pacman2;

/**
 * Vista del juego.
 * Cubre View + Controller en un modelo MVC.
 * Se presta a diversas implementaciones.
 *
 * @author Jose A. Manas
 * @version 17.11.2014
 */
public interface View {

    /**
     * Setter.
     *
     * @param terreno un escenario de juego.
     */
    void setTerreno(Terreno terreno);

    /**
     * Le dice al visor deberia refrescar la pantalla.
     */
    void pintame();

    /**
     * Muestra un mensaje en una pantalla emergente.
     *
     * @param mensaje texto a presentar en la ventana.
     */
    void muestra(String mensaje);

    /**
     * Registramos un fantasma.
     * Se usara para poder crear diferentes tipos de fantasmas.
     *
     * @param id     numero unico para saber a qué tipo de fantasma nos referimos.
     * @param nombre texto para identificar al tipo de fatasma.
     */
    void registra(int id, String nombre);
}
