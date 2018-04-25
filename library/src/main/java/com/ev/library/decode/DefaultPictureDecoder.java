package com.ev.library.decode;

import android.content.Context;

import com.ev.library.EmoticonManager;
import com.ev.library.R;
import com.ev.library.bean.emotion.Emotion;
import com.ev.library.bean.group.Group;
import com.ev.library.exception.DecodeException;

import java.util.LinkedHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * default picture decoder
 * Created by EV on 2018/4/23.
 */
public class DefaultPictureDecoder implements IPicDecoder {

    public static final String DEFAULT_IMAGE_URL = "drawable://" + R.drawable.ic_emotion_default;

    private Context mContext;

    public DefaultPictureDecoder(Context context) {
        mContext = context;
    }

    @Override
    public String decode(String text) throws DecodeException {
        LinkedHashMap<String, Group> groups = EmoticonManager.getInstance(mContext).getGroups();
        String regEx = "\\[(\\w*):(\\w*)\\]";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(text);
        if (m.find()) {
            final String group = m.group(1);
            final String id = m.group(2);
            final Group emotionGroup = groups.get(group);
            if (emotionGroup != null) {
                final Emotion emotion = emotionGroup.getEmotions().get(id);
                if (emotion != null) {
                    return emotion.getFileName();
                }
            }
            return String.format(DEFAULT_IMAGE_URL, id);
        }
        throw new DecodeException();
    }

}
