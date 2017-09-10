package com.cnkaptan.memorygame.game;

import com.cnkaptan.memorygame.utils.Level;
import com.cnkaptan.memorygame.model.ChecableItem;

import java.util.List;

/**
 * Created by cnkaptan on 07/09/17.
 */

public interface GameView {
    void notifyAdapter();
    void gameFinished(int totalPoints);
    void initPhotoList(List<ChecableItem> photos, Level level);
    void updateRemainTime(String time);
    void updatePoints(int totalPoints);
}
