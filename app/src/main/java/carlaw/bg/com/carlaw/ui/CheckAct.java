package carlaw.bg.com.carlaw.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import com.flyco.tablayout.SegmentTabLayout;
import com.flyco.tablayout.listener.OnTabSelectListener;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import carlaw.bg.com.carlaw.R;
import carlaw.bg.com.carlaw.ui.fragment.PaiWuFragment;
import carlaw.bg.com.carlaw.ui.fragment.WeiQiFragment;

/**
 * Created by pyj on 2016/12/15.
 */

public class CheckAct extends BaseAct {

    @BindView(R.id.tl)
    SegmentTabLayout tl;
    @BindView(R.id.vp)
    ViewPager vp;

    private ArrayList<Fragment> mFragments = new ArrayList<>();
    private String[] mTitles = {"尾气检测", "排污装置"};
    public int initLayoutId() {
        return R.layout.activity_check;
    }

    @Override
    public void initUi() {
        setPageTitle("执法中...");
        mFragments.add(new WeiQiFragment());
        mFragments.add(new PaiWuFragment());
        vp.setAdapter(new MyPagerAdapter(getSupportFragmentManager()));

        tl.setTabData(mTitles);
        tl.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                vp.setCurrentItem(position);
            }
            @Override
            public void onTabReselect(int position) {
            }
        });
        vp.addOnPageChangeListener(new MyPageChangeListener());
        vp.setCurrentItem(0);
    }


    @OnClick(R.id.iv_back)
    public void iv_back() {
        onBackPressed();
    }




    private class MyPageChangeListener implements ViewPager.OnPageChangeListener {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        }

        @Override
        public void onPageSelected(int position) {
            tl.setCurrentTab(position);
        }

        @Override
        public void onPageScrollStateChanged(int state) {
        }
    }

    private class MyPagerAdapter extends FragmentPagerAdapter {
        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mTitles[position];
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }
    }
}
