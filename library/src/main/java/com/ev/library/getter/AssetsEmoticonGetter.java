package com.ev.library.getter;

import android.content.Context;

import com.ev.library.EmoticonManager;
import com.ev.library.bean.group.Group;
import com.ev.library.parser.DefaultEmoticonParser;
import com.ev.library.parser.IEmoticonParser;
import com.ev.library.strategy.IAssetsStrategyFactory;
import com.ev.library.strategy.configs.IConfigFileStrategy;
import com.ev.library.strategy.configs.IConfigsStrategy;

import java.util.ArrayList;
import java.util.List;

/**
 * get emotion from assets
 * Created by EV on 2018/4/23.
 */
public class AssetsEmoticonGetter implements IEmoticonGetter {

    @Override
    public List<Group> getEmotionGroups(Context pContext) {
        ArrayList<Group> groupList = new ArrayList<>();

        final IAssetsStrategyFactory strategyFactory = EmoticonManager.getInstance(pContext).getAssetsStrategyFactory();
        IEmoticonParser mEmotionParser = new DefaultEmoticonParser(strategyFactory.getFileStrategy());
        IConfigsStrategy configsstrategy = strategyFactory.getAssetsConfigs();
        final String[] smileys = configsstrategy.getConfigs(pContext);
        if (smileys != null) {
            for (String smiley : smileys) {
                IConfigFileStrategy mFilestrategy = strategyFactory.getConfigFileStrategy(pContext, smiley);
                final Group group = mEmotionParser.parse(pContext, smiley, mFilestrategy);
                groupList.add(group);
            }
        }
        return groupList;
    }
}
