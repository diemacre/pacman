package es.upm.dit.adsw.pacman2;

/**
 * Singleton. Mantiene los elementos del juego.
 *
 * @author jose a. manas
 * @version 14.11.2014
 */
public class Juego {
    /**
     * Nombre del juego.
     */
    public static final String TITULO = "Pacman I (19.11.2014)";

    private static Juego instance = new Juego();

    /**
     * Constructor privado: inaccesible.
     * Nadie más puede crear objetos de este tipo.
     */
    private Juego() {
    }

    /**
     * Getter.
     *
     * @return el objeto de tipo Juego.
     */
    public static Juego getInstance() {
        return instance;
    }

    /**
     * Dimensiones del cuadrado de juego.
     */
    public static final int N = 15;

    private View view;
    private Terreno terreno;

    /**
     * Setter.
     */
    public void setView(View view) {
        this.view = view;
    }

    /**
     * Para empezar un nuevo juego.
     * Crea un terreno y lo carga en la GUI.
     */
    public void start() {
        terreno = new Terreno(N);
        if (view != null)
            view.setTerreno(terreno);
    }

    /**
     * Para acabar una partida.
     * Para los moviles que haya y vuelve a empezar.
     */
    public void restart() {
        if (terreno != null)
            terreno.paraMoviles();
        start();
    }

    /**
     * Actualiza la pantalla.
     */
    public void pintar() {
        if (view != null)
            view.pintame();
    }

    /**
     * Muestra un mensaje en una ventana emergente.
     *
     * @param texto
     */
    public void mensaje(String texto) {
        if (view != null)
            view.muestra(texto);
    }

    /**
     * Coloca y arranca un nuevo movil en el terreno.
     *
     * @param casilla donde poner el nuevo movil.
     * @param id      tipo de movil.
     */
    public void pon(Casilla casilla, Integer id) {
        switch (id) {
            case 0: {
                Estatua estatua = new Estatua();
                terreno.put(casilla, estatua);
                break;
            }
        
            case 1: {
            	Fantasma00 fantasma00 = new Fantasma00(terreno); 
            	terreno.put(casilla, fantasma00);
            	Thread thread = new Thread(fantasma00); 
            	thread.start();
            	break;
            }
            case 2: {
            	Depredador depredador = new Depredador(terreno); 
            	terreno.put(casilla, depredador);
            	Thread thread = new Thread(depredador); 
            	thread.start();
            	break;
            }
            case 3: {
            	casilla.setTrampa(true);
            	break;
            }
            case 4: {
            	casilla.setLlave(true);;
            	break;
            	
            }
            case 5:{
            	FantasmaListo fantasmaListo = new FantasmaListo(terreno); 
            	terreno.put(casilla, fantasmaListo);
            	Thread thread = new Thread(fantasmaListo); 
            	thread.start();

            	break;
            }
    		case 6:{
    			Clonador clonado = new Clonador(terreno); 
    			terreno.put(casilla, clonado);
    			Thread thread = new Thread(clonado); 
    			thread.start();

    			break;
    		}
    	}
        pintar(); 
        }

    /**
     * Para arrancar.
     */
    public static void main(String[] args) {
        Juego juego = getInstance();
        GUI gui = new GUI();
        gui.registra(0, "Patito");
        gui.registra(1, "Fantasma00");
        gui.registra(2, "Depredador");
        gui.registra(3, "Trampa");
        gui.registra(4, "Llave");
        gui.registra(5, "FantasmaListo");
        gui.registra(6, "Clonador");
        juego.setView(gui);
        juego.start();
    }
}
