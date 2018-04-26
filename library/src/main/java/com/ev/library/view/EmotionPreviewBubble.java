package com.ev.library.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupWindow;

import com.ev.library.R;
import com.ev.library.bean.emotion.Emotion;
import com.ev.library.bean.emotion.PicEmotion;
import com.ev.library.utils.EmoticonImageLoader;

import pl.droidsonroids.gif.GifImageView;

/**
 * bubble view to show preview emotions
 * Created by EV on 2018/4/26.
 */
public class EmotionPreviewBubble extends PopupWindow {

    private Context mContext;
    private GifImageView mIvPicPreview;
    private ImageView mIvEmojiPreview;

    public EmotionPreviewBubble(Context context) {
        super(context);
        this.mContext = context;

        View view = LayoutInflater.from(mContext).inflate(R.layout.layout_emotion_preview_popup, null);
        mIvPicPreview = view.findViewById(R.id.iv_emotion_pic_preview_popup);
        mIvEmojiPreview = view.findViewById(R.id.iv_emotion_emoji_preview_popup);
        setContentView(view);
        setBackgroundDrawable(new BitmapDrawable(mContext.getResources(), (Bitmap) null));
    }

    public void showEmotionPreview(View parent, Emotion emotion) {
        if (null == parent || null == emotion || TextUtils.isEmpty(emotion.getFileName())) {
            return;
        }

        int height;
        if (emotion instanceof PicEmotion) {
            height = mContext.getResources().getDimensionPixelSize(R.dimen.size_70);
            EmoticonImageLoader.getInstance().displayGif(emotion.getFileName(), mIvPicPreview);
            mIvEmojiPreview.setVisibility(View.GONE);
            mIvPicPreview.setVisibility(View.VISIBLE);
        } else {
            height = mContext.getResources().getDimensionPixelSize(R.dimen.size_40);
            EmoticonImageLoader.getInstance().displayImage(emotion.getFileName(), mIvEmojiPreview);
            mIvEmojiPreview.setVisibility(View.VISIBLE);
            mIvPicPreview.setVisibility(View.GONE);
        }
        showAsDropDown(parent, 0, -(height + parent.getMeasuredHeight()));
    }

}
