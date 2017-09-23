package com.liompei.chatbuilder.main.activity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.liompei.chatbuilder.R;
import com.liompei.chatbuilder.base.BaseActivity;
import com.liompei.chatbuilder.listener.OnItemChildClickListener;
import com.liompei.chatbuilder.main.MyType;
import com.liompei.chatbuilder.main.WeChatBuilderBean;
import com.liompei.chatbuilder.main.adapter.WeChatBuilderAdapter;
import com.liompei.chatbuilder.widget.EditUpdateDialog;

import java.util.ArrayList;
import java.util.List;

public class WeChatMainActivity extends BaseActivity implements View.OnClickListener {

    private Toolbar mToolbar;
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
        mToolbar = getToolbar("点击设置聊天对象", true);
        mToolbar.setOnClickListener(this);
        tv_preview = findView(R.id.tv_preview);
        mRecyclerView = findView(R.id.recycler);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mBaseActivity));
        mWeChatBuilderBeanList = new ArrayList<>();
        mWeChatBuilderAdapter = new WeChatBuilderAdapter(mBaseActivity, mWeChatBuilderBeanList);
        mRecyclerView.setAdapter(mWeChatBuilderAdapter);
    }

    @Override
    public void initData() {
        tv_preview.setOnClickListener(this);
    }

    @Override
    public void onEvent() {
        mWeChatBuilderAdapter.setOnItemChildClickListener(new OnItemChildClickListener() {
            @Override
            public void onItemChildClick(View view, final int position) {
                switch (view.getId()) {
                    case R.id.tv_text:
                        new AlertDialog.Builder(mBaseActivity)
                                .setTitle("提醒")
                                .setMessage("是否删除此条?")
                                .setNegativeButton("否", null)
                                .setPositiveButton("是", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        mWeChatBuilderAdapter.deleteChat(position);
                                    }
                                })
                                .show();
                        break;
                }
            }
        });
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
            case R.id.toolbar:  //点击修改对象名称
                final EditUpdateDialog editUpdateDialog = new EditUpdateDialog(mBaseActivity);
                editUpdateDialog.setTitle("修改聊天对象");
                editUpdateDialog.setEditText("");
                editUpdateDialog.setOnSureListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if ("".equals(editUpdateDialog.getStringContent())) {
                            toast("名字不能为空");
                            return;
                        } else {
                            mToolbar.setTitle(editUpdateDialog.getStringContent());
                            editUpdateDialog.dismiss();
                        }
                    }
                });
                editUpdateDialog.show();
                break;
            case R.id.tv_preview:  //预览
                if (a) {
                    addData(MyType.WHO_TYPE_ME);
                    a = false;
                } else {
                    addData(MyType.WHO_TYPE_OTHER);
                    a = true;
                }
                mRecyclerView.smoothScrollToPosition(mWeChatBuilderAdapter.getItemCount() - 1);
                break;
        }
    }

    int mInt = 0;

    private void addData(int whoType) {
        WeChatBuilderBean weChatBuilderBean = new WeChatBuilderBean();
        weChatBuilderBean.setWhoType(whoType);
        weChatBuilderBean.setMsgType(MyType.MSG_TYPE_TEXT);
        weChatBuilderBean.setTextContent("输入内容: " + mInt);
        weChatBuilderBean.setHeadUri("");
        mWeChatBuilderAdapter.addChat(weChatBuilderBean);
        mInt++;
    }
}
