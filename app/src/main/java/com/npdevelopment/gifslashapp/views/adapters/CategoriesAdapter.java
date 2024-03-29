package com.npdevelopment.gifslashapp.views.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.npdevelopment.gifslashapp.R;

import java.util.List;

public class CategoriesAdapter extends RecyclerView.Adapter<CategoriesAdapter.ViewHolder> {

    private List<String> categories;
    private Context context;
    private CategoryListener categoryListener;

    public CategoriesAdapter(Context context, List<String> categories, CategoryListener categoryListener) {
        this.categories = categories;
        this.context = context;
        this.categoryListener = categoryListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View view = inflater.inflate(R.layout.category_cell, viewGroup, false);
        return new CategoriesAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        // Gets a single item in the list from its position
        final String category = categories.get(position);

        viewHolder.categoryName.setText(category);
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView categoryName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            categoryName = itemView.findViewById(R.id.category_text);

            categoryName.setOnClickListener(v -> categoryListener.onCardClick(categories.get(getAdapterPosition())));
        }
    }

    public interface CategoryListener {
        void onCardClick(String category);
    }
}
