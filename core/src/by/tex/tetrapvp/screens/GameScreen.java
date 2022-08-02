package by.tex.tetrapvp.screens;

import by.tex.tetrapvp.logic.Brick;
import by.tex.tetrapvp.logic.Color;
import by.tex.tetrapvp.logic.GamePresenter;
import by.tex.tetrapvp.logic.input.FirstInput;
import by.tex.tetrapvp.logic.input.SecondInput;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.controllers.Controllers;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.ScreenUtils;

public class GameScreen extends ScreenAdapter {
    private static GamePresenter presenter = new GamePresenter();

    private static final FirstInput FIRST_PLAYER_INPUT = new FirstInput(presenter);
    private static final SecondInput SECOND_PLAYER_INPUT = new SecondInput(presenter);
    private int cellSize;
    private SpriteBatch batch;
    private ShapeRenderer shapeRenderer;
    private Texture red, orange, yellow, green, cyan, blue, purple;
    private BitmapFont font;

    public GameScreen() {
        batch = new SpriteBatch();
        shapeRenderer = new ShapeRenderer();
        red = new Texture(Gdx.files.internal("bricks/red.png"));
        orange = new Texture(Gdx.files.internal("bricks/orange.png"));
        yellow = new Texture(Gdx.files.internal("bricks/yellow.png"));
        green = new Texture(Gdx.files.internal("bricks/green.png"));
        cyan = new Texture(Gdx.files.internal("bricks/cyan.png"));
        blue = new Texture(Gdx.files.internal("bricks/blue.png"));
        purple = new Texture(Gdx.files.internal("bricks/purple.png"));
        cellSize = red.getWidth() * 3;
        font = new BitmapFont();

        Controllers.addListener(FIRST_PLAYER_INPUT);
        Controllers.addListener(SECOND_PLAYER_INPUT);
    }

    @Override
    public void show() {
        updateInput();
    }

    @Override
    public void render(float delta) {
        presenter.update(delta);

        ScreenUtils.clear(com.badlogic.gdx.graphics.Color.GRAY);
        float gridOffsetX = (Gdx.graphics.getWidth() / 2) - (presenter.GRID_WIDTH / 2 * cellSize);
        float gridOffsetY = (Gdx.graphics.getHeight() / 2) - (presenter.GRID_HEIGHT / 2 * cellSize);

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(com.badlogic.gdx.graphics.Color.BLACK);
        shapeRenderer.rect(gridOffsetX, gridOffsetY, presenter.GRID_WIDTH * cellSize, presenter.GRID_HEIGHT * cellSize);
        shapeRenderer.end();

        Brick brick;
        batch.begin();
        font.getData().setScale(3f);
        String score = String.format("%s score: %d", presenter.getPlayers()[0].name, presenter.getPlayers()[0].getScore());
        font.setColor(presenter.getActivePlayer() == 0 ? com.badlogic.gdx.graphics.Color.WHITE : com.badlogic.gdx.graphics.Color.BLACK);
        font.draw(batch, score, Gdx.graphics.getWidth() * 0.05f, Gdx.graphics.getHeight() * 0.9f);
        score = String.format("%s score: %d", presenter.getPlayers()[1].name, presenter.getPlayers()[1].getScore());
        font.setColor(presenter.getActivePlayer() == 1 ? com.badlogic.gdx.graphics.Color.WHITE : com.badlogic.gdx.graphics.Color.BLACK);
        font.draw(batch, score, Gdx.graphics.getWidth() * 0.75f, Gdx.graphics.getHeight() * 0.9f);

        for(int y = 0; y < presenter.GRID_HEIGHT; y++) {
            for(int x = 0; x < presenter.GRID_WIDTH; x++) {
                brick = presenter.getBrick(x, y);
                if(brick == null)
                    continue;
                batch.draw(getSpriteByColor(brick.getColor()), gridOffsetX + brick.getX() * cellSize, gridOffsetY + brick.getY() * cellSize, cellSize, cellSize);
            }
        }
        batch.end();
    }

    @Override
    public void resize (int width, int height) {
        batch = new SpriteBatch();
        shapeRenderer = new ShapeRenderer();
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
        font.dispose();
    }

    public static void updateInput() {
        if(presenter.getActivePlayer() == 0) {
            Gdx.input.setInputProcessor(FIRST_PLAYER_INPUT);
        }
        else {
            Gdx.input.setInputProcessor(SECOND_PLAYER_INPUT);
        }
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
