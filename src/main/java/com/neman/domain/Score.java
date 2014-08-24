package com.neman.domain;

/**
 * Created by nemanja on 8/21/14.
 */
public class Score implements Comparable<Score> {

    private int userId;
    private int score;

    public Score(int userId, int score) {
        this.userId = userId;
        this.score = score;
    }

    public int getUserId() {
        return userId;
    }

    public int getScore() {
        return score;
    }

    @Override
    public String toString() {
        return userId + "=" + score;
    }

    @Override
    public int compareTo(Score o) {
        if (o.score != score) {
            return o.score - score;
        } else {
            return userId - o.userId;
        }
    }
}
