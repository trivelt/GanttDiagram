import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Line2D;
import java.util.ArrayList;

public class UI extends JFrame {

    static final int lineWidth = 715;
    static final int lineHeight = 60;
    static final int taskHeight = lineHeight-10;
    static final int taskVerticalOffset = 5;

    private TasksManager tasksManager;
    private DrawingPanel drawingPanel;
    private int numberOfLines;

    private JTextField taskNameTextField;
    private JComboBox<String> colorTextField;
    private JTextField lengthTextField;

    private Task editedTask = null;

    public UI() {
        numberOfLines = Parameters.getInstance().numberOfLines;
        tasksManager = new TasksManager();
        tasksManager.createTasks();

        setLayout(new BorderLayout());
        setLocationRelativeTo( null );
        setResizable(false);
        setSize(800, 600);
        setTitle("Gantt Diagram");
        createDetailsPanel();
        createDiagramPanel();

        setWindowInCentre();
        setVisible(true);
    }

    private void setWindowInCentre() {
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (int) ((dimension.getWidth() - getWidth()) / 2);
        int y = (int) ((dimension.getHeight() - getHeight()) / 2);
        setLocation(x, y);
    }

    private void createDetailsPanel() {
        JPanel panel = new JPanel();
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        panel.setLayout(new GridLayout(4,2));

        final JLabel taskNameLabel = new JLabel();
        taskNameLabel.setText("Task name:");
        panel.add(taskNameLabel);
        taskNameTextField = new JTextField();
        panel.add(taskNameTextField);

        final JLabel colorLabel = new JLabel();
        colorLabel.setText("Color");
        panel.add(colorLabel);
        String[] colorStrings = {"BLUE", "GREEN", "ORANGE", "PINK", "RED"};
        colorTextField = new JComboBox(colorStrings);
        colorTextField.setSelectedIndex(0);
        panel.add(colorTextField);

        final JLabel lengthLabel = new JLabel();
        lengthLabel.setText("Length");
        panel.add(lengthLabel);
        lengthTextField = new JTextField();
        panel.add(lengthTextField);


        JButton updateButton = new JButton("Update");
        panel.add(updateButton);

        JButton removeButton = new JButton("Remove");
        removeButton.setEnabled(false);
        panel.add(removeButton);

        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(editedTask == null)
                    return;
                System.out.println("Updated");
            }
        });

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

        private void drawTasks(Graphics2D g2d) {
            ArrayList<Task> tasks = tasksManager.getTasks();
            for (Task task: tasks) {
                drawTask(g2d, task);
            }
        }

        private void drawTask(Graphics2D g2d, Task task) {
            g2d.setColor(task.getColor());
            g2d.fillRoundRect(task.getX(),task.getY(), task.getLength(), taskHeight, 20, 20);

            Font font = new Font("Verdana", Font.ITALIC, 15);
            g2d.setFont(font);
            g2d.setColor(Color.BLACK);
            float textPosX = task.getX() + (float)(1.0/3.0)*(float)task.getLength();
            float textPosY = task.getY() + lineHeight * (float)(1.0/2.0);
            g2d.drawString(task.getName(), (textPosX), textPosY);
        }

        public void paintComponent(Graphics g) {
            Graphics2D g2d = (Graphics2D)g;
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            drawBackground(g2d);
            drawLines(g2d);
            drawTasks(g2d);
        }

        @Override
        public void mouseClicked(MouseEvent e) {
            Task clickedTask = tasksManager.getTask(e.getX(), e.getY());
            if(clickedTask == null) {
                editedTask = null;
                taskNameTextField.setText("");
                lengthTextField.setText("");

            } else {
                taskNameTextField.setText(clickedTask.getName());
                lengthTextField.setText(Integer.toString(clickedTask.getLength()));
                colorTextField.setSelectedIndex(ColorHelper.colorToIndex(clickedTask.getColor()));
//                colorTextField.setText(clickedTask.getColor().toString());
                editedTask = clickedTask;
            }
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
