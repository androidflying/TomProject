package com.hub.welfare.ui;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.hub.welfare.R;
import com.hub.welfare.base.MyActivity;
import com.hub.welfare.base.MyFragment;
import com.hub.welfare.fragment.BooksFragment;
import com.hub.welfare.fragment.MoreFragment;
import com.hub.welfare.fragment.MusicFragment;
import com.hub.welfare.fragment.PhotoFragment;
import com.hub.welfare.fragment.VideoFragment;
import com.tom.baselib.utils.BarUtils;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

public class MainActivity extends MyActivity {


    private ViewPager viewPager;
    private BottomNavigationView navigation;
    private ArrayList<MyFragment> fragments = new ArrayList<>(5);

    @Override
    public boolean isNeedRegister() {
        return false;
    }

    @Override
    public void initData(@NonNull Bundle bundle) {

    }

    @Override
    public int bindLayout() {
        return R.layout.activity_main;
    }

    @Override
    public void initView(Bundle savedInstanceState, View contentView) {
        navigation = findViewById(R.id.navigation);
        viewPager = findViewById(R.id.viewPager);
        navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.navigation_books:
                        viewPager.setCurrentItem(0, false);
                        return true;
                    case R.id.navigation_photo:
                        viewPager.setCurrentItem(1, false);
                        return true;
                    case R.id.navigation_music:
                        viewPager.setCurrentItem(2, false);
                        return true;
                    case R.id.navigation_video:
                        viewPager.setCurrentItem(3, false);
                        return true;
                    case R.id.navigation_more:
                        viewPager.setCurrentItem(4, false);
                        return true;
                    default:
                        return false;
                }
            }
        });
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                navigation.getMenu().getItem(position).setChecked(true);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
        fragments.add(new BooksFragment());
        fragments.add(new PhotoFragment());
        fragments.add(new MusicFragment());
        fragments.add(new VideoFragment());
        fragments.add(new MoreFragment());
        FragmentPagerAdapter adapter = new FragmentPagerAdapter(getSupportFragmentManager()) {

            @Override
            public int getCount() {
                return fragments.size();
            }

            @Override
            public Fragment getItem(int position) {
                return fragments.get(position);
            }

        };
        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(fragments.size());

    }

    @Override
    public void doBusiness() {

    }

    @Override
    public void onWidgetClick(View view) {

    }
}
