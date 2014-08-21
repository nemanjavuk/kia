package com.king.data;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListMap;

/**
 * Created by nemanja on 8/21/14.
 */
public enum HighScoresImpl implements HighScores {
    INSTANCE;

    //make it final?
    private ConcurrentHashMap<Integer, ConcurrentSkipListMap<Integer, Object>> highScores;

    @Override
    public void putScore(Integer level, Integer userId, Integer score) {
        System.out.println(level + " " + userId + " " + score);
    }

    @Override
    public List<Object> getHighScores(Integer level) {
        return new ArrayList<Object>();//test comment
    }

    HighScoresImpl() {
        highScores = new ConcurrentHashMap<Integer, ConcurrentSkipListMap<Integer, Object>>();
    }


}
