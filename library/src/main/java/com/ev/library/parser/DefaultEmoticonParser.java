package com.ev.library.parser;

import android.content.Context;
import android.content.res.XmlResourceParser;
import android.util.Xml;

import com.ev.library.bean.group.EmojiGroup;
import com.ev.library.bean.emotion.Emotion;
import com.ev.library.bean.group.Group;
import com.ev.library.bean.group.PicGroup;
import com.ev.library.strategy.configs.IConfigFileStrategy;
import com.ev.library.strategy.files.IFileStrategy;
import com.ev.library.utils.EmoticonTypeUtils;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Locale;

/**
 * default xml parser
 * Created by EV on 2018/4/23.
 */
public class DefaultEmoticonParser implements IEmoticonParser {

    private IFileStrategy mFilestrategy;

    public DefaultEmoticonParser(IFileStrategy pFilestrategy) {
        mFilestrategy = pFilestrategy;
    }

    @Override
    public Group parse(Context pContext, String pDirName, IConfigFileStrategy pFilestrategy) {
        try {
            final InputStream inputStream = pFilestrategy.getConfig();
            if (inputStream == null) {
                return null;
            }
            IFileStrategy filestrategy = mFilestrategy;
            XmlPullParser xrp = Xml.newPullParser();
            xrp.setInput(inputStream, "UTF-8");
            Group group = null;
            Emotion lastEmotion = null;
            ArrayList<Emotion> emotionArrayList = new ArrayList<Emotion>();
            while (xrp.getEventType() != XmlPullParser.END_DOCUMENT) {
                if (xrp.getEventType() == XmlResourceParser.START_TAG) {
                    String tagName = xrp.getName();
                    if (tagName.equals("group")) {
                        final String type = xrp.getAttributeValue(null, "type");
                        if (EmoticonTypeUtils.EMOJI_STR.equals(type)) {
                            group = new EmojiGroup(filestrategy);
                        } else {
                            group = new PicGroup(filestrategy);
                        }
                        final String id = xrp.getAttributeValue(null, "id");
                        group.setId(id);
                        group.setNormalImg(xrp.getAttributeValue(null, "normal_img"));
                        group.setSelecteddImg(xrp.getAttributeValue(null, "selected_img"));
                        group.setType(EmoticonTypeUtils.strToInt(type));
                        group.setExt(xrp.getAttributeValue(null, "ext"));
                        group.setThumbExt(xrp.getAttributeValue(null, "thumb_ext"));
                        group.setDirName(pDirName);
                        group.setOrder(Integer.parseInt(xrp.getAttributeValue(null, "order")));
                    } else if (tagName.equals("smiley")) {
                        Emotion emotion = Emotion.create(filestrategy, group.getClass());
                        emotion.setId(xrp.getAttributeValue(null, "id"));
                        emotion.setFileName(xrp.getAttributeValue(null, "filename"));
                        emotion.setGroupID(group.getId());
                        emotion.setGroupDirName(pDirName);
                        group.addEmotion(emotion.getId(), emotion);
                        emotion.setExt(group.getExt());
                        emotion.setThumbExt(group.getThumbExt());
                        emotionArrayList.add(emotion);
                        lastEmotion = emotion;
                    } else if (tagName.equalsIgnoreCase("def")) {
                        lastEmotion.putLangText(Locale.ENGLISH, xrp.nextText().trim());
                    } else if (tagName.equalsIgnoreCase("cn")) {
                        lastEmotion.putLangText(Locale.CHINA, xrp.nextText().trim());
                    } else if (tagName.equalsIgnoreCase("tw")) {
                        lastEmotion.putLangText(Locale.TAIWAN, xrp.nextText().trim());
                    } else if (tagName.equalsIgnoreCase("en")) {
                        lastEmotion.putLangText(Locale.ENGLISH, xrp.nextText().trim());
                    }
                }
                xrp.next();
            }
            group.setEmotionArrays(emotionArrayList.toArray(new Emotion[emotionArrayList.size()]));
            return group;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        }
        return null;
    }
}
