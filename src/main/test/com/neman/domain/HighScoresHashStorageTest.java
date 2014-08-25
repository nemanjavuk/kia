package com.neman.domain;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

/**
 * Created by nemanja.
 */
public class HighScoresHashStorageTest {
    private HighScores highScores;

    @Before
    public void setUp() {
        highScores = new HighScoresHashStorage();
    }

    @Test
    public void testNoMoreThan15ResultsForALevel() {
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
        highScores.putScore(1, 19, 35);
        highScores.putScore(1, 18, 34);

        List<Score> scores = highScores.getHighScores(1, 15);
        Assert.assertEquals(15, scores.size());
        Assert.assertEquals(50, scores.get(0).getScore());
        Assert.assertEquals(3, scores.get(0).getUserId());

        Assert.assertEquals("3=50,19=35,18=34,17=33,16=32,15=31,14=30,13=29,12=28,11=27,10=26,9=25,8=24,7=23,6=22", highScores.getHighScoresCSV(1, 15));
    }

    @Test
    public void testHighScoresWithNonExistentLevel() {
        highScores.putScore(1, 656, 1);
        highScores.putScore(1, 324, 2);
        String highScoresForLevel1 = highScores.getHighScoresCSV(1, 15);
        Assert.assertEquals("324=2,656=1", highScoresForLevel1);
        String highScoresForLevel2 = highScores.getHighScoresCSV(2, 15);
        Assert.assertEquals("", highScoresForLevel2);
    }


}
