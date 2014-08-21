package com.king.data;

import java.util.List;

/**
 * Created by nemanja on 8/21/14.
 */
public interface HighScores {

    public void putScore(Integer level, Score score);

    public List<Score> getHighScores(Integer level);
}
