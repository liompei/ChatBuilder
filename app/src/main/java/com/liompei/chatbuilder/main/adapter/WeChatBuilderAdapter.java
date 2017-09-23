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
            LeftHolder leftHolder = new LeftHolder(view);
            return leftHolder;
        } else if (viewType == MyType.WHO_TYPE_OTHER) {  //左边
            View view = inflater.inflate(R.layout.item_we_chat_builder_left, parent, false);
            RightHolder rightHolder = new RightHolder(view);
            return rightHolder;
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof RightHolder) {  //我发的消息
            final RightHolder rightHolder = (RightHolder) holder;
            GlideUtils.loadHead(rightHolder.mIvHead, mWeChatBuilderBeanList.get(position).getHeadUri());
            int msgType = mWeChatBuilderBeanList.get(position).getMsgType();
            if (msgType == MyType.MSG_TYPE_TEXT) {  //文字
                rightHolder.mLlTextLayout.setVisibility(View.VISIBLE);
                rightHolder.mTvText.setText(mWeChatBuilderBeanList.get(position).getTextContent());
                rightHolder.mTvText.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //文字点击事件
                        if (mOnItemChildClickListener != null)
                            mOnItemChildClickListener.onItemChildClick(rightHolder.mTvText, position);
                    }
                });
            } else if (msgType == MyType.MSG_TYPE_IMG) {  //图片

            } else if (msgType == MyType.MSG_TYPE_AUDIO) {  //语音

            }
        } else if (holder instanceof LeftHolder) {  //别人发的消息
            final LeftHolder leftHolder = (LeftHolder) holder;
            GlideUtils.loadHead(leftHolder.mIvHead, mWeChatBuilderBeanList.get(position).getHeadUri());
            int msgType = mWeChatBuilderBeanList.get(position).getMsgType();
            if (msgType == MyType.MSG_TYPE_TEXT) {  //文字
                leftHolder.mLlTextLayout.setVisibility(View.VISIBLE);
                leftHolder.mTvText.setText(mWeChatBuilderBeanList.get(position).getTextContent());
                leftHolder.mTvText.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //文字点击事件
                        if (mOnItemChildClickListener != null)
                            mOnItemChildClickListener.onItemChildClick(leftHolder.mTvText, position);
                    }
                });
            } else if (msgType == MyType.MSG_TYPE_IMG) {  //图片

            } else if (msgType == MyType.MSG_TYPE_AUDIO) {  //语音

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
//        notifyDataSetChanged();
        notifyItemRangeChanged(mWeChatBuilderBeanList.size(), getItemCount());
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

        private LinearLayout mLlTextLayout;
        private TextView mTvText;

        public LeftHolder(View itemView) {
            super(itemView);
            mIvHead = itemView.findViewById(R.id.iv_head);
            mLlTextLayout = itemView.findViewById(R.id.ll_text);
            mTvText = itemView.findViewById(R.id.tv_text);
        }
    }

    //我发的消息
    class RightHolder extends RecyclerView.ViewHolder {

        private ImageView mIvHead;

        private LinearLayout mLlTextLayout;
        private TextView mTvText;

        public RightHolder(View itemView) {
            super(itemView);
            mIvHead = itemView.findViewById(R.id.iv_head);
            mLlTextLayout = itemView.findViewById(R.id.ll_text);
            mTvText = itemView.findViewById(R.id.tv_text);
        }
    }


    public void update(List<WeChatBuilderBean> weChatBuilderBeanList) {
        mWeChatBuilderBeanList = weChatBuilderBeanList;
        notifyDataSetChanged();
    }

}
