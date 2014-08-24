package com.neman.domain;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

/**
 * Created by nemanja on 8/22/14.
 */
public class HighScoresHashStorageTest {
    private HighScores highScores;

    @Before
    public void setUp() {
        highScores = new HighScoresHashStorage();
    }

    @Test
    public void testNoMoreThan15EntriesForALevel() {
        highScores.putScore(1, 3, 2);
        highScores.putScore(1, 3, 10);
        highScores.putScore(1, 3, 50);
        highScores.putScore(1, 4, 2);
        highScores.putScore(1, 4, 20);
        highScores.putScore(1, 5, 21);
        highScores.putScore(1, 6, 22);
        highScores.putScore(1, 7, 23);
        highScores.putScore(1, 8, 24);
        highScores.putScore(1, 9, 25);
        highScores.putScore(1, 10, 26);
        highScores.putScore(1, 11, 27);
        highScores.putScore(1, 12, 28);
        highScores.putScore(1, 13, 29);
        highScores.putScore(1, 14, 30);
        highScores.putScore(1, 15, 31);
        highScores.putScore(1, 16, 32);
        highScores.putScore(1, 17, 33);
        highScores.putScore(1, 18, 34);
        highScores.putScore(1, 19, 35);

        List<Score> scoresArr = highScores.getHighScores(1, 15);

        Assert.assertEquals("3=50,19=35,18=34,17=33,16=32,15=31,14=30,13=29,12=28,11=27,10=26,9=25,8=24,7=23,6=22", highScores.getHighScoresCSV(1, 15));
        Assert.assertEquals(15, scoresArr.size());
        Assert.assertEquals(50, (int) ((Score) scoresArr.get(0)).getScore());
        Assert.assertEquals(3, (int) ((Score) scoresArr.get(0)).getUserId());
    }

}
