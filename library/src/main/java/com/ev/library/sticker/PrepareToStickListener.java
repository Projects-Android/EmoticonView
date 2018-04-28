package com.ev.library.sticker;

import android.view.View;

import com.ev.library.bean.emotion.Emotion;

/**
 * interface to notify sticker layout
 * Created by EV on 2018/4/27.
 */
public interface PrepareToStickListener {

    boolean prepareToStick(View stickerView, Emotion emotion);
}
