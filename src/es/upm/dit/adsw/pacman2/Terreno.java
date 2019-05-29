package es.upm.dit.adsw.pacman2;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * El terreno de juego.
 *
 * @author jose a. manas
 * @version 14.11.2014
 */
public class Terreno {
    private Random random = new Random();

    private final Casilla[][] casillas;
    private Paredes paredes = new Paredes();

    private EstadoJuego estado;
    private Movil jugador;

    /**
     * Constructor.
     *
     * @param n numero de casillas en horizontal (X) y vertical (Y).
     */
    public Terreno(int n) {
        casillas = new Casilla[n][n];
        for (int x = 0; x < n; x++) {
            for (int y = 0; y < n; y++) {
                casillas[x][y] = new Casilla(this, x, y);
            }
        }

        estado = EstadoJuego.JUGANDO;
        ponParedes();
        setObjetivo(getN() - 1, getN() - 1);
        jugador = new Jugador(this);
        put(getCasilla(0, 0), jugador);
    }

    
    public synchronized void eliminaTrampas(){
    	for(int i=0; i< casillas.length;i++){
    		for(int j=0; j< casillas[i].length; j++){
    			casillas[i][j].setTrampa(false);
    		}
    	}
    }
    /**
     * Getter.
     *
     * @return dimension horizontal o vertical del terreno.
     */
    public synchronized int getN() {
        return casillas.length;
    }

    /**
     * Organiza el laberinto.
     */
    public synchronized void ponParedes() {
//        ponParedes_1();
        putParedes_2();
//        System.out.println("Test conectividad: " + testConectividad());
    }
    //algoritmo malo: no garantiza la conectividad

    private synchronized void ponParedes_1() {
        int n = getN();

        Direccion[] direcciones = Direccion.values();

        int nParedes = n * n;
        for (int i = 0; i < nParedes; i++) {
            int x = random.nextInt(n);
            int y = random.nextInt(n);
            int d = random.nextInt(direcciones.length);
            Direccion direccion = direcciones[d];
            paredes.add(getCasilla(x, y), direccion);
        }
    }
// algoritmo bueno: garantiza la conectividad , ademas abre mas paredes
    // es decir  rutas alternativas
    private synchronized void putParedes_2() {
        int n = getN();

        Direccion[] direcciones = Direccion.values();

        // pongo todas las paredes
        for (int x = 0; x < n; x++) {
            for (int y = 0; y < n; y++) {
                Casilla casilla = getCasilla(x, y);
                for (Direccion direccion : direcciones)
                    paredes.add(casilla, direccion);
            }
        }

        // pongo el minimo para interconectar todas las casilla
        java.util.List<Casilla> conectadas = new ArrayList<Casilla>();
        List<Pared> borde = new ArrayList<Pared>();

        int x0 = random.nextInt(n);
        int y0 = random.nextInt(n);
        Casilla casilla0 = getCasilla(x0, y0);
        conectadas.add(casilla0);
        for (Direccion direccion : direcciones) {
            Casilla sig = casilla0.siguiente(direccion);
            if (sig != null)
                borde.add(new Pared(casilla0, sig));
        }

        while (borde.size() > 0) {
            int i = (int) (Math.random() * borde.size());
            Pared pared = borde.get(i);
            borde.remove(pared);
            Casilla siguiente = pared.getCasilla1();
            if (conectadas.contains(siguiente))
                siguiente = pared.getCasilla2();
            if (conectadas.contains(siguiente))
                continue;
            paredes.remove(pared);
            conectadas.add(siguiente);
            for (Direccion d : direcciones) {
                Pared p = paredes.get(siguiente, d);
                if (p != null)
                    borde.add(p);
            }
        }

        // quito unas cuantas paredes mas para abrir rutas alternativas
        for (int q = 0; q < 2 * n; q++) {
            int x = random.nextInt(n);
            int y = random.nextInt(n);
            Direccion d = direcciones[random.nextInt(direcciones.length)];
            paredes.remove(getCasilla(x, y), d);
        }
    }

