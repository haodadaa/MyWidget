package com.hao.viewpageranimator.adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hao.viewpageranimator.R;

/**
 * Created by hao on 2017/5/11.
 */

public class PagerFragment extends Fragment {
    public static final String KEY_ITEM = "KEY_ITEM";

    public static PagerFragment newInstance(Context context, PagerItem pagerItem) {
        Bundle arguments = new Bundle();
        arguments.putSerializable(KEY_ITEM, pagerItem);
        PagerFragment pagerFragment = (PagerFragment) Fragment.instantiate(context, PagerFragment.class.getName());
        pagerFragment.setArguments(arguments);
        return pagerFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Bundle arguments = getArguments();
        PagerItem item = (PagerItem) arguments.getSerializable(KEY_ITEM);
        View view = inflater.inflate(R.layout.fragment_pager, container, false);
        TextView textView = (TextView) view.findViewById(R.id.textView);
        textView.setText(item.getTitle());
        return view;
    }
}
