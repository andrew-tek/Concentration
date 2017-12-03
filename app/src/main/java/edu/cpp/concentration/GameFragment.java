package edu.cpp.concentration;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.content.res.Configuration.ORIENTATION_LANDSCAPE;
import static android.content.res.Configuration.ORIENTATION_PORTRAIT;


public class GameFragment extends Fragment {

    private int numCards;
    private List<Button> buttonList;
    private Map<Button, Integer> buttonMap;
    private GameHandler theGame;
    private Button firstSelected;
    private Button secondSelected;
    private View thisFragmentView;
    private final int[] CARD_FACES = {R.drawable.anduin, R.drawable.druid, R.drawable.garrosh,
            R.drawable.guldan, R.drawable.jaina, R.drawable.lich, R.drawable.rexxar, R.drawable.thrall,
            R.drawable.uther, R.drawable.valeera};

    //onCreate only runs once for a fragment - these methods setup the initial game state
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        Bundle passed = getArguments();
        numCards = passed.getInt("numCards", -1);
        if (numCards == -1) {
            Log.i("gameError", "Number of cards not properly passed! Defaulting to max cards.");
            numCards = 20;
        }

        theGame = new GameHandler(numCards);
        initCardsHandler();

        Log.w("hello", "onCreate: DAMNIT");

    }

    //this runs each time the fragment is handled - in other words, first when it is initialized, and then every time the
    //state changes (such as a rotation)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        thisFragmentView = inflater.inflate(R.layout.fragment_game, container, false);
        if(savedInstanceState != null){
            clearCardView();
        }
        initCardView();

        return thisFragmentView;
    }


    //Method to programatically create the proper number of card buttons for the game. Once buttons have been
    //created, mapCards() is called to randomly map each card button to its face-value.
    private void initCardsHandler(){
        buttonList = new ArrayList<>();
        for(int i = 0; i < numCards; i++){
            Button cardButton = initButton(i);
            buttonList.add(cardButton);
        }
        mapCards();
    }

    //Method to set up a button with the proper parameters - used by initCardsHandler method
    private Button initButton(int i){
        Button cardButton = new Button(getActivity());
        String buttonTag = "button" + i;
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        params.setMargins(5,5,5,5);
        if((numCards%4 == 0) || ((i != numCards-1) && (i != numCards-2))) {
            params.weight = 1;
        }
        cardButton.setLayoutParams(params);
        cardButton.setBackgroundResource(R.drawable.cardback);
        cardButton.setTag(buttonTag);
        cardButton.setGravity(Gravity.CENTER);
        cardButton.setOnClickListener(onClickFlipper(cardButton));

        return cardButton;
    }

    //Custom click listener to be assigned to each of the "card" buttons in the game
    private View.OnClickListener onClickFlipper(final Button button)  {
        return new View.OnClickListener() {
            public void onClick(View v) {
                Log.i("cardClick","The card selected was: " + button.getTag());
                if(theGame.getCardsSelected() == 0){
                    firstSelected = button;
                    button.setBackgroundResource(buttonMap.get(button));
                    theGame.selectFirstCard(buttonMap.get(button));
                }else if(theGame.getCardsSelected() == 1){
                    secondSelected = button;
                    button.setBackgroundResource(buttonMap.get(button));
                    theGame.selectSecondCard(buttonMap.get(button));
                    if(theGame.isLastPairMatch()){
                        firstSelected.setEnabled(false);
                        secondSelected.setEnabled(false);
                        firstSelected = null;
                        secondSelected = null;
                    }
                    if(theGame.isGameWon()){
                        gameOver();
                    }
                }
            }
        };
    }

    //only call after buttonList has been initialized (call initCardsHandler)
    private void mapCards(){
        buttonMap = new HashMap<>();
        List<Integer> cardFaceList = new ArrayList<>();
        for(int i = 0; i < numCards; i++){
            int index = i/2; //integer division ensures that two copies of each card face end up in list
            cardFaceList.add(CARD_FACES[index]);
            Log.i("faces","card face index added: " + index);
        }
        Collections.shuffle(cardFaceList); //randomize order of card faces represented in the list
        for(Button button : buttonList){
            buttonMap.put(button, cardFaceList.remove(0)); //map each button to a card face from the list
        }
    }

    //Method to display the programatically created cards on the screen
    private void initCardView(){

        for(int i = 0; i < buttonList.size(); i++){
            LinearLayout layout = getRow(i);
            layout.addView(buttonList.get(i));
        }

    }

    //Method to clear button associations to prior layouts so that they can be re-placed (for rotation)
    private void clearCardView(){
        for(Button button : buttonList) {
            if (button.getParent() != null) {
                ((ViewGroup) button.getParent()).removeView(button);
            }
        }
    }

    //method to get the appropriate row into which to place a button, based on how many have been placed so far.
    //Only want a max of 4 buttons per row in portrait view.
    private LinearLayout getRow(int i){
        LinearLayout layout = thisFragmentView.findViewById(R.id.firstRow); //default to first row
        if(getResources().getConfiguration().orientation != ORIENTATION_LANDSCAPE) {
            if (i >= 4 && i <= 7) { //second row indices 4 through 7
                layout = thisFragmentView.findViewById(R.id.secondRow);
            } else if (i >= 8 && i <= 11) { //third row indices 8 through 11
                layout = thisFragmentView.findViewById(R.id.thirdRow);
            } else if (i >= 12 && i <= 15) { //fourth row indices 12 through 15
                layout = thisFragmentView.findViewById(R.id.fourthRow);
            } else if (i >= 16 && i <= 19) { //fifth row indices 16 through 19
                layout = thisFragmentView.findViewById(R.id.fifthRow);
            }
        }else{
            if(i >= 7 && i <= 13){ //second row indices 7 through 13
                layout = thisFragmentView.findViewById(R.id.secondRow);
            }else if(i >= 14 && i <= 19){ //third row indices 8 through 19
                layout = thisFragmentView.findViewById(R.id.thirdRow);
            }
        }

        return layout;
    }


    private void gameOver(){
        //placeholder - should transition to game over/high scores screen and display final score
        //call getActivity() to get the base activity and initiate changing Activities
        //see moveToGameActivity in InfoActivity for how to pass score and numCards to game over screen
    }

    public GameHandler getTheGame(){
        return theGame;
    }

    public void tryAgainHandler(){
        if(theGame.getCardsSelected() == 2){

            if(!(theGame.isLastPairMatch()) && (firstSelected != null) && (secondSelected != null)){
                firstSelected.setBackgroundResource(R.drawable.cardback);
                secondSelected.setBackgroundResource(R.drawable.cardback);
                firstSelected = null;
                secondSelected = null;
            }
            theGame.tryAgain();
        }
    }

}
