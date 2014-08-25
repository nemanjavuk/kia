package com.neman.domain;

import java.util.List;

/**
 * Created by nemanja.
 */
public interface ScoresPerLevel {
    public void putScore(int userId, int score);

    public List<Score> getHighScores(int limit);

    public String getHighScoresCSV(int limit);
}
