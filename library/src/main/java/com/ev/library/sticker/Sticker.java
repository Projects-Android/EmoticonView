package com.ev.library.sticker;

import android.graphics.Rect;

import com.ev.library.bean.emotion.Emotion;

/**
 * class for sticker
 * Created by EV on 2018/4/29.
 */
public class Sticker {

    private Emotion mEmotion;

    private float mLeftRate; // left coordinate of sticker in stick layout
    private float mTopRate; // top coordinate of sticker in stick layout

    private float mWidthRate; // increase rate of stick layout's width, 1 means keep original
    private float mHeightRate; // increase rate of stick layout's height, 1 means keep original

    private float mStickerWidthRate;
    private float mStickerHeightRate;

    private Rect mCurrentParent;

    public Sticker(Emotion emotion, Rect parent, Rect sticker) {
        this.mEmotion = emotion;

        Rect oldParent = new Rect();
        oldParent.set(parent);

        parent.union(sticker);
        this.mLeftRate = (float) (sticker.left - parent.left) / (float) parent.width();
        this.mTopRate = (float) (sticker.top - parent.top) / (float) parent.height();
        this.mWidthRate = (float) parent.width() / (float) oldParent.width();
        this.mHeightRate = (float) parent.height() / (float) oldParent.height();
        this.mStickerWidthRate = (float) sticker.width() / (float) parent.width();
        this.mStickerHeightRate = (float) sticker.height() / (float) parent.height();
        this.mCurrentParent = parent;
    }

    /**
     * update if parent rect increase
     * @param newParent
     */
    public void update(Rect newParent) {
        float widthUpdateRate = (float) mCurrentParent.width() / (float) newParent.width();
        float heightUpdateRate = (float) mCurrentParent.height() / (float) newParent.height();
        this.mLeftRate *= widthUpdateRate;
        this.mTopRate *= heightUpdateRate;
        this.mWidthRate /= widthUpdateRate;
        this.mHeightRate /= heightUpdateRate;
        this.mStickerWidthRate *= widthUpdateRate;
        this.mStickerHeightRate *= heightUpdateRate;
        this.mCurrentParent = newParent;
    }

    public Emotion getmEmotion() {
        return mEmotion;
    }

    public void setmEmotion(Emotion mEmotion) {
        this.mEmotion = mEmotion;
    }

    public float getmLeftRate() {
        return mLeftRate;
    }

    public void setmLeftRate(float mLeftRate) {
        this.mLeftRate = mLeftRate;
    }

    public float getmTopRate() {
        return mTopRate;
    }

    public void setmTopRate(float mTopRate) {
        this.mTopRate = mTopRate;
    }

    public float getmWidthRate() {
        return mWidthRate;
    }

    public void setmWidthRate(float mWidthRate) {
        this.mWidthRate = mWidthRate;
    }

    public float getmHeightRate() {
        return mHeightRate;
    }

    public void setmHeightRate(float mHeightRate) {
        this.mHeightRate = mHeightRate;
    }

    public float getmStickerWidthRate() {
        return mStickerWidthRate;
    }

    public void setmStickerWidthRate(float mStickerWidthRate) {
        this.mStickerWidthRate = mStickerWidthRate;
    }

    public float getmStickerHeightRate() {
        return mStickerHeightRate;
    }

    public void setmStickerHeightRate(float mStickerHeightRate) {
        this.mStickerHeightRate = mStickerHeightRate;
    }

    public Rect getmCurrentParent() {
        return mCurrentParent;
    }

    public void setmCurrentParent(Rect mCurrentParent) {
        this.mCurrentParent = mCurrentParent;
    }
}