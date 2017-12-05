package edu.cpp.concentration;

/**
 * This class allows for the comparison of two Scores so that the program may
 * later sort the list of Scores.
 */

import java.util.Comparator;

public class CompareScore implements Comparator<Score> {
    // method: compare
    // purpose: comparse 2 scores to see which is higher than the other using Score's getScore() method
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
