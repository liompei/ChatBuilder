package com.liompei.chatbuilder.main.activity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.liompei.chatbuilder.R;
import com.liompei.chatbuilder.base.BaseActivity;
import com.liompei.chatbuilder.util.GlideUtils;
import com.liompei.chatbuilder.util.PreferenceUtils;
import com.liompei.chatbuilder.widget.EditUpdateDialog;
import com.liompei.zxlog.Zx;
import com.vondear.rxtools.RxPhotoUtils;
import com.vondear.rxtools.view.dialog.RxDialogChooseImage;
import com.yalantis.ucrop.UCrop;

import java.io.File;

import static com.vondear.rxtools.view.dialog.RxDialogChooseImage.LayoutType.TITLE;

public class WeChatFirstActivity extends BaseActivity implements View.OnClickListener {

    private LinearLayout ll_head;
    private ImageView iv_head;
    private LinearLayout ll_username;
    private TextView tv_username;
    private TextView tv_save;

    private Uri resultUri;

    public static void start(Activity activity) {
        Intent intent = new Intent();
        intent.setClass(activity, WeChatFirstActivity.class);
        activity.startActivity(intent);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_we_chat_first;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        getToolbar("设置",true);
        ll_head = findView(R.id.ll_head);
        iv_head = findView(R.id.iv_head);
        ll_username = findView(R.id.ll_username);
        tv_username = findView(R.id.tv_username);
        tv_save = findView(R.id.tv_save);

        ll_head.setOnClickListener(this);
//        iv_head.setOnClickListener(this);
        ll_username.setOnClickListener(this);
        tv_save.setOnClickListener(this);
    }

    @Override
    public void initData() {
//        Resources r = mBaseActivity.getResources();
//        resultUri = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE + "://"
//                + r.getResourcePackageName(R.drawable.ic_default_head_24dp) + "/"
//                + r.getResourceTypeName(R.drawable.ic_default_head_24dp) + "/"
//                + r.getResourceEntryName(R.drawable.ic_default_head_24dp));
        GlideUtils.loadHead(iv_head, resultUri);
    }

    @Override
    public void onEvent() {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_head:
                RxDialogChooseImage dialogChooseImage = new RxDialogChooseImage(mBaseActivity, TITLE);
                dialogChooseImage.show();
                break;
            case R.id.iv_head:
//                RxImageUtils.showBigImageView(mBaseActivity, resultUri);
                break;
            case R.id.ll_username:
                String username = tv_username.getText().toString().trim();
                final EditUpdateDialog dialogUsername = new EditUpdateDialog(mBaseActivity);
                dialogUsername.setTitle("修改用户名");
                dialogUsername.setEditText(username);
                dialogUsername.setOnSureListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if ("".equals(dialogUsername.getStringContent())) {
                            Zx.show("请输入内容");
                            return;
                        }
                        dialogUsername.dismiss();
                        tv_username.setText(dialogUsername.getStringContent());
                    }
                });
                dialogUsername.show();
                break;
            case R.id.tv_save:
                Zx.d("头像地址: " + resultUri);
                Zx.d("姓名: " + tv_username.getText().toString());
                String mUsername = tv_username.getText().toString().trim();
                if (resultUri == null || "".equals(resultUri)) {
                    toast("请选择头像");
                    return;
                }
                if ("".equals(mUsername)) {
                    toast("未设置昵称");
                    return;
                }
                PreferenceUtils.saveUsername(mUsername);
                PreferenceUtils.saveHead(resultUri.toString());
                PreferenceUtils.isFirstChat(false);
                WeChatMainActivity.start(this);
                finish();
                break;
        }
    }


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
                GlideUtils.loadHead(iv_head, RxPhotoUtils.cropImageUri);  //加载图片
                break;
            case UCrop.REQUEST_CROP://UCrop裁剪之后的处理
                if (resultCode == RESULT_OK) {
                    Zx.d("UCrop裁剪之后的处理");
                    resultUri = UCrop.getOutput(data);
                    GlideUtils.loadHead(iv_head, resultUri);
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
}
