package edu.cpp.concentration;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class GameActivity extends AppCompatActivity {

    private int numCards;
    private List<Button> buttonList;
    private Map<Button, Integer> buttonMap;
    private GameHandler theGame;
    private Button firstSelected;
    private Button secondSelected;
    private final int[] CARD_FACES = {R.drawable.anduin, R.drawable.druid, R.drawable.garrosh,
            R.drawable.guldan, R.drawable.jaina, R.drawable.lich, R.drawable.rexxar, R.drawable.thrall,
            R.drawable.uther, R.drawable.valeera};
    private final String BUTTON_LIST_TAG = "ButtonList";
    private final String BUTTON_MAP_TAG = "ButtonMap";
    private final String THE_GAME_TAG = "TheGame";

    @BindView(R.id.endGameButton)
    Button endGame;
    @BindView(R.id.tryAgainButton)
    Button tryAgain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        numCards = getIntent().getIntExtra("numCards", -1);
        if (numCards == -1) {
            Log.i("gameError", "Number of cards not properly passed! Defaulting to max cards.");
            numCards = 20;
        }

        if(savedInstanceState == null){
            Log.i("testing", "savedInstanceState was null!");
            theGame = new GameHandler(numCards);
            initCardsHandler();
        }else{
            Log.i("testing", "savedInstanceState was NOT null!");
            Log.i("testing", "theGame score is currently: " + theGame.getScore());
        }

        initCardView();
        //Log.i("cards","numCards reads: " + numCards);

        Log.w("hello", "onCreate: DAMNIT");
        ButterKnife.bind(this);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable(BUTTON_LIST_TAG, (ArrayList)buttonList);
        outState.putSerializable(BUTTON_MAP_TAG, (HashMap)buttonMap);
    }


    private void gameOver(){
        //placeholder - should transition to game over/high scores screen and display final score
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
        Button cardButton = new Button(this);
        String buttonTag = "button" + i;
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        params.setMargins(5,5,5,5);
        if((numCards%4 == 0) || ((i != numCards-1) && (i != numCards-2))) {
            params.weight = 1;
        }
        cardButton.setLayoutParams(params);
        cardButton.setBackgroundResource(R.drawable.cardback);
        cardButton.setTag(buttonTag);
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

    //method to get the appropriate row into which to place a button, based on how many have been placed so far.
    //Only want a max of 4 buttons per row in portrait view.
    private LinearLayout getRow(int i){
        LinearLayout layout = findViewById(R.id.firstRow); //default to first row
        if(i >= 4 && i <= 7){ //second row indices 4 through 7
            layout = findViewById(R.id.secondRow);
        }else if(i >= 8 && i <= 11){ //third row indices 8 through 11
            layout = findViewById(R.id.thirdRow);
        }else if(i >= 12 && i <= 15){ //fourth row indices 12 through 15
            layout = findViewById(R.id.fourthRow);
        }else if(i >= 16 && i <= 19){ //fifth row indices 16 through 19
            layout = findViewById(R.id.fifthRow);
        }

        return layout;
    }

    @OnClick(R.id.endGameButton)
    public void endGameHandler() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.tryAgainButton)
    public void tryAgainHandler() {
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