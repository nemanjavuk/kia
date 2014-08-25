package com.neman.domain;

import org.junit.Assert;
import org.junit.Test;

/**
 * Created by nemanja.
 */
public class ScoreTest {

    @Test
    public void testOrderingOfDifferentScores() {
        Score score1 = new Score(5, 20);
        Score score2 = new Score(6, 50);

        Assert.assertEquals(30, score1.compareTo(score2));
    }

    @Test
    public void testOrderingOfSameScores() {
        Score score1 = new Score(5, 20);
        Score score2 = new Score(6, 20);

        Assert.assertEquals(-1, score1.compareTo(score2));
    }
}
