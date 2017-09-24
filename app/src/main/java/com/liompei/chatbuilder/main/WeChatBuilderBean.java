package com.liompei.chatbuilder.main;

/**
 * Created by Liompei
 * time : 2017/9/22 15:31
 * 1137694912@qq.com
 * remark:
 */

public class WeChatBuilderBean {

    private int whoType;  //收发类型 1我自己 2对方  3添加时间
    //当收发类型为1或2时
    private int msgType;  //类型 1文本消息 2图片 3语音
    private String textContent;  //文字内容  (类型为0时可用)
    private String imageContent;  //图片内容  (类型为1时可用)
    private int audioLength;  //语音内容长短  (类型为2时可用)
    //当收发类型为3时
    private String timeContent;  //时间内容  (收发类型为3时可用)


    public int getWhoType() {
        return whoType;
    }

    public void setWhoType(int whoType) {
        this.whoType = whoType;
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

    public int getAudioLength() {
        return audioLength;
    }

    public void setAudioLength(int audioLength) {
        this.audioLength = audioLength;
    }

    public String getTimeContent() {
        return timeContent;
    }

    public void setTimeContent(String timeContent) {
        this.timeContent = timeContent;
    }
}
