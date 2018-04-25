package com.ev.library.utils;

import android.graphics.Bitmap;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import com.ev.library.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import java.io.IOException;
import java.nio.ByteBuffer;

import pl.droidsonroids.gif.GifDrawable;
import pl.droidsonroids.gif.GifImageView;

/**
 * image loader for emoticon
 * Created by EV on 2018/4/23.
 */
public class EmoticonImageLoader extends ImageLoader {

    private static volatile EmoticonImageLoader instance;

    public static DisplayImageOptions sDisplayImageOptions = new DisplayImageOptions.Builder()
            .cacheInMemory(true)
            .cacheOnDisk(true)
            .build();

    protected EmoticonImageLoader() {
    }

    public static EmoticonImageLoader getInstance() {
        if(instance == null) {
            synchronized(EmoticonImageLoader.class) {
                if(instance == null) {
                    instance = new EmoticonImageLoader();
                }
            }
        }

        return instance;
    }

    public void displayGif(String url, final GifImageView imageView) {
        if (null == imageView) {
            return;
        }
        if (TextUtils.isEmpty(url)) {
            imageView.setImageResource(R.drawable.ic_emotion_default);
            return;
        }

        GifDrawable gifDrawable;
        if (url.startsWith("assets://")) {
            url = url.replace("assets://" , "");
            try {
                gifDrawable = new GifDrawable(imageView.getContext().getAssets(), url);
                imageView.setImageDrawable(gifDrawable);
                return;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        try {
            gifDrawable = new GifDrawable(url);
            imageView.setImageDrawable(gifDrawable);
            return;
        } catch (IOException e) {
            e.printStackTrace();
        }

        imageView.setImageResource(R.drawable.ic_emotion_default);
    }
}
