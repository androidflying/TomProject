package com.tom.easyphotos.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.tom.easyphotos.R;
import com.tom.easyphotos.constant.Type;
import com.tom.easyphotos.models.album.entity.Photo;
import com.tom.easyphotos.setting.Setting;

import java.util.ArrayList;

/**
 * 拼图相册适配器
 * Created by huan on 2017/10/23.
 */

public class PuzzleSelectorAdapter extends RecyclerView.Adapter {


    private ArrayList<Photo> dataList;
    private LayoutInflater mInflater;
    private OnClickListener listener;


    public PuzzleSelectorAdapter(Context cxt, ArrayList<Photo> dataList, OnClickListener listener) {
        this.dataList = dataList;
        this.listener = listener;
        this.mInflater = LayoutInflater.from(cxt);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return new PhotoViewHolder(mInflater.inflate(R.layout.item_puzzle_selector_easy_photos, parent, false));

    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {

        final int p = position;
        Photo photo = dataList.get(position);
        String path = photo.path;
        String type = photo.type;
        if (Setting.showGif) {
            if (path.endsWith(Type.GIF) || type.endsWith(Type.GIF)) {
                Setting.imageEngine.loadGifAsBitmap(((PhotoViewHolder) holder).ivPhoto.getContext(), path, ((PhotoViewHolder) holder).ivPhoto);
                ((PhotoViewHolder) holder).tvGif.setVisibility(View.VISIBLE);
            } else {
                Setting.imageEngine.loadPhoto(((PhotoViewHolder) holder).ivPhoto.getContext(), path, ((PhotoViewHolder) holder).ivPhoto);
                ((PhotoViewHolder) holder).tvGif.setVisibility(View.GONE);
            }
        } else {
            Setting.imageEngine.loadPhoto(((PhotoViewHolder) holder).ivPhoto.getContext(), path, ((PhotoViewHolder) holder).ivPhoto);
            ((PhotoViewHolder) holder).tvGif.setVisibility(View.GONE);
        }

        ((PhotoViewHolder) holder).ivPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onPhotoClick(p);
            }
        });
    }


    @Override
    public int getItemCount() {
        return null == dataList ? 0 : dataList.size();
    }


    public interface OnClickListener {
        void onPhotoClick(int position);
    }

    public class PhotoViewHolder extends RecyclerView.ViewHolder {
        ImageView ivPhoto;
        TextView tvGif;

        public PhotoViewHolder(View itemView) {
            super(itemView);
            this.ivPhoto = itemView.findViewById(R.id.iv_photo);
            this.tvGif = itemView.findViewById(R.id.tv_gif);
        }
    }
}