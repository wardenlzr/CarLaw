package carlaw.bg.com.carlaw.ui;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageButton;

import org.xutils.ex.DbException;

import java.util.ArrayList;
import java.util.List;

import carlaw.bg.com.carlaw.R;
import carlaw.bg.com.carlaw.app.L;
import carlaw.bg.com.carlaw.db.MyDbUtil;
import carlaw.bg.com.carlaw.db.User;
import carlaw.bg.com.carlaw.ui.fragment.Fragment1;
import carlaw.bg.com.carlaw.ui.fragment.Fragment2;
import carlaw.bg.com.carlaw.ui.fragment.Fragment3;

/**
 * Created by pyj on 2016/12/15.
 */

public class MainAct extends BaseAct implements View.OnClickListener{
    private MyAdapter mAdapter;
    public ViewPager mPager;
    private ArrayList<Fragment> pagerItemList = new ArrayList<Fragment>();
    private ImageButton ib_lately;
    private ImageButton ib_chatlist;
    private ImageButton ib_settings;
    @Override
    public int initLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void initUi() {
        MyDbUtil myDbUtil = MyDbUtil.getInstence();
        try {
//            myDbUtil.dbAdd();
            List<User> users = myDbUtil.dbFind();
            L.t(mContext,"users:"+users.size());
        } catch (DbException e) {
            e.printStackTrace();
        }
        mPager = (ViewPager) findViewById(R.id.pager);
        findViewById(R.id.ll_lately).setOnClickListener(this);
        findViewById(R.id.ll_chatlist).setOnClickListener(this);
        findViewById(R.id.ll_settings).setOnClickListener(this);
        ib_lately = (ImageButton) findViewById(R.id.ib_lately);
        ib_chatlist = (ImageButton) findViewById(R.id.ib_chatlist);
        ib_settings = (ImageButton) findViewById(R.id.ib_settings);
        Fragment1 page1 = new Fragment1();
        Fragment2 page2 = new Fragment2();
        Fragment3 page3 = new Fragment3();
        pagerItemList.add(page1);
        pagerItemList.add(page2);
        pagerItemList.add(page3);

        mAdapter = new MyAdapter(getSupportFragmentManager());
        mPager.setAdapter(mAdapter);
        mPager.setCurrentItem(0);
        mPager.setOffscreenPageLimit(3);
        mPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                if (myPageChangeListener != null)
                    myPageChangeListener.onPageSelected(position);
                if (position == 0) {
                    ib_lately.setImageResource(R.mipmap.tab_weixin_pressed);
                    ib_chatlist.setImageResource(R.mipmap.tab_find_frd_normal);
                    ib_settings.setImageResource(R.mipmap.tab_settings_normal);
                } else if (position == 1) {
                    ib_lately.setImageResource(R.mipmap.tab_weixin_normal);
                    ib_chatlist.setImageResource(R.mipmap.tab_find_frd_pressed);
                    ib_settings.setImageResource(R.mipmap.tab_settings_normal);
                } else if (position == 2) {
                    ib_chatlist.setImageResource(R.mipmap.tab_find_frd_normal);
                    ib_lately.setImageResource(R.mipmap.tab_weixin_normal);
                    ib_settings.setImageResource(R.mipmap.tab_settings_pressed);
                }
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {
            }

            @Override
            public void onPageScrollStateChanged(int position) {
            }
        });
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){

            case R.id.ll_lately:
                mPager.setCurrentItem(0);
                break;
            case R.id.ll_chatlist:
                mPager.setCurrentItem(1);
                break;
            case R.id.ll_settings:
                mPager.setCurrentItem(2);
                break;
        }
    }
    public class MyAdapter extends FragmentPagerAdapter {
        public MyAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getCount() {
            return pagerItemList.size();
        }

        @Override
        public Fragment getItem(int position) {
            Fragment fragment = null;
            if (position < pagerItemList.size() )
                fragment = pagerItemList.get(position);
            else
                fragment = pagerItemList.get(0);
            return fragment;
        }
    }
    private MyPageChangeListener myPageChangeListener;

    public void setMyPageChangeListener(MyPageChangeListener l) {
        myPageChangeListener = l;
    }

    public interface MyPageChangeListener {
        public void onPageSelected(int position);
    }
}
