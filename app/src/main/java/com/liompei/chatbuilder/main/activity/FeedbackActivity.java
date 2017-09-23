package com.liompei.chatbuilder.main.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.liompei.chatbuilder.R;
import com.liompei.chatbuilder.base.BaseActivity;

public class FeedbackActivity extends BaseActivity {

    public static void start(Activity activity){
        Intent intent=new Intent();
        intent.setClass(activity,FeedbackActivity.class);
        activity.startActivity(intent);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_feedback;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        getToolbar("软件反馈", false);
    }

    @Override
    public void initData() {

    }

    @Override
    public void onEvent() {

    }
}
