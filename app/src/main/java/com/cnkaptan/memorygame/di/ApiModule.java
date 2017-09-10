package com.cnkaptan.memorygame.di;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.cnkaptan.memorygame.BuildConfig;
import com.cnkaptan.memorygame.FlickerApi;
import com.cnkaptan.memorygame.data.DataManager;
import com.cnkaptan.memorygame.data.DataManagerImpl;
import com.cnkaptan.memorygame.data.SharedPrefsManager;
import com.cnkaptan.memorygame.data.SharedPrefsManagerImp;
import com.cnkaptan.memorygame.data.api.RemoteSource;
import com.cnkaptan.memorygame.data.api.RemouteSourceImpl;
import com.cnkaptan.memorygame.data.database.LocalSource;
import com.cnkaptan.memorygame.data.database.LocalSourceImpl;
import com.cnkaptan.memorygame.model.MyObjectBox;

import java.io.File;
import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.objectbox.BoxStore;
import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by cnkaptan on 06/09/17.
 */

@Module(includes = AppModule.class)
public class ApiModule {
    @Nullable
//    private static final String BASE_URL = BuildConfig.BASE_URL;
    private static final String BASE_URL = "https://api.flickr.com/services/rest/";
    private static final String CACHE_DIR = "HttpResponseCache";
    private static final long CACHE_SIZE = 10 * 1024 * 1024;    // 10 MB
    private static final int CONNECT_TIMEOUT = 15;
    private static final int WRITE_TIMEOUT = 60;
    private static final int TIMEOUT = 60;
    private static final String NORMAL_HTTP_CLIENT = "normalHttpClient";
    private static final String LOG_HTTP_CLIENT = "logHttpClient";

    @Provides
    @Singleton
    String provideBaseUrl() {
        return BASE_URL;
    }

    @Provides
    @Singleton
    OkHttpClient provideLogOkHttpClient(@NonNull Context context) {
        final OkHttpClient.Builder okHttpClientBuilder = new OkHttpClient.Builder();
        if (BuildConfig.DEBUG) {
            final HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            okHttpClientBuilder.addInterceptor(loggingInterceptor);
        }
        okHttpClientBuilder
                .connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(TIMEOUT, TimeUnit.SECONDS);

        final File baseDir = context.getCacheDir();
        if (baseDir != null) {
            final File cacheDir = new File(baseDir, CACHE_DIR);
            okHttpClientBuilder.cache(new Cache(cacheDir, CACHE_SIZE));
        }
        return okHttpClientBuilder.build();
    }

    @Provides
    @Singleton
    @NonNull
    Retrofit provideRetrofit(@NonNull String baseUrl, OkHttpClient okHttpClient) {
        return new Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(okHttpClient)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    @Provides
    @Singleton
    FlickerApi provideApi(Retrofit retrofit){
        return retrofit.create(FlickerApi.class);
    }

    @Provides
    @Singleton
    RemoteSource provideRemote(FlickerApi flickerApi){
        return new RemouteSourceImpl(flickerApi);
    }

    @Provides
    @Singleton
    BoxStore provideBox(Context context){ return MyObjectBox.builder().androidContext(context).build();}

    @Provides
    @Singleton
    LocalSource provideLocalSource(BoxStore boxStore){
        return new LocalSourceImpl(boxStore);
    }

    @Provides
    @Singleton
    DataManager provideDataManager(RemoteSource remoteSource,LocalSource localSource){
        return new DataManagerImpl(remoteSource,localSource);
    }
    @Provides
    @Singleton
    SharedPreferences provideSharedPrefs(Context context){
        return PreferenceManager.getDefaultSharedPreferences(context);
    }

    @Provides
    @Singleton
    SharedPrefsManager provideSharedPrefsManager(SharedPreferences sharedPreferences){
        return new SharedPrefsManagerImp(sharedPreferences);
    }
}