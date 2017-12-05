package edu.cpp.concentration;

/**
 * This activity is displayed to prompt the user for a difficulty, and then it displays the top
 * 3 high scores of that difficulty.
 */

import android.content.Intent;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.NumberPicker;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class HighScoreInfoActivity extends AppCompatActivity {
    // Scroll wheel for number of cards (4-20, evens)
    NumberPicker numberPicker;
    // Button to submit numberPicker number
    @BindView(R.id.submitButtonHighScore)
    Button submitButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_highscore_info);
        numberPicker = findViewById(R.id.numberPickerHighScore);
        String[] values = {"4", "6", "8", "10", "12", "14", "16", "18", "20"};
        numberPicker.setMinValue(0);
        numberPicker.setMaxValue(values.length - 1);
        numberPicker.setDisplayedValues(values);
        ButterKnife.bind(this);
    }

    // method: moveToHighScoreScreen
    // purpose: button listener; if clicked, get number of cards (difficulty), and pass to
    //          HighScoreActivity and then switch to that activity
    @OnClick(R.id.submitButtonHighScore)
    public void moveToHighScoreScreen() {
        Intent intent = new Intent(this, HighScoreActivity.class);
        int numCardsSelected = Integer.parseInt(numberPicker.getDisplayedValues()[numberPicker.getValue()]);
        Log.i("toPass","cards selected reads: " + numCardsSelected);
        intent.putExtra("numCards", numCardsSelected);
        startActivity(intent);
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
}