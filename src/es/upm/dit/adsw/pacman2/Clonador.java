package es.upm.dit.adsw.pacman2;

import java.util.Random;

/**
 * @author diego martin crespo G22
 *
 */
public class Clonador extends Movil implements Runnable{

	
	private Terreno terreno;
	private Casilla casilla;
	private boolean vivo;
	private long delay=400;
	public Clonador(Terreno terreno) {
		this.terreno = terreno; 
		setImage("Rastas.png");
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
		
		if (!vivo ) {
				return 2;
		}
		if (this.casilla == null){
			return 2;
		}
		if (this.casilla.siguiente(direccion) == null){
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
	
	// cada 20 movimientos cada movil clonador se clona 
	@Override
	public void run() {
		Direccion[] direcciones= Direccion.values();
		Random random= new Random();
		int time=0;
		
		
		while (vivo){ 
				
			try {
				Casilla next;
				if(time<10){
					this.terreno.move(this,direcciones[random.nextInt(direcciones.length)]);
					time++;
				}
				else if(time==10){	
					Clonador clon= new Clonador(terreno);
					 next=casilla.siguiente(direcciones[random.nextInt(direcciones.length)]);
					 System.out.println(next);
					if(next!=null && next.getMovil()==null){
						terreno.put(next, clon);
						time++;
						Thread thread= new Thread(clon);
						thread.start();
					}
				}
				else {
					time=0;
				}
				
				sleeping();
			} catch (NullPointerException e) {
				// en la linea de codigo 85 (next= ...) puede saltar un nullpointer
				e.printStackTrace();
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
	
}