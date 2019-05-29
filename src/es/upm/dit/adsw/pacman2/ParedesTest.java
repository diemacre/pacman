package es.upm.dit.adsw.pacman2;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;


/**
 * Test de la clase Paredes
 * @author Diego Martin Crespo
 * @version 20.02.2015
 */



public class ParedesTest {
	private Terreno terreno= new Terreno(10);
	private Paredes paredes;
	private Casilla c34= terreno.getCasilla(3, 4);
	private Casilla c35= terreno.getCasilla(3, 5);
	private Casilla c00= terreno.getCasilla(0, 0);
	@Before
	public void setUp() throws Exception {
		paredes= new Paredes();
	}

	@Test
	public void testAdd() {
		
		paredes.add(null, null);
		assertEquals(0, paredes.size());
		
		paredes.add(null, Direccion.NORTE);
		assertEquals(0, paredes.size());
		
		paredes.add(c34, null);
		assertEquals(0, paredes.size());
		
		paredes.add(c34, Direccion.NORTE);
		assertEquals(1, paredes.size());
		
		paredes.add(c35, Direccion.SUR);
		assertEquals(1, paredes.size());
		
		paredes.add(c00, Direccion.NORTE);
		assertEquals(2, paredes.size());
		
		
		
	}
	@Test
	public void testGetPared(){
		paredes.add(c34, Direccion.NORTE);
		assertNotNull(paredes.get(c34, Direccion.NORTE));
		assertNull(paredes.get(c00, Direccion.NORTE));
		paredes.remove(paredes.get(c34, Direccion.NORTE));
		assertNull(paredes.get(c34, Direccion.NORTE));
		
	}

	@Test
	public void testRemovePared(){ 
		paredes.add(c00, Direccion.NORTE);
		assertEquals(1, paredes.size());
		paredes.remove(null);
		assertEquals(1, paredes.size());
		paredes.remove(paredes.get(c00, Direccion.NORTE));
		paredes.remove(paredes.get(c00, Direccion.SUR));
		assertEquals(0, paredes.size());
		assertFalse(paredes.hayPared(c00,Direccion.NORTE));
}

	@Test
	public void testRemoveCasillaDireccion() {
		paredes.add(c34, Direccion.NORTE);
		assertEquals(1, paredes.size());
		paredes.remove(null,null);
		paredes.remove(null,Direccion.NORTE);
		assertEquals(1, paredes.size());
		paredes.remove(c34, Direccion.NORTE);
		assertEquals(0, paredes.size());
		assertFalse(paredes.hayPared(c34,Direccion.NORTE));
		
	}

	@Test
	public void testHayPared() {
		assertTrue(paredes.hayPared(null,null));
		assertTrue(paredes.hayPared(null,Direccion.NORTE));
		assertTrue(paredes.hayPared(c34,null));
		paredes.add(c34, Direccion.NORTE);
		assertTrue(paredes.hayPared(c34,Direccion.NORTE));
	}

	@Test
	public void testSize() {
		paredes.add(c00, Direccion.NORTE);
		assertEquals(1, paredes.size());
		
		paredes.add(c00, Direccion.SUR);
		assertEquals(1, paredes.size());
		
		paredes.add(c00, Direccion.OESTE);
		assertEquals(1, paredes.size());
		
		paredes.add(c34, Direccion.NORTE);
		assertEquals(2, paredes.size());
		
		paredes.add(c35, Direccion.SUR);
		assertEquals(2, paredes.size());
		
		
	}
}
