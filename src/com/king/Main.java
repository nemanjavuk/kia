package com.king;

import com.king.data.HighScores;
import com.king.data.HighScoresImpl;

/**
 * Created by nemanja on 8/21/14.
 */
public class Main {//test

    public static void main(String[] args) {
        HighScores hs = HighScoresImpl.INSTANCE;
        hs.putScore(1, 2, 3);
    }

}
