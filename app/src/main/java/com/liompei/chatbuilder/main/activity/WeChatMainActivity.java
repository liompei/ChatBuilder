package com.liompei.chatbuilder.main.activity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.liompei.chatbuilder.R;
import com.liompei.chatbuilder.base.BaseActivity;
import com.liompei.chatbuilder.listener.OnItemChildClickListener;
import com.liompei.chatbuilder.main.MyType;
import com.liompei.chatbuilder.main.WeChatBuilderBean;
import com.liompei.chatbuilder.main.adapter.WeChatBuilderAdapter;
import com.liompei.chatbuilder.util.EditTextUtils;
import com.liompei.chatbuilder.util.GlideUtils;
import com.liompei.chatbuilder.util.PreferenceUtils;
import com.liompei.chatbuilder.widget.EditUpdateDialog;
import com.liompei.zxlog.Zx;
import com.vondear.rxtools.RxPhotoUtils;
import com.vondear.rxtools.view.dialog.RxDialogChooseImage;
import com.yalantis.ucrop.UCrop;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static com.vondear.rxtools.view.dialog.RxDialogChooseImage.LayoutType.TITLE;

public class WeChatMainActivity extends BaseActivity implements View.OnClickListener, View.OnTouchListener {

    private Toolbar mToolbar;

    private LinearLayout ll_recycler;  //包含recycler
    private RecyclerView mRecyclerView;  //
    private WeChatBuilderAdapter mWeChatBuilderAdapter;
    private List<WeChatBuilderBean> mWeChatBuilderBeanList;

    private LinearLayout ll_now_input;  //点击切换输入角色
    private TextView tv_now_input;  //显示当前角色
    //底部输入
    private EditText et_input;  //输入内容
    private ImageView input_audio;  //语音
    private TextView tv_input_audio;  //按住输入语音
    private ImageView input_smile;  //表情
    private ImageView input_add;  //其他输入
    private TextView input_send;  //文字输入
    private TextView tv_sims_audio;  //模拟录音
    private int mAudioTime;  //录音时长

    private LinearLayout ll_bottom_other;  //底部更多
    private boolean mIsBottomShow = false;

    //更多功能
    private LinearLayout ll_bottom_image;  //发送图片
    private LinearLayout ll_bottom_money;  //发送红包
    private LinearLayout ll_bottom_transfer;  //发送转账

    //点击预览
    private TextView tv_preview;  //预览

    private int mInputUser;  //当前输入者 whoType
    private int mInputState = 0;  //输入状态 0文字 1语音

    private int mPhotoUser = 0;  //记住即将换头像的人 和whoType相同

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
        if ("".equals(PreferenceUtils.getOtherUsername())) {
            mToolbar = getToolbar("点击设置聊天对象", true);
        } else {
            mToolbar = getToolbar(PreferenceUtils.getOtherUsername(), true);
        }

        mToolbar.setOnClickListener(this);
        ll_recycler = findView(R.id.ll_recycler);
        tv_preview = findView(R.id.tv_preview);
        et_input = findView(R.id.et_input);
        mRecyclerView = findView(R.id.recycler);
        ll_now_input = findView(R.id.ll_now_input);
        tv_now_input = findView(R.id.tv_now_input);
        ll_bottom_other = findView(R.id.ll_bottom_other);

        input_audio = findView(R.id.input_audio);
        tv_input_audio = findView(R.id.tv_input_audio);
        input_smile = findView(R.id.input_smile);
        input_add = findView(R.id.input_add);
        input_send = findView(R.id.input_send);
        tv_sims_audio = findView(R.id.tv_sims_audio);

