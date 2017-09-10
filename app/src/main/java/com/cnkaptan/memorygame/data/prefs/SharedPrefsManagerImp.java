package com.cnkaptan.memorygame.data;

import android.content.SharedPreferences;
import android.text.TextUtils;

import com.cnkaptan.memorygame.model.HighScoreRecord;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by cnkaptan on 10/09/2017.
 */
//TODO Singleton yap
public class SharedPrefsManagerImp implements SharedPrefsManager {
    private SharedPreferences sharedPreferences;
    private Gson gson;
    private static final String HIGH_SCORE_LIST = "highscores";


    public SharedPrefsManagerImp(SharedPreferences sharedPreferences){
        this.sharedPreferences = sharedPreferences;
        this.gson = new GsonBuilder().create();
    }


    @Override
    public void addHighScore(HighScoreRecord highScoreRecord) {
        List<HighScoreRecord> highScoreRecordList = getOrderedHighScoreList();
        if (highScoreRecordList == null){
            highScoreRecordList = new ArrayList<>();
        }
        highScoreRecordList.add(highScoreRecord);
        Collections.sort(highScoreRecordList);
        sharedPreferences.edit().putString(HIGH_SCORE_LIST,gson.toJson(highScoreRecordList)).apply();
    }

    @Override
    public List<HighScoreRecord> getOrderedHighScoreList() {
        String jsonHighScoreList = sharedPreferences.getString(HIGH_SCORE_LIST,null);
        if (TextUtils.isEmpty(jsonHighScoreList)){
            return new ArrayList<>();
        }else{
            Type type = new TypeToken<List<HighScoreRecord>>(){}.getType();
            return gson.fromJson(jsonHighScoreList,type);
        }
    }
}
