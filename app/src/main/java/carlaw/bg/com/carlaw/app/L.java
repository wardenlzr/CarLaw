package carlaw.bg.com.carlaw.app;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/** 打印日志 转换时间等 工具类
 * Created by yb on 2016/10/31.
 */

public class L {
    public static boolean isDebug = true;// 是否需要打印bug，可以在application的onCreate函数里面初始化
    private static final String TAG = "BG";



    // 下面四个是默认tag的函数
    public static void i(String msg)
    {
        if (isDebug)
            android.util.Log.e(TAG, msg);
    }
    // 下面四个是默认tag的函数
    public static void i(String tag,String msg)
    {
        if (isDebug)
            android.util.Log.e(tag, msg);
    }
    public static void t(Context c, String s){
        Toast.makeText(c,s, Toast.LENGTH_SHORT).show();
    }
    //将textview中的字符全角化
    public static String ToDBC(String src) {
        if (TextUtils.isEmpty(src)) {
            return "";
        }
        StringBuilder buf = new StringBuilder(src.length());
        char[] ca = src.toCharArray();
        for (int i = 0; i < ca.length; i++) {
            if (ca[i] == DBC_SPACE) { // 如果是半角空格，直接用全角空格替代
                buf.append(SBC_SPACE);
            } else if ((ca[i] >= DBC_CHAR_START) && (ca[i] <= DBC_CHAR_END)) { // 字符是!到~之间的可见字符
                buf.append((char) (ca[i] + CONVERT_STEP));
            } else { // 不对空格以及ascii表中其他可见字符之外的字符做任何处理
                buf.append(ca[i]);
            }
        }
        return buf.toString();
        /*char[] c = input.toCharArray();
        for (int i = 0; i< c.length; i++) {
            if (c[i] == 12288) {
                c[i] = (char) 32;
                continue;
            }if (c[i]> 65280&& c[i]< 65375)
                c[i] = (char) (c[i] - 65248);
        }
        return new String(c);*/
    }
    /**
     * 半角空格的值，在ASCII中为32(Decimal)
     */
    static final char DBC_SPACE = ' '; // 半角空格
    /**
     * 全角空格的值，它没有遵从与ASCII的相对偏移，必须单独处理
     */
    static final char SBC_SPACE = 12288; // 全角空格 12288
    /**
     * ASCII表中可见字符从!开始，偏移位值为33(Decimal)
     */
    static final char DBC_CHAR_START = 33; // 半角!

    /**
     * ASCII表中可见字符到~结束，偏移位值为126(Decimal)
     */
    static final char DBC_CHAR_END = 126; // 半角~

    /**
     * 全角对应于ASCII表的可见字符从！开始，偏移值为65281
     */
    static final char SBC_CHAR_START = 65281; // 全角！

    /**
     * 全角对应于ASCII表的可见字符到～结束，偏移值为65374
     */
    static final char SBC_CHAR_END = 65374; // 全角～

    /**
     * ASCII表中除空格外的可见字符与对应的全角字符的相对偏移
     */
    static final int CONVERT_STEP = 65248; // 全角半角转换间隔
    /**
     * 清楚字符串前面的空格
     * @param s
     * @return
     */
    public static String cleanFrist(String s){
        for(int i=0;i<s.length();i++){
            if(s.charAt(i)!=' '){
                s=s.substring(i,s.length());
                break;
            }
        }
        return s;
    }

    /**
     * 隐藏软键盘
     * @param c
     */
    public static void hideKeyBoard(Context c){
        InputMethodManager imm = (InputMethodManager) c.getSystemService(Context.INPUT_METHOD_SERVICE);
        if(imm.isActive())
            imm.hideSoftInputFromWindow(((Activity)c).getWindow().getDecorView().getWindowToken(),0);
    }
    /**
     * 判断是否是今天
     * @return 是true, 否false
     */

    public static boolean isToday(String time)  {
        SimpleDateFormat format =  new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        try {
            Date date = format.parse(time);
            Calendar c1 = Calendar.getInstance();
            c1.setTime(date);
            int year1 = c1.get(Calendar.YEAR);
            int month1 = c1.get(Calendar.MONTH) + 1;
            int day1 = c1.get(Calendar.DAY_OF_MONTH);
            Calendar c2 = Calendar.getInstance();
            c2.setTime(new Date());
            int year2 = c2.get(Calendar.YEAR);
            int month2 = c2.get(Calendar.MONTH) + 1;
            int day2 = c2.get(Calendar.DAY_OF_MONTH);
            if (year1 == year2 && month1 == month2 && day1 == day2) {
                return true;
            }
            return false;
        }catch (ParseException pe){
            L.i("ParseException..."+pe.getMessage());
        }
        return false;
    }

    //两位小数
    public static String decimal2(double d){
        DecimalFormat df = new DecimalFormat("0.00");
        return df.format(d);
    }


    public static String formatChangData(long m){
        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(m);
        String data = formatter.format(calendar.getTime());
        return data;
    }


    /**
     * 判定输入汉字
     * @param c
     * @return
     */
    public static   boolean isChinese(char c) {
        boolean result = false;
        if (c >= 19968 && c <= 171941) {// 汉字范围 \u4e00-\u9fa5 (中文)
            result = true;
        }
        return result;
        /* 中文或者中文字符
        Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
        if (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS
                || ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS
                || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A
                || ub == Character.UnicodeBlock.GENERAL_PUNCTUATION
                || ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION
                || ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS) {
            return true;
        }
        return false;*/
    }

    /**
     * 检测String是否全是中文
     * @param name
     * @return
     */
    public static  boolean checkChinese(String name)
    {
        boolean res = false;
        String regex = "[\u4E00-\u9FA5]+";
        if(name.matches(regex)){
            res=true;
        }
        /*中文符号无法检测
        boolean res=true;
        char [] cTemp = name.toCharArray();
        for(int i=0;i<name.length();i++)
        {
            if(!isChinese(cTemp[i]))
            {
                res=false;
                break;
            }
        }*/
        return res;
    }

}
