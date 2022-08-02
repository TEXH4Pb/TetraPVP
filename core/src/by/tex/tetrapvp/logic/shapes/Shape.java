package by.tex.tetrapvp.logic.shapes;

import by.tex.tetrapvp.logic.Brick;
import by.tex.tetrapvp.logic.Color;

public abstract class Shape {
    protected Brick[] bricks;

    public abstract Color getDefaultColor();

    public Brick[] getBricks() {
        return bricks;
    }

    public void stop() {
        for (Brick brick : bricks) {
            brick.stop();
        }
    }
}
