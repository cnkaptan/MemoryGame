package com.cnkaptan.memorygame.scorelist;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cnkaptan.memorygame.MemoryApp;
import com.cnkaptan.memorygame.R;
import com.cnkaptan.memorygame.data.SharedPrefsManager;
import com.cnkaptan.memorygame.model.HighScoreRecord;

import java.util.List;

import javax.inject.Inject;

public class ScoresActivity extends AppCompatActivity {
    @Inject
    SharedPrefsManager sharedPrefsManager;
    RecyclerView rvHighScores;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scores);
        ((MemoryApp)getApplicationContext()).getApiComponent().inject(this);

        rvHighScores = (RecyclerView)findViewById(R.id.rv_highScores);
        rvHighScores.setLayoutManager(new LinearLayoutManager(this));
        rvHighScores.setAdapter(new ScoreAdapter(sharedPrefsManager.getOrderedHighScoreList()));

    }
}
