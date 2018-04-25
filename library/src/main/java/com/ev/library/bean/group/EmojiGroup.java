package com.ev.library.bean.group;

import com.ev.library.strategy.files.IFileStrategy;

/**
 * group for Emoji
 * Created by EV on 2018/4/23.
 */
public class EmojiGroup extends Group {
    public EmojiGroup(IFileStrategy pFilestrategy) {
        super(pFilestrategy);
    }

    @Override
    public int getColumn() {
        return 7;
    }

}
