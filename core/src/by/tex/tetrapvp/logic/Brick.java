package by.tex.tetrapvp.logic;

/**
 * A simple 1x1 cell-brick. Every {@link by.tex.tetrapvp.logic.shapes.Shape} consists of 4 of them.
 */
public class Brick {
    private Color color;
    /**
     * Horizontal position on grid. From left to right.
     */
    private int x;
    /**
     * Vertical position on grid. From bottom to top.
     */
    private int y;
    /**
     * False while brick is still a part of a moving shape.
     */
    private boolean laid;

    public Brick(int x, int y, Color color)
    {
        this.x = x;
        this.y = y;
        this.color = color;
        laid = false;
    }

    /**
     * Use it when {@link by.tex.tetrapvp.logic.shapes.Shape} reaches the bottom.
     */
    public void stop()
    {
        laid = true;
    }

    public void moveDown()
    {
        y--;
    }

    public void moveLeft()
    {
        x--;
    }

    public void moveRight()
    {
        x++;
    }

    public boolean isLaid()
    {
        return laid;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }
}
