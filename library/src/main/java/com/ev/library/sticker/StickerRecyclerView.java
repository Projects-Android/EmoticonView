package com.ev.library.sticker;

import android.content.Context;
import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;

import com.ev.library.bean.emotion.Emotion;

import java.util.ArrayList;

/**
 * Created by EV on 2018/4/27.
 */
public class StickerRecyclerView extends RecyclerView implements IStickLayout {

    private int mMaxDeviation = 0;

    public StickerRecyclerView(Context context) {
        super(context);
    }

    public StickerRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public StickerRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public View findChildViewUnderStick(@NonNull View stickerView) {
        int[] coordinate = StickerUtils.getViewCenterCoordinate(stickerView);
        View child = findChildViewUnder(coordinate[0], coordinate[1]);
        if (null == child) { // sticker's center is not in RecyclerView's item view
            LayoutManager layoutManager = getLayoutManager();
            if (!(layoutManager instanceof LinearLayoutManager)) {
                // only support LinearLayoutManager
                return null;
            }

            int firstVisibleItemPosition = ((LinearLayoutManager) layoutManager).findFirstVisibleItemPosition();
            int lastVisibleItemPosition = ((LinearLayoutManager) layoutManager).findLastVisibleItemPosition();
            for (; firstVisibleItemPosition <= lastVisibleItemPosition; firstVisibleItemPosition ++) {
                child = getChildAt(firstVisibleItemPosition);
                if (stickerInItem(child, stickerView)) {
                    int position = getChildAdapterPosition(child);
                    if (position != NO_POSITION) { // check again
                        return child;
                    }
                }
            }
        }

        return child;
    }

    @Override
    public void setMaxDeviation(int maxDeviation) {
        mMaxDeviation = maxDeviation;
    }

    @Override
    public boolean stickerInLayout(View stickerView) {
        if (null == stickerView) {
            return false;
        }
        Rect layoutRect = new Rect();
        Rect stickerRect = StickerUtils.getGlobalVisibleRect(stickerView);
        getGlobalVisibleRect(layoutRect);

        return layoutRect.contains(stickerRect);
    }

    @Override
    public void addSticker(View stickerView, Emotion emotion) throws StickerOutOfRangeException {
        if (!stickerInLayout(stickerView)) {
            // TODO: 2018/4/28 developers can catch this exception to remind users, like making a toast.
            throw new StickerOutOfRangeException(this);
        }

        View child = findChildViewUnderStick(stickerView);
        if (child instanceof IStickerItem) {
            int position = getChildAdapterPosition(child);
            Adapter adapter = getAdapter();
            if (adapter instanceof IStickRecyclerViewAdapter) {
                StickerMessage stickerMessage = ((IStickRecyclerViewAdapter) adapter).getItemData(position);
                if (null != stickerMessage) {
                    ArrayList<StickerMessage.Sticker> stickers = stickerMessage.getmStickers();
                    if (null == stickers) {
                        stickers = new ArrayList<>();
                    }

                    Rect layoutRect = new Rect();
                    Rect stickerRect = StickerUtils.getGlobalVisibleRect(stickerView);
                    child.getGlobalVisibleRect(layoutRect);
                    StickerMessage.Sticker sticker = new StickerMessage.Sticker(emotion, layoutRect, stickerRect);
                    stickers.add(sticker);
                    stickerMessage.setmStickers(stickers);
                    adapter.notifyItemChanged(position);
                }
            }
        }
    }

    /**
     * whether the sticker is inside the stick layout
     * </p>
     * supports deviation {@link #mMaxDeviation}
     * @param itemView
     * @param stickerView
     * @return
     */
    private boolean stickerInItem(View itemView, View stickerView) {
        Rect rect = new Rect();
        itemView.getGlobalVisibleRect(rect);

        int[] stickerCoordinate = StickerUtils.getViewCenterCoordinate(stickerView);
        return rect.contains(stickerCoordinate[0], stickerCoordinate[1])
                || rect.contains(stickerCoordinate[0] + mMaxDeviation, stickerCoordinate[1])
                || rect.contains(stickerCoordinate[0], stickerCoordinate[1] + mMaxDeviation)
                || rect.contains(stickerCoordinate[0] + mMaxDeviation, stickerCoordinate[1] + mMaxDeviation)
                || rect.contains(stickerCoordinate[0] - mMaxDeviation, stickerCoordinate[1])
                || rect.contains(stickerCoordinate[0], stickerCoordinate[1] - mMaxDeviation)
                || rect.contains(stickerCoordinate[0] - mMaxDeviation, stickerCoordinate[1] - mMaxDeviation);
    }
}
