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
    //Test Thing
    private int numCards;
    private List<Button> buttonList;
    private Map<Button, Integer> buttonMap;
    private int cardsSelected;
    private Button firstSelected;
    private Button secondSelected;
    private boolean lastPairMatch;
    private int score;
    private final int[] CARD_FACES = {R.drawable.anduin, R.drawable.druid, R.drawable.garrosh,
            R.drawable.guldan, R.drawable.jaina, R.drawable.lich, R.drawable.rexxar, R.drawable.thrall,
            R.drawable.uther, R.drawable.valeera};

    @BindView(R.id.endGameButton)
    Button endGame;
    @BindView(R.id.tryAgainButton)
    Button tryAgain;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        numCards = getIntent().getIntExtra("numCards", -1);
        cardsSelected = 0;
        score = 0;
        lastPairMatch = false;
        if (numCards == -1) {
            Log.i("gameError", "Number of cards not properly passed! Defaulting to max cards.");
            numCards = 20;
        }
        initCardsHandler();
        initCardView();
        //Log.i("cards","numCards reads: " + numCards);

        Log.w("hello", "onCreate: DAMNIT");
        ButterKnife.bind(this);
    }

    private View.OnClickListener onClickFlipper(final Button button)  {
        return new View.OnClickListener() {
            public void onClick(View v) {
                if(cardsSelected == 0){
                    firstSelected = button;
                    cardsSelected = 1;
                    button.setBackgroundResource(buttonMap.get(button));
                }else if(cardsSelected == 1){
                    cardsSelected = 2;
                    secondSelected = button;
                    if(buttonMap.get(button) == buttonMap.get(firstSelected)){
                        score += 2;
                        lastPairMatch = true;
                    }else{
                        if(score > 0){
                            score -= 1;
                        }
                    }
                    button.setBackgroundResource(buttonMap.get(button));
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
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);//10,LinearLayout.LayoutParams.WRAP_CONTENT);

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

    private void initCardView(){

        for(int i = 0; i < buttonList.size(); i++){
            LinearLayout layout = getRow(i);
            layout.addView(buttonList.get(i));
        }

    }

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
        if(cardsSelected == 2){
            cardsSelected = 0;
            if(!lastPairMatch && (firstSelected != null) && (secondSelected != null)){
                firstSelected.setBackgroundResource(R.drawable.cardback);
                secondSelected.setBackgroundResource(R.drawable.cardback);
            }
            firstSelected = null;
            secondSelected = null;
            lastPairMatch = false;
        }
    }
}