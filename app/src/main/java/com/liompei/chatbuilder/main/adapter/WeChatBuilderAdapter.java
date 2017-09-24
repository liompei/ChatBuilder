package com.liompei.chatbuilder.main.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.liompei.chatbuilder.R;
import com.liompei.chatbuilder.listener.OnItemChildClickListener;
import com.liompei.chatbuilder.main.MyType;
import com.liompei.chatbuilder.main.WeChatBuilderBean;
import com.liompei.chatbuilder.util.GlideUtils;
import com.liompei.chatbuilder.util.PreferenceUtils;

import java.util.List;

/**
 * Created by Liompei
 * time : 2017/9/22 15:19
 * 1137694912@qq.com
 * remark:
 */

public class WeChatBuilderAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<WeChatBuilderBean> mWeChatBuilderBeanList;
    private Context mContext;
    private LayoutInflater inflater;
    private OnItemChildClickListener mOnItemChildClickListener;

    public void setOnItemChildClickListener(OnItemChildClickListener onItemChildClickListener) {
        mOnItemChildClickListener = onItemChildClickListener;
    }

    public WeChatBuilderAdapter(Context context, List<WeChatBuilderBean> weChatBuilderBeanList) {
        mContext = context;
        mWeChatBuilderBeanList = weChatBuilderBeanList;
        inflater = LayoutInflater.from(mContext);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == MyType.WHO_TYPE_ME) {  //右边
            View view = inflater.inflate(R.layout.item_we_chat_builder_right, parent, false);
            RightHolder rightHolder = new RightHolder(view);
            return rightHolder;
        } else if (viewType == MyType.WHO_TYPE_OTHER) {  //左边
            View view = inflater.inflate(R.layout.item_we_chat_builder_left, parent, false);
            LeftHolder leftHolder = new LeftHolder(view);
            return leftHolder;
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof RightHolder) {  //我发的消息
            final RightHolder rightHolder = (RightHolder) holder;
            GlideUtils.loadHead(rightHolder.mIvHead, PreferenceUtils.getHead());
            rightHolder.mIvHead.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //文字点击事件
                    if (mOnItemChildClickListener != null)
                        mOnItemChildClickListener.onItemChildClick(mWeChatBuilderBeanList.get(position).getWhoType(), rightHolder.mIvHead, position);
                }
            });
            rightHolder.ll_item_layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //文字点击事件
                    if (mOnItemChildClickListener != null)
                        mOnItemChildClickListener.onItemChildClick(mWeChatBuilderBeanList.get(position).getWhoType(), rightHolder.ll_item_layout, position);
                }
            });
            int msgType = mWeChatBuilderBeanList.get(position).getMsgType();
            if (msgType == MyType.MSG_TYPE_TEXT) {  //文字
                rightHolder.mLlTextLayout.setVisibility(View.VISIBLE);
                rightHolder.mIvImg.setVisibility(View.GONE);
                rightHolder.mIvMoney.setVisibility(View.GONE);
                rightHolder.mIvAudio.setVisibility(View.GONE);
                rightHolder.mTvText.setText(mWeChatBuilderBeanList.get(position).getTextContent());
            } else if (msgType == MyType.MSG_TYPE_IMG) {  //图片
                rightHolder.mLlTextLayout.setVisibility(View.GONE);
                rightHolder.mIvImg.setVisibility(View.VISIBLE);
                rightHolder.mIvMoney.setVisibility(View.GONE);
                rightHolder.mIvAudio.setVisibility(View.GONE);
                GlideUtils.loadHead(rightHolder.mIvImg, mWeChatBuilderBeanList.get(position).getImageContent());
            } else if (msgType == MyType.MSG_TYPE_AUDIO) {  //语音
                rightHolder.mLlTextLayout.setVisibility(View.GONE);
                rightHolder.mIvImg.setVisibility(View.GONE);
                rightHolder.mIvMoney.setVisibility(View.GONE);
                rightHolder.mIvAudio.setVisibility(View.VISIBLE);
            } else if (msgType == MyType.MSG_TYPE_MONEY) {  //红包
                rightHolder.mLlTextLayout.setVisibility(View.GONE);
                rightHolder.mIvImg.setVisibility(View.GONE);
                rightHolder.mIvMoney.setVisibility(View.VISIBLE);
                rightHolder.mIvAudio.setVisibility(View.GONE);
            }
        } else if (holder instanceof LeftHolder) {  //别人发的消息
            final LeftHolder leftHolder = (LeftHolder) holder;
            GlideUtils.loadHead(leftHolder.mIvHead, PreferenceUtils.getOtherHead());
            leftHolder.mIvHead.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //文字点击事件
                    if (mOnItemChildClickListener != null)
                        mOnItemChildClickListener.onItemChildClick(mWeChatBuilderBeanList.get(position).getWhoType(), leftHolder.mIvHead, position);
                }
            });
            leftHolder.ll_item_layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //文字点击事件
                    if (mOnItemChildClickListener != null)
                        mOnItemChildClickListener.onItemChildClick(mWeChatBuilderBeanList.get(position).getWhoType(), leftHolder.ll_item_layout, position);
                }
            });
            int msgType = mWeChatBuilderBeanList.get(position).getMsgType();
            if (msgType == MyType.MSG_TYPE_TEXT) {  //文字
                leftHolder.mLlTextLayout.setVisibility(View.VISIBLE);
                leftHolder.mIvImg.setVisibility(View.GONE);
                leftHolder.mIvMoney.setVisibility(View.GONE);
                leftHolder.mIvAudio.setVisibility(View.GONE);
                leftHolder.mTvText.setText(mWeChatBuilderBeanList.get(position).getTextContent());

            } else if (msgType == MyType.MSG_TYPE_IMG) {  //图片
                leftHolder.mLlTextLayout.setVisibility(View.GONE);
                leftHolder.mIvImg.setVisibility(View.VISIBLE);
                leftHolder.mIvMoney.setVisibility(View.GONE);
                leftHolder.mIvAudio.setVisibility(View.GONE);
                GlideUtils.loadHead(leftHolder.mIvImg, mWeChatBuilderBeanList.get(position).getImageContent());
            } else if (msgType == MyType.MSG_TYPE_AUDIO) {  //语音
                leftHolder.mLlTextLayout.setVisibility(View.GONE);
                leftHolder.mIvImg.setVisibility(View.GONE);
                leftHolder.mIvMoney.setVisibility(View.GONE);
                leftHolder.mIvAudio.setVisibility(View.VISIBLE);
            } else if (msgType == MyType.MSG_TYPE_MONEY) {  //红包
                leftHolder.mLlTextLayout.setVisibility(View.GONE);
                leftHolder.mIvImg.setVisibility(View.GONE);
                leftHolder.mIvMoney.setVisibility(View.VISIBLE);
                leftHolder.mIvAudio.setVisibility(View.GONE);
            }

        }
    }

    @Override
    public int getItemCount() {
        return mWeChatBuilderBeanList.size();
    }


    //据此判断是谁发的消息
    @Override
    public int getItemViewType(int position) {
        return mWeChatBuilderBeanList.get(position).getWhoType();
    }

    //添加一条聊天消息
    public void addChat(WeChatBuilderBean weChatBuilderBean) {
        mWeChatBuilderBeanList.add(weChatBuilderBean);
        notifyItemInserted(mWeChatBuilderBeanList.size());
        notifyDataSetChanged();
//        notifyItemRangeChanged(mWeChatBuilderBeanList.size(), getItemCount());
    }

    //删除一条聊天消息
    public void deleteChat(int position) {
        mWeChatBuilderBeanList.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, getItemCount());
    }


    //别人发的消息
    class LeftHolder extends RecyclerView.ViewHolder {
        private ImageView mIvHead;
        private LinearLayout ll_item_layout;
        private LinearLayout mLlTextLayout;
        private TextView mTvText;
        private ImageView mIvAudio;
        private ImageView mIvImg;
        private ImageView mIvMoney;

        public LeftHolder(View itemView) {
            super(itemView);
            mIvHead = itemView.findViewById(R.id.iv_head);
            ll_item_layout = itemView.findViewById(R.id.ll_item_layout);
            mLlTextLayout = itemView.findViewById(R.id.ll_text);
            mTvText = itemView.findViewById(R.id.tv_text);
            mIvAudio = itemView.findViewById(R.id.iv_audio);
            mIvImg = itemView.findViewById(R.id.iv_img);
            mIvMoney = itemView.findViewById(R.id.iv_money);
        }
    }

    //我发的消息
    class RightHolder extends RecyclerView.ViewHolder {

        private ImageView mIvHead;
        private LinearLayout ll_item_layout;
        private LinearLayout mLlTextLayout;
        private TextView mTvText;
        private ImageView mIvAudio;
        private ImageView mIvImg;
        private ImageView mIvMoney;

        public RightHolder(View itemView) {
            super(itemView);
            mIvHead = itemView.findViewById(R.id.iv_head);
            ll_item_layout = itemView.findViewById(R.id.ll_item_layout);
            mLlTextLayout = itemView.findViewById(R.id.ll_text);
            mTvText = itemView.findViewById(R.id.tv_text);
            mIvAudio = itemView.findViewById(R.id.iv_audio);
            mIvImg = itemView.findViewById(R.id.iv_img);
            mIvMoney = itemView.findViewById(R.id.iv_money);
        }
    }


    public void update(List<WeChatBuilderBean> weChatBuilderBeanList) {
        mWeChatBuilderBeanList = weChatBuilderBeanList;
        notifyDataSetChanged();
    }

}
