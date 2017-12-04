package edu.cpp.concentration;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.Buffer;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class HighScoreActivity extends AppCompatActivity {
    @BindView(R.id.highScoreBackButton)
    Button backButton;
    NumberPicker numberPicker;
    @BindView(R.id.highScoreTextView)
    TextView highScoreTextView;
    @BindView(R.id.highScorePlayerOneTextView)
    TextView playerOne;
    @BindView(R.id.highScorePlayerTwoTextView)
    TextView playerTwo;
    @BindView(R.id.highScorePlayerThreeTextView)
    TextView playerThree;

    private int numberofCards;
    private String filename;
    private ArrayList<Score> scoresList;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_highscore);
        ButterKnife.bind(this);

        scoresList = new ArrayList<Score>();

        numberPicker = findViewById(R.id.numberPickerHighScore);
        numberofCards = getIntent().getIntExtra("numCards", -1);
        highScoreTextView.setText("High Scores For: " + numberofCards + " Cards");

        filename = Integer.toString(numberofCards) + "-highscores.txt";

        Log.i("Filename", filename);

        displayScores(filename);

    }

    @OnClick (R.id.highScoreBackButton)
    public void moveBackToStartScreen() {
        Intent intent = new Intent (this, MainActivity.class);
        startActivity(intent);
    }

    private void displayScores(String highScoreList) {
        try {

            FileInputStream fis = openFileInput(highScoreList);
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr);

            String[] info;
            String line;
            String name;
            int score;

            while ((line = br.readLine()) != null) {
                info = line.split(" ");

                name = info[0];
                score = Integer.parseInt(info[1]);
                scoresList.add(new Score(name, score));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        playerOne.setText("1. " + scoresList.get(0).getName() + ": " + scoresList.get(0).getScore());
        playerTwo.setText("2. " + scoresList.get(1).getName() + ": " + scoresList.get(1).getScore());
        playerThree.setText("3. " + scoresList.get(2).getName() + ": " + scoresList.get(2).getScore());
    }
}
