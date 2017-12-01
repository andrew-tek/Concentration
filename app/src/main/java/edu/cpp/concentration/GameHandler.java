package edu.cpp.concentration;

import android.util.Log;

public class GameHandler {

    private int numCards;
    private int cardsSelected;
    private int score;
    private int firstSelectedValue;
    private int secondSelectedValue;
    private int matches;
    private boolean lastPairMatch;
    private boolean gameWon;

    public GameHandler(int numCards) {
        this.numCards = numCards;
        cardsSelected = 0;
        score = 0;
        matches = 0;
        lastPairMatch = false;
        gameWon = false;
    }

    public void selectFirstCard(int cardVal){
        if(!gameWon && (cardsSelected == 0)){ //only run if the game isn't won and no cards have been selected
            cardsSelected = 1;
            firstSelectedValue = cardVal;
        }else{
            Log.i("game", "The game has either been won, or the first card has already been selected. No action taken.");
        }
    }

    public void selectSecondCard(int cardVal){
        if(!gameWon && (cardsSelected == 1)){ //only run if game isn't won and 1 card has already been selected
            cardsSelected = 2;
            secondSelectedValue = cardVal;
            if(firstSelectedValue == secondSelectedValue){
                lastPairMatch = true;
            }else{
                lastPairMatch = false;
            }
            matchHandler(); //handles updating of scores and other variables based on lastPairMatch setting
        }else{
            Log.i("game", "The game has either been won, or the proper number of cards has not been selected yet. No action taken.");
        }
    }

    //Only to be called by another method from within after lastPairMatch has been set to the proper value.
    //Used by selectSecondCard to handle game management tasks after checking for a match.
    private void matchHandler(){
        if(lastPairMatch) {
            score += 2; //matches gain 2 points
            matches++; //increment total matches
            cardsSelected = 0; //reset selected to zero (no need for "tryAgain" if a match is made)
            firstSelectedValue = 0;
            secondSelectedValue = 0;
            if((numCards/2) == matches){ //check for game winning state
                gameWon = true;
                Log.i("game", "Game has been won! Score was: " + score);
            }
            Log.i("match","The selected cards match!");
        }else{
            if(score > 0){ //only decrement score if it's above zero (no negative scores)
                score -= 1;
            }
            Log.i("match","No match, try again!");
        }
    }

    public void tryAgain(){
        if(!gameWon && (cardsSelected == 2)){ //only run if the game isn't won and two cards have already been selected
            cardsSelected = 0;
            firstSelectedValue = 0;
            secondSelectedValue = 0;
            //lastPairMatch = false;
        }else{
            Log.i("game", "The game has either been won, or the proper number of cards has not been selected yet. No action taken.");
        }
    }


    public int getCardsSelected() {
        return cardsSelected;
    }

    public int getScore() {
        return score;
    }

    public boolean isLastPairMatch() {
        return lastPairMatch;
    }

    public boolean isGameWon(){
        return gameWon;
    }
}
