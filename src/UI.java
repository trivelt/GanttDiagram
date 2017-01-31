import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Line2D;

public class UI extends JFrame {

    static final int numberOfLines = 6;
    static final int lineHeight = 60;

    private DrawingPanel drawingPanel;

    public UI() {
        setLayout(new BorderLayout());
        setLocationRelativeTo( null );
        setResizable(false);
        setSize(800, 600);
        setTitle("Gantt Diagram");
        createDetailsPanel();
        createDiagramPanel();

        setVisible(true);
    }

    private void createDetailsPanel() {
        JPanel panel = new JPanel();
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        panel.setLayout(new GridLayout(4,2));

        final JLabel taskNameLabel = new JLabel();
        taskNameLabel.setText("Task name:");
        panel.add(taskNameLabel);
        final JTextField taskNameTextField = new JTextField();
        panel.add(taskNameTextField);

        final JLabel colorLabel = new JLabel();
        colorLabel.setText("Color");
        panel.add(colorLabel);
        final JTextField colorTextField = new JTextField();
        panel.add(colorTextField);

        final JLabel lengthLabel = new JLabel();
        lengthLabel.setText("Length");
        panel.add(lengthLabel);
        final JTextField lengthTextField = new JTextField();
        panel.add(lengthTextField);


        JButton updateButton = new JButton("Update");
        panel.add(updateButton);

        JButton removeButton = new JButton("Remove");
        removeButton.setEnabled(false);
        panel.add(removeButton);

        add(panel, BorderLayout.NORTH);
    }


    public void createDiagramPanel() {

        drawingPanel = new DrawingPanel();
        add(drawingPanel, BorderLayout.CENTER);
    }

    class DrawingPanel extends JPanel implements MouseListener, MouseMotionListener {
        public DrawingPanel() {
            System.out.println("Creating drawing panel");
            addMouseListener(this);
            addMouseMotionListener(this);
            repaint();
        }

        private void drawBackground(Graphics2D g2d) {
            g2d.setColor(Color.LIGHT_GRAY);
            g2d.fillRect(0,0, getWidth(), getHeight());
        }

        private void drawLines(Graphics2D g2d) {
            g2d.setPaint(Color.GRAY);
            g2d.setStroke(new BasicStroke(3));

            float positionOfVerticalLine = (float)((1.0/10.0)*getWidth());
            Shape verticalLine = new Line2D.Float(positionOfVerticalLine, 0, positionOfVerticalLine, getHeight());
            g2d.draw(verticalLine);

            Font font = new Font("Verdana", Font.BOLD, 19);
            g2d.setFont(font);
            for(int i=0; i<=numberOfLines; i++)
            {
                Shape line = new Line2D.Float(0, lineHeight*i, getWidth(), lineHeight*i);
                g2d.draw(line);

                if(i>=0) {
                    g2d.drawString("#" + Integer.toString(i), (float)10, (float)(lineHeight*i-(1.0/3.0)*lineHeight));
                }
            }
        }

        public void paintComponent(Graphics g) {
            Graphics2D g2d = (Graphics2D)g;
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            drawBackground(g2d);
            drawLines(g2d);
        }

        @Override
        public void mouseClicked(MouseEvent e) {
//            System.out.println("mouseClicked");
        }

        @Override
        public void mousePressed(MouseEvent e) {
//            System.out.println("mousePressed");
        }

        @Override
        public void mouseReleased(MouseEvent e) {
//            System.out.println("mouseReleased");
        }

        @Override
        public void mouseEntered(MouseEvent e) {
//            System.out.println("mouseEntered");
        }

        @Override
        public void mouseExited(MouseEvent e) {
//            System.out.println("mouseExited");
        }

        @Override
        public void mouseDragged(MouseEvent e) {
//            System.out.println("mouseDragged");
        }

        @Override
        public void mouseMoved(MouseEvent e) {
//            System.out.println("mouseMoved");
        }
    }

}
