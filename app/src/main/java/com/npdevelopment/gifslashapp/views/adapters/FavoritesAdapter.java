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
import com.npdevelopment.gifslashapp.models.Favorite;

import java.util.List;

public class FavoritesAdapter extends RecyclerView.Adapter<FavoritesAdapter.ViewHolder> {

    private List<Favorite> favoriteList;
    private Context context;
    public FavoriteListener favoriteListener;

    public FavoritesAdapter(Context context, List<Favorite> favoriteList, FavoriteListener favoriteListener) {
        this.favoriteList = favoriteList;
        this.context = context;
        this.favoriteListener = favoriteListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View view = inflater.inflate(R.layout.favorite_cell, viewGroup, false);
        return new FavoritesAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        // Gets a single item in the list from its position
        final Favorite favorite = favoriteList.get(position);

        Glide.with(context).load(favorite.getImageUrl()).into(viewHolder.favoriteImage);
        viewHolder.favoriteTitle.setText(favorite.getTitle());
        viewHolder.favoriteDescription.setText(favorite.getDescription());
        viewHolder.favoriteDateSaved.setText(favorite.getDateSaved());
    }

    @Override
    public int getItemCount() {
        return favoriteList.size();
    }

    /**
     * Method for refreshing the data
     *
     * @param newList the list that has to be refreshed
     */
    public void refreshList(List<Favorite> newList) {
        favoriteList = newList;
        if (newList != null) {
            // Force the RecyclerView to refresh
            this.notifyDataSetChanged();
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView favoriteTitle, favoriteDescription, favoriteDateSaved;
        private ImageView favoriteImage;
        private CardView favoriteCard;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            favoriteCard = itemView.findViewById(R.id.favorite_card);
            favoriteImage = itemView.findViewById(R.id.favorite_image);
            favoriteTitle = itemView.findViewById(R.id.favorite_title);
            favoriteDescription = itemView.findViewById(R.id.favorite_description);
            favoriteDateSaved = itemView.findViewById(R.id.favorite_date_saved);

            favoriteCard.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    favoriteListener.onCardClick(favoriteList.get(getAdapterPosition()));
                }
            });
        }
    }

    public interface FavoriteListener {
        void onCardClick(Favorite favorite);
    }
}
