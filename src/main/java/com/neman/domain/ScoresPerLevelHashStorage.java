package com.neman.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListSet;

/**
 * Created by nemanja on 8/24/14.
 */
public class ScoresPerLevelHashStorage implements ScoresPerLevel {

    private ConcurrentHashMap<Integer, Integer> scoresForLevel;

    public ScoresPerLevelHashStorage() {
        scoresForLevel = new ConcurrentHashMap<Integer, Integer>();
    }

    @Override
    public void putScore(int userId, int score) {
        if (scoresForLevel.containsKey(userId)) {
            if (score > scoresForLevel.get(userId)) {
                scoresForLevel.replace(userId, score);
            }
        } else {
            scoresForLevel.put(userId, score);
        }
    }

    @Override
    public List<Score> getHighScores(int limit) {
        Set<Score> skipListForLevel = orderByScore(scoresForLevel);
        ArrayList<Score> scores = new ArrayList<Score>();
        int index = 0;
        for (Score score : skipListForLevel) {
            if (index >= limit) {
                return scores;
            }
            scores.add(score);
            index++;
        }
        return scores;
    }

    private Set<Score> orderByScore(Map<Integer, Integer> scoresPerLevel) {
        ConcurrentSkipListSet<Score> scoresSkipList = new ConcurrentSkipListSet<Score>();

        for (Map.Entry<Integer, Integer> entry : scoresPerLevel.entrySet()) {
            scoresSkipList.add(new Score(entry.getKey(), entry.getValue()));
        }

        return scoresSkipList;
    }

    @Override
    public String getHighScoresCSV(int limit) {
        Set<Score> skipListForLevel = orderByScore(scoresForLevel);
        int index = 0;
        StringBuffer buffer = new StringBuffer();
        for (Score score : skipListForLevel) {
            if (index >= limit) {
                return buffer.toString();
            }
            if (index > 0) {
                buffer.append(",");
            }
            buffer.append(score);
            index++;
        }
        return buffer.toString();
    }

}
