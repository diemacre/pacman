package es.upm.dit.adsw.pacman2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * @author diego martin crespo G22
 *
 */

public class FantasmaListo extends Movil implements Runnable {


	private Terreno terreno;
	private Casilla casilla;
	private boolean vivo;
	private long delay = 300;

	public FantasmaListo(Terreno terreno) {
		this.terreno = terreno;
		setImage("FantasmaVerde.png");
		vivo = true;
	}

	@Override
	public Casilla getCasilla() {
		return this.casilla;

	}

	@Override
	public void setCasilla(Casilla casilla) {
		this.casilla = casilla;

	}

	@Override
	public void muere(boolean devorado) {
		this.vivo = false;

	}

	// fantasma que traviesa con movimiento aleatorio
	@Override
	public int puedoMoverme(Direccion direccion) {
		int memoria=0;
		if (!vivo || this.terreno.hayPared(this.casilla, direccion)){
			return 2;
		}
		
		while (this.casilla.isTrampa()) {
			return 1;
		}
		if (this.casilla.siguiente(direccion).getMovil() != null
				&& this.casilla.siguiente(direccion).getMovil() != this.terreno
						.getJugador()) {
			if(memoria>6){
				memoria ++;
				return 1;	
			}
			memoria=0;
			return 2;
			
		}

		return 0;

	} 
//alterna entre moverse aleatorio durante 6 movimientos( fantasma verde) 
// y despues durante 10 persiguiendo al jugador(fantasma amarillo) 
	@Override
	public void run() {
			int movimientos = 0;
			Direccion[] direcciones = Direccion.values();
			Random random = new Random();
		while (vivo) {
			
			if (movimientos <= 6) {
				this.terreno.move(this,
						direcciones[random.nextInt(direcciones.length)]);
				movimientos++;
				//System.out.println("color del fantasma/ tipo de movimiento: "+ this.getImagen());
				//System.out.println("time: " + time);
			}else if (movimientos > 6 && movimientos <= 16) {
				this.setImage("FantasmaAmarillo.png");
				Casilla origen = terreno.getJugador().getCasilla();
				Casilla destino = this.getCasilla();
				List<Casilla> pendientes = new ArrayList<Casilla>();
				pendientes.add(origen);
				List<Casilla> visitadas = new ArrayList<Casilla>();
				Map<Casilla, Casilla> predecesor = new HashMap<Casilla, Casilla>();
				bfs(destino, pendientes, visitadas, predecesor);
				Casilla siguiente = predecesor.get(this.casilla);
				this.terreno.move(this, this.casilla.getDireccion(siguiente));
				movimientos++;
				//System.out.println("color del fantasma/ tipo de movimiento: "+ this.getImagen());
				

			} else {
				this.setImage("FantasmaVerde.png");
				movimientos=0;
			
			
			}
			sleeping();
		}
	}

	private boolean bfs(Casilla destino, List<Casilla> pendientes,
			List<Casilla> visitadas, Map<Casilla, Casilla> predecesor) {

		while (!pendientes.isEmpty()) {
			Casilla c1 = pendientes.remove(0);
			visitadas.add(c1);
			if (c1.equals(destino))
				return true;

			for (Direccion direccion : Direccion.values()) {
				Casilla casilla = terreno.getCasilla(c1, direccion);
				if (casilla != null && !terreno.hayPared(c1, direccion)
						&& !visitadas.contains(casilla)) {
					predecesor.put(casilla, c1);
					pendientes.add(casilla);

				}
			}

		}
		return false;
	}

	private void sleeping() {
		try {
			Thread.sleep(delay);
			

		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
