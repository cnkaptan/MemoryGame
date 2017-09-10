package com.cnkaptan.memorygame.game;

import android.support.v7.widget.RecyclerView;
import android.util.ArrayMap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.cnkaptan.memorygame.R;
import com.cnkaptan.memorygame.model.ChecableItem;
import com.cnkaptan.memorygame.utils.SquareView;
import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.UUID;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by cnkaptan on 04/09/2017.
 */

public class CellAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final static int DISABLED = 90;
    private final static int ENABLED = 91;

    private static final String TAG = CellAdapter.class.getSimpleName();
    private List<ChecableItem> checableItems;
    private ArrayMap<UUID, ChecableItem> checkedItemsMap;
    private ItemCheckedListener itemCheckedListener;
    private final int selectLimit = 2;
    private boolean clickable = true;

    public CellAdapter(List<ChecableItem> checableItems, ItemCheckedListener itemCheckedListener) {
        this.checableItems = checableItems;
        this.itemCheckedListener = itemCheckedListener;
        checkedItemsMap = new ArrayMap<>();
    }

    @Override
    public int getItemViewType(int position) {
        if (checableItems.get(position).isMatch()){
            return DISABLED;
        }
        return ENABLED;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == ENABLED){
            final View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.cell_item, parent, false);
            return new EnabledHolder(itemView);
        }else{
            final View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.cell_item_disabled, parent, false);
            return new DisabledHolder(itemView);
        }

    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {

        if (holder.getItemViewType() == ENABLED){
            final EnabledHolder enabledHolder = (EnabledHolder)holder;
            String photoUrl = checableItems.get(position).getName();
            Log.e("adapter",photoUrl);
            Picasso.with(((EnabledHolder) holder).item.getContext()).load(photoUrl).into(((EnabledHolder) holder).cellText);
            final ChecableItem ordinary = checableItems.get(position);
            final ChecableItem checkedNode = checkedItemsMap.get(ordinary.getName());
            if (checkedNode != null) {
                enabledHolder.cover.setVisibility(View.GONE);
            } else {
                enabledHolder.cover.setVisibility(View.VISIBLE);
            }

            enabledHolder.item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (clickable) {
                        enabledHolder.cover.setVisibility(View.GONE);
                        final ChecableItem ordinary = checableItems.get(position);
                        itemCheckedListener.onItemCheck(ordinary);
                    }
                }
            });
        }

    }

    public void setClickable(boolean clickable) {
        this.clickable = clickable;
    }

    @Override
    public int getItemCount() {
        return checableItems != null ? checableItems.size() : 0;
    }

    static class EnabledHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.cell_text)
        SquareView cellText;
        @BindView(R.id.cover)
        ImageView cover;

        View item;

        EnabledHolder(View view) {
            super(view);
            item = view;
            ButterKnife.bind(this, view);
        }
    }

    static class DisabledHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.cell_text)
        SquareView cellText;
        View item;

        DisabledHolder(View view) {
            super(view);
            item = view;
            ButterKnife.bind(this, view);
        }
    }


    public interface ItemCheckedListener {
        void onItemCheck(ChecableItem node);
    }

}
