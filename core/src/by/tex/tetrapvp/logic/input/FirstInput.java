package by.tex.tetrapvp.logic.input;

import by.tex.tetrapvp.logic.GamePresenter;
import com.badlogic.gdx.Input;

public class FirstInput extends PlayerInput{
    public FirstInput(GamePresenter gamePresenter) {
        super(gamePresenter, 0);
    }
    @Override
    public boolean keyDown(int keycode)
    {
        if(super.keyDown(keycode))
            return true;

        switch (keycode) {
            case Input.Keys.A:
                presenter.startMoving(MovingState.LEFT);
                return true;
            case Input.Keys.D:
                presenter.startMoving(MovingState.RIGHT);
                return true;
            case Input.Keys.S:
                presenter.setAccelerated(true);
                return true;
            case Input.Keys.W:
                presenter.rotateShape(true);
                return true;
        }
        return false;
    }

    @Override
    public boolean keyUp (int keycode) {
        switch (keycode) {
            case Input.Keys.S:
                presenter.setAccelerated(false);
                return true;
            case Input.Keys.A:
                presenter.stopMovingLeft();
                return true;
            case Input.Keys.D:
                presenter.stopMovingRight();
                return true;
        }
        return false;
    }
}
