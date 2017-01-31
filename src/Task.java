import java.awt.*;

public class Task {
    public Task(String name, int length, Color color, int x, int y) {
        this.name = name;
        this.length = length;
        this.color = color;
        this.xCoord = x;
        this.yCoord = y;

        this.isMoved = false;
        this.movingX = 0;
        this.movingY = 0;
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

    public void setMovingPos(int x, int y) {
        movingX = x;
        movingY = y;
    }

    public int getMovingX() {
        return movingX;
    }

    public int getMovingY() {
        return movingY;
    }

    public boolean isMoved() {
        return isMoved;
    }

    public void setMove(boolean isMoved) {
        this.isMoved = isMoved;
    }

    private String name;
    private int length;
    private Color color;

    private int xCoord;
    private int yCoord;

    private boolean isMoved;
    private int movingX;
    private int movingY;
}
