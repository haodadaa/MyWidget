package com.hao.viewpageranimator.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.List;

/**
 * Created by hao on 2017/5/11.
 */

public class PagerAdapter extends FragmentStatePagerAdapter {

    private final Context context;
    private final List<PagerItem> items;

    public PagerAdapter(FragmentManager fm, Context context, List<PagerItem> items) {
        super(fm);
        this.context = context;
        this.items = items;
    }

    @Override
    public Fragment getItem(int position) {
        PagerItem item = items.get(position);
        return PagerFragment.newInstance(context, item);
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        PagerItem item = items.get(position);
        return item.getTitle();
    }

    public int getColor(int position) {
        PagerItem item = items.get(position);
        return item.getColor();
    }
}
