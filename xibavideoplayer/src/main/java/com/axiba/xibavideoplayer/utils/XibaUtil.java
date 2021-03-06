package com.axiba.xibavideoplayer.utils;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.ContextThemeWrapper;
import android.view.WindowManager;

import java.util.Formatter;
import java.util.Locale;

/**
 * Created by xiba on 2016/11/26.
 */
public class XibaUtil {

    /**
     * 将毫秒数转化为字符串格式的时长
     *
     * @param timeMs 目标毫秒数
     * @return 转换后的字符串
     */
    public static String stringForTime(long timeMs) {
        if (timeMs <= 0 || timeMs >= 24 * 60 * 60 * 1000) {
            return "00:00";
        }
        long totalSeconds = timeMs / 1000;
        long seconds = totalSeconds % 60;
        long minutes = (totalSeconds / 60) % 60;
        long hours = totalSeconds / 3600;

        StringBuilder stringBuilder = new StringBuilder();
        Formatter mFormatter = new Formatter(stringBuilder, Locale.getDefault());

        if (hours > 0) {
            return mFormatter.format("%d:%02d:%02d", hours, minutes, seconds).toString();
        } else {
            return mFormatter.format("%02d:%02d", minutes, seconds).toString();
        }
    }


    /**
     * wifi是否连接
     *
     * @param context   上下文
     * @return true 已连接; false wifi未连接
     */
    public static boolean isWifiConnected(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.getType() == connectivityManager.TYPE_WIFI;
    }

    /**
     * Get activity from context object
     *
     * @param context something
     * @return object of Activity or null if it is not Activity
     */
    public static Activity scanForActivity(Context context) {
        if (context == null) return null;

        if (context instanceof Activity) {
            return (Activity) context;
        } else if (context instanceof ContextWrapper) {
            return scanForActivity(((ContextWrapper) context).getBaseContext());
        }

        return null;
    }

    /**
     * Get AppCompatActivity from context
     *
     * @param context   上下文
     * @return AppCompatActivity if it's not null
     */
    public static AppCompatActivity getAppCompActivity(Context context) {
        if (context == null) return null;
        if (context instanceof AppCompatActivity) {
            return (AppCompatActivity) context;
        } else if (context instanceof ContextThemeWrapper) {
            return getAppCompActivity(((ContextThemeWrapper) context).getBaseContext());
        }
        return null;
    }

    public static void hideSupportActionBar(Context context) {

        if (context == null) return;

        if (context instanceof AppCompatActivity) {
            ActionBar ab = ((AppCompatActivity) context).getSupportActionBar();
            if (ab != null) {
                ab.setShowHideAnimationEnabled(false);
                ab.hide();
            }
        } else if(context instanceof Activity){
            android.app.ActionBar ab = ((Activity) context).getActionBar();
            if (ab != null) {
                ab.hide();
            }
        }

        XibaUtil.scanForActivity(context).getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    public static void showSupportActionBar(Context context) {

        if (context == null) return;

        if (context instanceof AppCompatActivity) {
            ActionBar ab = ((AppCompatActivity) context).getSupportActionBar();
            if (ab != null) {
                ab.setShowHideAnimationEnabled(false);
                ab.show();
            }
        } else if(context instanceof Activity){
            android.app.ActionBar ab = ((Activity) context).getActionBar();
            if (ab != null) {
                ab.show();
            }
        }

        XibaUtil.scanForActivity(context).getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

}
