package es.upm.dit.adsw.pacman2;

/**
 * Vista del juego.
 * Implementacion para pruebas que basicamente no hace nada.
 *
 * @author Jose A. Manas
 * @version 17.11.2014
 */
public class TestingView
        implements View {

    /**
     * Setter.
     *
     * @param terreno un escenario de juego.
     */
    public void setTerreno(Terreno terreno) {
    }

    /**
     * Le dice al visor deberia refrescar la pantalla.
     */
    public void pintame() {
    }

    /**
     * Muestra un mensaje en una pantalla emergente sobre el terreno.
     *
     * @param mensaje texto a presentar en la ventana.
     */
    public void muestra(String mensaje) {
        System.out.println(mensaje);
    }

    /**
     * Registramos un fantasma.
     * Se usara para poder crear diferentes tipos de fantasmas.
     *
     * @param id     numero unico para saber a qué tipo de fantasma nos referimos.
     * @param nombre texto para identificar al tipo de fatasma.
     */
    public void registra(int id, String nombre) {
    }
}
