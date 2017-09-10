package com.cnkaptan.memorygame.game;

import android.util.ArrayMap;
import android.util.Log;

import com.cnkaptan.memorygame.utils.Level;
import com.cnkaptan.memorygame.data.DataManager;
import com.cnkaptan.memorygame.model.ChecableItem;
import com.cnkaptan.memorygame.utils.PausableCountDownTimer;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by cnkaptan on 06/09/17.
 */

public class GameEngine implements PausableCountDownTimer.CountDownListener {
    private final static String LOG_TAG = GameEngine.class.getSimpleName();
    private final DataManager dataManager;
    private List<ChecableItem> photos;
    private final ArrayMap<UUID, ChecableItem> checkedPhotosMap;
    private GameView gameView;
    private PausableCountDownTimer countDownTimer;
    private int type;
    private int totalPoints = 0;
    private int timePoint = 0;
    private CompositeSubscription compositeSubscription;

    public GameEngine(DataManager dataManager, GameView gameView) {
        this.dataManager = dataManager;
        this.gameView = gameView;
        checkedPhotosMap = new ArrayMap<>();
        compositeSubscription = new CompositeSubscription();
    }

    public void start(final Level level) {
        timePoint = 100 * level.getMILLISECONDS() / 1000;
        Observable<List<ChecableItem>> observablePhotos = dataManager.getPhotos(level.getCELL_NUMBER());

        compositeSubscription.add(
                observablePhotos.observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Subscriber<List<ChecableItem>>() {
                            @Override
                            public void onCompleted() {

                            }

                            @Override
                            public void onError(Throwable e) {
                                Log.e(LOG_TAG,e.getMessage());
                            }

                            @Override
                            public void onNext(List<ChecableItem> checableItems) {
                                photos = checableItems;
                                gameView.initPhotoList(checableItems,level);
                                if (countDownTimer != null) {
                                    countDownTimer.cancel();
                                }
                                countDownTimer = new PausableCountDownTimer(level.getMILLISECONDS(), 1000, GameEngine.this);
                                countDownTimer.start();
                            }
                        })
        );
    }

    public void photoSelected(ChecableItem checableItem) {
        final ChecableItem checkedNode = checkedPhotosMap.get(checableItem.getName());
        if (!checableItem.equals(checkedNode) && checkedPhotosMap.size() < 2) {
            handleChecked(checableItem, checkedNode);
        }
    }


    private void handleChecked(ChecableItem ordinary, ChecableItem checkedNode) {
        if (checkedNode == null) {
            checkedPhotosMap.put(ordinary.getId(), ordinary);
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    if (checkedPhotosMap.size() == 2) {
                        List<ChecableItem> items = new ArrayList<>(checkedPhotosMap.values());
                        if (items.get(0).getName().equals(items.get(1).getName())) {
                            Log.e("Cell", "equal");
                            items.get(0).setMatch(true);
                            items.get(1).setMatch(true);
                            totalPoints += 400;
                            gameView.updatePoints(totalPoints);
                        } else {
                            Log.e("Cell", "not equal");
                        }
                        checkedPhotosMap.clear();
                        if (isGameFinished()) {
                            Log.e("Cell", "Game Finished");
                            gameFinished();
                        }
                        gameView.notifyAdapter();
                    }
                }
            }).start();
        }
    }

    private boolean isGameFinished() {
        for (ChecableItem item : photos) {
            if (!item.isMatch()) {
                return false;
            }
        }
        return true;
    }

    public void detachView() {
        gameView = null;
        compositeSubscription.clear();
    }

    public void pauseGame() {
        countDownTimer.pause();
    }

    public void resumeGame() {
        countDownTimer.start();
    }

    public boolean isPaused() {
        if (countDownTimer == null) {
            throw new IllegalStateException("The game isnt started yet");
        }
        return countDownTimer.isPaused();
    }

    @Override
    public void onTick(long millisUntilFinished) {
        timePoint -= 10;
        final int minute = (int) (millisUntilFinished / 1000) / 60;
        final int seconds = (int) (millisUntilFinished / 1000) % 60;
        gameView.updateRemainTime(String.format("%d:%d", minute, seconds));
    }

    @Override
    public void onFinish() {
        gameFinished();
    }


    private void gameFinished() {
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
        totalPoints += timePoint;
        gameView.gameFinished(totalPoints);
        totalPoints = 0;
        timePoint = 1800;

    }
}
