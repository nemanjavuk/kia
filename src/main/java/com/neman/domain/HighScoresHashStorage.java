package com.neman.domain;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by nemanja on 8/21/14.
 */
public class HighScoresHashStorage implements HighScores {
    private ConcurrentHashMap<Integer, ScoresPerLevel> highScores;
    private Lock lock;

    public HighScoresHashStorage() {
        highScores = new ConcurrentHashMap<Integer, ScoresPerLevel>();
        lock = new ReentrantLock(true);
    }

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
