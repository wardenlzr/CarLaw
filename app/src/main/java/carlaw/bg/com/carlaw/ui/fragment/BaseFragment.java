package carlaw.bg.com.carlaw.ui.fragment;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import butterknife.ButterKnife;


public abstract class BaseFragment extends Fragment {
	
	protected Context mContext;
	public ProgressDialog pd;
	private View mRootView;

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		mContext = activity;
	}
	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		mRootView = LayoutInflater.from(mContext).inflate(initLayoutId(), null);
		ButterKnife.bind(this, mRootView);
		if (mRootView != null)
		{
			initUi(mRootView);
		}
		return mRootView;
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);

		pd = new ProgressDialog(mContext);
		pd.setMessage("正在拼命加载中...");
	}


	public abstract int initLayoutId();
	public abstract void initUi(View view);

	/**
	 *让edittext失去焦点
	 * 2016年12月15日16:54:10
	 * @param view
	 */
	public void setEtFocus(View view){
		view.setFocusable(true);
		view.setFocusableInTouchMode(true);
		view.requestFocus();
	}
	/**
	 * 加载图片
	 * 2016年12月15日16:05:42
	 * @param iv
	 * @param path
	 */
	public void loadImg(ImageView iv, String path){
		Glide.with(mContext).load(path).into(iv);
	}

	/**
	 * 设置加载中  进度条
	 * @param flag
	 */
	public void setPd(boolean flag){
		if(flag)
			pd.show();
		else
			pd.cancel();
	}
	public void setPd(boolean flag,String msg){
		if(flag) {
			pd.setMessage(msg);
			pd.show();
		}
		else
			pd.cancel();
		pd.setCanceledOnTouchOutside(false);
	}
	public void setPd(boolean flag,String msg, boolean dismis){
		if(flag) {
			pd.setMessage(msg);
			pd.show();
		}
		else
			pd.cancel();
		pd.setCanceledOnTouchOutside(dismis);
	}

	/**
	 * 不带参数的跳转Activity
	 * 2016年12月15日16:04:39
	 */
	public void startAct(Class clazz){
		startActivity(new Intent(mContext, clazz));
	}
}