        ll_bottom_image = findView(R.id.ll_bottom_image);
        ll_bottom_money = findView(R.id.ll_bottom_money);
        ll_bottom_transfer = findView(R.id.ll_bottom_transfer);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(mBaseActivity));
        mWeChatBuilderBeanList = new ArrayList<>();
        mWeChatBuilderAdapter = new WeChatBuilderAdapter(mBaseActivity, mWeChatBuilderBeanList);
        mRecyclerView.setAdapter(mWeChatBuilderAdapter);
    }

    @Override
    public void initData() {
        mInputUser = MyType.WHO_TYPE_ME;
        updateInputUser();

        tv_preview.setOnClickListener(this);
        ll_now_input.setOnClickListener(this);
        input_audio.setOnClickListener(this);
        input_smile.setOnClickListener(this);
        input_add.setOnClickListener(this);
        input_send.setOnClickListener(this);
        et_input.setOnClickListener(this);
        ll_bottom_image.setOnClickListener(this);
        ll_bottom_money.setOnClickListener(this);
        ll_bottom_transfer.setOnClickListener(this);
        tv_input_audio.setOnTouchListener(this);
        et_input.addTextChangedListener(mTextWatcher);
    }


    @Override
    public void onEvent() {
        mWeChatBuilderAdapter.setOnItemChildClickListener(
                new OnItemChildClickListener() {
                    @Override
                    public void onItemChildClick(int whoType, View view, final int position) {
                        switch (view.getId()) {
                            case R.id.iv_head:  //头像
                                mPhotoUser = whoType;
                                RxDialogChooseImage dialogChooseImage = new RxDialogChooseImage(mBaseActivity, TITLE);
                                dialogChooseImage.show();
                                break;
                            case R.id.ll_item_layout:  //文字
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

    private void updateInputUser() {
        if (mInputUser == MyType.WHO_TYPE_ME) {
            tv_now_input.setText("(点击切换)当前输入者: 我自己(" + PreferenceUtils.getUsername() + ")");
        } else if (mInputUser == MyType.WHO_TYPE_OTHER) {
            if ("".equals(PreferenceUtils.getOtherUsername())) {
                tv_now_input.setText("(点击切换)当前输入者: 聊天对象(点击顶部设置名称)");
            } else {
                tv_now_input.setText("(点击切换)当前输入者: 聊天对象(" + PreferenceUtils.getOtherUsername() + ")");
            }
        }
    }

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
                            PreferenceUtils.saveOtherUsername(editUpdateDialog.getStringContent());
                            updateInputUser();
                            editUpdateDialog.dismiss();
                        }
                    }
                });
                editUpdateDialog.show();
                break;
            case R.id.ll_now_input:  //切换输入者
                String username = "我自己(" + PreferenceUtils.getUsername() + ")";
                String otherUsername = null;
                if ("".equals(PreferenceUtils.getOtherUsername())) {
                    otherUsername = "聊天对象(点击顶部设置名称)";
                } else {
                    otherUsername = "聊天对象(" + PreferenceUtils.getOtherUsername() + ")";
                }
                new AlertDialog.Builder(mBaseActivity)
                        .setTitle("切换输入者")
                        .setItems(new String[]{username, otherUsername}, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                if (i == 0) {
                                    mInputUser = MyType.WHO_TYPE_ME;
                                } else if (i == 1) {
                                    mInputUser = MyType.WHO_TYPE_OTHER;
                                }
                                updateInputUser();
                            }
                        })
                        .show();
                break;
            //下面是输入框
            case R.id.input_audio:  //音频
                if (mInputState == 0) {
                    //切换为语音输入
                    input_audio.setImageResource(R.drawable.ic_input_keybroad_24dp);
                    mInputState = 1;
                    et_input.setVisibility(View.GONE);
                    tv_input_audio.setVisibility(View.VISIBLE);
                    EditTextUtils.closeKeyboard(this, et_input);
                } else if (mInputState == 1) {
                    //切换为文字
                    input_audio.setImageResource(R.drawable.ic_input_audio_24dp);
                    mInputState = 0;
                    et_input.setVisibility(View.VISIBLE);
                    tv_input_audio.setVisibility(View.GONE);
                    EditTextUtils.openKeyboard(this, et_input);
                }

                break;
            case R.id.et_input:  //输入内容
                if (mIsBottomShow) {
                    ll_bottom_other.setVisibility(View.GONE);
                    mIsBottomShow = false;
                }
                break;
            case R.id.input_smile:  //表情

                break;
            case R.id.input_add:  //其他输入
                if (mIsBottomShow) {
                    ll_bottom_other.setVisibility(View.GONE);
                    mIsBottomShow = false;
                } else {
                    ll_bottom_other.setVisibility(View.VISIBLE);
                    mIsBottomShow = true;
                    EditTextUtils.closeKeyboard(this, et_input);
                }
                break;
            case R.id.ll_bottom_image:  //发送图片
                mPhotoUser = 10;  //图片
                RxDialogChooseImage dialogChooseImage = new RxDialogChooseImage(mBaseActivity, TITLE);
                dialogChooseImage.show();
                break;
            case R.id.ll_bottom_money:  //红包
                WeChatBuilderBean weChatBuilderBeann = new WeChatBuilderBean();
                weChatBuilderBeann.setWhoType(mInputUser);
                weChatBuilderBeann.setMsgType(MyType.MSG_TYPE_MONEY);
                mWeChatBuilderAdapter.addChat(weChatBuilderBeann);
                mRecyclerView.smoothScrollToPosition(mWeChatBuilderAdapter.getItemCount() - 1);
                break;
            case R.id.ll_bottom_transfer:  //转账

                break;
            case R.id.input_send:  //发送
                WeChatBuilderBean weChatBuilderBean = new WeChatBuilderBean();
                weChatBuilderBean.setTextContent(et_input.getText().toString().trim());
                weChatBuilderBean.setMsgType(MyType.MSG_TYPE_TEXT);
                weChatBuilderBean.setWhoType(mInputUser);
                mWeChatBuilderAdapter.addChat(weChatBuilderBean);
                mRecyclerView.smoothScrollToPosition(mWeChatBuilderAdapter.getItemCount() - 1);
                et_input.setText("");
                break;
            case R.id.tv_preview:  //预览

                break;
        }
    }

    Handler mHandler = new Handler();

    Runnable mRunnable = new Runnable() {
        @Override
        public void run() {
            //要做的事情，这里再次调用此Runnable对象，以实现每两秒实现一次的定时器操作
            mAudioTime++;
            mHandler.postDelayed(this, 1000);
        }
    };

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {

        if (view.getId() == R.id.tv_input_audio) {
            Zx.d(motionEvent.getAction());
            if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                //按下
                tv_sims_audio.setVisibility(View.VISIBLE);
                tv_input_audio.setText("松开 结束");
                mHandler.postDelayed(mRunnable, 1000);
            } else if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                //抬起
                tv_sims_audio.setVisibility(View.GONE);
                tv_input_audio.setText("按住 说话");
                //取消添加语音
                mHandler.removeCallbacks(mRunnable);
                //发送语音
                WeChatBuilderBean weChatBuilderBean = new WeChatBuilderBean();
                weChatBuilderBean.setWhoType(mInputUser);
                weChatBuilderBean.setMsgType(MyType.MSG_TYPE_AUDIO);
                weChatBuilderBean.setAudioLength(mAudioTime);
                mWeChatBuilderAdapter.addChat(weChatBuilderBean);
                mRecyclerView.smoothScrollToPosition(mWeChatBuilderAdapter.getItemCount() - 1);
                mAudioTime = 0;
            }
        }
        return true;
    }

    //文字监听
    private TextWatcher mTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void afterTextChanged(Editable editable) {
            if ("".equals(et_input.getText().toString().trim())) {  //若输入内容为空
                if (input_add.getVisibility() == View.GONE) {
                    input_add.setVisibility(View.VISIBLE);
                }
                if (input_send.getVisibility() == View.VISIBLE) {
                    input_send.setVisibility(View.GONE);
                }
            } else {  //输入内容不为空
                if (input_add.getVisibility() == View.VISIBLE) {
                    input_add.setVisibility(View.GONE);
                }
                if (input_send.getVisibility() == View.GONE) {
                    input_send.setVisibility(View.VISIBLE);
                }
            }
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case RxPhotoUtils.GET_IMAGE_FROM_PHONE://选择相册之后的处理
                if (resultCode == RESULT_OK) {
                    Zx.d("选择相册之后的处理");
                    GlideUtils.initUCrop(this, data.getData());  //裁剪图片
                }
                break;
            case RxPhotoUtils.GET_IMAGE_BY_CAMERA://选择照相机之后的处理
                if (resultCode == RESULT_OK) {
                    Zx.d("选择照相机之后的处理");
                    Zx.d(RxPhotoUtils.imageUriFromCamera.toString());
                    GlideUtils.initUCrop(this, RxPhotoUtils.imageUriFromCamera);  //裁剪图片
                }
                break;
            case RxPhotoUtils.CROP_IMAGE://普通裁剪后的处理
                Zx.d("普通裁剪后的处理");
                Zx.d(RxPhotoUtils.cropImageUri);
//                GlideUtils.loadHead(iv_head, RxPhotoUtils.cropImageUri);  //加载图片
                break;
            case UCrop.REQUEST_CROP://UCrop裁剪之后的处理
                if (resultCode == RESULT_OK) {
                    Zx.d("UCrop裁剪之后的处理");
                    //更新头像
                    updatePhoto(UCrop.getOutput(data));
                    //从Uri中加载图片 并将其转化成File文件返回
                    Zx.d(new File(RxPhotoUtils.getImageAbsolutePath(this, UCrop.getOutput(data))).getAbsolutePath());
                } else if (resultCode == UCrop.RESULT_ERROR) {
                    final Throwable cropError = UCrop.getError(data);
                }
                break;
            case UCrop.RESULT_ERROR://UCrop裁剪错误之后的处理
                if (resultCode == RESULT_OK) {
                    Zx.d("UCrop裁剪错误之后的处理");
                    final Throwable cropError = UCrop.getError(data);
                }
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }


    //切换头像
    private void updatePhoto(Uri uri) {
        if (mPhotoUser == MyType.WHO_TYPE_ME) {
            Zx.d("我的头像");
            PreferenceUtils.saveHead(uri.toString());
            mWeChatBuilderAdapter.notifyDataSetChanged();
        } else if (mPhotoUser == MyType.WHO_TYPE_OTHER) {
            Zx.d("对象头像");
            PreferenceUtils.saveOtherHead(uri.toString());
            mWeChatBuilderAdapter.notifyDataSetChanged();
        } else if (mPhotoUser == 10) {
            Zx.d("发送图片");
            //发送语音
            WeChatBuilderBean weChatBuilderBean = new WeChatBuilderBean();
            weChatBuilderBean.setWhoType(mInputUser);
            weChatBuilderBean.setMsgType(MyType.MSG_TYPE_IMG);
            weChatBuilderBean.setImageContent(uri.toString());
            mWeChatBuilderAdapter.addChat(weChatBuilderBean);
            mRecyclerView.smoothScrollToPosition(mWeChatBuilderAdapter.getItemCount() - 1);
        }

    }

}
