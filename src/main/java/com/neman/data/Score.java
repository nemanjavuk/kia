package com.neman.data;

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
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Score score = (Score) o;

        if (userId != score.userId) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        return userId;
    }

    @Override
    public String toString() {
        return userId + "=" + score;
    }

    @Override
    public int compareTo(Score o) {
        return o.score - score;
    }
}
