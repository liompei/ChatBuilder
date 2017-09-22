package com.liompei.chatbuilder.main;

/**
 * Created by Liompei
 * time : 2017/9/22 15:31
 * 1137694912@qq.com
 * remark:
 */

public class WeChatBuilderBean {

    public static final int WHO_TYPE_ME = 1;
    public static final int WHO_TYPE_OTHER = 2;
    public static final int WHO_TYPE_TIME = 3;

    public static final int MSG_TYPE_TEXT = 0;
    public static final int MSG_TYPE_IMG = 1;
    public static final int MSG_TYPE_AUDIO = 2;

    private int whoType;  //收发类型 1我自己 2对方  3添加时间
    //当收发类型为1或2时
    private String fromHeadUri="";  //发送者头像
    private int msgType;  //类型 0文本消息 1图片 2语音
    private String textContent;  //文字内容  (类型为0时可用)
    private String imageContent;  //图片内容  (类型为1时可用)
    private String audioLength;  //语音内容长短  (类型为2时可用)
    //当收发类型为3时
    private String timeContent;  //时间内容  (收发类型为3时可用)


    public int getWhoType() {
        return whoType;
    }

    public void setWhoType(int whoType) {
        this.whoType = whoType;
    }

    public String getFromHeadUri() {
        return fromHeadUri;
    }

    public void setFromHeadUri(String fromHeadUri) {
        this.fromHeadUri = fromHeadUri;
    }

    public int getMsgType() {
        return msgType;
    }

    public void setMsgType(int msgType) {
        this.msgType = msgType;
    }

    public String getTextContent() {
        return textContent;
    }

    public void setTextContent(String textContent) {
        this.textContent = textContent;
    }

    public String getImageContent() {
        return imageContent;
    }

    public void setImageContent(String imageContent) {
        this.imageContent = imageContent;
    }

    public String getAudioLength() {
        return audioLength;
    }

    public void setAudioLength(String audioLength) {
        this.audioLength = audioLength;
    }

    public String getTimeContent() {
        return timeContent;
    }

    public void setTimeContent(String timeContent) {
        this.timeContent = timeContent;
    }
}