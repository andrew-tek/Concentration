package edu.cpp.concentration;

import android.content.Intent;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.view.View;
import android.content.Context;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Collections;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import android.widget.EditText;
import android.widget.TextView;

public class GameOverActivity extends AppCompatActivity {

    private int score;
    private int numCards;
    private String filename;
    private ArrayList<Score> scoresList;

    @BindView(R.id.askForScore)
    TextView askForScore;

    @BindView(R.id.finalScore)
    TextView finalScore;

    @BindView(R.id.highScoreTextView)
    TextView highScoreTextView;

    @BindView(R.id.submitScore)
    Button submitScore;

    @BindView(R.id.nameSubmit)
    EditText nameSubmit;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_over);
        ButterKnife.bind(this);

        score = getIntent().getIntExtra("score", 0);
        numCards = getIntent().getIntExtra("numCards", 0);
        scoresList = new ArrayList<Score>();
        Log.i("retreived: ", "score: " + score);
        Log.i("num cards", "numcards: " + numCards);

        finalScore.setText("Your score is: " + score);
        filename = Integer.toString(numCards) + "-highscores.txt";

//        if (isHighScore() != -1) {
//            askForScore.setText("You have a new high score! Please enter a name for this score: ");
//            String input = nameSubmit.getText().toString();
//            addHighScore(isHighScore(), input);
//        }

        isHighScore();

    }

    @Override
    protected void onStop() {
        super.onStop();
    }


    private void isHighScore() {
        try {
            FileInputStream fis = openFileInput(filename);
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

        CompareScore comparator = new CompareScore();
        Collections.sort(scoresList, comparator);
        Log.i("scoresList[0]", ""+ scoresList.get(0).getScore());

        if (score >= scoresList.get(1).getScore()) {
            askForScore.setText("You have a new high score! Please enter a name for this score: ");
            submitScore.setOnClickListener((new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String input = nameSubmit.getText().toString();
                    scoresList.add(new Score(input, score));
                    CompareScore comparator = new CompareScore();
                    Collections.sort(scoresList, comparator);
                    Log.i("name/score", input);
                    for (int i = 0; i < scoresList.size(); i++) {
                        Log.i("array", "NAME: " + scoresList.get(i).getName() + " | SCORE: " + scoresList.get(i).getScore());
                    }

                    try {
                    OutputStream os = openFileOutput(filename, MODE_PRIVATE);
                    BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(os));

                    for (int i = 0; i < 3; i++) {
                        bw.write(scoresList.get(i).getName() + " " + scoresList.get(i).getScore() + "\n");
                        Log.i("NAME WRITTEN: ", scoresList.get(i).getName());
                    }
                    os.flush();
                    bw.flush();
                    bw.close();
                    os.close();

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    Intent intent = new Intent(v.getContext(), MainActivity.class);
                    startActivity(intent);
                }
            }));
        }
        else {
            askForScore.setVisibility(View.GONE);
            nameSubmit.setVisibility(View.GONE);
            nameSubmit.setVisibility(View.GONE);
        }
    }

//    @OnClick(R.id.nameSubmit)
//    public void submitName() {
//        // add score to file
//        String input = nameSubmit.getText().toString();
//        scoresList.add(new Score(input, score));
//        CompareScore comparator = new CompareScore();
//        Collections.sort(scoresList, comparator);
//        Log.i("name/score", input);
//
//
//        for (int i = 0; i < scoresList.size(); i++) {
//            Log.i("array", "NAME: " + scoresList.get(i).getName() + " | SCORE: " + scoresList.get(i).getScore());
//        }
//        try {
//            OutputStream os = openFileOutput(filename, MODE_PRIVATE);
//            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(os));
//
//
//
//            for (int i = 0; i < 3; i++) {
////                bw.write(scoresList.get(i).getName() + " " + scoresList.get(i).getScore() + "\n");
//                Log.i("NAME WRITTEN: ", scoresList.get(i).getName());
//            }
//            os.close();
//            bw.close();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }


//    private int isHighScore() {
//        String dataInFile = readFile(filename);
//        String[] scoreList = dataInFile.split("\n");
//        for (int i = 0; i < 3; i++) {
//            String[] nameAndScore = scoreList[i].split(" ");
//            if (score > Integer.parseInt(nameAndScore[1])) {
//                return i;
//            }
//        }
//        return -1;
//    }
//
//    private void addHighScore(int i, String userName) {
//        String dataInFile = readFile(filename);
//        String[] scoreList = dataInFile.split("\n");
//
//        if (i > -1 && i < 4) {
//            if (i+1 < 3) {
//                scoreList[i+1] = scoreList[i];
//            }
//            scoreList[i] = (userName + " " + score);
//        }
//        try {
//            FileOutputStream fos = openFileOutput(filename, Context.MODE_PRIVATE);
//
//            for (int j = 0; j < 3; j++) {
//                fos.write((scoreList[j] + "\n").getBytes());
//            }
//            fos.close();
//        }
//        catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    private String readFile(String filename) {
//        int i;
//
//        String contents = "";
//        try {
//            FileInputStream fis = openFileInput(filename);
//
//            while ((i = fis.read()) != -1) {
//                contents += Character.toString((char)i);
//            }
//            fis.close();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return contents;
//    }




}
