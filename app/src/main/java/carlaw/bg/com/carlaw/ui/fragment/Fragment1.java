package carlaw.bg.com.carlaw.ui.fragment;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;
import carlaw.bg.com.carlaw.R;
import carlaw.bg.com.carlaw.app.L;
import carlaw.bg.com.carlaw.ui.ResultAct;
import carlaw.bg.com.carlaw.view.wheelview.LoopView;
import carlaw.bg.com.carlaw.view.wheelview.OnItemSelectedListener;


/**
 * 2016年12月16日09:27:43
 */
public class Fragment1 extends BaseFragment {

    @BindView(R.id.rdb_query_chepai)
    RadioButton rdbQueryChepai;
    @BindView(R.id.rdb_query_chejia)
    RadioButton rdbQueryChejia;
    @BindView(R.id.rdb_query_fadongji)
    RadioButton rdbQueryFadongji;
    @BindView(R.id.txv_query_carinfo)
    TextView txvQueryCarinfo;
    @BindView(R.id.btn_query_city2)
    Button btnQueryCity2;
    @BindView(R.id.edt_uqery_chepai)
    EditText edtUqeryChepai;
    @BindView(R.id.spi_query_paizhao)
    Spinner spiQueryPaizhao;
    @BindView(R.id.ll_rd)
    LinearLayout llRd;
    @BindView(R.id.btn_query)
    Button btnQuery;
    @BindView(R.id.rel_tab_chepaihao)
    RelativeLayout relTabChepaihao;
    @BindView(R.id.ll_query)
    LinearLayout llQuery;
    @BindView(R.id.loopview)
    LoopView loopView;


    @BindView(R.id.fl)
    RelativeLayout fl;

    @Override
    public int initLayoutId() {
        return R.layout.fragment1;
    }

    @Override
    public void initUi(View view) {
        setEtFocus(llQuery);
    }


    @OnClick(R.id.btn_query)
    public void onClick() {
        L.t(mContext, "query...");
        startAct(ResultAct.class);
    }


    @OnClick(R.id.fl)
    public void onClick2() {
        loopView.setVisibility(View.GONE);
        btnQuery.setVisibility(View.VISIBLE);
    }
    @OnClick(R.id.btn_query_city2)
    public void onClick1() {
        btnQuery.setVisibility(View.GONE);
        ArrayList<String> list = new ArrayList<>();
        for (int i = 0; i < 15; i++) {
            list.add("item " + i);
        }
        //设置是否循环播放
        //loopView.setNotLoop();
        //滚动监听
        loopView.setListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(int index) {
                L.t(mContext, "Item " + index);

            }
        });

        //设置原始数据
        loopView.setItems(list);
        //设置初始位置
        loopView.setInitPosition(5);
        //设置字体大小
        loopView.setTextSize(30);
        loopView.setVisibility(View.VISIBLE);
    }
}
