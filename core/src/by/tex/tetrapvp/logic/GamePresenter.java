package by.tex.tetrapvp.logic;

import by.tex.tetrapvp.logic.shapes.*;

import java.util.Random;

public class GamePresenter {
    public static final int GRID_HEIGHT = 20;
    public static final int GRID_WIDTH = 10;
    private final Random random;
    private final Brick[][] grid;
    private Shape shape;
    //TODO: player managment

    public GamePresenter() {
        random = new Random();
        grid = new Brick[GRID_WIDTH][GRID_HEIGHT];

        addRandomShape();
    }

    public void moveShapeDown() {
        if(!canMoveShapeDown()) {
            shape.stop();
            addRandomShape();
            return;
        }

        for (Brick brick: shape.getBricks()) {
            grid[brick.getX()][brick.getY()] = null;
        }
        for (Brick brick: shape.getBricks()) {
            brick.moveDown();
            grid[brick.getX()][brick.getY()] = brick;
        }
    }

    public void moveShapeLeft() {
        if(!canMoveShapeLeft())
            return;

        for (Brick brick: shape.getBricks()) {
            grid[brick.getX()][brick.getY()] = null;
        }
        for (Brick brick: shape.getBricks()) {
            brick.moveLeft();
            grid[brick.getX()][brick.getY()] = brick;
        }
    }

    public void moveShapeRight() {
        if(!canMoveShapeRight())
            return;

        for (Brick brick: shape.getBricks()) {
            grid[brick.getX()][brick.getY()] = null;
        }
        for (Brick brick: shape.getBricks()) {
            brick.moveRight();
            grid[brick.getX()][brick.getY()] = brick;
        }
    }

    public void rotateShape(boolean clockwise) {
        if(!canRotateShape(clockwise))
            return;

        Brick central = shape.getBricks()[0];
        Brick brick;
        int relX, relY;
        int newX, newY;
        int dir = clockwise ? 1 : -1; //inverse relative position for counterclockwise

        for (Brick B : shape.getBricks())
            grid[B.getX()][B.getY()] = null;

        for(int i = 1; i < 4; i++) {
            brick = shape.getBricks()[i];
            relX = brick.getX() - central.getX();
            relY = brick.getY() - central.getY();
            newX = central.getX() + relY * dir;
            newY = central.getY() - relX * dir;

            brick.setX(newX);
            brick.setY(newY);
        }

        for (Brick B : shape.getBricks())
            grid[B.getX()][B.getY()] = B;
    }

    public Brick getBrick(int x, int y) {
        if(x >= GRID_WIDTH || x < 0)
            return null;
        if(y >= GRID_HEIGHT || y < 0)
            return null;

        return grid[x][y];
    }

    private void addRandomShape() {
        shape = getRandomShape();
        for (Brick brick: shape.getBricks()) {
            grid[brick.getX()][brick.getY()] = brick;
        }
    }

    private boolean canMoveShapeLeft() {
        for (Brick brick: shape.getBricks()) {
            if(brick.getX() - 1 < 0)
                return false;

            if(grid[brick.getX() - 1][brick.getY()] != null)
                if(grid[brick.getX() - 1][brick.getY()].isLaid())
                    return false;
        }
        return true;
    }

    private boolean canMoveShapeRight() {
        for (Brick brick: shape.getBricks()) {
            if(brick.getX() + 1 >= GRID_WIDTH)
                return false;

            if(grid[brick.getX() + 1][brick.getY()] != null)
                if(grid[brick.getX() + 1][brick.getY()].isLaid())
                    return false;
        }
        return true;
    }

    private boolean canMoveShapeDown() {
        for (Brick brick: shape.getBricks()) {
            if(brick.getY() - 1 < 0)
                return false;

            if(grid[brick.getX()][brick.getY() - 1] != null)
                if(grid[brick.getX()][brick.getY() - 1].isLaid())
                    return false;
        }
        return true;
    }

    private boolean canRotateShape(boolean clockwise) {
        if(!shape.canBeRotated())
            return false;

        Brick central = shape.getBricks()[0];
        int relX, relY;
        int newX, newY;
        int dir = clockwise ? 1 : -1; //inverse relative position for counterclockwise

        for(int i = 1; i < 4; i++) {
            relX = shape.getBricks()[i].getX() - central.getX();
            relY = shape.getBricks()[i].getY() - central.getY();
            newX = central.getX() + relY * dir;
            newY = central.getY() - relX * dir;

            if(newX >= GRID_WIDTH || newX < 0)
                return false;
            if(newY >= GRID_HEIGHT || newY < 0)
                return false;

            if(grid[newX][newY] != null)
                if(grid[newX][newY].isLaid())
                    return false;
        }
        return true;
    }

    private Shape getRandomShape() {
        int pick = random.nextInt(Shapes.values().length);
        Shapes shape = Shapes.values()[pick];
        int x = GRID_WIDTH / 2;
        int y = GRID_HEIGHT - 1;

        switch (shape) {
            case T:
                return new TShape(x, y);
            case S:
                return new SShape(x, y);
            case Z:
                return new ZShape(x, y);
            case J:
                return new JShape(x, y);
            case L:
                return new LShape(x, y);
            case I:
                return new IShape(x, y);
            case O:
                return new OShape(x, y);
        }

        return null;
    }
}
