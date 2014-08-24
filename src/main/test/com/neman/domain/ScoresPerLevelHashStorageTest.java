package com.neman.domain;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

/**
 * Created by nemanja on 8/24/14.
 */
public class ScoresPerLevelHashStorageTest {
    private ScoresPerLevel scoresPerLevel;

    @Before
    public void setUp() {
        scoresPerLevel = new ScoresPerLevelHashStorage();
    }

    @Test
    public void testWhenLessThan15ResultsExist() {
        scoresPerLevel.putScore(3, 2);
        scoresPerLevel.putScore(3, 10);
        scoresPerLevel.putScore(3, 50);
        scoresPerLevel.putScore(4, 2);
        scoresPerLevel.putScore(4, 20);
        scoresPerLevel.putScore(5, 21);
        scoresPerLevel.putScore(6, 22);
        scoresPerLevel.putScore(7, 23);
        scoresPerLevel.putScore(8, 24);
        scoresPerLevel.putScore(9, 25);
        scoresPerLevel.putScore(10, 26);

        List<Score> scores = scoresPerLevel.getHighScores(15);
        Assert.assertEquals(8, scores.size());
        Assert.assertEquals(50, scores.get(0).getScore());
        Assert.assertEquals(3, scores.get(0).getUserId());

        Assert.assertEquals("3=50,10=26,9=25,8=24,7=23,6=22,5=21,4=20", scoresPerLevel.getHighScoresCSV(15));
    }

    @Test
    public void testNoMoreThan15WhenMoreResultsExist() {
        scoresPerLevel.putScore(3, 2);
        scoresPerLevel.putScore(3, 10);
        scoresPerLevel.putScore(3, 50);
        scoresPerLevel.putScore(4, 2);
        scoresPerLevel.putScore(4, 20);
        scoresPerLevel.putScore(5, 21);
        scoresPerLevel.putScore(6, 22);
        scoresPerLevel.putScore(7, 23);
        scoresPerLevel.putScore(8, 24);
        scoresPerLevel.putScore(9, 25);
        scoresPerLevel.putScore(10, 26);
        scoresPerLevel.putScore(11, 27);
        scoresPerLevel.putScore(12, 28);
        scoresPerLevel.putScore(13, 29);
        scoresPerLevel.putScore(14, 30);
        scoresPerLevel.putScore(15, 31);
        scoresPerLevel.putScore(16, 32);
        scoresPerLevel.putScore(17, 33);
        scoresPerLevel.putScore(19, 35);
        scoresPerLevel.putScore(18, 34);

        List<Score> scores = scoresPerLevel.getHighScores(15);
        Assert.assertEquals(15, scores.size());
        Assert.assertEquals(50, scores.get(0).getScore());
        Assert.assertEquals(3, scores.get(0).getUserId());

        Assert.assertEquals("3=50,19=35,18=34,17=33,16=32,15=31,14=30,13=29,12=28,11=27,10=26,9=25,8=24,7=23,6=22", scoresPerLevel.getHighScoresCSV(15));
    }

    @Test
    public void testOnlyOneResultForOneUser() {
        scoresPerLevel.putScore(6, 50);
        scoresPerLevel.putScore(6, 25);
        scoresPerLevel.putScore(6, 75);

        List<Score> scores = scoresPerLevel.getHighScores(15);

        Assert.assertEquals(1, scores.size());
        Assert.assertEquals(6, scores.get(0).getUserId());
        Assert.assertEquals(75, scores.get(0).getScore());

        Assert.assertEquals("6=75", scoresPerLevel.getHighScoresCSV(15));
    }

    @Test
    public void testOnlyHighestScoreValid() {
        scoresPerLevel.putScore(6, 50);
        List<Score> scores = scoresPerLevel.getHighScores(15);

        Assert.assertEquals(1, scores.size());
        Assert.assertEquals(6, scores.get(0).getUserId());
        Assert.assertEquals(50, scores.get(0).getScore());

        scoresPerLevel.putScore(6, 25);
        scores = scoresPerLevel.getHighScores(15);

        Assert.assertEquals(1, scores.size());
        Assert.assertEquals(6, scores.get(0).getUserId());
        Assert.assertEquals(50, scores.get(0).getScore());

        scoresPerLevel.putScore(6, 75);
        scores = scoresPerLevel.getHighScores(15);

        Assert.assertEquals(1, scores.size());
        Assert.assertEquals(6, scores.get(0).getUserId());
        Assert.assertEquals(75, scores.get(0).getScore());
    }

    @Test
    public void testEmptyStringWhenNoDataPresent() {
        List<Score> scores = scoresPerLevel.getHighScores(15);
        Assert.assertEquals(true, scores.isEmpty());
        Assert.assertEquals("", scoresPerLevel.getHighScoresCSV(15));
    }

}
