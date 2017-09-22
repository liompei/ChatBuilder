package com.liompei.chatbuilder.main.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.liompei.chatbuilder.R;
import com.liompei.chatbuilder.main.WeChatBuilderBean;
import com.liompei.chatbuilder.util.GlideUtils;
import com.liompei.zxlog.Zx;

import java.util.List;

/**
 * Created by Liompei
 * time : 2017/9/22 15:19
 * 1137694912@qq.com
 * remark:
 */

public class WeChatBuilderAdapter extends RecyclerView.Adapter<WeChatBuilderAdapter.MyHolder> {

    private List<WeChatBuilderBean> mWeChatBuilderBeanList;

    public WeChatBuilderAdapter(List<WeChatBuilderBean> weChatBuilderBeanList) {
        mWeChatBuilderBeanList = weChatBuilderBeanList;
    }

    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_we_chat_list, parent, false));
    }

    @Override
    public void onBindViewHolder(MyHolder holder, int position) {
        View itemView = null;
        if (mWeChatBuilderBeanList.get(position).getWhoType() == 1) {  //我
            Zx.d("我");
            itemView = LayoutInflater.from(holder.container.getContext()).inflate(R.layout.item_we_chat_builder_right, null);
            Zx.d("他");
        } else if (mWeChatBuilderBeanList.get(position).getWhoType() == 2) {  //他
            itemView = LayoutInflater.from(holder.container.getContext()).inflate(R.layout.item_we_chat_builder_left, null);
        } else if (mWeChatBuilderBeanList.get(position).getWhoType() == 3) {  //时间
            itemView = LayoutInflater.from(holder.container.getContext()).inflate(R.layout.item_we_chat_builder_center, null);
        }
        holder.container.addView(itemView);

        //判断赋值
        if (mWeChatBuilderBeanList.get(position).getWhoType() == 1 || mWeChatBuilderBeanList.get(position).getWhoType() == 2) {
            Zx.d("判断赋值");
            ImageView iv_head = itemView.findViewById(R.id.iv_head);
            TextView tv_text = itemView.findViewById(R.id.tv_text);
            GlideUtils.loadHead(iv_head, mWeChatBuilderBeanList.get(position).getFromHeadUri());

            //判断消息类型
            if (mWeChatBuilderBeanList.get(position).getMsgType() == 0) {  //文字
                tv_text.setText(mWeChatBuilderBeanList.get(position).getTextContent());
                Zx.d("设置文字");
            } else if (mWeChatBuilderBeanList.get(position).getMsgType() == 1) {  //图片
                Zx.d("图片");
            } else if (mWeChatBuilderBeanList.get(position).getMsgType() == 2) {  //语音
                Zx.d("语音");
            }

        } else if (mWeChatBuilderBeanList.get(position).getWhoType() == 3) {  //时间

        }

    }

    @Override
    public int getItemCount() {
        return mWeChatBuilderBeanList.size();
    }

    class MyHolder extends RecyclerView.ViewHolder {

        FrameLayout container;

        public MyHolder(View itemView) {
            super(itemView);
            container = itemView.findViewById(R.id.container);

        }
    }

    public void update(List<WeChatBuilderBean> weChatBuilderBeanList) {
        mWeChatBuilderBeanList = weChatBuilderBeanList;
        notifyDataSetChanged();
    }
}
