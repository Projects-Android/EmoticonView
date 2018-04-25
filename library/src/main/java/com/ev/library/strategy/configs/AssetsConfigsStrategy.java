package com.ev.library.strategy.configs;

import android.content.Context;

import java.io.IOException;

/**
 * strategy for files in assets
 * Created by EV on 2018/4/23.
 */
public class AssetsConfigsStrategy implements IConfigsStrategy {
    @Override
    public String[] getConfigs(Context pContext) {
        try {
            return pContext.getAssets().list("emoticon");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
