package es.upm.dit.adsw.pacman2;

import java.util.Random;
/**
 * @author diego martin crespo G22
 *
 */

public class Fantasma00 extends Movil implements Runnable{

	
	private Terreno terreno;
	private Casilla casilla;
	private boolean vivo;
	private long delay=400;
	public Fantasma00(Terreno terreno) {
		this.terreno = terreno; 
		setImage("FantasmaRojo.png");
		 vivo = true;
	}
	
	@Override
	public Casilla getCasilla() {
		return this.casilla;
		
	}
	@Override
	public void setCasilla(Casilla casilla) {
		this.casilla=casilla;
		
	}
	@Override
	public void muere(boolean devorado) {
			this.vivo=false;
	
	}

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
	@Override
	public void run() {
		Direccion[] direcciones= Direccion.values();
		Random random= new Random();
		while (vivo==true){
			this.terreno.move(this,direcciones[random.nextInt(direcciones.length)]);
		sleeping();
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
	
}