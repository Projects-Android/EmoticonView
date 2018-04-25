package com.ev.library.span;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.text.style.DynamicDrawableSpan;

import com.ev.library.bean.emotion.Emotion;
import com.ev.library.utils.EmoticonImageLoader;

import java.lang.ref.WeakReference;

/**
 * DynamicDrawableSpan for emotion
 * Created by EV on 2018/4/23.
 */
public class EmotionSpan extends DynamicDrawableSpan {

    private Context mContext;
    private final Emotion mEmotion;
    private final int mSize;
    private final int mTextSize;
    private int mWidth;
    private int mHeight;
    private Drawable mDrawable;
    private WeakReference<Drawable> mDrawableRef;
    private int mTop;

    public EmotionSpan(Context context, Emotion pEmotion, int pSize, int pTextSize) {
        super(DynamicDrawableSpan.ALIGN_BASELINE);
        mContext = context;
        mEmotion = pEmotion;
        mWidth = mHeight = mSize = pSize + 8;
        mTextSize = pTextSize;
    }

    /**
     * get emotion.
     * </p>
     * @return the emotion
     */
    public Emotion getEmotion() {
        return mEmotion;
    }

    @Override
    public int getSize(Paint paint, CharSequence text, int start, int end, Paint.FontMetricsInt fm) {
        if (fm != null) {
            fm.ascent = -mSize;
            fm.descent = 0;

            fm.top = fm.ascent;
            fm.bottom = 0;
        }

        return mSize;
    }

    @Override
    public Drawable getDrawable() {
        if (mDrawable == null) {
            try {
                final Bitmap bitmap = EmoticonImageLoader.getInstance().loadImageSync(mEmotion.getThumbFileName());
                mHeight = mSize;
                mWidth = mHeight * bitmap.getWidth() / bitmap.getHeight();
                mTop = (mTextSize - mHeight) / 2;
                mDrawable = new BitmapDrawable(bitmap);
                mDrawable.setBounds(0, mTop, mWidth, mTop + mHeight);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return mDrawable;
    }

    @Override
    public void draw(Canvas canvas, CharSequence text, int start, int end, float x, int top, int y, int bottom, Paint paint) {
        Drawable b = getCachedDrawable();
        if (b == null) {
            return;
        }
        canvas.save();

        int transY = bottom - b.getBounds().bottom;
        if (mVerticalAlignment == ALIGN_BASELINE) {
            transY = top + ((bottom - top) / 2) - ((b.getBounds().bottom - b.getBounds().top) / 2) - mTop;
        }

        canvas.translate(x, transY);
        b.draw(canvas);
        canvas.restore();
    }


    private Drawable getCachedDrawable() {
        if (mDrawableRef == null
                || mDrawableRef.get() == null
                || (((BitmapDrawable) mDrawableRef.get()).getBitmap()) == null
                || (((BitmapDrawable) mDrawableRef.get()).getBitmap()).isRecycled()) {
            mDrawable = null;
            mDrawableRef = new WeakReference<>(getDrawable());
        }
        return mDrawableRef.get();
    }
}
