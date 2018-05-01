package com.ev.emoticonview.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ev.emoticonview.R;
import com.ev.emoticonview.bean.Message;
import com.ev.library.EmoticonManager;
import com.ev.library.sticker.IStickRecyclerViewAdapter;
import com.ev.library.sticker.Sticker;
import com.ev.library.sticker.StickerItemView;
import com.ev.library.utils.EmoticonImageLoader;

import java.util.ArrayList;

import pl.droidsonroids.gif.GifImageView;

/**
 * Created by EV on 2018/4/25.
 */
public class MessageRecyclerViewAdapter extends RecyclerView.Adapter<MessageRecyclerViewAdapter.ViewHolder> implements IStickRecyclerViewAdapter {

    private Context mContext;
    private ArrayList<Message> mMessages;

    public MessageRecyclerViewAdapter(Context context, ArrayList<Message> messages) {
        this.mContext = context;
        this.mMessages = messages;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        StickerItemView itemView = new StickerItemView(mContext);
        if (Message.TYPE_MESSAGE_RECEIVE == viewType) {
            itemView.setContentView(R.layout.layout_item_message_receive);
        } else {
            itemView.setContentView(R.layout.layout_item_message_send);
        }
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Message msg = getItemData(position);
        if (null != msg) {
            if (Message.TYPE_MESSAGE_CONTENT_TEXT == msg.getmType()) {
                holder.mIvContent.setVisibility(View.GONE);
                holder.mTvContent.setVisibility(View.VISIBLE);
                int size = (int) holder.mTvContent.getTextSize();
                holder.mTvContent.setText(EmoticonManager.getInstance(mContext).decode(msg.getmContent(), size, size));
            } else {
                holder.mTvContent.setVisibility(View.GONE);
                holder.mIvContent.setVisibility(View.VISIBLE);

                String url = EmoticonManager.getInstance(mContext).decodePic(msg.getmContent());
                EmoticonImageLoader.getInstance().displayGif(url, holder.mIvContent);
            }

            holder.mStickItemView.reset();
            ArrayList<Sticker> stickers = msg.getmStickers();
            if (null != stickers && !stickers.isEmpty()) {
                holder.mStickItemView.addStickers(stickers);
            }
        }
    }

    @Override
    public int getItemCount() {
        return null == mMessages ? 0 : mMessages.size();
    }

    @Override
    public long getItemId(int position) {
        if (null != mMessages) {
            return getItemData(position).getmMsgId();
        }
        return super.getItemId(position);
    }

    @Override
    public int getItemViewType(int position) {
        if (null != mMessages) {
            return getItemData(position).getmSourceType();
        }
        return Message.TYPE_MESSAGE_RECEIVE;
    }

    @Override
    public Message getItemData(int position) {
        if (null != mMessages) {
            return mMessages.get(position);
        }

        return null;
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private StickerItemView mStickItemView;
        private TextView mTvContent;
        private GifImageView mIvContent;

        public ViewHolder(View itemView) {
            super(itemView);
            mStickItemView = (StickerItemView) itemView;
            mTvContent = itemView.findViewById(R.id.tv_item_message_content_text);
            mIvContent = itemView.findViewById(R.id.iv_item_message_content_pic);
        }
    }
}
