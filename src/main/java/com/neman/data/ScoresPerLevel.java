package com.neman.data;

import java.util.List;

/**
 * Created by nemanja on 8/24/14.
 */
public interface ScoresPerLevel {
    public void putScore(int userId, int score);

    public List<Score> getHighScores(int limit);

    public String getHighScoresCSV(int limit);
}
