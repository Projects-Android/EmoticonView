package com.ev.library.getter;

import android.content.Context;

import com.ev.library.bean.group.Group;
import com.ev.library.parser.DefaultEmoticonParser;
import com.ev.library.parser.IEmoticonParser;
import com.ev.library.strategy.configs.IConfigFileStrategy;
import com.ev.library.strategy.configs.IConfigsStrategy;
import com.ev.library.strategy.configs.SdcardConfigsFilesStrategy;
import com.ev.library.strategy.configs.SdcardConfigsStrategy;
import com.ev.library.strategy.files.SdcardFileStrategy;

import java.util.ArrayList;
import java.util.List;

/**
 * get emotion from SD card
 * Created by EV on 2018/4/23.
 */
public class SdcardEmoticonGetter implements IEmoticonGetter {

    @Override
    public List<Group> getEmotionGroups(Context pContext) {
        ArrayList<Group> groupList = new ArrayList<Group>();
        IConfigsStrategy configsStrategy = new SdcardConfigsStrategy();
        final String[] smileys = configsStrategy.getConfigs(pContext);
        if (smileys != null) {
            for (String smiley : smileys) {
                IEmoticonParser emotionParser = new DefaultEmoticonParser(new SdcardFileStrategy(pContext, smiley));
                IConfigFileStrategy mFilestrategy = new SdcardConfigsFilesStrategy(pContext, smiley);
                final Group group = emotionParser.parse(pContext, smiley, mFilestrategy);
                if (group != null) {
                    groupList.add(group);
                }
            }
        }
        return groupList;
    }
}
