package com.shu.xxnote.adapter;

import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.loopeer.cardstack.CardStackView;
import com.loopeer.cardstack.StackAdapter;
import com.shu.xxnote.Bmob.Note;
import com.shu.xxnote.R;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobFile;

public class TestStackAdapter extends StackAdapter<Integer> {
    Note[] notes;
    String userId;

    public TestStackAdapter(Context context) {
        super(context);
    }

    @Override
    public void bindView(Integer color, int position, CardStackView.ViewHolder holder) {
        if (holder instanceof ColorItemLargeHeaderViewHolder) {
            ColorItemLargeHeaderViewHolder h = (ColorItemLargeHeaderViewHolder) holder;
            h.onBind(color, position);
        }
        if (holder instanceof ColorItemWithNoHeaderViewHolder) {
            ColorItemWithNoHeaderViewHolder h = (ColorItemWithNoHeaderViewHolder) holder;
            h.onBind(color, position);
        }
        if (holder instanceof ColorItemViewHolder) {
            ColorItemViewHolder h = (ColorItemViewHolder) holder;
            h.onBind(color, position);
        }
    }

    @Override
    protected CardStackView.ViewHolder onCreateView(ViewGroup parent, int viewType) {
        View view;
        switch (viewType) {
            case R.layout.list_card_item_larger_header:
                view = getLayoutInflater().inflate(R.layout.list_card_item_larger_header, parent, false);
                return new ColorItemLargeHeaderViewHolder(view);
            case R.layout.list_card_item_with_no_header:
                view = getLayoutInflater().inflate(R.layout.list_card_item_with_no_header, parent, false);
                return new ColorItemWithNoHeaderViewHolder(view);
            default:
                view = getLayoutInflater().inflate(R.layout.list_card_item, parent, false);
                ColorItemViewHolder holder = new ColorItemViewHolder(view);
                holder.setNotes(notes);
                return holder;
        }
    }

    @Override
    public int getItemViewType(int position) {
            return R.layout.list_card_item;

    }

    static class ColorItemViewHolder extends CardStackView.ViewHolder {
        View mLayout;
        View mContainerContent;
        TextView mTextTitle;
        TextView mTextView;
        TextView mTitile;
        TextView mDate;
        ImageButton mImageButton;
        Note[] notes;

        public ColorItemViewHolder(View view) {
            super(view);
            mLayout = view.findViewById(R.id.frame_list_card_item);
            mContainerContent = view.findViewById(R.id.container_list_content);
            mTextTitle = (TextView) view.findViewById(R.id.text_list_card_title);
            mTextView = (TextView) view.findViewById(R.id.card_textView);
            mTitile = (TextView) view.findViewById(R.id.textView_title);
            mDate = (TextView)view.findViewById(R.id.textView_date);
            mImageButton = (ImageButton)view.findViewById(R.id.image_large_button);
        }

        @Override
        public void onItemExpand(boolean b) {
            mContainerContent.setVisibility(b ? View.VISIBLE : View.GONE);
        }

        public void onBind(Integer data, int position) {
            mLayout.getBackground().setColorFilter(ContextCompat.getColor(getContext(), data), PorterDuff.Mode.SRC_IN);
            mTextTitle.setText(String.valueOf(position));
            mTextView.setText(notes[position].getComment());
            mTitile.setText(notes[position].getTitle());
            mDate.setText(notes[position].getDate());
            final String source = notes[position].getBmobfile().getFileUrl();
            System.out.println(source);
            new DownloadImageTask().execute(source) ;
            mImageButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
        }
        private class DownloadImageTask extends AsyncTask<String, Void, Drawable>
        {

            protected Drawable doInBackground(String... urls) {
                return loadImageFromNetwork(urls[0]);
            }

            protected void onPostExecute(Drawable result) {
                mImageButton.setImageDrawable(result);
            }
        }

        private Drawable loadImageFromNetwork(String imageUrl)
        {
            Drawable drawable = null;
            try {
                // 可以在这里通过文件名来判断，是否本地有此图片
                drawable = Drawable.createFromStream(
                        new URL(imageUrl).openStream(), "image.jpg");
            } catch (IOException e) {
                Log.d("test", e.getMessage());
            }
            if (drawable == null) {
                Log.d("test", "null drawable");
            } else {
                Log.d("test", "not null drawable");
            }
            return drawable ;
        }

        public void setNotes(Note[] notes) {
            this.notes = notes;
        }
    }

    static class ColorItemWithNoHeaderViewHolder extends CardStackView.ViewHolder {
        View mLayout;
        TextView mTextTitle;

        public ColorItemWithNoHeaderViewHolder(View view) {
            super(view);
            mLayout = view.findViewById(R.id.frame_list_card_item);
            mTextTitle = (TextView) view.findViewById(R.id.text_list_card_title);
        }

        @Override
        public void onItemExpand(boolean b) {
        }

        public void onBind(Integer data, int position) {
            mLayout.getBackground().setColorFilter(ContextCompat.getColor(getContext(), data), PorterDuff.Mode.SRC_IN);
            mTextTitle.setText(String.valueOf(position));
        }

    }

    static class ColorItemLargeHeaderViewHolder extends CardStackView.ViewHolder {
        View mLayout;
        View mContainerContent;
        TextView mTextTitle;

        public ColorItemLargeHeaderViewHolder(View view) {
            super(view);
            mLayout = view.findViewById(R.id.frame_list_card_item);
            mContainerContent = view.findViewById(R.id.container_list_content);
            mTextTitle = (TextView) view.findViewById(R.id.text_list_card_title);
        }

        @Override
        public void onItemExpand(boolean b) {
            mContainerContent.setVisibility(b ? View.VISIBLE : View.GONE);
        }

        @Override
        protected void onAnimationStateChange(int state, boolean willBeSelect) {
            super.onAnimationStateChange(state, willBeSelect);
            if (state == CardStackView.ANIMATION_STATE_START && willBeSelect) {
                onItemExpand(true);
            }
            if (state == CardStackView.ANIMATION_STATE_END && !willBeSelect) {
                onItemExpand(false);
            }
        }

        public void onBind(Integer data, int position) {
            mLayout.getBackground().setColorFilter(ContextCompat.getColor(getContext(), data), PorterDuff.Mode.SRC_IN);
            mTextTitle.setText(String.valueOf(position));

            itemView.findViewById(R.id.text_view).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((CardStackView)itemView.getParent()).performItemClick(ColorItemLargeHeaderViewHolder.this);
                }
            });
        }

    }

    public void setNotes(Note[] notes) {
        this.notes = notes;
    }

}
