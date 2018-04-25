package com.ev.library.bean.group;

import com.ev.library.strategy.files.IFileStrategy;

/**
 * bean for picture groups
 * Created by EV on 2018/4/23.
 */
public class PicGroup extends Group {
    public PicGroup(IFileStrategy pFilestrategy) {
        super(pFilestrategy);
    }

    @Override
    public int getColumn() {
        return 4;
    }
}
