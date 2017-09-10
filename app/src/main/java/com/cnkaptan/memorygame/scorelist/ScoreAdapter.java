package com.cnkaptan.memorygame.scorelist;

/**
 * Created by cnkaptan on 10/09/2017.
 */

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cnkaptan.memorygame.R;
import com.cnkaptan.memorygame.model.HighScoreRecord;

import java.util.List;

public class ScoreAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private List<HighScoreRecord> scoreRecords;

    public ScoreAdapter(List<HighScoreRecord> scoreRecords) {
        this.scoreRecords = scoreRecords;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout_score_list,parent,false);
        return new ScoreViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final HighScoreRecord scoreRecord = scoreRecords.get(position);
        ScoreViewHolder scoreViewHolder = (ScoreViewHolder) holder;
        scoreViewHolder.tvPosition.setText(String.valueOf(position+1));
        scoreViewHolder.tvName.setText(scoreRecord.getName());
        scoreViewHolder.tvPoints.setText(String.valueOf(scoreRecord.getPoints()));
    }

    @Override
    public int getItemCount() {
        return scoreRecords.size();
    }

    public static class ScoreViewHolder extends RecyclerView.ViewHolder{
        public TextView tvPosition;
        public TextView tvName;
        public TextView tvPoints;
        public ScoreViewHolder(View itemView) {
            super(itemView);
            tvPosition = itemView.findViewById(R.id.tv_item_position);
            tvName = itemView.findViewById(R.id.tv_item_name);
            tvPoints = itemView.findViewById(R.id.tv_item_points);

        }
    }
}