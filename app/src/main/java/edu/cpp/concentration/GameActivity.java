package edu.cpp.concentration;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.NumberPicker;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class GameActivity extends AppCompatActivity {

    private int numCards;
    private List<Button> buttonList;
    private Map<Integer, Button> buttonMap;

    @BindView(R.id.endGameButton)
    Button endGame;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        numCards = getIntent().getIntExtra("numCards",-1);
        if(numCards == -1){
            Log.i("gameError", "Number of cards not properly passed! Defaulting to max cards.");
            numCards = 20;
        }
        initCardsHandler();
        initCardView();
        //Log.i("cards","numCards reads: " + numCards);

        Log.w("hello", "onCreate: DAMNIT");
        ButterKnife.bind(this);
    }

    private void initCardsHandler(){
        buttonList = new ArrayList<>();
        for(int i = 0; i < numCards; i++){
            Button cardButton = new Button(this);
            cardButton.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            cardButton.setBackgroundResource(R.drawable.cardback);
            buttonList.add(cardButton);
        }
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
}