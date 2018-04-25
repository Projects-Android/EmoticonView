package com.ev.library.bean.emotion;

import android.content.Context;
import android.text.TextUtils;

import com.ev.library.EmoticonHandlers;
import com.ev.library.RecentEmotionsManager;
import com.ev.library.bean.group.EmojiGroup;
import com.ev.library.bean.group.Group;
import com.ev.library.bean.group.PicGroup;
import com.ev.library.encode.EmojiEncoder;
import com.ev.library.encode.Encoder;
import com.ev.library.encode.IEncoder;
import com.ev.library.strategy.files.IFileStrategy;
import com.ev.library.utils.ObjectSerializer;
import com.ev.library.view.IInputView;

import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * bean for Emotion
 * </p>
 * Created by EV on 2018/4/23.
 */
public class Emotion implements Serializable {

    private final IFileStrategy mFilestrategy;

    protected IEncoder mEncoder;

    private String mId;

    private String mFileName;

    private String mGroupID;

    private String mGroupDirName;

    private String mExt;

    private String mThumbExt;

    private Map<Locale, String> mLangText = new HashMap<Locale, String>();

    protected Emotion(IFileStrategy pFilestrategy, IEncoder pEncoder) {
        mFilestrategy = pFilestrategy;
        mEncoder = pEncoder;
    }

    /**
     * @return the emotion
     */
    public static Emotion createEmotion(IFileStrategy pFilestrategy) {
        return new Emotion(pFilestrategy, new Encoder());
    }

    /**
     * @param pFilestrategy the p file strategy
     * @return the emotion
     */
    public static Emotion createEmoji(IFileStrategy pFilestrategy) {
        final Emotion emotion = new Emotion(pFilestrategy, new EmojiEncoder());
        return emotion;
    }

    /**
     * @param pFilestrategy the file strategy
     * @param groupClass    the group class
     * @return the emotion
     */
    public static Emotion create(IFileStrategy pFilestrategy, Class<? extends Group> groupClass) {
        final IEncoder encoder = (groupClass.equals(EmojiGroup.class)) ? new EmojiEncoder() : new Encoder();
        boolean isPic = (groupClass.equals(PicGroup.class));
        final Emotion emotion = isPic ? new PicEmotion(pFilestrategy, encoder) : new Emotion(pFilestrategy, encoder);
        return emotion;
    }

    public void setGroupDirName(String pGroupDirName) {
        mGroupDirName = pGroupDirName;
    }

    public void setExt(String pExt) {
        mExt = pExt;
    }

    public String getGroupID() {
        return mGroupID;
    }

    public void setGroupID(String pGroupID) {
        mGroupID = pGroupID;
    }

    public String getId() {
        return mId;
    }

    public void setId(String pId) {
        mId = pId;
    }

    public String getThumbFileName() {
        return mFilestrategy.getImagePath(mGroupDirName, mFileName, mThumbExt);
    }

    public String getFileName() {
        return mFilestrategy.getImagePath(mGroupDirName, mFileName, mExt);
    }

    public long getFileSize(Context pContext) {
        return mFilestrategy.getFileSize(pContext, mGroupDirName, mFileName, mExt);
    }

    public void setFileName(String pFileName) {
        mFileName = pFileName;
    }

    /**
     * encode
     * @return the string
     */
    public String encode() {
        return mEncoder.encode(mGroupID, String.valueOf(mId));
    }

    /**
     * serialize
     */
    public String serialize() throws IOException {
        return ObjectSerializer.serialize(this);
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof Emotion) {
            Emotion e2 = ((Emotion) o);
            if (e2.getGroupID().equals(getGroupID()) && e2.getId().equals(getId())) {
                return true;
            }
        }
        return false;
    }

    public String getThumbExt() {
        return mThumbExt;
    }

    public void setThumbExt(String pThumbExt) {
        mThumbExt = pThumbExt;
    }

    /**
     * click event
     * </p>
     * @param pContext   the p context
     * @param pInputView the p input view
     */
    public void click(Context pContext, IInputView pInputView) {
        EmoticonHandlers.addEmotion(pContext, pInputView, this, (int) pInputView.getTextSize(), (int) pInputView.getTextSize());
        RecentEmotionsManager.getInstance(pContext).push(this);
    }

    /**
     * put text by locale
     * </p>
     * @param lang
     * @param text
     */
    public void putLangText(Locale lang, String text) {
        mLangText.put(lang, text);
    }

    /**
     * get text by locale
     * </p>
     * @param lang
     */
    public String getLangText(Locale lang) {
        String decode = mLangText.get(lang);
        if (TextUtils.isEmpty(decode)) {
            decode = mLangText.get(Locale.ENGLISH);
        }
        return decode;
    }
}
