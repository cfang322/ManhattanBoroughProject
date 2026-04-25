package Backend;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.*;

public class Map extends JPanel {

    private BufferedImage mapImage;
    private double zoomFactor = 1.0;
    private final double ZOOM_STEP = 0.1;
    private final double MIN_ZOOM = 0.5;
    private final double MAX_ZOOM = 3.0;
    private int osmZoom = 13;

    public Map() {
        try {
            mapImage = ImageIO.read(new File("manhattan_map.png"));
        } catch (IOException e) {
            System.out.println("Map image not found: " + e.getMessage());
        }

        addMouseWheelListener(e -> {
            if (e.getWheelRotation() < 0) zoomIn();
            else zoomOut();
        });

        setFocusable(true);
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_PLUS
                        || e.getKeyCode() == KeyEvent.VK_EQUALS) zoomIn();
                else if (e.getKeyCode() == KeyEvent.VK_MINUS) zoomOut();
            }
        });
    }

    public void zoomIn() {
        if (osmZoom < 17) { osmZoom++; loadMapFromOSM(); }
    }

    public void zoomOut() {
        if (osmZoom > 10) { osmZoom--; loadMapFromOSM(); }
    }

    private void loadMapFromOSM() {
        // TODO: fetch OSM tile at this.osmZoom and repaint
        repaint();
    }

    public double getZoomFactor() { return zoomFactor; }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
            RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING,
            RenderingHints.VALUE_RENDER_QUALITY);

        if (mapImage != null) {
            int newWidth  = (int)(mapImage.getWidth()  * zoomFactor);
            int newHeight = (int)(mapImage.getHeight() * zoomFactor);
            int x = (getWidth()  - newWidth)  / 2;
            int y = (getHeight() - newHeight) / 2;
            g2d.drawImage(mapImage, x, y, newWidth, newHeight, this);
        } else {
            g2d.setColor(Color.LIGHT_GRAY);
            g2d.fillRect(0, 0, getWidth(), getHeight());
            g2d.setColor(Color.BLACK);
            g2d.drawString("Map not loaded", getWidth() / 2 - 50, getHeight() / 2);
        }
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Manhattan Map");
        Map map = new Map();
        JButton zoomInBtn  = new JButton("+");
        JButton zoomOutBtn = new JButton("-");
        zoomInBtn.addActionListener(e  -> map.zoomIn());
        zoomOutBtn.addActionListener(e -> map.zoomOut());
        JPanel controls = new JPanel();
        controls.add(zoomOutBtn);
        controls.add(zoomInBtn);
        frame.setLayout(new BorderLayout());
        frame.add(map, BorderLayout.CENTER);
        frame.add(controls, BorderLayout.SOUTH);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        map.requestFocusInWindow();
    }
}