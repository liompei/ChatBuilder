package com.liompei.chatbuilder.base;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.liompei.chatbuilder.App;
import com.liompei.chatbuilder.R;


/**
 * Created by Liompei
 * Time 2017/8/8 21:36
 * 1137694912@qq.com
 * remark:
 */

public abstract class BaseActivity extends AppCompatActivity {

    protected BaseActivity mBaseActivity;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        App.getInstance().addActivity(this);
        mBaseActivity = this;
        setContentView(getLayoutId());
        initViews(savedInstanceState);
        initData();
        onEvent();
    }

    protected <T extends View> T findView(int resId) {
        return (T) (findViewById(resId));
    }

    public abstract int getLayoutId();

    public abstract void initViews(Bundle savedInstanceState);

    public abstract void initData();

    public abstract void onEvent();



    @Override
    protected void onDestroy() {
        dismissProgress();
        App.getInstance().deleteActivity(this);
        super.onDestroy();
    }


    private ProgressDialog mProgressDialog = null;

    protected void showProgress() {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(this);
            mProgressDialog.setCanceledOnTouchOutside(false);
            mProgressDialog.setCancelable(false);
            mProgressDialog.setMessage("请稍后...");
        }
        if (!mProgressDialog.isShowing()) {
            mProgressDialog.show();
        }
    }

    protected void setOnProgressCancelListener(@Nullable DialogInterface.OnCancelListener listener) {
        mProgressDialog.setOnCancelListener(listener);
    }

    protected void hideProgress() {
        if (mProgressDialog != null) {
            mProgressDialog.hide();
        }

    }

    protected void dismissProgress() {
        if (mProgressDialog != null) {
            mProgressDialog.dismiss();
        }
    }

    protected Toolbar getToolbar(CharSequence title, boolean showHomeAsUp) {
        Toolbar toolbar = findView(R.id.toolbar);
        toolbar.setTitle(title);
        toolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(showHomeAsUp);
        return toolbar;
    }

    protected void toast(final String s) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(App.getInstance(), s, Toast.LENGTH_SHORT).show();
            }
        });
    }

}
