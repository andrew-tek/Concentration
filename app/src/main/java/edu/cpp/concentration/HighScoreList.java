package edu.cpp.concentration;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;

public class HighScoreList extends MainActivity {

    private ArrayList<Score> scoresList;

    public HighScoreList(String filename) {
        scoresList = new ArrayList<Score>();
        loadFile(filename);
    }

    public void loadFile(String filename) {
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
    }






    public void sortScores() {
        CompareScore comparator = new CompareScore();
        Collections.sort(scoresList, comparator);
    }


    public ArrayList<Score> getScores() {
        return scoresList;
    }

}
