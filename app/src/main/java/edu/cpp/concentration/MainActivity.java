package edu.cpp.concentration;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.util.Log;
import android.content.Context;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStreamReader;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.startGameButton)
    Button startGame;
    @BindView(R.id.highScoreButton)
    Button highScore;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setHomeButtonEnabled(false); //ancestral navigation button
        ButterKnife.bind(this);
    }

    @OnClick(R.id.startGameButton)
    public void changeScreenToActivityInfo() {
        Intent intent = new Intent(this, InfoActivity.class);
        startActivity(intent);
    }
    @OnClick(R.id.highScoreButton)
    public void changeScreenToHighScoreInfo() {
        Intent intent = new Intent (this, HighScoreInfoActivity.class);
        startActivity(intent);
    }

    // method: highScoreFileToMemory
    // purpose: takes the high scores file associated with the difficulty and places it in internal memory if it isn't there
    private void highScoreFileToMemory() {
        String filename = "-highscores.txt";
        String appendedFilename;

        try {
            for (int i = 4; i <= 20; i+=2) {
                // i = number, so #-highscores.txt (see assets folder)
                appendedFilename = i + filename;

                File file = getBaseContext().getFileStreamPath(appendedFilename);
                if (!file.exists()) {
                    FileOutputStream fos = openFileOutput(appendedFilename, Context.MODE_PRIVATE);
                    fos.write(loadFile(appendedFilename).getBytes());
                    fos.close();
                }
                else {
                    // File exists
                    Log.i("FILE_EXIST", appendedFilename + " EXISTS");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // method: loadFile
    // purpose: retrieve contents of file
    private String loadFile(String file) {
        String contents = "";
        try {
            InputStreamReader isr = new InputStreamReader(getAssets().open(file));
            BufferedReader br = new BufferedReader(isr);
            // Read 3 lines (top 3 scores)
            for (int i = 0; i < 3; i++) {
                contents += br.readLine() + "\n";
            }
            isr.close();
            br.close();
        } catch (Exception e){
            e.printStackTrace();
        }
        return contents;
    }

}
