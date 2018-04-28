package com.ev.library.sticker;

import android.content.Context;
import android.graphics.PixelFormat;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;

import com.ev.library.R;
import com.ev.library.bean.emotion.Emotion;
import com.ev.library.bean.emotion.PicEmotion;
import com.ev.library.utils.EmoticonImageLoader;

import pl.droidsonroids.gif.GifImageView;

/**
 * util for emotion sticker
 * Created by EV on 2018/4/26.
 */
public class StickerEmotionUtil {

    private Context mContext;
    private PrepareToStickListener mPrepareToStickListener;
    private LayoutInflater mLayoutInflater;

    private WindowManager mWindowManager;
    private WindowManager.LayoutParams mLayoutParams;
    private View mEmotionStickerView;
    private GifImageView mIvEmotion;
    private View mBtnStickerCancel;
    private View mBtnStickerConfirm;
    private Emotion mEmotion;

    private boolean mStickerHandleTouch;

    private float mStartX, mStartY;

    /**
     * public constructor
     *
     * @param context
     */
    public StickerEmotionUtil(Context context, PrepareToStickListener prepareToStickListener) {
        this.mContext = context;
        mPrepareToStickListener = prepareToStickListener;
        this.mStartY = -1;
        this.mStartX = -1;
        this.mStickerHandleTouch = true;
        this.mLayoutInflater = LayoutInflater.from(mContext);
    }

    /**
     * add view contains emotion
     *
     * @param emotionView
     * @param emotion
     */
    public void addEmotionView(View emotionView, Emotion emotion) {
        if (null == emotionView || null == emotion) {
            return;
        }

        // dismiss old sticker if exists
        dismissSticker();

        mEmotion = emotion;
        initStickerView();

        if (mEmotion instanceof PicEmotion) {
            EmoticonImageLoader.getInstance().displayGif(mEmotion.getFileName(), mIvEmotion);
        } else {
            EmoticonImageLoader.getInstance().displayImage(mEmotion.getFileName(), mIvEmotion);
        }

        createPopupLayoutParams(emotionView);

        showSticker();
    }

    /**
     * whether should add new emotion view or not
     *
     * @param emotion
     * @return
     */
    public boolean shouldAddNew(Emotion emotion) {
        return -1 == mStartX || null == mEmotion || !mEmotion.equals(emotion);
    }

    private View.OnTouchListener mOnTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            if (mStickerHandleTouch) {
                switch (event.getActionMasked()) {
                    case MotionEvent.ACTION_DOWN:
                        mStartX = event.getRawX();
                        mStartY = event.getRawY();
                        int[] location = new int[2];
                        mEmotionStickerView.getLocationOnScreen(location);
                        mLayoutParams.x = location[0];
                        mLayoutParams.y = location[1];
                        break;
                    case MotionEvent.ACTION_UP:
                    case MotionEvent.ACTION_CANCEL:
                        reset();
                        break;
                    case MotionEvent.ACTION_MOVE:
                        if (mStartX >= 0) {
                            int disX = (int) (event.getRawX() - mStartX);
                            int disY = (int) (event.getRawY() - mStartY);
                            mLayoutParams.x += disX;
                            mLayoutParams.y += disY;
                            mWindowManager.updateViewLayout(mEmotionStickerView, mLayoutParams);
                        }

                        mStartX = event.getRawX();
                        mStartY = event.getRawY();
                        break;
                }
            }
            return mStickerHandleTouch;
        }
    };

    /**
     * outside touch handler
     * not stickers' own touch handler
     *
     * @param event
     * @see #mOnTouchListener
     */
    public void handleMotionEvent(MotionEvent event) {
        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                showStickerButtons();
                reset();
                break;
            case MotionEvent.ACTION_MOVE:
                if (mStartX >= 0) {
                    int disX = (int) (event.getRawX() - mStartX);
                    int disY = (int) (event.getRawY() - mStartY);
                    mLayoutParams.x += disX;
                    mLayoutParams.y += disY;
                    mWindowManager.updateViewLayout(mEmotionStickerView, mLayoutParams);
                }

                mStartX = event.getRawX();
                mStartY = event.getRawY();
                break;
            default:
                break;
        }
    }

    private View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int id = v.getId();
            if (id == R.id.iv_emotion_sticker_confirm) {
                if (null != mPrepareToStickListener) {
                    if (mPrepareToStickListener.prepareToStick(mIvEmotion, mEmotion)) {
                        mStickerHandleTouch = false;
                        dismissSticker();
                    }
                }
            } else if (id == R.id.iv_emotion_sticker_cancel) {
                dismissSticker();
            }
        }
    };

    /**
     * add sticker view to window
     */
    private void showSticker() {
        if (null == mWindowManager) {
            mWindowManager = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
        }
        mWindowManager.addView(mEmotionStickerView, mLayoutParams);
    }

    /**
     * remove sticker view from window
     */
    private void dismissSticker() {
        if (null != mWindowManager && null != mEmotionStickerView) {
            mWindowManager.removeViewImmediate(mEmotionStickerView);
            mEmotionStickerView = null;
        }
    }

    private void initStickerView() {
        mEmotionStickerView = mLayoutInflater.inflate(R.layout.layout_emotion_sticker, null);
        mIvEmotion = mEmotionStickerView.findViewById(R.id.giv_emotion_sticker);
        mBtnStickerCancel = mEmotionStickerView.findViewById(R.id.iv_emotion_sticker_cancel);
        mBtnStickerConfirm = mEmotionStickerView.findViewById(R.id.iv_emotion_sticker_confirm);
        hideStickerButtons();

        mIvEmotion.setOnTouchListener(mOnTouchListener);
        mBtnStickerCancel.setOnClickListener(mOnClickListener);
        mBtnStickerConfirm.setOnClickListener(mOnClickListener);
    }

    private void hideStickerButtons() {
        if (null != mBtnStickerCancel) {
            mBtnStickerCancel.setVisibility(View.GONE);
        }

        if (null != mBtnStickerConfirm) {
            mBtnStickerConfirm.setVisibility(View.GONE);
        }
    }

    private void showStickerButtons() {
        if (null != mBtnStickerCancel) {
            mBtnStickerCancel.setVisibility(View.VISIBLE);
        }

        if (null != mBtnStickerConfirm) {
            mBtnStickerConfirm.setVisibility(View.VISIBLE);
        }
    }

    private void createPopupLayoutParams(View view) {
        mLayoutParams = new WindowManager.LayoutParams(
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.WRAP_CONTENT,
                0,
                0,
                PixelFormat.TRANSPARENT);
        mLayoutParams.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL
                | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
        mLayoutParams.type = WindowManager.LayoutParams.TYPE_APPLICATION;
        mLayoutParams.gravity = Gravity.LEFT | Gravity.TOP;

        int[] location = new int[2];
        view.getLocationOnScreen(location);
        mLayoutParams.x = location[0];
        mLayoutParams.y = location[1];
    }

    private void reset() {
        mStartX = -1;
        mStartY = -1;
        mStickerHandleTouch = true;
    }
}
