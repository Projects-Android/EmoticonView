package com.ev.library.strategy.configs;

import android.content.Context;

/**
 * Created by EV on 2018/4/23.
 */
public interface IConfigsStrategy {

    /**
     * get file in assets
     * <p/>
     * @param pContext
     * @return the string [ ]
     */
    String[] getConfigs(Context pContext);

}
