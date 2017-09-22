package com.liompei.chatbuilder;

import android.app.Activity;
import android.app.Application;

import com.liompei.zxlog.Zx;
import com.vondear.rxtools.RxUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Liompei
 * time : 2017/9/22 9:33
 * 1137694912@qq.com
 * remark:
 */

public class App extends Application{


    //***全局***
    public static App instance;

    public static App getInstance() {
        return instance;
    }

    private List<Activity> activityList = new ArrayList<>();

    // 添加Activity到容器中
    public void addActivity(Activity activity) {
        activityList.add(activity);
    }

    public void deleteActivity(Activity activity) {
        activityList.remove(activity);
    }

    public void finishAllActivity() {
        for (Activity activity : activityList) {
            activity.finish();
        }
        activityList.clear();
    }

    // 退出
    public void exit() {
        for (Activity activity : activityList) {
            activity.finish();
        }
        activityList.clear();
        System.exit(0);
    }
    //***end***

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        initZx();
        RxUtils.init(this);  //RxUtils工具包
    }

    private void initZx() {
        Zx.initLog("blm", true);
        Zx.initToast(getApplicationContext(), true);
    }
}
