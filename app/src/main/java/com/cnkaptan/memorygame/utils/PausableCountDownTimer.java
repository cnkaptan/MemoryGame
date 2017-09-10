package com.cnkaptan.memorygame.utils;

import android.os.CountDownTimer;

/**
 * Created by cnkaptan on 10/09/2017.
 */

public class PausableCountDownTimer {
    long millisInFuture = 0;
    long countDownInterval = 0;
    long millisRemaining =  0;
    private CountDownListener countDownListener;
    CountDownTimer countDownTimer = null;

    boolean isPaused = true;

    public PausableCountDownTimer(long millisInFuture, long countDownInterval,CountDownListener countDownListener) {
        super();
        this.millisInFuture = millisInFuture;
        this.countDownInterval = countDownInterval;
        this.millisRemaining = this.millisInFuture;
        this.countDownListener = countDownListener;
    }

    private void createCountDownTimer(){
        countDownTimer = new CountDownTimer(millisRemaining,countDownInterval) {

            @Override
            public void onTick(long millisUntilFinished) {
                millisRemaining = millisUntilFinished;
                if (countDownListener != null) {
                    countDownListener.onTick(millisUntilFinished);
                }
            }

            @Override
            public void onFinish() {
                if (countDownListener != null) {
                    countDownListener.onFinish();
                }
            }
        };
    }
    public final void cancel(){
        if(countDownTimer!=null){
            countDownTimer.cancel();
        }
        this.millisRemaining = 0;
    }
    /**
     * Start or Resume the countdown.
     * @return CountDownTimerPausable current instance
     */
    public synchronized final PausableCountDownTimer start(){
        if(isPaused){
            createCountDownTimer();
            countDownTimer.start();
            isPaused = false;
        }
        return this;
    }
    /**
     * Pauses the CountDownTimerPausable, so it could be resumed(start)
     * later from the same point where it was paused.
     */
    public void pause()throws IllegalStateException{
        if(isPaused==false){
            countDownTimer.cancel();
        } else{
            throw new IllegalStateException("CountDownTimerPausable is already in pause state, start counter before pausing it.");
        }
        isPaused = true;
    }
    public boolean isPaused() {
        return isPaused;
    }


    public interface CountDownListener{
        void onTick(long millisUntilFinished);
        void onFinish();
    }
}
