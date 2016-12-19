package carlaw.bg.com.carlaw.app;

import android.app.Application;

import org.xutils.x;

import carlaw.bg.com.carlaw.db.MyDbUtil;

/**
 * Created by pyj on 2016/12/15.
 */

public class App extends Application {
    private ActivityLifecycleHelper mActivityLifecycleHelper;
    @Override
    public void onCreate() {
        super.onCreate();
        CrashHandler.getInstance().init(getApplicationContext());
        registerActivityLifecycleCallbacks(mActivityLifecycleHelper = new ActivityLifecycleHelper());

        x.Ext.init(this);
        x.Ext.setDebug(true);

        MyDbUtil.getInstence().init("bg.db");

    }
    public ActivityLifecycleHelper getActivityLifecycleHelper() {
        return mActivityLifecycleHelper;
    }

   /* public void onSlideBack(boolean isReset, float distance) {
        if(mActivityLifecycleHelper != null) {
            Activity lastActivity = mActivityLifecycleHelper.getPreActivity();
            if(lastActivity != null) {
                View contentView = lastActivity.findViewById(android.R.id.content);
                if(isReset) {
                    contentView.setX(contentView.getLeft());
                } else {
                    final int width = getResources().getDisplayMetrics().widthPixels;
                    contentView.setX(-width / 3 + distance / 3);
                }
            }
        }
    }*/
}
