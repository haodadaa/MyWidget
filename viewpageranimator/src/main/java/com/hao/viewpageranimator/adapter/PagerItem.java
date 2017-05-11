package com.hao.viewpageranimator.adapter;

import java.io.Serializable;

/**
 * Created by hao on 2017/5/11.
 */

public class PagerItem implements Serializable{
    private final String title;
    private final int color;

    PagerItem(String title, int color) {
        this.title = title;
        this.color = color;
    }

    String getTitle() {
        return title;
    }

    int getColor() {
        return color;
    }
}
