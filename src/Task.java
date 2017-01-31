import java.awt.*;

public class Task {
    public Task(String name, int length, Color color, int x, int y) {
        this.name = name;
        this.length = length;
        this.color = color;
        this.xCoord = x;
        this.yCoord = y;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public int getX() {
        return xCoord;
    }

    public int getY() {
        return yCoord;
    }

    public void setPos(int x, int y) {
        xCoord = x;
        yCoord = y;
    }

    private String name;
    private int length;
    private Color color;

    private int xCoord;
    private int yCoord;
}
