package com.npdevelopment.gifslashapp.views.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.npdevelopment.gifslashapp.R;
import com.npdevelopment.gifslashapp.models.Giphy;

import java.util.List;

public class TrendingStickersAdapter extends RecyclerView.Adapter<TrendingStickersAdapter.ViewHolder> {

    private List<Giphy> stickerList;

    public TrendingStickersAdapter(List<Giphy> stickerList) {
        this.stickerList = stickerList;
    }

    @NonNull
    @Override
    public TrendingStickersAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View view = inflater.inflate(R.layout.gif_sticker_cell, viewGroup, false);
        return new TrendingStickersAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TrendingStickersAdapter.ViewHolder viewHolder, int i) {

    }

    @Override
    public int getItemCount() {
        return stickerList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView imageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

        }
    }
}
