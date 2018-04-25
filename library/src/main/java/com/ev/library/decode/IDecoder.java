package com.ev.library.decode;

import android.content.Context;
import android.text.Spannable;

/**
 * interface for decoder
 * Created by EV on 2018/4/23.
 */
public interface IDecoder {

    /**
     * decode text to emoji
     * </p>
     * @param text       the text
     * @param pEmojiSize the emoji size
     * @param pTextSize  @return the spannable
     * @return the spannable
     */
    Spannable decode(Spannable text, int pEmojiSize, int pTextSize);

    /**
     * decode to description of emotion
     * </p>
     * @param pContext
     * @param text
     * @return
     */
    String decodeToText(Context pContext, String text);

}
