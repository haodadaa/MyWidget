package com.hao.behavior.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.hao.behavior.fragment.ContentFragment;

import java.util.List;

/**
 * Created by hao on 2017/3/10.
 */

public class ContentFragmentAdapter extends FragmentPagerAdapter {
    List<ContentFragment> mFragments;


    public ContentFragmentAdapter(List<ContentFragment> fragments, FragmentManager fm) {
        super(fm);
        this.mFragments = fragments;
    }

    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }

    @Override
    public int getCount() {
        return mFragments == null ? 0 : mFragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {

        return mFragments.get(position).getName();
    }
}
