package com.example.yangtianrui.doubanapitest;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by yangtianrui on 16-5-29.
 */
public class MyAdapter extends BaseAdapter {
    private List<Book> mBooks;
    private LayoutInflater mInflater;
    private Context mContext;

    public MyAdapter(Context context, List<Book> list) {
        mInflater = LayoutInflater.from(context);
        mContext = context;
        mBooks = list;
    }

    @Override
    public int getCount() {
        return mBooks.size();
    }

    @Override
    public Object getItem(int position) {
        return mBooks.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.item_book, null);
            holder = new ViewHolder();
            holder.mTvTitle = (TextView) convertView.findViewById(R.id.id_tv_book_title);
            holder.mTvAuthor = (TextView) convertView.findViewById(R.id.id_tv_book_author);
            holder.mTvSubTitle = (TextView) convertView.findViewById(R.id.id_tv_book_subtitle);
            holder.mTvPublisher = (TextView) convertView.findViewById(R.id.id_tv_book_publisher);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.mTvTitle.setText(mBooks.get(position).getTitle());
        holder.mTvSubTitle.setText(mBooks.get(position).getAlt_Title());
        if (mBooks.get(position).getAuthor().length != 0)
            holder.mTvAuthor.setText(mBooks.get(position).getAuthor()[0]);
        holder.mTvPublisher.setText(mBooks.get(position).getPublisher());
        return convertView;
    }

    static class ViewHolder {
        TextView mTvTitle;
        TextView mTvAuthor;
        TextView mTvSubTitle;
        TextView mTvPublisher;
    }
}
