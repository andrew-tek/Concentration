package edu.cpp.concentration;

import java.util.Comparator;

public class CompareScore implements Comparator<Score> {
    public int compare(Score score1, Score score2) {
        int value1 = score1.getScore();
        int value2 = score2.getScore();

        if (value1 > value2) {
            return -1;
        }
        else if (value1 < value2) {
            return 1;
        }
        else {
            return 0;
        }
    }

}