    /**
     * Casilla en una cierta posicion.
     *
     * @param x posicion horizontal (0..N-1).
     * @param y posicion vertical (0..N-1).
     * @return casilla en esas coordenadas.
     * @throws ArrayIndexOutOfBoundsException si las coordenadas estan fuera del tablero.
     */
    public synchronized Casilla getCasilla(int x, int y) {
        return casillas[x][y];
    }

    /**
     * Coloca un movil sobre el terreno.
     * Indirectamente, hay una casilla afectada.
     *
     * @param casilla posicion .
     * @param movil   lo que queremos colocar.
     * @return true si se puede y queda colocado; false si no es posible.
     */
    public synchronized boolean put(Casilla casilla, Movil movil) {
        try {
            if (casilla.getMovil() != null)
                return false;
            casilla.setMovil(movil);
            movil.setCasilla(casilla);
            Juego.getInstance().pintar();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Intenta trasladar el movil en la direccion indicada,
     * devolviendo la casilla en la que acaba el movil.
     * Si no puede moverse, devuelve la misma casilla origen.
     * Si en el destino hay un fantasma, se lo 'come' = muere().
     *
     * @param movil     el que queremos mover.
     * @param direccion direccion en la que nos queremos desplazar.
     * @return la casilla a donde se mueve el movil, si es posible moverse.
     * @see Movil#puedoMoverme(Direccion)
     */
    public synchronized Casilla move(Movil movil, Direccion direccion) {
    	
			Casilla origen = movil.getCasilla();
			int puedoMoverme = movil.puedoMoverme(direccion);
			
			while(puedoMoverme == 1){
				waiting();
				
			}
			if (puedoMoverme == 2)
			    return origen;

			Casilla destino = origen.siguiente(direccion);
			if (destino == null)
			    return origen;

			Movil m2 = destino.getMovil();
			if (m2 != null) {
			    if (m2.getClass() == Jugador.class)
			        m2.muere(true);
			    else
			        movil.muere(true);
			}
			origen.setMovil(null);
			destino.setMovil(movil);
			movil.setCasilla(destino);
			Juego.getInstance().pintar();

			if (estado == EstadoJuego.GANA_JUGADOR) {
			    paraMoviles();
			    Juego.getInstance().mensaje("El jugador gana");
			}
			if (estado == EstadoJuego.PIERDE_JUGADOR) {
			    paraMoviles();
			    Juego.getInstance().mensaje("El jugador pierde");
			}
			notifyAll();
			return destino;
		
    }

	private void waiting() {
		try {
			wait();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

    /**
     * Marca una posicion como objetivo para que el jugador gane.
     *
     * @param x posicion horizontal (0..N-1).
     * @param y posicion vertical (0..N-1).
     * @return true si es posible marcar la casilla como objetivo; false si no es posible.
     */
    public synchronized boolean setObjetivo(int x, int y) {
        try {
            Casilla casilla = getCasilla(x, y);
            casilla.setObjetivo(true);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Para (o congela) los moviles.
     * Mueren todos.
     */
    public synchronized void paraMoviles() {
        // 1. para los móviles
        for (int x = 0; x < getN(); x++) {
            for (int y = 0; y < getN(); y++) {
                Casilla casilla = getCasilla(x, y);
                Movil movil = casilla.getMovil();
                if (movil != null)
                    movil.muere(false);
            }
        }
    }

    /**
     * Setter.
     *
     * @param estado marca el estado del juego.
     */
    public synchronized void setEstado(EstadoJuego estado) {
        this.estado = estado;
    }

    public synchronized EstadoJuego getEstadoJuego() {
        return estado;
    }

    public synchronized Movil getJugador() {
        return jugador;
    }

    public synchronized boolean hayPared(Casilla casilla, Direccion direccion) {
        return paredes.hayPared(casilla, direccion);
    }

    public synchronized void ponPared(Casilla casilla, Direccion direccion) {
        paredes.add(casilla, direccion);
    }

	public synchronized Casilla getCasilla(Casilla c1, Direccion direccion) {
		return c1.siguiente(direccion);
	}
	
}
