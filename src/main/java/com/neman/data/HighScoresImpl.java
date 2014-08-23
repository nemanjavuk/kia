package com.neman.data;

import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListSet;

/**
 * Created by nemanja on 8/21/14.
 */
public enum HighScoresImpl implements HighScores {
    INSTANCE;

    //make it final?
    //level -> score -> userId
    private ConcurrentHashMap<Integer, ConcurrentHashMap<Integer, Score>> highScores;

    @Override
    public void putScore(int level, Score score) {
        if (highScores.containsKey(level)) {
            ConcurrentHashMap<Integer, Score> scoresForLvl = highScores.get(level);
            if (scoresForLvl.containsKey(score.getUserId())) {
                Integer userId = score.getUserId();
                Integer newScore = score.getScore();
                if (newScore > scoresForLvl.get(userId).getScore()) {
                    highScores.get(level).replace(userId, new Score(userId, newScore));
                }
            } else {
                highScores.get(level).put(score.getUserId(), score);
            }
        } else {
            ConcurrentHashMap<Integer, Score> scores = new ConcurrentHashMap<Integer, Score>();
            scores.put(score.getUserId(), score);
            highScores.put(level, scores);
        }
    }

    @Override
    public String getHighScoresCSV(int level, int limit) {
        if (highScores.get(level) == null) {
            return "";
        }
        ConcurrentSkipListSet<Score> scoresForLevel = orderByScore(highScores.get(level));
        int index = 0;
        StringBuffer buffer = new StringBuffer();
        for (Score score : scoresForLevel) {
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

    private ConcurrentSkipListSet<Score> orderByScore(Map<Integer, Score> scoresForLevel) {
        ConcurrentSkipListSet<Score> scoresSkipList = new ConcurrentSkipListSet<Score>();

        for (Map.Entry<Integer, Score> scoreEntry : scoresForLevel.entrySet()) {
            scoresSkipList.add(scoreEntry.getValue());
        }

        return scoresSkipList;
    }

    @Override
    public ArrayList<Score> getHighScores(int level, int limit) {
        if (highScores.get(level) == null) {
            return null;
        }
        ConcurrentSkipListSet<Score> scoresForLevel = orderByScore(highScores.get(level));
        ArrayList<Score> scores = new ArrayList<Score>();
        int index = 0;
        for (Score score : scoresForLevel) {
            if (index >= limit) {
                return scores;
            }
            scores.add(score);
            index++;
        }
        return scores;
    }


    HighScoresImpl() {
        highScores = new ConcurrentHashMap<Integer, ConcurrentHashMap<Integer, Score>>();
    }


}
