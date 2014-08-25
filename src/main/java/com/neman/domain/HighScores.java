package com.neman.domain;

import java.util.List;

/**
 * Created by nemanja.
 */
public interface HighScores {

    public void putScore(int level, int userId, int score);

    public List<Score> getHighScores(int level, int limit);

    public String getHighScoresCSV(int level, int limit);

}
