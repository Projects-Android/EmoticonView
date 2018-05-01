package com.ev.library.sticker;

import android.content.Context;
import android.graphics.Canvas;
import android.os.Build;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;

import com.ev.library.bean.emotion.PicEmotion;
import com.ev.library.utils.EmoticonImageLoader;

import java.util.ArrayList;

import pl.droidsonroids.gif.GifImageView;

/**
 * Created by EV on 2018/4/28.
 */

public class StickerItemView extends FrameLayout implements IStickerItem {

    private Context mContext;
    private ArrayList<Sticker> mStickers = new ArrayList<>();
    private Sticker mSticker;
    private GifImageView mStickerView;

    private boolean mResetMode = false;

    public StickerItemView(@NonNull Context context) {
        super(context);

        this.mContext = context;
        this.mResetMode = false;
    }

    /**
     * set content view
     * @param resId
     */
    public void setContentView(@LayoutRes int resId) {
        LayoutInflater.from(mContext).inflate(resId, this);
    }

    @Override
    public void addStickers(ArrayList<Sticker> stickers) {
        mStickers.clear();
        mStickers.addAll(stickers);
        addStickers();
        getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                addStickers();

                if (Build.VERSION.SDK_INT >= 16) {
                    getViewTreeObserver().removeOnGlobalLayoutListener(this);
                } else {
                    getViewTreeObserver().removeGlobalOnLayoutListener(this);
                }
            }
        });
    }

    @Override
    public void reset() {
        mResetMode = true;
        invalidate();
    }

    private void addStickers() {
        for (int i = 0; i < mStickers.size(); i ++) {
            mSticker = mStickers.get(i);
            ViewGroup.LayoutParams layoutParams = getLayoutParams();
            if (null == layoutParams) {
                return;
            }
            layoutParams.width = (int) (getMeasuredWidth() * mSticker.getmWidthRate());
            layoutParams.height = (int) (getMeasuredHeight() * mSticker.getmHeightRate());
            setLayoutParams(layoutParams);

            mStickerView = new GifImageView(mContext);
            mStickerView.layout(
                    0,
                    0,
                    (int) (mSticker.getmStickerWidthRate() * layoutParams.width),
                    (int) (mSticker.getmStickerHeightRate() * layoutParams.height));
            if (mSticker.getmEmotion() instanceof PicEmotion) {
                EmoticonImageLoader.getInstance().displayGif(mSticker.getmEmotion().getFileName(), mStickerView);
            } else {
                EmoticonImageLoader.getInstance().displayImage(mSticker.getmEmotion().getFileName(), mStickerView);
            }
            int widthMode = MeasureSpec.getMode(layoutParams.width);
            int widthSize = MeasureSpec.getSize(layoutParams.width);
            int widthMeasureSpec = MeasureSpec.makeMeasureSpec(widthSize, widthMode);
            int heightMode = MeasureSpec.getMode(layoutParams.height);
            int heightSize = MeasureSpec.getSize(layoutParams.height);
            int heightMeasureSpec = MeasureSpec.makeMeasureSpec(heightSize, heightMode);
            measure(widthMeasureSpec, heightMeasureSpec);

            mResetMode = false;
            invalidate();
        }
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);

        if (null != mStickerView && !mResetMode) {
            canvas.save();
            canvas.translate(mSticker.getmLeftRate() * canvas.getWidth(), mSticker.getmTopRate() * canvas.getHeight());
            mStickerView.draw(canvas);
            canvas.restore();
        }

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (null != mStickerView && !mResetMode) {
            canvas.save();
            canvas.translate(mSticker.getmLeftRate() * canvas.getWidth(), mSticker.getmTopRate() * canvas.getHeight());
            mStickerView.draw(canvas);
            canvas.restore();
        }
    }
}
