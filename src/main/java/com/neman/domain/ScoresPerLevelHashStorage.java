package com.neman.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListSet;

/**
 * Created by nemanja.
 */
public class ScoresPerLevelHashStorage implements ScoresPerLevel {

    private ConcurrentHashMap<Integer, Integer> scoresForLevel;

    public ScoresPerLevelHashStorage() {
        scoresForLevel = new ConcurrentHashMap<Integer, Integer>();
    }

    //the following assumption is made here and that is that it's highly improbable for a client with the same userId
    //simultaneously submit 2 scores that will cause one overriding the other. In that case, since it is the same user,
    //we will accept either one because we don't know which insert has a higher priority: the one with higher score or
    //the one with some other important property.
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
