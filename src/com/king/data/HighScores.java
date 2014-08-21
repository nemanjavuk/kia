package com.king.data;

import java.util.List;

/**
 * Created by nemanja on 8/21/14.
 */
public interface HighScores {

    public void putScore(Integer level, Integer userId, Integer score);

    // should return Score objects
    public List<Object> getHighScores(Integer level);
}
