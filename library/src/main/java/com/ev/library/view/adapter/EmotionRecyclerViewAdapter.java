package com.ev.library.view.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.ev.library.R;
import com.ev.library.bean.emotion.Emotion;
import com.ev.library.bean.emotion.PicEmotion;
import com.ev.library.bean.group.IGroup;
import com.ev.library.utils.EmoticonImageLoader;

/**
 * Created by EV on 2018/4/24.
 */
public class EmotionRecyclerViewAdapter extends RecyclerView.Adapter<EmotionRecyclerViewAdapter.ViewHolder> {

    private Context mContext;
    private IGroup mGroup;
    private LayoutInflater mInflater;
    private View.OnClickListener mOnClickListener;

    private final int EMOTION_ITEM_TYPE_EMOJI = 0x01;
    private final int EMOTION_ITEM_TYPE_PIC = 0x02;

    public EmotionRecyclerViewAdapter(Context context, IGroup iGroup, View.OnClickListener onClickListener) {
        this.mContext = context;
        this.mGroup = iGroup;
        this.mInflater = LayoutInflater.from(mContext);
        this.mOnClickListener = onClickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView;
        switch (viewType) {
            case EMOTION_ITEM_TYPE_PIC:
                itemView = mInflater.inflate(R.layout.layout_item_emotion_pic, parent, false);
                break;
            case EMOTION_ITEM_TYPE_EMOJI:
                default:
                    itemView = mInflater.inflate(R.layout.layout_item_emotion_emoji, parent, false);
                    break;
        }
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Emotion emotion = getItemData(position);
        if (null != emotion) {
            EmoticonImageLoader.getInstance().displayImage(emotion.getThumbFileName(), holder.mIvEmotion, EmoticonImageLoader.sDisplayImageOptions);
        }

        holder.mItemView.setTag(emotion);
        holder.mItemView.setOnClickListener(mOnClickListener);
    }

    @Override
    public int getItemCount() {
        if (null == mGroup) {
            return 0;
        }

        return mGroup.getEmotionTotal();
    }

    @Override
    public int getItemViewType(int position) {
        Emotion emotion = getItemData(position);
        if (null != emotion) {
            if (emotion instanceof PicEmotion) {
                return EMOTION_ITEM_TYPE_PIC;
            } else {
                return EMOTION_ITEM_TYPE_EMOJI;
            }
        }
        return EMOTION_ITEM_TYPE_EMOJI;
    }

    public Emotion getItemData(int position) {
        if (null == mGroup || null == mGroup.getEmotion(position)) {
            return null;
        }

        return mGroup.getEmotion(position);
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView mIvEmotion;
        private View mItemView;

        public ViewHolder(View itemView) {
            super(itemView);
            mItemView = itemView;
            mIvEmotion = itemView.findViewById(R.id.iv_item_emotion);
        }
    }
}
