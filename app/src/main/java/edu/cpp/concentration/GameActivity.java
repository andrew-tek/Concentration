/** *************************************************************
 * file: GameActivity.java
 * author: Christopher Kilian, Andrew Tek
 * class: CS 245 – Programming Graphical User Interfaces
 *
 * assignment: Android App - Concentration
 * date last modified: 12/04/2017
 *
 * purpose: Handles the main Concentration game and related fragments (the game and music fragments).
 * Also manages button clicks for New Game, End Game, Try Again, and Toggle Music buttons.
 *
 *************************************************************** */
package edu.cpp.concentration;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Handler;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;

import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class GameActivity extends AppCompatActivity {

    private int numCards;
    private GameFragment theGameFragment;
    private MusicFragment musicFragment;
    private final String SAVED_FRAGMENT_TAG = "theGameFragment";
    private final String SAVED_MUSIC_FRAGMENT_TAG = "thisMusicFragment";

    TextView score;
    @BindView(R.id.toggleMusicButton)
    Button toggleMusic;
    @BindView(R.id.endGameButton)
    Button endGame;
    @BindView(R.id.tryAgainButton)
    Button tryAgain;
    @BindView(R.id.newGameButton)
    Button newGame;

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
        musicFragment = (MusicFragment) fragmentManager.findFragmentByTag(SAVED_MUSIC_FRAGMENT_TAG);

        if (theGameFragment == null) {
            Bundle myBundle = new Bundle();
            myBundle.putInt("numCards", numCards);
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            theGameFragment = new GameFragment();
            theGameFragment.setArguments(myBundle);
            fragmentTransaction.add(R.id.fragment_container, theGameFragment, SAVED_FRAGMENT_TAG);
            fragmentTransaction.commit();
        }
        if (musicFragment == null) {
            FragmentTransaction musicTransaction = fragmentManager.beginTransaction();
            musicFragment = new MusicFragment();
            musicTransaction.add(R.id.music_fragment_container, musicFragment, SAVED_MUSIC_FRAGMENT_TAG);
            musicTransaction.commit();
        }
        while (theGameFragment == null) {
            try {
                String scoreText = "Score: " + theGameFragment.getTheGame().getScore();
                score.setText(scoreText);
            }
            catch (Exception e) {
            }
        }

        getSupportActionBar().setHomeButtonEnabled(true); //ancestral navigation button

        ButterKnife.bind(this);
    }
    //Will resume on rotate and set the appropriate text for thr button
    @Override
    protected void onResume() {
        super.onResume();
        if(musicFragment.getMediaPlayer().isPlaying()){
            toggleMusic.setText("Turn Music Off");
        }else{
            toggleMusic.setText("Turn Music On");
        }
    }


    //Disable all buttons, flip over all cards for 8 seconds then return to main activity screen
    @OnClick(R.id.endGameButton)
    public void endGameHandler() {
        //toggleMusic.setEnabled(false);
        endGame.setEnabled(false);
        newGame.setEnabled(false);
        tryAgain.setEnabled(false);
        Map<Button, Integer> buttonMap = theGameFragment.getButtonMap();
        for (Button imageButton : theGameFragment.getButtonList()) {
            imageButton.setBackgroundResource(buttonMap.get(imageButton));
            imageButton.setEnabled(false);
        }
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                moveToMainActivity();
            }
        }, 5000);
    }

    //Button on click will move to InfoActivity class
    @OnClick(R.id.newGameButton)
    public void newGameButton() {
        Intent intent = new Intent(this, InfoActivity.class);
        startActivity(intent);
    }

    //Call method that will flip over the two cards and deduct points
    @OnClick(R.id.tryAgainButton)
    public void tryAgainHandler() {
        theGameFragment.tryAgainHandler();

    }

    //Play music and set appropriate text for the button
    @OnClick(R.id.toggleMusicButton)
    public void toggleMusic() {
        MediaPlayer mediaPlayer = musicFragment.getMediaPlayer();
        if (mediaPlayer.isPlaying()) {
            mediaPlayer.pause();
            toggleMusic.setText("Turn Music On");
            musicFragment.setWasPlaying(false);
        }
        else {
            mediaPlayer.start();
            toggleMusic.setText("Turn Music Off");
            musicFragment.setWasPlaying(true);
        }
    }

    // method: opOptionsItemSelected
    // purpose: method to handle the tapping of the "up" button for ancestral navigation
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    //Will move screen to main activity
    public void moveToMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}