package com.king;

import com.king.data.HighScores;
import com.king.data.HighScoresImpl;
import com.king.data.Score;

import java.util.List;

/**
 * Created by nemanja on 8/21/14.
 */
public class Main {

    public static void main(String[] args) {
        HighScores hs = HighScoresImpl.INSTANCE;
        hs.putScore(1, new Score(3, 2));
        hs.putScore(1, new Score(3, 10));
        hs.putScore(1, new Score(3, 50));
        hs.putScore(1, new Score(4, 2));
        List<Score> highScores = hs.getHighScores(1);
        for (Score score : highScores) {
            System.out.println(score);
        }
    }

}
