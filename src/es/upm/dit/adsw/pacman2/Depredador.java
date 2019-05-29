package es.upm.dit.adsw.pacman2;

import java.util.*;

/**
 * @author diego martin crespo G22
 *
 */

public class Depredador extends Movil implements Runnable {

	private Terreno terreno;
	private Casilla casilla;
	private boolean vivo;
	private long delay = 300;

	public Depredador(Terreno terreno) {
		this.terreno = terreno;
		setImage("Anibal.png");
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

	@Override
	public int puedoMoverme(Direccion direccion) {
		int memoria =0;
		if (!vivo || this.terreno.hayPared(this.casilla, direccion)) {
			return 2;
		}

		while(this.casilla.isTrampa()){
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

	@Override
	public void run() {
		while (vivo) {
			Casilla origen = terreno.getJugador().getCasilla();
			
			Casilla destino = this.getCasilla();
			
			List<Casilla> pendientes = new ArrayList<Casilla>();
			pendientes.add(origen);
			
			List<Casilla> visitadas = new ArrayList<Casilla>();
			
			Map<Casilla, Casilla> predecesor = new HashMap<Casilla, Casilla>();
			
			if (bfs(destino, pendientes, visitadas, predecesor)){
			Casilla siguiente = predecesor.get(this.casilla);
			this.terreno.move(this, this.casilla.getDireccion(siguiente));
			sleeping();
			}
		}
	}

	private void sleeping() {
		try {
			Thread.sleep(delay);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * @param destino
	 * @param pendientes
	 * @param visitadas
	 * @param predecesor
	 * @return boolean
	 * metodo de busqueda
	 */
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
			// System.out.println("pendientes: " + pendientes);
			// System.out.println("visitadas: " + visitadas);
			// System.out.println("predecesor: " + predecesor);
		}
		return false;
	}
}
