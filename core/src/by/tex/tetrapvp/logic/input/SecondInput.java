package by.tex.tetrapvp.logic.input;

import by.tex.tetrapvp.logic.GamePresenter;
import com.badlogic.gdx.Input;

public class SecondInput extends PlayerInput {
    public SecondInput(GamePresenter gamePresenter) {
        super(gamePresenter, 1);
    }

    @Override
    public boolean keyDown(int keycode)
    {
        switch (keycode) {
            case Input.Keys.LEFT:
                presenter.startMoving(MovingState.LEFT);
                return true;
            case Input.Keys.RIGHT:
                presenter.startMoving(MovingState.RIGHT);
                return true;
            case Input.Keys.DOWN:
                presenter.setAccelerated(true);
                return true;
            case Input.Keys.UP:
                presenter.rotateShape(true);
                return true;
        }
        return false;
    }

    @Override
    public boolean keyUp (int keycode) {
        switch (keycode) {
            case Input.Keys.DOWN:
                presenter.setAccelerated(false);
                return true;
            case Input.Keys.LEFT:
                presenter.stopMovingLeft();
                return true;
            case Input.Keys.RIGHT:
                presenter.stopMovingRight();
                return true;
        }
        return false;
    }
}
