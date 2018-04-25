package com.ev.library.strategy.files;

import android.content.Context;
import android.net.Uri;

import com.ev.library.utils.FileUtils;

import java.io.File;

/**
 * SD卡策略
 * Created by EV on 2018/4/23.
 */
public class SdcardFileStrategy implements IFileStrategy {

    private String mGroupDir;

    /**
     * Instantiates a new Sdcard file strategy.
     *
     * @param pContext the p context
     */
    public SdcardFileStrategy(Context pContext, String pGroupDir) {
        mGroupDir = new File(FileUtils.getSdcardEmotionDir(pContext), pGroupDir).getAbsolutePath();
    }

    @Override
    public String getImagePath(String pGroupName, String pFileName, String pExt) {
        File picFile = new File(mGroupDir, "/res/" + pFileName + "." + pExt);
        return Uri.fromFile(picFile).toString();
    }

    @Override
    public long getFileSize(Context pContext, String pGroupName, String pFileName, String pExt) {
        File picFile = new File(mGroupDir, "/res/" + pFileName + "." + pExt);
        return picFile.length();
    }
}
