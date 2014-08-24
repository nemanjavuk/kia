package com.neman.data;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by nemanja on 8/21/14.
 */
public class HighScoresHashStorage implements HighScores {
    private Map<Integer, ScoresPerLevel> highScores;

    public HighScoresHashStorage() {
        highScores = new ConcurrentHashMap<Integer, ScoresPerLevel>();
    }

    @Override
    public void putScore(int level, int userId, int score) {
        if (highScores.containsKey(level)) {
            ScoresPerLevel scoresForLvl = highScores.get(level);
            scoresForLvl.putScore(userId, score);
        } else {
            ScoresPerLevel scores = new ScoresPerLevelHashStorage();
            scores.putScore(userId, score);
            highScores.put(level, scores);
        }
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
