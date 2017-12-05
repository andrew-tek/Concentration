package edu.cpp.concentration;

/**
 * This class allows for storing "Score" objects containing the name and score of a player.
 */

public class Score {

    // name of player or user
    private String name;
    // player's score from game
    private int score;

    // method: Scores
    // purpose: constructor for player name and score
    public Score(String name, int score) {
        this.name = name;
        this.score = score;
    }

    // method: Scores
    // purpose: constructor for player score
    public Score(int score) {
        this.score = score;
    }

    // method: getPlayer
    // purpose: return name of player (String)
    public String getName() {
        return name;
    }

    // method: getScore
    // purpose: return score of player
    public int getScore() {
        return score;
    }

    // method: setPlayer
    // purpose: set name of player
    public void setName(String name) {
        this.name = name;
    }

    // method: setScore
    // purpose: set score of player
    public void setScore(int score) {
        this.score = score;
    }
}