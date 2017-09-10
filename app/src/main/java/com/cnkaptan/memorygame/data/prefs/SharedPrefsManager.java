package com.cnkaptan.memorygame.data;

import com.cnkaptan.memorygame.model.HighScoreRecord;

import java.util.List;

/**
 * Created by cnkaptan on 10/09/2017.
 */

public interface SharedPrefsManager {
    void addHighScore(HighScoreRecord highScoreRecord);
    List<HighScoreRecord> getOrderedHighScoreList();
}
