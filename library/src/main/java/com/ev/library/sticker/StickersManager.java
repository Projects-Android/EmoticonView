package com.ev.library.sticker;

import android.graphics.Rect;

import java.util.ArrayList;

/**
 * manager for sticker list
 * Created by EV on 2018/5/1.
 */
public class StickersManager extends ArrayList<Sticker> {

    private Rect mCurrentParentRect;

    @Override
    public boolean add(Sticker sticker) {
        boolean success = super.add(sticker);

        if (null == mCurrentParentRect) {
            mCurrentParentRect = sticker.getmCurrentParent();
        } else {
            mCurrentParentRect.union(sticker.getmCurrentParent());
            updateAllStickers();
        }
        return success;
    }

    @Override
    public void add(int index, Sticker sticker) {
        super.add(index, sticker);

        if (null == mCurrentParentRect) {
            mCurrentParentRect = new Rect();
            mCurrentParentRect.set(sticker.getmCurrentParent());
        } else {
            mCurrentParentRect.union(sticker.getmCurrentParent());
            updateAllStickers();
        }
    }

    private void updateAllStickers() {
        for (Sticker sticker : this) {
            // should resize
            if (sticker.getmCurrentParent().width() < mCurrentParentRect.width()
                    || sticker.getmCurrentParent().height() < mCurrentParentRect.height()) {
                sticker.update(mCurrentParentRect);
            }
        }
    }
}
