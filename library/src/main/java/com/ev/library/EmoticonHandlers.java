package com.ev.library;

import android.content.Context;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.util.Pair;

import com.ev.library.bean.emotion.Emotion;
import com.ev.library.decode.IDecoder;
import com.ev.library.span.EmotionSpan;
import com.ev.library.view.IInputView;

import java.util.ArrayList;

/**
 * handler for rich text
 * Created by EV on 2018/4/23.
 */
public class EmoticonHandlers {

    /**
     * add emotion
     * </p>
     * @param pInputView
     * @param pEmotion
     * @param pTextSize
     * @param pDrawableSize
     */
    public static void addEmotion(Context context, final IInputView pInputView, final Emotion pEmotion, final int pTextSize, final int pDrawableSize) {
        final SpannableString append = new SpannableString(pEmotion.encode());
        final int selectionStart = pInputView.getSelectionStart();
        final int length = append.length();
        final Spannable text = pInputView.getText();
        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(text);
        final EmoticonConfig configs = EmoticonManager.getInstance(context).getConfigs();
        if (configs != null) {
            ArrayList<IDecoder> decoderList = configs.getDecoders();
            if (decoderList != null) {
                for (IDecoder decoder : decoderList) {
                    decoder.decode(append, pDrawableSize, pTextSize);
                }
            }
        }
        spannableStringBuilder.replace(selectionStart, selectionStart, append);
        pInputView.setText(spannableStringBuilder);
        if (pInputView.getText().length() != spannableStringBuilder.length()) {
            pInputView.setText(text);
        }
        pInputView.setSelection(new Pair<>(selectionStart + length, selectionStart + length));
    }

    /**
     * update emotions
     * </p>
     * @param context
     * @param pSpannable
     * @param emojiSize
     * @param textSize
     */
    public static void updateEmotions(Context context, final Spannable pSpannable, final int emojiSize, final int textSize) {
        int textLength = pSpannable.length();
        EmotionSpan[] oldSpans = pSpannable.getSpans(0, textLength, EmotionSpan.class);
        for (int i = 0; i < oldSpans.length; i++) {
            pSpannable.removeSpan(oldSpans[i]);
        }
        final EmoticonConfig configs = EmoticonManager.getInstance(context).getConfigs();
        if (configs != null) {
            ArrayList<IDecoder> decoderList = configs.getDecoders();
            if (decoderList != null) {
                for (IDecoder decoder : decoderList) {
                    decoder.decode(pSpannable, emojiSize, textSize);
                }
            }
        }
    }

}
