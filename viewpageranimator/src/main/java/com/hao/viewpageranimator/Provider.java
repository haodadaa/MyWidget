package com.hao.viewpageranimator;

/**
 * Created by hao on 2017/5/11.
 */

public interface Provider<V> {
    V get(int position);
}
