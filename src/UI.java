import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Line2D;
import java.util.ArrayList;

public class UI extends JFrame {

    static final int frameWidth = 800;
    static final int lineWidth = 715;
    static final int lineHeight = 60;
    static final int taskHeight = lineHeight-10;
    static final int taskVerticalOffset = 5;

    private TasksManager tasksManager;
    private DrawingPanel drawingPanel;
    private JPanel configPanel;
    private int numberOfLines;

    private JTextField taskNameTextField;
    private JComboBox<String> colorComboBox;
    private JSpinner lengthSpinner;
    private JButton removeButton;
    private JButton updateButton;

    private Task editedTask = null;
    private Task movingTask = null;
    private int movedTaskX = 0;
    private int movedTaskY = 0;

    public UI() {
        tasksManager = new TasksManager();
        setLayout(new BorderLayout());
        setLocationRelativeTo( null );
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        setSize(frameWidth, 600);
        setTitle("Gantt Diagram");
        createConfigPanel();

        setWindowInCentre();
        setVisible(true);
    }

    private void runApp() {
        configPanel.setVisible(false);
        remove(configPanel);
        tasksManager.createTasks();
        numberOfLines = Parameters.getInstance().numberOfLines;
        createDetailsPanel();
        createDiagramPanel();
    }

    private void setWindowInCentre() {
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (int) ((dimension.getWidth() - getWidth()) / 2);
        int y = (int) ((dimension.getHeight() - getHeight()) / 2);
        setLocation(x, y);
    }

    private void createDetailsPanel() {
        JPanel panel = new JPanel();
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
        colorComboBox = new JComboBox(colorStrings);
        colorComboBox.setSelectedIndex(0);
        panel.add(colorComboBox);

        final JLabel lengthLabel = new JLabel();
        lengthLabel.setText("Length");
        panel.add(lengthLabel);
        SpinnerNumberModel spinnerModel = new SpinnerNumberModel(0, 0, lineWidth, 1);
        lengthSpinner = new JSpinner(spinnerModel);
        panel.add(lengthSpinner);

        updateButton = new JButton("Update");
        updateButton.setEnabled(false);
        panel.add(updateButton);

        removeButton = new JButton("Remove");
        removeButton.setEnabled(false);
        panel.add(removeButton);

        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(editedTask == null)
                    return;
                editedTask.setName(taskNameTextField.getText());

                String selectedColor = colorComboBox.getSelectedItem().toString();
                editedTask.setColor(ColorHelper.stringToColor(selectedColor));

                int newLength = (int) lengthSpinner.getValue();
                if(!tasksManager.isCollisionAfterResize(editedTask, newLength)) {
                    editedTask.setLength(newLength);
                } else {
                    lengthSpinner.setValue(editedTask.getLength());
                }

                drawingPanel.repaint();
            }
        });

        removeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(editedTask == null)
                    return;
                tasksManager.removeTask(editedTask);
                drawingPanel.repaint();
                editedTask = null;

                taskNameTextField.setText("");
                lengthSpinner.setValue(0);
                updateButton.setEnabled(false);
                removeButton.setEnabled(false);
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
            Font font = new Font("Verdana", Font.ITALIC, 15);
            g2d.setFont(font);
            if(task == movingTask) {
                g2d.fillRoundRect(movedTaskX, movedTaskY, task.getLength(), taskHeight, 20, 20);
            } else
            {
                g2d.fillRoundRect(task.getX(),task.getY(), task.getLength(), taskHeight, 20, 20);
                g2d.setColor(Color.BLACK);
                float textPosX = task.getX() + (float)(1.0/3.0)*(float)task.getLength();
                float textPosY = task.getY() + lineHeight * (float)(1.0/2.0);
                g2d.drawString(task.getName(), (textPosX), textPosY);
            }
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
                lengthSpinner.setValue(0);
                updateButton.setEnabled(false);
                removeButton.setEnabled(false);

            } else {
                taskNameTextField.setText(clickedTask.getName());
                lengthSpinner.setValue(clickedTask.getLength());
                colorComboBox.setSelectedIndex(ColorHelper.colorToIndex(clickedTask.getColor()));
                editedTask = clickedTask;
                updateButton.setEnabled(true);
                removeButton.setEnabled(true);
            }
        }

        @Override
        public void mousePressed(MouseEvent e) {
            Task clickedTask = tasksManager.getTask(e.getX(), e.getY());
            if(clickedTask == null) {
                movingTask = null;
                return;
            }
            movingTask = clickedTask;
            movedTaskX = clickedTask.getX();
            movedTaskY = clickedTask.getY();
        }

        @Override
        public void mouseReleased(MouseEvent e) {
                if(movingTask == null) {
                    return;
                }

                if(movedTaskX == movingTask.getX() && movedTaskY == movingTask.getY()) {
                    movingTask = null;
                    repaint();
                    return;
                }

                movedTaskY = tasksManager.getBestYPosAfterMove(movedTaskY);
                if(!tasksManager.isCollisionAfterMove(movingTask, movedTaskX, movedTaskY)) {
                    movingTask.setPos(movedTaskX, movedTaskY);
                }
                movingTask = null;
                repaint();
        }

        @Override
        public void mouseEntered(MouseEvent e) {
        }

        @Override
        public void mouseExited(MouseEvent e) {
        }

        @Override
        public void mouseDragged(MouseEvent e) {
            if(movingTask == null)
                return;

            movedTaskX = e.getX();
            movedTaskY = e.getY();
            repaint();
        }

        @Override
        public void mouseMoved(MouseEvent e) {
        }
    }


    private void createConfigPanel() {
        Font font = new Font("Verdana", Font.BOLD, 19);
        configPanel = new JPanel();
        configPanel.setLayout(new GridLayout(2, 1));

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(2, 2));

        final JLabel numberOfLinesLabel = new JLabel();
        numberOfLinesLabel.setText("Number of lines:");
        panel.add(numberOfLinesLabel);

        SpinnerNumberModel linesSpinnerModel = new SpinnerNumberModel(1, 1, 8, 1);
        JSpinner numberOfLinesSpinner = new JSpinner(linesSpinnerModel);
        numberOfLinesSpinner.setValue(6);
        numberOfLinesSpinner.setFont(font);
        panel.add(numberOfLinesSpinner);


        final JLabel numberOfTasksLabel = new JLabel();
        numberOfTasksLabel.setText("Number of tasks:");
        panel.add(numberOfTasksLabel);

        SpinnerNumberModel tasksSpinnerModel = new SpinnerNumberModel(0, 0, 100, 1);
        JSpinner numberOfTasksSpinner = new JSpinner(tasksSpinnerModel);
        numberOfTasksSpinner.setValue(5);
        numberOfTasksSpinner.setFont(font);
        panel.add(numberOfTasksSpinner);

        JButton okButton = new JButton();
        okButton.setText("OK");
        okButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Parameters parameters = Parameters.getInstance();
                parameters.numberOfLines = (int) numberOfLinesSpinner.getValue();
                parameters.numberOfTasks = (int) numberOfTasksSpinner.getValue();

                runApp();
            }
        });

        configPanel.add(panel);
        configPanel.add(okButton);
        add(configPanel);
    }

}
