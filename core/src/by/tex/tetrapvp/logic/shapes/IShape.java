package by.tex.tetrapvp.logic.shapes;

import by.tex.tetrapvp.logic.Brick;
import by.tex.tetrapvp.logic.Color;

public class IShape extends Shape {
    public IShape(int x, int y) {
        super();
        bricks[0] = new Brick(x, y, getDefaultColor());
        bricks[1] = new Brick(x - 1, y, getDefaultColor());
        bricks[2] = new Brick(x + 1, y, getDefaultColor());
        bricks[3] = new Brick(x + 2, y, getDefaultColor());
    }
    @Override
    public Color getDefaultColor() {
        return Color.PURPLE;
    }
}
