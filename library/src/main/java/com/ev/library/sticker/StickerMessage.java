package com.ev.library.sticker;

import android.graphics.Rect;

import com.ev.library.bean.emotion.Emotion;

import java.util.ArrayList;

/**
 * Created by EV on 2018/4/28.
 */
public class StickerMessage {

    private ArrayList<Sticker> mStickers;

    public ArrayList<Sticker> getmStickers() {
        return mStickers;
    }

    public void setmStickers(ArrayList<Sticker> mStickers) {
        this.mStickers = mStickers;
    }

    public static class Sticker {

        private Emotion mEmotion;

        private float mLeftRate; // left coordinate of sticker in stick layout
        private float mTopRate; // top coordinate of sticker in stick layout

        private float mWidthRate; // increase rate of stick layout's width, 1 means keep original
        private float mHeightRate; // increase rate of stick layout's height, 1 means keep original

        private float mStickerWidthRate;
        private float mStickerHeightRate;

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
    }
}
