package by.tex.tetrapvp.logic;

import by.tex.tetrapvp.logic.input.FirstInput;
import by.tex.tetrapvp.logic.input.SecondInput;
import by.tex.tetrapvp.logic.shapes.*;
import by.tex.tetrapvp.screens.GameScreen;

import java.util.Random;

public class GamePresenter {
    public static final int GRID_HEIGHT = 20;
    public static final int GRID_WIDTH = 10;
    public static final float START_CAP = 1f;

    private Player[] players;
    private int activePlayer;
    private int topLine;
    private int lineCounter;
    private boolean accelerated;
    private final Random random;
    private final Brick[][] grid;
    private Shape shape;
    private boolean paused;

    private float timerCap;
    private float acceleratedCap;
    private float timer;
    //TODO: player managment

    public GamePresenter() {
        random = new Random();
        grid = new Brick[GRID_WIDTH][GRID_HEIGHT];
        timer = 0;
        timerCap = START_CAP;
        acceleratedCap = timerCap / 20;
        players = new Player[2];
        players[0] = new Player("P1", new FirstInput(this));
        players[1] = new Player("P2", new SecondInput(this));
        activePlayer = 0;
        topLine = 1;
        accelerated = false;
        lineCounter = 0;
        paused = false;

        addRandomShape();
    }

    public void update(float delta) {
        if(paused)
            return;

        timer += delta;
        if(timer >= timerCap) {
            timer -= timerCap;
            moveShapeDown();
        }
        else if (accelerated && timer >= acceleratedCap) {
            timer = 0;
            moveShapeDown();
        }
    }

    public void switchPlayers() {
        accelerated = false;
        activePlayer = (activePlayer + 1) % 2;
        GameScreen.updateInput();
    }

    public void setAccelerated(boolean accelerated) {
        this.accelerated = accelerated;
    }

    public void moveShapeDown() {
        if(!canMoveShapeDown()) {
            shape.stop();
            if(!checkLines())
                topLinePenalty();
            addRandomShape();
            switchPlayers();
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

    public Player[] getPlayers() {
        return players;
    }

    public int getActivePlayer() {
        return activePlayer;
    }

    public int getTopLine() {
        return topLine;
    }

    private void topLinePenalty() {
        int newTopLine = topLine;
        for(Brick B : shape.getBricks()) {
            if(B.getY() > newTopLine)
                newTopLine = B.getY();
        }

        for (int i = newTopLine - topLine; i > 0; i--)
            players[activePlayer].addScore(-i);
        topLine = newTopLine;
    }

    private void updateTopLine() {
        for (int y = GRID_HEIGHT - 1; y >= 0; y--)
            for (int x = 0; x < GRID_WIDTH; x++)
                if (grid[x][y] != null) {
                    topLine = y;
                    return;
                }
        topLine = 0;
    }

    private void addRandomShape() {
        shape = getRandomShape();
        for(Brick B : shape.getBricks()) {
            if(grid[B.getX()][B.getY()] != null)
                paused = true;
        }

        for (Brick brick: shape.getBricks()) {
            grid[brick.getX()][brick.getY()] = brick;
        }
    }

    private boolean checkLines() {
        int cleaned = 0;
        for(int y = GRID_HEIGHT - 1, counter = 0; y >= 0; y--, counter = 0) {
            for(int x = 0; x < GRID_WIDTH; x++)
                if(grid[x][y] != null)
                    counter++;
            if(counter == GRID_WIDTH) {
                cleaned++;
                emptyLine(y);
                dropLine(y + 1);
            }
        }
        if(cleaned == 0)
            return false;

        updateTopLine();
        for (int i = cleaned; i > 0; i--)
            players[activePlayer].addScore(i);
        return true;
    }

    private void emptyLine(int line) {
        for(int x = 0; x < GRID_WIDTH; x++)
            grid[x][line] = null;
        lineCounter++;
        int mod = lineCounter / 10;
        timerCap = START_CAP * (1 - Math.max((float)mod / 10, 0.3f));
    }

    private void dropLine(int line) {
        for(int y = line; y < GRID_HEIGHT; y++) {
            for(int x = 0; x < GRID_WIDTH; x++) {
                grid[x][y - 1] = grid[x][y];
                if(grid[x][y] != null)
                    grid[x][y].moveDown();
                grid[x][y] = null;
            }
        }
    }

    private boolean canMoveShapeLeft() {
        if(paused)
            return false;

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
        if(paused)
            return false;

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
        if(paused)
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
