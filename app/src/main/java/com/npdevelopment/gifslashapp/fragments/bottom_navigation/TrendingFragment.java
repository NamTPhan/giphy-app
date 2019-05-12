package com.npdevelopment.gifslashapp.fragments.bottom_navigation;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.npdevelopment.gifslashapp.R;
import com.npdevelopment.gifslashapp.fragments.top_navigation_tabs.TrendingGifFragment;
import com.npdevelopment.gifslashapp.fragments.top_navigation_tabs.TrendingStickerFragment;
import com.npdevelopment.gifslashapp.fragments.top_navigation_tabs.ViewPagerAdapter;


public class TrendingFragment extends Fragment {

    private TabLayout tabLayout;
    private ViewPager viewPager;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        tabLayout = view.findViewById(R.id.tablayout_id);
        viewPager = view.findViewById(R.id.viewpager_id);

        ViewPagerAdapter adapter = new ViewPagerAdapter(getChildFragmentManager());
        adapter.addFragment(new TrendingGifFragment(), getResources().getString(R.string.gif_title));
        adapter.addFragment(new TrendingStickerFragment(), getResources().getString(R.string.sticker_title));

        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_trending, container, false);
    }
}
