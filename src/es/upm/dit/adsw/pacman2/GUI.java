package es.upm.dit.adsw.pacman2;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.AffineTransform;
import java.util.HashMap;

/**
 * Interfaz grafica.
 * Presenta el estado del juego y captura la interaccion del usuario.
 *
 * @author Jose A. Manas
 * @version 29.3.2014
 */
public class GUI
        extends JPanel implements View {
    /**
     * Espacio entre la zona de juego y el borde de la ventana.
     */
    private static final int MARGEN = 10;
    /**
     * Ancho de la zona de juego.
     */
    private static final int ANCHO = 500;
    /**
     * Tamano de una casilla: pixels.
     */
    private int lado1;

    private Terreno terreno;

    private java.util.Map<Integer, String> registro = new HashMap<Integer, String>();

    /**
     * Interfaz grafica de usuario.
     * Hace de Visor y de Controlador en el modelo MVC.
     */
    public GUI() {
        JFrame frame = new JFrame(Juego.TITULO);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(ANCHO, ANCHO));
        frame.getContentPane().add(this, BorderLayout.CENTER);
        setFocusable(true);

        JToolBar toolBar = new JToolBar();
        toolBar.setFloatable(false);
        frame.add(toolBar, BorderLayout.SOUTH);

        JButton restartButton = new JButton("reinicio");
        toolBar.add(Box.createHorizontalGlue());
        toolBar.add(restartButton);
        toolBar.add(Box.createHorizontalGlue());

        restartButton.addActionListener(new RestartAction());

        addKeyListener(new MyKeyListener());
        addMouseListener(new MyMouseListener());

        frame.pack();
        frame.setVisible(true);
        requestFocusInWindow();
    }

    /**
     * Setter.
     *
     * @param terreno un escenario de juego.
     */
    public void setTerreno(Terreno terreno) {
        this.terreno = terreno;
        lado1 = (ANCHO - 2 * MARGEN) / terreno.getN();
        pintame();
    }

    /**
     * Registramos un fantasma.
     * Se usara para poder crear diferentes tipos de fantasmas.
     *
     * @param id     numero unico para saber a qué tipo de fantasma nos referimos.
     * @param nombre texto para identificar al tipo de fatasma.
     */
    public void registra(int id, String nombre) {
        registro.put(id, nombre);
    }

    /**
     * Le dice al thread de swing que deberia refrescar la pantalla.
     * Swing lo hara cuando le parezca bien.
     */
    @Override
    public void pintame() {
        repaint();
    }

    /**
     * Llamada por java para pintarse en la pantalla.
     *
     * @param g sistema grafico 2D para dibujarse.
     */
    public void paint(Graphics g) {
        if (terreno == null)
            return;

        g.setColor(Color.WHITE);
        g.fillRect(0, 0, getWidth(), getHeight());

        g.setColor(Color.LIGHT_GRAY);
        int nwx = MARGEN;
        int nwy = MARGEN;
        int lado = terreno.getN();

        // pinta las celdas con su contenido (movil)
        for (int x = 0; x < lado; x++) {
            for (int y = 0; y < lado; y++) {
                pintaCasilla(g, x, y);
            }
        }

        // pinta el marco
        g.setColor(Color.BLACK);
        g.drawLine(nwx - 1, nwy - 1, nwx - 1, nwy + lado * lado1 + 1);
        g.drawLine(nwx + lado * lado1 + 1, nwy - 1, nwx + lado * lado1 + 1, nwy + lado * lado1 + 1);
        g.drawLine(nwx - 1, nwy - 1, nwx + lado * lado1 + 1, nwy - 1);
        g.drawLine(nwx - 1, nwy + lado * lado1 + 1, nwx + lado * lado1 + 1, nwy + lado * lado1 + 1);
    }

    /**
     * Pinta una celda.
     *
     * @param g sistema grafico 2D para dibujarse.
     * @param x columna.
     * @param y fila.
     */
    private void pintaCasilla(Graphics g, int x, int y) {
        Casilla casilla = terreno.getCasilla(x, y);

        pintaTipo(g, casilla);

        // pinta las paredes de la casilla
        g.setColor(Color.RED);
        if (terreno.hayPared(casilla, Direccion.NORTE))
            g.drawLine(sw_x(x), sw_y(y + 1), sw_x(x + 1), sw_y(y + 1));
        if (terreno.hayPared(casilla, Direccion.SUR))
            g.drawLine(sw_x(x), sw_y(y), sw_x(x + 1), sw_y(y));
        if (terreno.hayPared(casilla, Direccion.ESTE))
            g.drawLine(sw_x(x + 1), sw_y(y), sw_x(x + 1), sw_y(y + 1));
        if (terreno.hayPared(casilla, Direccion.OESTE))
            g.drawLine(sw_x(x), sw_y(y), sw_x(x), sw_y(y + 1));

        Movil movil = casilla.getMovil();
        if (movil != null) {
            pintaImagen((Graphics2D) g, movil.getImagen(), x, y);
        }
    }

    /**
     * Pinta el tipo de casilla.
     *
     * @param g       sistema grafico 2D para dibujarse.
     * @param casilla casilla a pintar.
     */
    private void pintaTipo(Graphics g, Casilla casilla) {
        Color color;
        if (casilla.isObjetivo())
            color = Color.BLUE;
        else if (casilla.isTrampa())
            color = Color.RED;
        else if (casilla.isLlave())
            color = Color.GREEN;
        else
            return;
        int x = casilla.getX();
        int y = casilla.getY();
        int nwx = sw_x(x) + 3;
        int nwy = sw_y(y + 1) + 3;
        int dx = this.lado1 - 6;
        int dy = this.lado1 - 6;
        g.setColor(color);
        g.fillOval(nwx, nwy, dx, dy);
    }

    /**
     * Rellena el cuadrado de un cierto color.
     *
     * @param g     sistema grafico 2D para dibujarse.
     * @param x     columna.
     * @param y     fila.
     * @param color para rellenar.
     */
    private void rellena(Graphics g, int x, int y, Color color) {
        int nwx = sw_x(x) + 1;
        int nwy = sw_y(y + 1) + 1;
        int dx = this.lado1 - 2;
        int dy = this.lado1 - 2;
        g.setColor(color);
        g.fillRect(nwx, nwy, dx, dy);
    }

    /**
     * Pinta la imagen propia del movil.
     *
     * @param g2d    sistema grafico 2D para dibujar.
     * @param imagen imagen a dibujar.
     * @param x      columna.
     * @param y      fila.
     */
    private void pintaImagen(Graphics2D g2d, Image imagen, int x, int y) {
        if (imagen == null)
            return;
        int iWidth = imagen.getWidth(null);
        int iHeight = imagen.getHeight(null);
        double escalaX = 0.9 * lado1 / iWidth;
        double escalaY = 0.9 * lado1 / iHeight;
        double escala = Math.min(escalaX, escalaY);
        double nwX = sw_x(x) + (lado1 - escala * iWidth) / 2;
        double nwY = sw_y(y + 1) + (lado1 - escala * iHeight) / 2;
        AffineTransform transform = new AffineTransform(escala, 0, 0, escala, nwX, nwY);
        g2d.drawImage(imagen, transform, null);
    }

    /**
     * Dada una columna, calcula el vertice inferior izquierdo.
     *
     * @param columna columna.
     * @return abscisa del vertice inferior izquierdo.
     */
    private int sw_x(int columna) {
        return MARGEN + columna * lado1;
    }

    /**
     * Dada una fila, calcula el vertice inferior izquierdo.
     *
     * @param fila fila.
     * @return vertice inferior izquierdo.
     */
    private int sw_y(int fila) {
        int lado = terreno.getN();
        return MARGEN + (lado - fila) * lado1;
    }

    /**
     * Muestra un mensaje en una pantalla emergente sobre el terreno.
     *
     * @param mensaje texto a presentar en la ventana.
     */
    public void muestra(final String mensaje) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                JOptionPane.showMessageDialog(GUI.this,
                        mensaje, Juego.TITULO,
                        JOptionPane.INFORMATION_MESSAGE);
            }
        });
    }

    /**
     * Captura el teclado.
     */
    private class MyKeyListener
            extends KeyAdapter {
        /**
         * Gestiona el teclado.
         *
         * @param event tecla pulsada.
         */
        @Override
        public void keyPressed(KeyEvent event) {
            Direccion direccion = getDireccion(event);
            if (direccion != null)
                terreno.move(terreno.getJugador(), direccion);
        }

        private Direccion getDireccion(KeyEvent ke) {
            if (ke.getKeyCode() == KeyEvent.VK_UP)
                return Direccion.NORTE;
            if (ke.getKeyCode() == KeyEvent.VK_DOWN)
                return Direccion.SUR;
            if (ke.getKeyCode() == KeyEvent.VK_RIGHT)
                return Direccion.ESTE;
            if (ke.getKeyCode() == KeyEvent.VK_LEFT)
                return Direccion.OESTE;
            return null;
        }
    }

    /**
     * Captura el raton.
     */
    private class MyMouseListener
            extends MouseAdapter {
        @Override
        public void mouseClicked(MouseEvent event) {
            try {
                int pixelX = event.getX();
                int pixelY = event.getY();
                int x = (pixelX - MARGEN) / lado1;
                int y = terreno.getN() - 1 - (pixelY - MARGEN) / lado1;
                Casilla casilla = terreno.getCasilla(x, y);

                if (event.getClickCount() > 1 || SwingUtilities.isRightMouseButton(event)) {
                    if (terreno.getEstadoJuego() != EstadoJuego.JUGANDO)
                        return;

                    JPopupMenu popupMenu = new JPopupMenu("additions");
                    for (Integer id : registro.keySet()) {
                        String name = registro.get(id);
                        JMenuItem item = new JMenuItem(name);
                        item.addActionListener(new AddAction(id, casilla));
                        popupMenu.add(item);
                    }
                    popupMenu.show(event.getComponent(), pixelX, pixelY);
                }
            } catch (Exception ignored) {
            }
        }
    }

    /**
     * Cuando el usuario pulsa en reset.
     */
    private class RestartAction
            extends AbstractAction {
        @Override
        public void actionPerformed(ActionEvent e) {
            Juego.getInstance().restart();
            repaint();
            requestFocusInWindow();
        }
    }

    /**
     * Para poner algo en el terreno.
     */
    private class AddAction
            extends AbstractAction {
        private final Integer id;
        private final Casilla casilla;

        public AddAction(Integer id, Casilla casilla) {
            this.id = id;
            this.casilla = casilla;
        }

        @Override
        public void actionPerformed(ActionEvent event) {
            Juego.getInstance().pon(casilla, id);
        }
    }
}
