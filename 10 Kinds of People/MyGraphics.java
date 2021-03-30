import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class MyGraphics extends JPanel implements MouseListener {

    Solver solver;
    int unit = 25;
    int WIDTH = 600;
    int HEIGHT = 600;
    int HEIGHT_OFFSET = 28; // Corrects for the fact that the window height includes the bar at the top

    int wait = 50;

    boolean drawMode;

    public MyGraphics(Solver solver, boolean drawMode) {

        this.drawMode = drawMode;
        this.solver = solver;

        if (drawMode) {
            // setup code from main method
            JFrame frame = new JFrame("10 Kinds of People");
            frame.add(this);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setBounds(0, 0, WIDTH, HEIGHT + HEIGHT_OFFSET);
            frame.setVisible(true);
            
            // Constructor
            addMouseListener(this);
        }
    }

    // This method, with this exact name, parameter and return type, overwrites the paint() method in JPanel
    // (This is where the drawing actually happens)
    public void paint(Graphics g) {
        // Code to draw things in the window goes here
        super.paint(g);
        Graphics2D g2 = (Graphics2D) g; // Convert to a Graphics2D object, because Graphics2D has improved functionality

        g.setFont(new Font("Lora", Font.PLAIN, 32));
        // Draw current puzzle state
        for (int i = 0; i < solver.num_rows; i++) {
            for (int j = 0; j < solver.num_cols; j++) {

                // highlight goal cell in red
                if ((solver.goal != null) && (solver.get(i, j).equals(solver.goal))) {
                    g2.setColor(Color.RED);
                }
                else if (solver.get(i, j).visited) {
                    g2.setColor(Color.GREEN);
                }
                else if (solver.get(i, j).reachable) {
                    g2.setColor(Color.YELLOW);
                }
                else {
                    g2.setColor(Color.BLACK);
                }
                g2.drawString(String.valueOf(solver.get(i, j).value), (j * unit), ((i + 1) * unit));
            }
        }
    }

    public void updateBoard() {
        if (drawMode) {
            repaint();
            try {
                Thread.sleep(wait);
            }
            catch(Exception e) {
                System.out.println("Error, could not call Thread.sleep");
            }
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {}
    @Override
    public void mousePressed(MouseEvent e) {}
    @Override
    public void mouseReleased(MouseEvent e) {}
    @Override
    public void mouseEntered(MouseEvent e) {}
    @Override
    public void mouseExited(MouseEvent e) {}
}