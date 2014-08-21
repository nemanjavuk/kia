package com.king.data;

/**
 * Created by nemanja on 8/21/14.
 */
public class Score {

    private Integer userId;
    private Integer score;

    public Score(Integer userId, Integer score) {
        this.userId = userId;
        this.score = score;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    @Override
    public String toString() {
        return "Score{" +
                "userId=" + userId +
                ", score=" + score +
                '}';
    }
}
