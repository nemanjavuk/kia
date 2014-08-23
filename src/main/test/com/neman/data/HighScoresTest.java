package com.neman.data;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

/**
 * Created by nemanja on 8/22/14.
 */
public class HighScoresTest {
    private HighScores highScores;

    @Before
    public void setUp() {
        highScores = HighScoresImpl.INSTANCE;
    }

    @Test
    public void testNoMoreThan15EntriesForALevel() {
        HighScores hs = HighScoresImpl.INSTANCE;
        hs.putScore(1, new Score(3, 2));
        hs.putScore(1, new Score(3, 10));
        hs.putScore(1, new Score(3, 50));
        hs.putScore(1, new Score(4, 2));
        hs.putScore(1, new Score(4, 20));
        hs.putScore(1, new Score(5, 21));
        hs.putScore(1, new Score(6, 22));
        hs.putScore(1, new Score(7, 23));
        hs.putScore(1, new Score(8, 24));
        hs.putScore(1, new Score(9, 25));
        hs.putScore(1, new Score(10, 26));
        hs.putScore(1, new Score(11, 27));
        hs.putScore(1, new Score(12, 28));
        hs.putScore(1, new Score(13, 29));
        hs.putScore(1, new Score(14, 30));
        hs.putScore(1, new Score(15, 31));
        hs.putScore(1, new Score(16, 32));
        hs.putScore(1, new Score(17, 33));
        hs.putScore(1, new Score(18, 34));
        hs.putScore(1, new Score(19, 35));

        List<Score> scoresArr = hs.getHighScores(1, 15);

        System.out.println(hs.getHighScoresCSV(1, 15));

        Assert.assertEquals(15, scoresArr.size());
        Assert.assertEquals(50, (int) ((Score) scoresArr.get(0)).getScore());
        Assert.assertEquals(3, (int) ((Score) scoresArr.get(0)).getUserId());
    }

}
