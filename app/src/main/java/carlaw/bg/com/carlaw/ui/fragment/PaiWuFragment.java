package carlaw.bg.com.carlaw.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import carlaw.bg.com.carlaw.R;


/**
 * 2016年12月16日09:27:43
 */
public class PaiWuFragment extends BaseFragment {

    @BindView(R.id.bt_cancel)
    Button btCancel;
    @BindView(R.id.ll_weiqi)
    LinearLayout llWeiqi;

    @Override
    public int initLayoutId() {
        return R.layout.fragment_weiqi;
    }

    @Override
    public void initUi(View view) {
        llWeiqi.setVisibility(View.GONE);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.bind(this, rootView);
        return rootView;
    }
}
