package by.tex.tetrapvp.screens;

import by.tex.tetrapvp.logic.Brick;
import by.tex.tetrapvp.logic.Color;
import by.tex.tetrapvp.logic.GamePresenter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.ScreenUtils;

public class GameScreen extends ScreenAdapter {
    private int cellSize;
    private GamePresenter presenter;
    private SpriteBatch batch;
    private Texture red, orange, yellow, green, cyan, blue, purple;

    public GameScreen() {
        presenter = new GamePresenter();
        batch = new SpriteBatch(256);
        red = new Texture(Gdx.files.internal("bricks/red.png"));
        orange = new Texture(Gdx.files.internal("bricks/orange.png"));
        yellow = new Texture(Gdx.files.internal("bricks/yellow.png"));
        green = new Texture(Gdx.files.internal("bricks/green.png"));
        cyan = new Texture(Gdx.files.internal("bricks/cyan.png"));
        blue = new Texture(Gdx.files.internal("bricks/blue.png"));
        purple = new Texture(Gdx.files.internal("bricks/purple.png"));
        cellSize = red.getWidth();
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(new InputAdapter() {
            @Override
            public boolean keyDown(int keycode)
            {
                switch (keycode) {
                    case Input.Keys.LEFT:
                        presenter.moveShapeLeft();
                        return true;
                    case Input.Keys.RIGHT:
                        presenter.moveShapeRight();
                        return true;
                    case Input.Keys.DOWN:
                        presenter.moveShapeDown();
                        return true;
                    case Input.Keys.UP:
                        presenter.rotateShape(false);
                        return true;
                }
                return false;
            }
        });
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(com.badlogic.gdx.graphics.Color.GRAY);
        ShapeRenderer shapeRenderer = new ShapeRenderer();
        float gridOffsetX = (Gdx.graphics.getWidth() / 2) - (presenter.GRID_WIDTH / 2 * cellSize);
        float gridOffsetY = (Gdx.graphics.getHeight() / 2) - (presenter.GRID_HEIGHT / 2 * cellSize);

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(com.badlogic.gdx.graphics.Color.BLACK);
        shapeRenderer.rect(gridOffsetX, gridOffsetY, presenter.GRID_WIDTH * cellSize, presenter.GRID_HEIGHT * cellSize);
        shapeRenderer.end();

        Brick brick;
        batch.begin();
        for(int y = 0; y < presenter.GRID_HEIGHT; y++) {
            for(int x = 0; x < presenter.GRID_WIDTH; x++) {
                brick = presenter.getBrick(x, y);
                if(brick == null)
                    continue;
                batch.draw(getSpriteByColor(brick.getColor()), gridOffsetX + brick.getX() * cellSize, gridOffsetY + brick.getY() * cellSize);
            }
        }
        batch.end();
    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        batch.dispose();
        red.dispose();
        orange.dispose();
        yellow.dispose();
        green.dispose();
        cyan.dispose();
        blue.dispose();
        purple.dispose();
    }

    private Texture getSpriteByColor(Color color) {
        switch (color) {
            case RED:
                return red;
            case ORANGE:
                return orange;
            case YELLOW:
                return yellow;
            case GREEN:
                return green;
            case CYAN:
                return cyan;
            case BLUE:
                return blue;
            case PURPLE:
                return purple;
        }
        return null;
    }
}
