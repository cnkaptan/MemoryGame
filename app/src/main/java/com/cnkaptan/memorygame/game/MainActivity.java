package com.cnkaptan.memorygame.game;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.cnkaptan.memorygame.utils.Level;
import com.cnkaptan.memorygame.MemoryApp;
import com.cnkaptan.memorygame.R;
import com.cnkaptan.memorygame.data.DataManager;
import com.cnkaptan.memorygame.data.SharedPrefsManager;
import com.cnkaptan.memorygame.model.ChecableItem;
import com.cnkaptan.memorygame.model.HighScoreRecord;
import com.cnkaptan.memorygame.scorelist.ScoresActivity;
import com.cnkaptan.memorygame.utils.SpaceItemDecoration;

import java.util.List;

import javax.inject.Inject;

import io.objectbox.BoxStore;

public class MainActivity extends AppCompatActivity implements GameView {
    private static final String LOG_TAG = MainActivity.class.getSimpleName();

    @Inject
    DataManager dataManager;
    @Inject
    BoxStore boxStore;
    @Inject
    SharedPrefsManager sharedPrefsManager;

    private CellAdapter cellAdapter;
    private GameEngine gameEngine;
    private TextView tvCD;
    private FloatingActionButton fabPause;
    private TextView tvPoints;
    private Button btnRestart;
    RecyclerView rvPhotoGridList;
    private AlertDialog nameDialog;
    private int defaultLevel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        rvPhotoGridList = findViewById(R.id.rv_photo_grid_list);
        tvCD = (TextView)findViewById(R.id.countDown);
        fabPause = (FloatingActionButton)findViewById(R.id.fab_pause);
        tvPoints = (TextView)findViewById(R.id.tvPoints);
        btnRestart = (Button)findViewById(R.id.btn_restart);
        ((MemoryApp)getApplicationContext()).getApiComponent().inject(this);

        gameEngine = new GameEngine(dataManager,MainActivity.this);
        askLevel(gameEngine);
        fabPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (gameEngine.isPaused()){
                    fabPause.setImageResource(R.drawable.ic_pause_black_24dp);
                    cellAdapter.setClickable(true);
                    gameEngine.resumeGame();
                }else{
                    fabPause.setImageResource(R.drawable.ic_play_arrow_black_24dp);
                    cellAdapter.setClickable(false);
                    gameEngine.pauseGame();
                }
            }
        });

        btnRestart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                askLevel(gameEngine);
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        gameEngine.detachView();
    }

    @Override
    public void notifyAdapter() {
        if (cellAdapter != null){
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    cellAdapter.notifyDataSetChanged();
                }
            });
        }
    }

    @Override
    public void gameFinished(final int totalPoins) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                cellAdapter.setClickable(false);
                tvCD.setText("Finished Total Points = "+totalPoins);
                saveResultsList(totalPoins);
                fabPause.setVisibility(View.GONE);
            }
        });
    }

    private void saveResultsList(final int totalPoints) {
        final View customDialogView = LayoutInflater.from(this).inflate(R.layout.highscore_save_dialog_layout,null,false);
        final EditText etName = customDialogView.findViewById(R.id.et_name);
        nameDialog = new AlertDialog.Builder(this)
                .setView(customDialogView)
                .setTitle("What is your name!!")
                .setPositiveButton("SAVE", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if (nameDialog != null && nameDialog.isShowing()) {
                            nameDialog.dismiss();
                        }
                        sharedPrefsManager.addHighScore(new HighScoreRecord(totalPoints,etName.getText().toString()));
                        dialogInterface.dismiss();
                    }
                })
                .setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                })
                .setCancelable(false)
                .create();
        nameDialog.show();

    }

    @Override
    public void initPhotoList(List<ChecableItem> photos,Level level) {
        cellAdapter = new CellAdapter(photos, new CellAdapter.ItemCheckedListener() {
            @Override
            public void onItemCheck(ChecableItem node) {
                gameEngine.photoSelected(node);
            }
        });
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(MainActivity.this,level.getCELL_NUMBER());
        rvPhotoGridList.addItemDecoration(new SpaceItemDecoration(12));
        rvPhotoGridList.setAdapter(cellAdapter);
        rvPhotoGridList.setLayoutManager(layoutManager);
        fabPause.setVisibility(View.VISIBLE);
    }

    @Override
    public void updateRemainTime(String time) {
        tvCD.setText(time);
    }

    @Override
    public void updatePoints(int totalPoints) {
        tvPoints.setText(String.valueOf(totalPoints));
    }

    @Override
    protected void onStart() {
        super.onStart();
        try{
            if (gameEngine != null && !gameEngine.isPaused()){
                gameEngine.resumeGame();
            }
        }catch (Exception e){

        }

    }

    @Override
    protected void onStop() {
        super.onStop();
        if (gameEngine != null && !gameEngine.isPaused()){
            gameEngine.pauseGame();
        }
        if (nameDialog != null){
            nameDialog.dismiss();
        }
    }

    public void openLeaderBoard(View view){
        startActivity(new Intent(this,ScoresActivity.class));
    }


    public void askLevel(final GameEngine gameEngine){
        String[] items = {"Very Easy","Easy","Normal","Hard","Very Hard"};
        final Level[] enums = {Level.VERYEASY,Level.EASY,Level.NORMAL,Level.HARD,Level.VERYHARD};
        defaultLevel = 0;
        AlertDialog alertDialog = new AlertDialog.Builder(this)
                .setSingleChoiceItems(items, 0, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        defaultLevel = i;
                    }
                })
                .setPositiveButton("Start", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        gameEngine.start(enums[defaultLevel]);
                    }
                })
                .setCancelable(false)
                .create();
        alertDialog.show();
    }
}
