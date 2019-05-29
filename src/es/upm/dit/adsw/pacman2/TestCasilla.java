package es.upm.dit.adsw.pacman2;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

/**
 * Bateria de pruebas sobre casillas.
 */
public class TestCasilla {
    public static final int N = 5;
    private Terreno terreno;

    @Before
    public void setup() {
        terreno = new Terreno(N);
    }

    /**
     * Una casilla normal.
     */
    @Test
    public void testSiguiente00() {
        Casilla a = new Casilla(terreno, 1, 2);
        assertEquals(new Casilla(terreno, 1, 3), a.siguiente(Direccion.NORTE));
        assertEquals(new Casilla(terreno, 1, 1), a.siguiente(Direccion.SUR));
        assertEquals(new Casilla(terreno, 2, 2), a.siguiente(Direccion.ESTE));
        assertEquals(new Casilla(terreno, 0, 2), a.siguiente(Direccion.OESTE));
    }

    /**
     * Casillas en los border.
     */
    @Test
    public void testSiguiente10() {
        assertNull(new Casilla(terreno, 1, N - 1).siguiente(Direccion.NORTE));
        assertNull(new Casilla(terreno, 1, 0).siguiente(Direccion.SUR));
        assertNull(new Casilla(terreno, N - 1, 1).siguiente(Direccion.ESTE));
        assertNull(new Casilla(terreno, 0, 1).siguiente(Direccion.OESTE));
    }

    /**
     * Entre dos casillas no adyacentes.
     */
    @Test
    public void testGetDireccion00() {
        Casilla a = new Casilla(terreno, 0, 0);
        Casilla b = new Casilla(terreno, 4, 5);
        assertNull(a.getDireccion(b));
        assertNull(b.getDireccion(a));
    }

    /**
     * Entre dos casillas adyacentes una encima de la otra.
     */
    @Test
    public void testGetDireccion01() {
        Casilla a = new Casilla(terreno, 4, 4);
        Casilla b = new Casilla(terreno, 4, 5);
        assertEquals(Direccion.NORTE, a.getDireccion(b));
        assertEquals(Direccion.SUR, b.getDireccion(a));
    }

}
