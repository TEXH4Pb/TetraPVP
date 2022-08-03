package by.tex.tetrapvp.logic.input;

import by.tex.tetrapvp.logic.GamePresenter;
import by.tex.tetrapvp.logic.shapes.Shapes;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.controllers.ControllerListener;
import com.badlogic.gdx.controllers.Controllers;

public abstract class PlayerInput extends InputAdapter implements ControllerListener {
    protected GamePresenter presenter;
    protected Controller controller;
    private int number;

    protected PlayerInput(GamePresenter gamePresenter, int number) {
        presenter = gamePresenter;
        this.number = number;
        if(Controllers.getControllers().size > number)
            controller = Controllers.getControllers().get(number);
        else
            controller = null;
    }

    @Override
    public boolean keyDown(int keycode) {
        if(keycode == Input.Keys.F3) {
            presenter.restart();
            return true;
        }

        switch (keycode) {
            case Input.Keys.NUMPAD_0:
                presenter.addShape(Shapes.O);
                return true;
            case Input.Keys.NUMPAD_1:
                presenter.addShape(Shapes.I);
                return true;
            case Input.Keys.NUMPAD_2:
                presenter.addShape(Shapes.L);
                return true;
            case Input.Keys.NUMPAD_3:
                presenter.addShape(Shapes.J);
                return true;
            case Input.Keys.NUMPAD_4:
                presenter.addShape(Shapes.S);
                return true;
            case Input.Keys.NUMPAD_5:
                presenter.addShape(Shapes.Z);
                return true;
            case Input.Keys.NUMPAD_6:
                presenter.addShape(Shapes.T);
                return true;
        }
        return false;
    }

    @Override
    public void connected(Controller controller) {

    }

    @Override
    public void disconnected(Controller controller) {
        //TODO: pause game
    }

    @Override
    public boolean buttonDown(Controller controller, int buttonCode) {
        if(presenter.getActivePlayer() != number)
            return false;
        if(this.controller == null)
            return false;
        if(controller.getUniqueId() != this.controller.getUniqueId())
            return false;

        if(buttonCode == controller.getMapping().buttonDpadLeft)
            presenter.startMoving(MovingState.LEFT);
        else if(buttonCode == controller.getMapping().buttonDpadRight)
            presenter.startMoving(MovingState.RIGHT);
        else if(buttonCode == controller.getMapping().buttonDpadDown)
            presenter.setAccelerated(true);
        else if (buttonCode == controller.getMapping().buttonDpadUp)
            presenter.rotateShape(true);
        else if(buttonCode == controller.getMapping().buttonL1)
            presenter.rotateShape(false);
        else if(buttonCode == controller.getMapping().buttonR1)
            presenter.rotateShape(true);
        else if(buttonCode == controller.getMapping().buttonBack)
            presenter.restart();
        else
            return false;
        return true;
    }

    @Override
    public boolean buttonUp(Controller controller, int buttonCode) {
        if(presenter.getActivePlayer() != number)
            return false;
        if(this.controller == null)
            return false;
        if(!controller.getUniqueId().equals(this.controller.getUniqueId()))
            return false;

        if(buttonCode == controller.getMapping().buttonDpadLeft)
            presenter.stopMovingLeft();
        else if(buttonCode == controller.getMapping().buttonDpadRight)
            presenter.stopMovingRight();
        else if(buttonCode == controller.getMapping().buttonDpadDown)
            presenter.setAccelerated(false);
        else
            return false;
        return true;
    }

    @Override
    public boolean axisMoved(Controller controller, int axisCode, float value) {
        return false;
    }
}
