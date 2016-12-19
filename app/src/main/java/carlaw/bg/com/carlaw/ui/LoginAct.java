package carlaw.bg.com.carlaw.ui;

import android.os.SystemClock;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;

import butterknife.BindView;
import butterknife.OnClick;
import carlaw.bg.com.carlaw.R;
import carlaw.bg.com.carlaw.app.L;


/**
 * A login screen that offers login via email/password.
 */
public class LoginAct extends BaseAct {


    @BindView(R.id.actv_user)
    AutoCompleteTextView actvUser;
    @BindView(R.id.bt_user)
    Button btUser;
    @BindView(R.id.et_password)
    EditText etPassword;
    @BindView(R.id.actv_chepai)
    AutoCompleteTextView actvChepai;
    @BindView(R.id.bt_chepai)
    Button btChepai;
    @BindView(R.id.bt_login)
    Button btLogin;
    @BindView(R.id.login_form)
    ScrollView loginForm;

    @Override
    public int initLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    public void initUi() {
        setPageTitle("登 录 界 面");
        setEtFocus(loginForm);
    }

    @OnClick({R.id.bt_user, R.id.bt_chepai, R.id.bt_login})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_user:
                break;
            case R.id.bt_chepai:
                break;
            case R.id.bt_login:
                L.hideKeyBoard(mContext);
                setPd(true, "登录中...");
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        SystemClock.sleep(1100);
                        setPd(false);
                        startAct(MainAct.class);
                    }
                }).start();

                break;
        }
    }

}

