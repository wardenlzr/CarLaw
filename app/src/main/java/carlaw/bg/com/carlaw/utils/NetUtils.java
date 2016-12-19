package carlaw.bg.com.carlaw.utils;

import android.content.Context;
import android.text.TextUtils;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import carlaw.bg.com.carlaw.app.L;
import okhttp3.Cache;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/** 网络请求
 * Created by pyj on 2016/12/16.
 */

public class NetUtils {
    //http://221.6.204.6:2000/WebService.asmx/GetInfoByTagNo?driverid=苏AN6666
    private static final String SERVICE = "http://221.6.204.6:2000/WebService.asmx/";

    private Context mContext;
    private OkHttpClient mOkHttpClient;

    public NetUtils(Context context) {
        mContext = context;
        File sdcache = mContext.getExternalCacheDir();
        int cacheSize = 10 * 1024 * 1024;
        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .connectTimeout(15, TimeUnit.SECONDS)
                .writeTimeout(20, TimeUnit.SECONDS)
                .readTimeout(20, TimeUnit.SECONDS)
                .cache(new Cache(sdcache.getAbsoluteFile(), cacheSize));
        mOkHttpClient = builder.build();
    }

    public interface CallBack{
        void ok(String result);
        void fail(String desc);
    }

    public void get(String url, final CallBack callBack){
        Request.Builder requestBuilder = new Request.Builder().url(url);
        //可以省略，默认是GET请求
//        requestBuilder.method("GET",null);
        Request request = requestBuilder.build();
        Call mcall= mOkHttpClient.newCall(request);
        mcall.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                callBack.fail(call.toString());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (null != response.cacheResponse()) {
                    String str = response.cacheResponse().toString();
                    callBack.ok(str);
                    L.i("NetUtils", "cache---" + str);
                } else {
                    response.body().string();
                    String str = response.networkResponse().toString();
                    callBack.ok(str);
                    L.i("NetUtils", "network---" + str);
                }
            }
        });
    }

    public void post(String url, final CallBack callBack){
        RequestBody formBody = new FormBody.Builder()
                .add("size", "10")
                .build();
        Request request = new Request.Builder()
                .url(url)
                .post(formBody)
                .build();
        Call call = mOkHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                callBack.fail(call.toString());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String str = response.body().string();
                callBack.ok(str);
                L.i("NetUtils", str);

            }

        });
    }
    /**
     * 拼接url
     * @param methodName
     * @param params
     * @return
     */
    private static String getUrl(String methodName, HashMap<String, Object> params) {
        String paramStr = getParamStr(params);
        String url = SERVICE + methodName + "?" + paramStr;
        return url;
    }
    /**
     * 拼接url
     * @param methodName
     * @return
     */
    private static String getUrl(String methodName) {
        String url = SERVICE + methodName ;
        return url;
    }
    /**
     * 拼接参数
     *
     * @param params
     * @return
     */
    private static String getParamStr(HashMap<String, Object> params) {
        StringBuilder builder = new StringBuilder();
        if (params != null && params.size() != 0) {
            Set<String> keys = params.keySet();
            for (String key : keys) {
                Object value = params.get(key);
                builder.append("&" + key + "=" + filNUll(value));
            }
        }
        return builder.toString();
    }
    private static String filNUll(Object value) {
        String str = "";
        if (value == null || TextUtils.isEmpty(value.toString())) {
            str = "";
        } else {
            str = value.toString();
        }
        return str;
    }
}
