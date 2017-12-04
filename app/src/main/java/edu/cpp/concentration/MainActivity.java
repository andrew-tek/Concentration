package edu.cpp.concentration;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

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

}
