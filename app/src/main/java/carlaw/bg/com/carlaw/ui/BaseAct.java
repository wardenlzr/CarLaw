package carlaw.bg.com.carlaw.ui;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import butterknife.ButterKnife;
import carlaw.bg.com.carlaw.R;
import carlaw.bg.com.carlaw.app.L;
import carlaw.bg.com.carlaw.view.swipeback.SwipeWindowHelper;

/**
 * Created by pyj on 2016/12/15.
 */

public abstract class BaseAct extends FragmentActivity {

    public Context mContext;
    public TextView mTitleView;
    public ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (initLayoutId() != -1) {
            setContentView(initLayoutId());
        }
    }

    @Override
    public void setContentView(int resId) {
        super.setContentView(resId);
        mContext = this;
        ButterKnife.bind(this);
        if (pd == null)
            pd = new ProgressDialog(mContext);
        pd.setMessage("正在拼命加载中...");
        initUi();

    }

    public abstract int initLayoutId();

    public abstract void initUi();

    private SwipeWindowHelper mSwipeWindowHelper;

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if(!supportSlideBack()) {
            return super.dispatchTouchEvent(ev);
        }

        if(mSwipeWindowHelper == null) {
            mSwipeWindowHelper = new SwipeWindowHelper(getWindow());
        }
        return mSwipeWindowHelper.processTouchEvent(ev) || super.dispatchTouchEvent(ev);
    }

    /**
     * 是否支持滑动返回
     *
     * @return
     */
    protected boolean supportSlideBack() {
        return true;
    }
    /**
     * 设置页面标题栏
     *
     * @param title
     */
    public void setPageTitle(String title) {
        mTitleView = (TextView) findViewById(R.id.tv_title);
        if (null != mTitleView) {
            mTitleView.setText(title);
        } else L.t(mContext, "mTitleView is null");

    }


    /**
     * 让edittext失去焦点
     * 2016年12月15日16:54:10
     *
     * @param view
     */
    public void setEtFocus(View view) {
        view.setFocusable(true);
        view.setFocusableInTouchMode(true);
        view.requestFocus();
    }

    /**
     * 加载图片
     * 2016年12月15日16:05:42
     *
     * @param iv
     * @param path
     */
    public void loadImg(ImageView iv, String path) {
        Glide.with(mContext).load(path).into(iv);
    }

    public void loadImg(ImageView iv, int path) {
        Glide.with(mContext).load(path).into(iv);
    }

    /**
     * 设置加载中  进度条
     *
     * @param flag
     */
    public void setPd(boolean flag) {
        if (flag)
            pd.show();
        else
            pd.cancel();
    }

    public void setPd(boolean flag, String msg) {
        if (flag) {
            pd.setMessage(msg);
            pd.show();
        } else
            pd.cancel();
        pd.setCanceledOnTouchOutside(false);
    }

    public void setPd(boolean flag, String msg, boolean dismis) {
        if (flag) {
            pd.setMessage(msg);
            pd.show();
        } else
            pd.cancel();
        pd.setCanceledOnTouchOutside(dismis);
    }

    /**
     * 不带参数的跳转Activity
     * 2016年12月15日16:04:39
     */
    public void startAct(Class clazz) {
        startActivity(new Intent(mContext, clazz));
    }


}
