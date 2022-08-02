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
                presenter.moveShapeLeft();
                return true;
            case Input.Keys.RIGHT:
                presenter.moveShapeRight();
                return true;
            case Input.Keys.DOWN:
                presenter.moveShapeDown();
                return true;
            case Input.Keys.UP:
                presenter.rotateShape(true);
                return true;
        }
        return false;
    }
}
