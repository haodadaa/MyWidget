package com.hao.radiobuttonrecyclerview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.hao.radiobuttonrecyclerview.bean.ShopBean;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView mRV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mRV = (RecyclerView) findViewById(R.id.rv);
        mRV.setLayoutManager(new LinearLayoutManager(this));
        //mRv.setLayoutManager(mStaggeredGridLayoutManager = new StaggeredGridLayoutManager(4, StaggeredGridLayoutManager.VERTICAL));
        mRV.setAdapter(new RecyclerAdapter(initDatas(), this, mRV));
    }

    private List<ShopBean> initDatas() {
        List<ShopBean> datas = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            datas.add(new ShopBean("111"));
            datas.add(new ShopBean("222", i == 0 ? true : false));
            datas.add(new ShopBean("333"));
            datas.add(new ShopBean("444"));
            datas.add(new ShopBean("555"));
            datas.add(new ShopBean("666"));
            datas.add(new ShopBean("777"));
            datas.add(new ShopBean("888"));
            datas.add(new ShopBean("999"));
            datas.add(new ShopBean("000"));
        }
        return datas;
    }
}
