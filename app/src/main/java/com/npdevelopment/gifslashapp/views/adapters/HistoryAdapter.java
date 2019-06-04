package com.npdevelopment.gifslashapp.views.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.npdevelopment.gifslashapp.R;
import com.npdevelopment.gifslashapp.models.History;

import java.util.List;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.ViewHolder> {

    private List<History> historyList;
    private Context context;
    public HistoryCardListener historyCardListener;

    public HistoryAdapter(Context context, List<History> historyList, HistoryCardListener historyCardListener) {
        this.historyList = historyList;
        this.context = context;
        this.historyCardListener = historyCardListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View view = inflater.inflate(R.layout.giphy_card_cell, viewGroup, false);
        return new HistoryAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        // Gets a single item in the list from its position
        final History historyGifSticker = historyList.get(position);

        Glide.with(context).load(historyGifSticker.getImageUrl()).into(viewHolder.historyGiphyImage);
        viewHolder.historyGiphyTitle.setText(historyGifSticker.getTitle());
        viewHolder.historyGiphyDescription.setText(historyGifSticker.getDescription());
        viewHolder.historyGiphyDateSaved.setText(historyGifSticker.getDateSaved());
    }

    @Override
    public int getItemCount() {
        return historyList.size();
    }

    /**
     * Method for refreshing the data
     *
     * @param newList the list that has to be refreshed
     */
    public void refreshList(List<History> newList) {
        historyList = newList;
        if (newList != null) {
            // Force the RecyclerView to refresh
            this.notifyDataSetChanged();
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView historyGiphyTitle, historyGiphyDescription, historyGiphyDateSaved;
        private ImageView historyGiphyImage;
        private CardView historyGiphyCard;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            historyGiphyCard = itemView.findViewById(R.id.cell_card);
            historyGiphyImage = itemView.findViewById(R.id.card_cell_image);
            historyGiphyTitle = itemView.findViewById(R.id.card_cell_title);
            historyGiphyDescription = itemView.findViewById(R.id.card_cell_description);
            historyGiphyDateSaved = itemView.findViewById(R.id.card_cell_date_saved);

            historyGiphyCard.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    historyCardListener.onCardClick(historyList.get(getAdapterPosition()));
                }
            });
        }
    }

    public interface HistoryCardListener {
        void onCardClick(History historyGifSticker);
    }
}
