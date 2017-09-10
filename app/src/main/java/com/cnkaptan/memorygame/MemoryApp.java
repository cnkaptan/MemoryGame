package com.cnkaptan.memorygame;

import android.app.Application;
import android.content.Context;

import com.cnkaptan.memorygame.di.ApiComponent;
import com.cnkaptan.memorygame.di.ApiModule;
import com.cnkaptan.memorygame.di.AppModule;
import com.cnkaptan.memorygame.di.DaggerApiComponent;

/**
 * Created by cnkaptan on 06/09/17.
 */

public class MemoryApp extends Application {
    private ApiComponent apiComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        apiComponent = DaggerApiComponent.builder()
                .appModule(new AppModule(this))
                .apiModule(new ApiModule())
                .build();
    }

    public ApiComponent getApiComponent() {
        return apiComponent;
    }
}
