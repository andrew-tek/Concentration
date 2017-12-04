package edu.cpp.concentration;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class HighScoreActivity extends AppCompatActivity{
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_highscore);
        ButterKnife.bind(this);
        numberPicker = findViewById(R.id.numberPickerHighScore);
        numberofCards = getIntent().getIntExtra("numCards", -1);
        highScoreTextView.setText(numberofCards + " Card Game");
        getSupportActionBar().setHomeButtonEnabled(true);//ancestral navigation button
    }

    @OnClick (R.id.highScoreBackButton)
    public void moveBackToStartScreen() {
        Intent intent = new Intent (this, MainActivity.class);
        startActivity(intent);
    }

    //method to handle the tapping of the "up" button for ancestral navigation
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
}
