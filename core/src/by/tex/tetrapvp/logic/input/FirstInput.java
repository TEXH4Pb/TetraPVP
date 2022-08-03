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
                presenter.moveShapeLeft();
                return true;
            case Input.Keys.D:
                presenter.moveShapeRight();
                return true;
            case Input.Keys.S:
                presenter.moveShapeDown();
                return true;
            case Input.Keys.W:
                presenter.rotateShape(true);
                return true;
        }
        return false;
    }
}
