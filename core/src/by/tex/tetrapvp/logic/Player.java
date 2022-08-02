package by.tex.tetrapvp.logic;

import by.tex.tetrapvp.logic.input.PlayerInput;

public class Player {
    public PlayerInput input;
    public String name;
    private int score;

    public Player(String name, PlayerInput input) {
        this.name = name;
        this.input = input;
    }

    public int getScore(){
        return score;
    }

    public void addScore(int points) {
        score += points;
    }

    public void dropScore() {
        score = 0;
    }
}
