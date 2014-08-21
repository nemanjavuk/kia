package com.king.data;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListMap;

/**
 * Created by nemanja on 8/21/14.
 */
public enum HighScoresImpl implements HighScores {
    INSTANCE;

    //make it final?
    //level -> (score -> user)
    private ConcurrentHashMap<Integer, ConcurrentSkipListMap<Integer, Integer>> highScores;

    @Override
    public void putScore(Integer level, Score score) {
        if (highScores.containsKey(level)) {
            highScores.get(level).put(score.getScore(), score.getUserId());
        } else {
            ConcurrentSkipListMap<Integer, Integer> scores = new ConcurrentSkipListMap<Integer, Integer>(new Comparator<Integer>() {
                //comparator that never returns 0 so it allows duplicates
                @Override
                public int compare(Integer o1, Integer o2) {
                    return o1.equals(o2) ? 1 : o1.compareTo(o2);
                }
            });
            scores.put(score.getScore(), score.getUserId());
            highScores.put(level, scores);
        }
    }

    @Override
    public List<Score> getHighScores(Integer level) {
        ConcurrentSkipListMap<Integer, Integer> listForLevel = highScores.get(level);
        System.out.println(listForLevel);
        int size = listForLevel.size();
        ArrayList<Score> scores = new ArrayList<Score>(size);
        for (Map.Entry<Integer, Integer> entry : listForLevel.entrySet()) {
            scores.add(new Score(entry.getValue(), entry.getKey()));
        }
        return scores;
    }

    HighScoresImpl() {
        highScores = new ConcurrentHashMap<Integer, ConcurrentSkipListMap<Integer, Integer>>();
    }


}
