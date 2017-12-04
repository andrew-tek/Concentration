package edu.cpp.concentration;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;


import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class GameActivity extends AppCompatActivity {

    private int numCards;
    private GameFragment theGameFragment;
    private final String SAVED_FRAGMENT_TAG = "theGameFragment";

    TextView score;
    @BindView(R.id.endGameButton)
    Button endGame;
    @BindView(R.id.tryAgainButton)
    Button tryAgain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        score = findViewById(R.id.scoreTextView);
        numCards = getIntent().getIntExtra("numCards", -1);
        if (numCards == -1) {
            Log.i("gameError", "Number of cards not properly passed! Defaulting to max cards.");
            numCards = 20;
        }

        FragmentManager fragmentManager = getSupportFragmentManager();
        theGameFragment = (GameFragment) fragmentManager.findFragmentByTag(SAVED_FRAGMENT_TAG);

        if (theGameFragment == null) {
            Bundle myBundle = new Bundle();
            myBundle.putInt("numCards", numCards);
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            theGameFragment = new GameFragment();
            theGameFragment.setArguments(myBundle);
            fragmentTransaction.add(R.id.fragment_container, theGameFragment, SAVED_FRAGMENT_TAG);
            fragmentTransaction.commit();
        }
        while (theGameFragment == null) {
            try {
                score.setText("Score: " + theGameFragment.getTheGame().getScore());
            }
            catch (Exception e) {
            }
        }


        ButterKnife.bind(this);
    }

    @OnClick(R.id.endGameButton)
    public void endGameHandler() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.tryAgainButton)
    public void tryAgainHandler() {
        theGameFragment.tryAgainHandler();
    }
}