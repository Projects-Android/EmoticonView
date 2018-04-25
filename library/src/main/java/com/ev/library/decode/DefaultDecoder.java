package com.ev.library.decode;

import android.content.Context;
import android.text.Spannable;

import com.ev.library.EmoticonManager;
import com.ev.library.R;
import com.ev.library.bean.emotion.Emotion;
import com.ev.library.bean.group.Group;
import com.ev.library.span.EmotionSpan;

import java.util.Locale;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * default decoder
 * Created by EV on 2018/4/23.
 */
public class DefaultDecoder implements IDecoder {

    private Context mContext;
    private Map<String, Group> mGroupMap;

    public DefaultDecoder(Context context) {
        mContext = context;
        mGroupMap = EmoticonManager.getInstance(context).getGroups();
    }

    @Override
    public Spannable decode(Spannable pSpannable, int pEmojiSize, int pTextSize) {
        String textString = pSpannable.toString();
        String regEx = "\\[(\\w*):(\\w*)\\]";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(textString);
        while (m.find()) {
            final String group = m.group(1);
            final String id = m.group(2);
            Group groupEntity = mGroupMap.get(group);
            if (groupEntity == null) {
                return pSpannable;
            }
            final Emotion emotion = groupEntity.getEmotions().get(id);
            if (emotion != null) {
                pSpannable.setSpan(new EmotionSpan(mContext, emotion, pEmojiSize, pTextSize), m.start(), m.end(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
        }
        return pSpannable;
    }

    @Override
    public String decodeToText(Context pContext, String text) {
        StringBuilder result = new StringBuilder(text);
        String regEx = "\\[(\\w*):(\\w*)\\]";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(result);
        while (m.find()) {
            final String group = m.group(1);
            final String id = m.group(2);
            final Group groupEntity = mGroupMap.get(group);
            if (groupEntity == null) {
                return text;
            }
            final Emotion emotion = groupEntity.getEmotions().get(id);
            if (emotion != null) {
                result.replace(m.start(1), m.end(2), emotion.getLangText(Locale.getDefault()));
            }else{
                result.replace(m.start(1), m.end(2),pContext.getString(R.string.tip_unknown_emotions));
            }
            m = p.matcher(result);
        }
        return result.toString();
    }
}
