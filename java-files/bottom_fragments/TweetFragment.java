package com.example.tweetreview_v2.bottom_fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.tweetreview_v2.R;
import com.example.tweetreview_v2.adapter.SectionsPageAdapter;
import com.example.tweetreview_v2.singletons.TweetSet;
import com.example.tweetreview_v2.tweet_media_tabs.TweetTab;
import com.example.tweetreview_v2.tweet_media_tabs.MediaTab;
import com.google.android.material.tabs.TabLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

/**
 * Author: Sufyaan Siddique
 * Handles the two tabs (TweetTab, MediaTab) within this Fragment by setting up a viewPager.
 */

public class TweetFragment extends Fragment {
    private ViewPager viewPager;
    private TabLayout tabLayout;
    private View view;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (TweetSet.getInstance().getMovieName() == null) {
            view = inflater.inflate(R.layout.empty_layout, container, false);
        } else {
            view = inflater.inflate(R.layout.tab_main, container, false);
            viewPager = view.findViewById(R.id.viewpager);
            tabLayout = view.findViewById(R.id.tabs);
        }

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (TweetSet.getInstance().getMovieName() == null) {

        } else {
            setupViewPager(viewPager);

            tabLayout.setupWithViewPager(viewPager);

            final int[] icon = new int[]{
                    R.drawable.ic_image_black_24dp,
                    R.drawable.ic_filter_list_black_24dp
            };

            tabLayout.getTabAt(0).setIcon(icon[1]);
            tabLayout.getTabAt(1).setIcon(icon[0]);
        }
    }

    private void setupViewPager (ViewPager viewPager) {
        SectionsPageAdapter adapter = new SectionsPageAdapter(getChildFragmentManager());

        adapter.addFragment(new TweetTab(), "Tweets");
        adapter.addFragment(new MediaTab(), "Images");

        viewPager.setOffscreenPageLimit(adapter.getCount());
        viewPager.setAdapter(adapter);
    }
}
