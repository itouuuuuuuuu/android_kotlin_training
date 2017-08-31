package com.example.masafumi_ito.pageviewsample;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // idを取得
        ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabLayout);

        // ViewPagerのインスタンスにフラグメントをセット
        viewPager.setAdapter(new MyFragmentStatePagerAdapter(getSupportFragmentManager()));

        // タブとViewPagerを同期
        tabLayout.setupWithViewPager(viewPager);
    }
}
