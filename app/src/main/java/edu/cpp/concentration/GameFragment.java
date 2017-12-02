package edu.cpp.concentration;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.Button;

import java.util.List;
import java.util.Map;

public class GameFragment extends Fragment {
    //private List<Button> buttonList;
    //private Map<Button, Integer> buttonMap;
    private GameHandler theGame;
    private Button firstSelected;
    private Button secondSelected;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }
}
