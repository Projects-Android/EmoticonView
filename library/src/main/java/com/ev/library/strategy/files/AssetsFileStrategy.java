package com.ev.library.strategy.files;


import android.content.Context;
import android.content.res.AssetManager;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;

/**
 * Created by EV on 2018/4/23.
 */
public class AssetsFileStrategy implements IFileStrategy, Serializable {

    /**
     * 预置文件策略
     */
    public AssetsFileStrategy() {
    }

    @Override
    public String getImagePath(String pGroupName, String pFileName, String pExt) {
        return "assets://emoticon/" + pGroupName + "/res/" + pFileName + "." + pExt;
    }

    @Override
    public long getFileSize(Context pContext, String pGroupName, String pFileName, String pExt) {
        AssetManager mngr = pContext.getAssets();
        InputStream open = null;
        try {
            open = mngr.open("emoticon/" + pGroupName + "/res/" + pFileName + "." + pExt);
            if (open != null) {
                return open.available();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (open != null) {
                    open.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return 0;
    }

}
