package com.neman.data;

import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListMap;

/**
 * Created by nemanja on 8/21/14.
 */
public enum HighScoresImpl implements HighScores {
    INSTANCE;

    //make it final?
    //level -> (user, score)
    private ConcurrentHashMap<Integer, ConcurrentSkipListMap<Integer, Score>> highScores;

    @Override
    public void putScore(int level, Score score) {
        System.out.println("trying to insert " + level + " " + score);
        if (highScores.containsKey(level)) {
            ConcurrentSkipListMap<Integer, Score> scoresForLvl = highScores.get(level);
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
            ConcurrentSkipListMap<Integer, Score> scores = new ConcurrentSkipListMap<Integer, Score>(); //TODO:nemanja:is it a good idea?
            scores.put(score.getUserId(), score);
            highScores.put(level, scores);
        }
    }

    @Override
    public ArrayList<Score> getHighScores(int level, int limit) {
        ConcurrentSkipListMap<Integer, Score> scoresForLevel = highScores.get(level);
        System.out.println(scoresForLevel);
        ArrayList<Score> scorez = new ArrayList<Score>();
        int index = 0;
        for (Map.Entry<Integer, Score> scoreEntry : scoresForLevel.entrySet()) {
            if (index >= limit) {
                return scorez;
            }
            System.out.println("adding " + scoreEntry.getValue());
            scorez.add(scoreEntry.getValue());
            index++;
        }
        return scorez;
    }

    @Override
    public String getHighScoresCSV(int level, int limit) {
        ArrayList<Score> highScores = getHighScores(level, limit);
        System.out.println("highscores " + highScores);
        StringBuffer buffer = new StringBuffer();
        int numOfScores = highScores.size();
        for (int i = 0; i < numOfScores; i++) {
            buffer.append(highScores.get(i).toString());
            if (!(i == (numOfScores - 1))) {
                buffer.append(",");
            }
        }
        System.out.println(buffer.toString());
        return buffer.toString();
    }

    private HighScoresImpl() {
        highScores = new ConcurrentHashMap<Integer, ConcurrentSkipListMap<Integer, Score>>();
    }


}
