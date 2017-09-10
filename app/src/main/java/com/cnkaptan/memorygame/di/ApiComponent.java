package com.cnkaptan.memorygame.di;

import com.cnkaptan.memorygame.game.MainActivity;
import com.cnkaptan.memorygame.scorelist.ScoresActivity;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by cnkaptan on 06/09/17.
 */

@Singleton
@Component(modules = {AppModule.class,ApiModule.class})
public interface ApiComponent {
    void inject(MainActivity mainActivity);
    void inject(ScoresActivity scoresActivity);
}