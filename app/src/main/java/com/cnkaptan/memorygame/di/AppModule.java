package com.cnkaptan.memorygame.di;

import android.content.Context;
import android.support.annotation.NonNull;

import dagger.Module;
import dagger.Provides;

/**
 * Created by cnkaptan on 06/09/17.
 */
@Module
public class AppModule {
    @NonNull
    private final Context mContext;

    public AppModule(@NonNull Context mContext) {
        this.mContext = mContext;
    }

    @Provides
    Context provideContext(){
        return this.mContext;
    }
}
