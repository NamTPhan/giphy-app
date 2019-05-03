package com.npdevelopment.gifslashapp.fragments.top_navigation_tabs;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.npdevelopment.gifslashapp.R;

public class TrendingStickerFragment extends Fragment {

    private View view;

    public TrendingStickerFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_trending_stickers, container, false);
        return view;
    }
}
