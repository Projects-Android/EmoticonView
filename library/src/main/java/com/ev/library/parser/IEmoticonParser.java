package com.ev.library.parser;

import android.content.Context;

import com.ev.library.bean.group.Group;
import com.ev.library.strategy.configs.IConfigFileStrategy;

/**
 * Created by EV on 2018/4/23.
 */
public interface IEmoticonParser {
    /**
     * Parse group from xml
     * </p>
     * @param pContext
     * @param pDirName
     * @param pFilestrategy
     * @return the group
     */
    Group parse(Context pContext, String pDirName, IConfigFileStrategy pFilestrategy);
}
