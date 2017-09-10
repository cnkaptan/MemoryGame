package com.cnkaptan.memorygame.utils;

/**
 * Created by cnkaptan on 10/09/2017.
 */

public enum Level {
    VERYEASY(2,240000),EASY(4,240000),NORMAL(4,90000),HARD(6,480000),VERYHARD(8,600000);

    private final int CELL_NUMBER;
    private final int MILLISECONDS;

    Level(int CELL_NUMBER,int milliseconds) {
        this.CELL_NUMBER = CELL_NUMBER;
        this.MILLISECONDS = milliseconds;
    }

    public int getCELL_NUMBER() {
        return CELL_NUMBER;
    }

    public int getMILLISECONDS() {
        return MILLISECONDS;
    }
}
