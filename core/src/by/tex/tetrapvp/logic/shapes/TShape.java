package by.tex.tetrapvp.logic.shapes;

import by.tex.tetrapvp.logic.Brick;
import by.tex.tetrapvp.logic.Color;

public class TShape extends Shape{
    public TShape(int x, int y)
    {
        bricks[0] = new Brick(x, y, getDefaultColor());
        bricks[1] = new Brick(x - 1, y - 1, getDefaultColor());
        bricks[2] = new Brick(x, y - 1, getDefaultColor());
        bricks[3] = new Brick(x + 1, y - 1, getDefaultColor());
    }

    @Override
    public Color getDefaultColor() {
        return Color.YELLOW;
    }
}
