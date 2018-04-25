package com.ev.emoticonview.bean;

/**
 * Created by EV on 2018/4/25.
 */
public class Message {

    public static final int TYPE_MESSAGE_CONTENT_TEXT = 0x01;
    public static final int TYPE_MESSAGE_CONTENT_IMG = 0x02;

    public static final int TYPE_MESSAGE_SEND = 0x10;
    public static final int TYPE_MESSAGE_RECEIVE = 0x20;

    private int mMsgId;

    private int mType;

    private String mContent;

    private int mSourceType;

    public int getmMsgId() {
        return mMsgId;
    }

    public void setmMsgId(int mMsgId) {
        this.mMsgId = mMsgId;
    }

    public int getmType() {
        return mType;
    }

    public void setmType(int mType) {
        this.mType = mType;
    }

    public String getmContent() {
        return mContent;
    }

    public void setmContent(String mContent) {
        this.mContent = mContent;
    }

    public int getmSourceType() {
        return mSourceType;
    }

    public void setmSourceType(int mSourceType) {
        this.mSourceType = mSourceType;
    }
}
