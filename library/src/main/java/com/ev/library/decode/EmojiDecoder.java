package com.ev.library.decode;

import android.content.Context;
import android.text.Spannable;

import com.ev.library.EmoticonManager;
import com.ev.library.bean.emotion.Emotion;
import com.ev.library.bean.group.Group;
import com.ev.library.span.EmotionSpan;

import java.util.Locale;
import java.util.Map;

/**
 * Emoji decoder
 * Created by EV on 2018/4/23.
 */
public class EmojiDecoder implements IDecoder {

    private Context mContext;
    private Map<String, Group> mGroupMap;

    public EmojiDecoder(Context context) {
        mContext = context;
        mGroupMap = EmoticonManager.getInstance(context).getGroups();
    }

    @Override
    public Spannable decode(Spannable pSpannable, int pEmojiSize, int pTextSize) {
        String textString = pSpannable.toString();
        final int length = textString.length();
        int skip;
        for (int i = 0; i < length; i += skip) {
            int unicode = Character.codePointAt(pSpannable, i);
            skip = Character.charCount(unicode);
            final Group group = mGroupMap.get("emoticon_emoji");
            if (group == null) {
                return pSpannable;
            }
            final Emotion emotion = group.getEmotions().get(String.valueOf(unicode));
            if (emotion != null) {
                pSpannable.setSpan(new EmotionSpan(mContext, emotion, pEmojiSize, pTextSize), i, i + skip, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
        }
        return null;
    }

    @Override
    public String decodeToText(Context pContext, String text) {
        StringBuilder result = new StringBuilder(text);
        int length;
        int skip;
        int i = 0;
        Emotion emotion;
        do {
            int unicode = Character.codePointAt(result, i);
            skip = Character.charCount(unicode);
            final Group group = mGroupMap.get("emoticon_emoji");
            if (group == null) {
                return text;
            }
            emotion = group.getEmotions().get(String.valueOf(unicode));
            int destLength = 0;
            if (emotion != null) {
                final String string = "[" + emotion.getLangText(Locale.getDefault()) + "]";
                destLength = string.length();
                result.replace(i, i + skip, string);
            }
            if (destLength == 0) {
                destLength = skip;
            }
            i += destLength;
            length = result.length();
        } while (i < length);
        return result.toString();
    }

}
