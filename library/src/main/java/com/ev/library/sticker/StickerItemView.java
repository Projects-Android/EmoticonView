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
    private ArrayList<StickerMessage.Sticker> mStickers = new ArrayList<>();
    private StickerMessage.Sticker mSticker;
    private GifImageView mStickerView;

    public StickerItemView(@NonNull Context context) {
        super(context);

        this.mContext = context;
    }

    /**
     * set content view
     * @param resId
     */
    public void setContentView(@LayoutRes int resId) {
        LayoutInflater.from(mContext).inflate(resId, this);
    }

    @Override
    public void addStickers(ArrayList<StickerMessage.Sticker> stickers) {
        mStickers.clear();
        mStickers.addAll(stickers);
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

    private void addStickers() {
        for (int i = 0; i < mStickers.size(); i ++) {
            mSticker = mStickers.get(i);
            ViewGroup.LayoutParams layoutParams = getLayoutParams();
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

            invalidate();
        }
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);

        if (null != mStickerView) {
            canvas.save();
            canvas.translate(mSticker.getmLeftRate() * canvas.getWidth(), mSticker.getmTopRate() * canvas.getHeight());
            mStickerView.draw(canvas);
            canvas.restore();
        }

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }
}
