package com.npdevelopment.gifslashapp.views.fragments.bottom_navigation;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.npdevelopment.gifslashapp.R;
import com.npdevelopment.gifslashapp.views.fragments.top_navigation_tabs.TrendingGifFragment;
import com.npdevelopment.gifslashapp.views.fragments.top_navigation_tabs.TrendingStickerFragment;
import com.npdevelopment.gifslashapp.views.fragments.top_navigation_tabs.ViewPagerAdapter;
import com.npdevelopment.gifslashapp.views.ui.RandomGiphyHistoryActivity;


public class TrendingFragment extends Fragment {

    private TabLayout tabLayout;
    private ViewPager viewPager;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

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

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // Inflate the menu; this adds items to the action bar if it is present.
        inflater.inflate(R.menu.menu_history, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        // Delete all games
        if (id == R.id.action_history) {
            Intent intent = new Intent(getContext(), RandomGiphyHistoryActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
