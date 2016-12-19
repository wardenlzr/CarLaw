package carlaw.bg.com.carlaw.ui.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import java.util.ArrayList;

import carlaw.bg.com.carlaw.R;


public class ViewPageFragment extends Fragment implements View.OnClickListener {

	private MyAdapter mAdapter;
	public ViewPager mPager;
	private ArrayList<Fragment> pagerItemList = new ArrayList<Fragment>();
	private ImageButton ib_lately;
	private ImageButton ib_chatlist;
	private ImageButton ib_settings;

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		return inflater.inflate(R.layout.view_pager, null);
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		mPager = (ViewPager) view.findViewById(R.id.pager);
		view.findViewById(R.id.ll_lately).setOnClickListener(this);
		view.findViewById(R.id.ll_chatlist).setOnClickListener(this);
		view.findViewById(R.id.ll_settings).setOnClickListener(this);
		ib_lately = (ImageButton) view.findViewById(R.id.ib_lately);
		ib_chatlist = (ImageButton) view.findViewById(R.id.ib_chatlist);
		ib_settings = (ImageButton) view.findViewById(R.id.ib_settings);
	}

	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);


		Fragment1 page1 = new Fragment1();
		Fragment2 page2 = new Fragment2();
		Fragment3 page3 = new Fragment3();
		pagerItemList.add(page1);
		pagerItemList.add(page2);
		pagerItemList.add(page3);
		FragmentManager fragmentManager = getFragmentManager();

		mAdapter = new MyAdapter(getFragmentManager());
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

	public boolean isFirst() {
		if (mPager.getCurrentItem() == 0)
			return true;
		else
			return false;
	}

	public boolean isEnd() {
		if (mPager.getCurrentItem() == pagerItemList.size() - 1)
			return true;
		else
			return false;
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
