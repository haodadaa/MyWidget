package com.hao.behavior;


import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.hao.behavior.adapter.ContentFragmentAdapter;
import com.hao.behavior.behavior.HeadBehavior;
import com.hao.behavior.fragment.ContentFragment;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private HeadBehavior mHeadBehavior;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTabLayout = (TabLayout) findViewById(R.id.news_view_tab_layout);
        mViewPager = (ViewPager) findViewById(R.id.news_view_content_layout);

        mHeadBehavior = (HeadBehavior) ((CoordinatorLayout.LayoutParams)findViewById(R.id.news_view_header_layout).getLayoutParams()).getBehavior();
        initViewData();

    }

    private void initViewData() {

        List<ContentFragment> fragments = new ArrayList<>();
        for(int i = 0; i < 5; i++) {
            fragments.add(ContentFragment.newInstance(i));
        }
        mTabLayout.setTabMode(TabLayout.MODE_FIXED);

        ContentFragmentAdapter adapter = new ContentFragmentAdapter(fragments, getSupportFragmentManager());
        mViewPager.setAdapter(adapter);
        mTabLayout.setupWithViewPager(mViewPager);
    }

    @Override
    public void onBackPressed() {

        if(mHeadBehavior.isClosed()) {
            mHeadBehavior.openPager();
        } else {
            super.onBackPressed();
        }
    }
}
