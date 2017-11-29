package edu.cpp.concentration;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.NumberPicker;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class InfoActivity extends AppCompatActivity {
    NumberPicker numberPicker;
    @BindView(R.id.submitButton)
    Button submitButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);
        numberPicker = findViewById(R.id.numberPicker);
        String [] values = {"4", "6", "8", "10", "12", "14", "16", "18", "20"};
        numberPicker.setMinValue(0);
        numberPicker.setMaxValue(values.length - 1);
        numberPicker.setDisplayedValues(values);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.submitButton)
    public void moveToGameActivity() {
        Intent intent = new Intent(this, GameActivity.class);
        startActivity(intent);

    }

}