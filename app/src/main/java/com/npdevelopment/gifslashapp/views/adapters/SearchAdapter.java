package com.npdevelopment.gifslashapp.views.adapters;

import android.content.Context;
import android.content.Intent;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.npdevelopment.gifslashapp.R;
import com.npdevelopment.gifslashapp.models.Giphy;
import com.npdevelopment.gifslashapp.views.ui.DisplayGiphyActivity;
import com.npdevelopment.gifslashapp.views.ui.DisplaySearchActivity;
import com.npdevelopment.gifslashapp.views.ui.MainActivity;

import java.util.List;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.ViewHolder> {

    private List<Giphy> giphyStickerList;
    private Context context;

    public SearchAdapter(Context context, List<Giphy> giphyStickerList) {
        this.giphyStickerList = giphyStickerList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View view = inflater.inflate(R.layout.gif_sticker_cell, viewGroup, false);
        return new SearchAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        // Gets a single item in the list from its position
        final Giphy giphy = giphyStickerList.get(position);

        if (context.getClass().equals(DisplaySearchActivity.class)) {
            Glide.with(context).load(giphy.getImages().getImageFixedHeight().getUrl()).into(viewHolder.gifSticker);
        }

        // On click send object to display Giphy activity
        viewHolder.gifSticker.setOnClickListener(view -> {
            Intent intent = new Intent(context, DisplayGiphyActivity.class);
            intent.putExtra(MainActivity.GIPHY_ITEM_KEY, giphy);
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return giphyStickerList.size();
    }

    /**
     * Method for refreshing the data
     *
     * @param newList the list that has to be refreshed
     */
    public void refreshList(List<Giphy> newList) {
        giphyStickerList = newList;
        if (newList != null) {
            // Force the RecyclerView to refresh
            this.notifyDataSetChanged();
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView gifSticker;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            gifSticker = itemView.findViewById(R.id.gif_sticker_image);
        }
    }
}
