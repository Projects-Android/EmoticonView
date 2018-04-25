package com.ev.emoticonview.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ev.emoticonview.R;
import com.ev.emoticonview.bean.Message;
import com.ev.library.EmoticonManager;
import com.ev.library.utils.EmoticonImageLoader;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;

import pl.droidsonroids.gif.GifImageView;

/**
 * Created by EV on 2018/4/25.
 */
public class MessageRecyclerViewAdapter extends RecyclerView.Adapter<MessageRecyclerViewAdapter.ViewHolder> {

    private Context mContext;
    private ArrayList<Message> mMessages;
    private LayoutInflater mInflater;

    public MessageRecyclerViewAdapter(Context context, ArrayList<Message> messages) {
        this.mContext = context;
        this.mMessages = messages;
        this.mInflater = LayoutInflater.from(mContext);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView;
        if (Message.TYPE_MESSAGE_RECEIVE == viewType) {
            itemView = mInflater.inflate(R.layout.layout_item_message_receive, parent, false);
        } else {
            itemView = mInflater.inflate(R.layout.layout_item_message_send, parent, false);
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

    private Message getItemData(int position) {
        if (null != mMessages) {
            return mMessages.get(position);
        }

        return null;
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private TextView mTvContent;
        private GifImageView mIvContent;

        public ViewHolder(View itemView) {
            super(itemView);

            mTvContent = itemView.findViewById(R.id.tv_item_message_content_text);
            mIvContent = itemView.findViewById(R.id.iv_item_message_content_pic);
        }
    }
}
