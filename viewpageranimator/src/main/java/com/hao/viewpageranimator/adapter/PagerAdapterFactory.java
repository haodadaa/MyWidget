package com.hao.viewpageranimator.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.app.FragmentManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hao on 2017/5/11.
 */

public class PagerAdapterFactory {
    private final Context context;

    public PagerAdapterFactory(Context context) {
        this.context = context;
    }

    public PagerAdapter getPagerAdapter(FragmentManager fragmentManager) {
        List<PagerItem> items = new ArrayList<>();
        items.add(new PagerItem("Item 1", Color.WHITE));
        items.add(new PagerItem("Item 2", Color.GRAY));
        items.add(new PagerItem("Item 3", Color.WHITE));
        return new PagerAdapter(fragmentManager, context, items);
    }
}
