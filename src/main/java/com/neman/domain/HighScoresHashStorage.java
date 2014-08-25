package com.neman.domain;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by nemanja.
 */
public class HighScoresHashStorage implements HighScores {
    private Map<Integer, ScoresPerLevel> highScores;
    private Lock lock;

    public HighScoresHashStorage() {
        highScores = new ConcurrentHashMap<Integer, ScoresPerLevel>();
        lock = new ReentrantLock(true);
    }

    //this is a critical concurrent place in code for cases when two threads wish to enter a score for a level
    //that doesn't exist yet. For that reason we need to temporarily lock the access of creating a new level.
    //This decision is made on assumption that it will happen only a couple of times per level, and that when a
    //level already exists everything will go smoothly.
    @Override
    public void putScore(int level, int userId, int score) {
        if (!highScores.containsKey(level)) {
            lock.lock();
            try {
                if (!highScores.containsKey(level)) {
                    ScoresPerLevel scores = new ScoresPerLevelHashStorage();
                    scores.putScore(userId, score);
                    highScores.put(level, scores);
                    return;
                }
            } finally {
                lock.unlock();
            }
        }
        ScoresPerLevel scoresForLvl = highScores.get(level);
        scoresForLvl.putScore(userId, score);
    }

    @Override
    public String getHighScoresCSV(int level, int limit) {
        if (highScores.get(level) == null) {
            return "";
        }
        return highScores.get(level).getHighScoresCSV(limit);
    }


    @Override
    public List<Score> getHighScores(int level, int limit) {
        if (highScores.get(level) == null) {
            return null;
        }
        return highScores.get(level).getHighScores(limit);
    }


}
