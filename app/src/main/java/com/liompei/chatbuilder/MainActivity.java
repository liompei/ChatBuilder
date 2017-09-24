package com.liompei.chatbuilder;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.liompei.chatbuilder.base.BaseActivity;
import com.liompei.chatbuilder.main.activity.FeedbackActivity;
import com.liompei.chatbuilder.main.activity.WeChatFirstActivity;
import com.liompei.chatbuilder.main.activity.WeChatMainActivity;
import com.liompei.chatbuilder.util.PreferenceUtils;

public class MainActivity extends BaseActivity implements View.OnClickListener {

    private TextView tv_weChat;
    private TextView tv_feedback;

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        getToolbar("聊天生成器", false);
        tv_weChat = findView(R.id.tv_weChat);
        tv_feedback = findView(R.id.tv_feedback);
    }

    @Override
    public void initData() {

    }

    @Override
    public void onEvent() {
        tv_weChat.setOnClickListener(this);
        tv_feedback.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_weChat:  //判断是否为第一次使用
                if (PreferenceUtils.getIsFirstChat()) {
                    //需要设置头像和昵称
                    WeChatFirstActivity.start(this);
                } else {
                    WeChatMainActivity.start(this);
                }

                break;
            case R.id.tv_feedback:  //反馈
                FeedbackActivity.start(this);
                break;
        }
    }
}
