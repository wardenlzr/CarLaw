package carlaw.bg.com.carlaw.ui;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;

import java.lang.reflect.Field;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import carlaw.bg.com.carlaw.R;
import carlaw.bg.com.carlaw.app.L;
import carlaw.bg.com.carlaw.bean.TabEntity;
import carlaw.bg.com.carlaw.ui.fragment.WeiQiFragment;

/**
 * Created by pyj on 2016/12/15.
 */

public class ResultAct extends BaseAct {

    @BindView(R.id.iv_start)
    ImageView ivStart;
    @BindView(R.id.vp_2)
    ViewPager mViewPager;
    @BindView(R.id.tl_2)
    CommonTabLayout mTabLayout_2;

    private ArrayList<Fragment> mFragments = new ArrayList<>();
    private String[] mTitles = {"车辆信息", "车辆信息", "车辆信息", "更多"};
    private int[] mIconUnselectIds = {
            R.drawable.img_cheliang_bg, R.drawable.img_cheliang_bg,
            R.drawable.img_cheliang_bg, R.drawable.img_cheliang_bg};
    private int[] mIconSelectIds = {
            R.drawable.img_cheliang_bg, R.drawable.img_cheliang_bg,
            R.drawable.img_cheliang_bg, R.drawable.img_cheliang_bg};
    private ArrayList<CustomTabEntity> mTabEntities = new ArrayList<>();

    public int initLayoutId() {
        return R.layout.activity_result;
    }

    @Override
    public void initUi() {
        setPageTitle("查询结果");
        ivStart.setVisibility(View.VISIBLE);
        mFragments.add(new WeiQiFragment());
        for (int i = 0; i < mTitles.length; i++) {
            mTabEntities.add(new TabEntity(mTitles[i], mIconSelectIds[i], mIconUnselectIds[i]));
        }
        mViewPager.setAdapter(new MyPagerAdapter(getSupportFragmentManager()));
        tl_2();
    }


    @OnClick(R.id.iv_back)
    public void iv_back() {
        onBackPressed();
    }


    @OnClick(R.id.iv_start)
    public void iv_start() {
        L.t(mContext, "start...");
        View view = LayoutInflater.from(mContext).inflate(R.layout.view_users_msg, null);
        final EditText et_name = (EditText) view.findViewById(R.id.et_name);
        final EditText et_nub = (EditText) view.findViewById(R.id.et_nub);
        final AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        et_name.setText("梁朝伟");
        builder.setTitle("请输入下列信息:").setView(view).setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                try {
                    Field field = dialog.getClass().getSuperclass().getDeclaredField("mShowing");
                    field.setAccessible(true);
                    field.set(dialog, true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                String name = et_name.getText().toString();
                String nub = et_nub.getText().toString();
                if (TextUtils.isEmpty(name)) {
                    L.t(mContext, "姓名不能为空");
                    try {
                        Field field = dialog.getClass().getSuperclass().getDeclaredField("mShowing");
                        field.setAccessible(true);
                        field.set(dialog, false);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else if (!TextUtils.isEmpty(name)) {
                    //TOD 存到数据库

                    startAct(CheckAct.class);
                    try {
                        Field field = dialog.getClass().getSuperclass().getDeclaredField("mShowing");
                        field.setAccessible(true);
                        field.set(dialog, true);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        builder.show();
    }
    private void tl_2() {
        mTabLayout_2.setTabData(mTabEntities);
        mTabLayout_2.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                mViewPager.setCurrentItem(position);
            }

            @Override
            public void onTabReselect(int position) {
                /*if (position == 0) {
                    mTabLayout_2.showMsg(0, mRandom.nextInt(100) + 1);
//                    UnreadMsgUtils.show(mTabLayout_2.getMsgView(0), mRandom.nextInt(100) + 1);
                }*/
            }
        });

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                mTabLayout_2.setCurrentTab(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        mViewPager.setCurrentItem(1);
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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
