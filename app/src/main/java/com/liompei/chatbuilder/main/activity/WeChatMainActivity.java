package com.liompei.chatbuilder.main.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.liompei.chatbuilder.R;
import com.liompei.chatbuilder.base.BaseActivity;
import com.liompei.chatbuilder.main.WeChatBuilderBean;
import com.liompei.chatbuilder.main.adapter.WeChatBuilderAdapter;

import java.util.ArrayList;
import java.util.List;

public class WeChatMainActivity extends BaseActivity implements View.OnClickListener {

    private TextView tv_preview;  //预览
    private RecyclerView mRecyclerView;
    private WeChatBuilderAdapter mWeChatBuilderAdapter;
    private List<WeChatBuilderBean> mWeChatBuilderBeanList;

    public static void start(Activity activity) {
        Intent intent = new Intent();
        intent.setClass(activity, WeChatMainActivity.class);
        activity.startActivity(intent);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_we_chat_main;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        getToolbar("aaa", true);
        tv_preview = findView(R.id.tv_preview);
        mRecyclerView = findView(R.id.recycler);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mBaseActivity));
        mWeChatBuilderBeanList = new ArrayList<>();
        mWeChatBuilderAdapter = new WeChatBuilderAdapter(mWeChatBuilderBeanList);
        mRecyclerView.setAdapter(mWeChatBuilderAdapter);
    }

    @Override
    public void initData() {
        tv_preview.setOnClickListener(this);
    }

    @Override
    public void onEvent() {

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    boolean a = false;

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_preview:  //预览
                if (a) {
                    addData(WeChatBuilderBean.WHO_TYPE_ME);
                    a = false;
                } else {
                    addData(WeChatBuilderBean.WHO_TYPE_OTHER);
                    a = true;
                }
                break;
        }
    }

    private void addData(int whoType) {
        WeChatBuilderBean weChatBuilderBean = new WeChatBuilderBean();
        weChatBuilderBean.setWhoType(whoType);
        weChatBuilderBean.setMsgType(WeChatBuilderBean.MSG_TYPE_TEXT);
        weChatBuilderBean.setTextContent("aaaaaa");
        weChatBuilderBean.setFromHeadUri("");
        mWeChatBuilderBeanList.add(weChatBuilderBean);
        mWeChatBuilderAdapter.update(mWeChatBuilderBeanList);
    }
}
