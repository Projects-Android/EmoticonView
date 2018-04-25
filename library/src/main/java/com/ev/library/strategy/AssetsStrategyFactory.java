package com.ev.library.strategy;

import android.content.Context;

import com.ev.library.strategy.configs.AssetsConfigFileStrategy;
import com.ev.library.strategy.configs.AssetsConfigsStrategy;
import com.ev.library.strategy.configs.IConfigFileStrategy;
import com.ev.library.strategy.configs.IConfigsStrategy;
import com.ev.library.strategy.files.AssetsFileStrategy;
import com.ev.library.strategy.files.IFileStrategy;

/**
 * factory for assets strategy
 * Created by EV on 2018/4/23.
 */
public class AssetsStrategyFactory implements IAssetsStrategyFactory {
    @Override
    public IConfigsStrategy getAssetsConfigs() {
        return new AssetsConfigsStrategy();
    }

    @Override
    public IConfigFileStrategy getConfigFileStrategy(Context pContext, String pGroupName) {
        return new AssetsConfigFileStrategy(pContext, pGroupName);
    }

    @Override
    public IFileStrategy getFileStrategy() {
        return new AssetsFileStrategy();
    }
}
