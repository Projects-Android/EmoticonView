package com.ev.library.strategy;

import android.content.Context;

import com.ev.library.strategy.configs.IConfigFileStrategy;
import com.ev.library.strategy.configs.IConfigsStrategy;
import com.ev.library.strategy.files.IFileStrategy;

/**
 * factory interface
 * Created by EV on 2018/4/23.
 */
public interface IAssetsStrategyFactory {

    /**
     * get assets config strategy
     * </p>
     * @return the assets configs
     */
    IConfigsStrategy getAssetsConfigs();

    /**
     * get assets config.xml strategy
     * </p>
     * @param pContext the context
     * @return the config file strategy
     */
    IConfigFileStrategy getConfigFileStrategy(Context pContext, String pGroupName);

    /**
     * get file strategy
     * </p>
     * @return the file strategy
     */
    IFileStrategy getFileStrategy();

}
