package by.tex.tetrapvp.logic.input;

import by.tex.tetrapvp.logic.GamePresenter;
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
            presenter.moveShapeLeft();
        else if(buttonCode == controller.getMapping().buttonDpadRight)
            presenter.moveShapeRight();
        else if(buttonCode == controller.getMapping().buttonDpadDown)
            presenter.setAccelerated(true);
        else if(buttonCode == controller.getMapping().buttonL1)
            presenter.rotateShape(false);
        else if(buttonCode == controller.getMapping().buttonR1)
            presenter.rotateShape(true);
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
        if(controller.getUniqueId() != this.controller.getUniqueId())
            return false;

        if(buttonCode == controller.getMapping().buttonDpadDown)
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