import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class UI extends JFrame {

    final static Color COLOURBACK =  Color.ORANGE;

    public UI() {
        setLayout(new BorderLayout());
        setLocationRelativeTo( null );
        setResizable(false);
        setSize(800, 600);
        setTitle("Gantt Diagram");
        setVisible(true);


        createAndShowGUI();
        createAndShowFirstWindow();
    }

    private void createAndShowFirstWindow() {
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


    public void createAndShowGUI() {

        DrawingPanel panel = new DrawingPanel();
        add(panel, BorderLayout.CENTER);
    }

    class DrawingPanel extends JPanel {
        public DrawingPanel() {
            setBackground(COLOURBACK);
        }

        public void paintComponent(Graphics g) {
            System.out.println("DDDD");
            Graphics2D g2 = (Graphics2D)g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g.setFont(new Font("TimesRoman", Font.PLAIN, 20));
            super.paintComponent(g2);

        }
    }

}
