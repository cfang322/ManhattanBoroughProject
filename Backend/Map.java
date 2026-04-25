package Backend;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class Map extends JPanel {

    private BufferedImage mapImage;
    private double zoomFactor = 1.0;
    private final double ZOOM_STEP = 0.1;
    private final double MIN_ZOOM = 0.5;
    private final double MAX_ZOOM = 3.0;

    public Map() {
        // Load your map image
        try {
            mapImage = ImageIO.read(new File("manhattan_map.png")); // put your map image path here
        } catch (IOException e) {
            System.out.println("Map image not found: " + e.getMessage());
        }

        // Zoom with mouse scroll wheel
        addMouseWheelListener(new MouseWheelListener() {
            @Override
            public void mouseWheelMoved(MouseWheelEvent e) {
                if (e.getWheelRotation() < 0) {
                    zoomIn();
                } else {
                    zoomOut();
                }
            }
        });

        // Zoom with keyboard + and -
        setFocusable(true);
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_PLUS || e.getKeyCode() == KeyEvent.VK_EQUALS) {
                    zoomIn();
                } else if (e.getKeyCode() == KeyEvent.VK_MINUS) {
                    zoomOut();
                }
            }
        });
    }

    public void zoomIn() {
        if (zoomFactor < MAX_ZOOM) {
            zoomFactor += ZOOM_STEP;
            repaint();
        }
    }

    public void zoomOut() {
        if (zoomFactor > MIN_ZOOM) {
            zoomFactor -= ZOOM_STEP;
            repaint();
        }
    }

    public double getZoomFactor() {
        return zoomFactor;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        // Smooth rendering
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);

        if (mapImage != null) {
            int newWidth  = (int) (mapImage.getWidth()  * zoomFactor);
            int newHeight = (int) (mapImage.getHeight() * zoomFactor);

            // Center the map on the panel
            int x = (getWidth()  - newWidth)  / 2;
            int y = (getHeight() - newHeight) / 2;

            g2d.drawImage(mapImage, x, y, newWidth, newHeight, this);
        } else {
            // Placeholder if no image loaded
            g2d.setColor(Color.LIGHT_GRAY);
            g2d.fillRect(0, 0, getWidth(), getHeight());
            g2d.setColor(Color.BLACK);
            g2d.drawString("Map not loaded", getWidth() / 2 - 50, getHeight() / 2);
        }
    }

    // Quick test — remove this if you're embedding Map into a larger frame
    public static void main(String[] args) {
        JFrame frame = new JFrame("Manhattan Map");
        Map map = new Map();

        // Zoom buttons
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

        map.requestFocusInWindow(); // needed for keyboard zoom
    }
}
