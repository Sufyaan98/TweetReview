package com.example.tweetreview_v2.adapter;

import java.util.ArrayList;
import java.util.List;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

/**
 * Author: Sufyaan Siddique
 * Class is used to set up Fragments for the different Tabs.
 */

public class SectionsPageAdapter extends FragmentPagerAdapter {
    private final List<Fragment> mFragmentlist = new ArrayList<>();
    private final List<String> mFragmentTitleList = new ArrayList<>();

    public void addFragment(Fragment fragment, String title) {
        mFragmentlist.add(fragment);
        mFragmentTitleList.add(title);
    }
    @Deprecated
    public SectionsPageAdapter(FragmentManager fm) {
        super(fm);
    }
    @Override
    public CharSequence getPageTitle(int position) {
        return mFragmentTitleList.get(position);
    }
    @Override
    public Fragment getItem (int position) {
        return mFragmentlist.get(position);
    }
    @Override
    public int getCount() {
        return mFragmentlist.size();
    }
}
