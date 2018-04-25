package com.ev.library.bean.emotion;

import android.content.Context;
import android.widget.ImageView;

import com.ev.library.encode.IEncoder;
import com.ev.library.strategy.files.IFileStrategy;
import com.ev.library.view.IInputView;


/**
 * bean for picture emotions
 * Created by EV on 2018/4/23.
 */
public class PicEmotion extends Emotion {

    protected PicEmotion(IFileStrategy pFilestrategy, IEncoder pEncoder) {
        super(pFilestrategy, pEncoder);
    }

    @Override
    public void click(Context pContext, IInputView pInputView) {

    }
}
