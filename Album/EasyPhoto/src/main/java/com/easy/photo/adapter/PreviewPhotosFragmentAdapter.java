package com.easy.photo.adapter;

import android.content.Context;

import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.easy.photo.R;
import com.easy.photo.constant.Type;
import com.easy.photo.result.Result;
import com.easy.photo.setting.Setting;
import com.easy.photo.widget.PressedImageView;

/**
 * 预览所有选中图片集合的适配器
 * Created by huan on 2017/12/1.
 */

public class PreviewPhotosFragmentAdapter extends RecyclerView.Adapter<PreviewPhotosFragmentAdapter.PreviewPhotoVH> {
    private LayoutInflater inflater;
    private OnClickListener listener;
    private int checkedPosition = -1;

    public PreviewPhotosFragmentAdapter(Context context, OnClickListener listener) {
        this.inflater = LayoutInflater.from(context);
        this.listener = listener;
    }


    @Override
    public PreviewPhotoVH onCreateViewHolder(ViewGroup parent, int viewType) {
        return new PreviewPhotoVH(inflater.inflate(R.layout.item_preview_selected_photos_easy_photos, parent, false));
    }

    @Override
    public void onBindViewHolder(PreviewPhotoVH holder, int position) {
        final int p = position;
        String path = Result.getPhotoPath(position);
        String type = Result.getPhotoType(position);
        if (Setting.showGif) {
            if (path.endsWith(Type.GIF) || type.endsWith(Type.GIF)) {
                Setting.imageEngine.loadGifAsBitmap(holder.ivPhoto.getContext(), path, holder.ivPhoto);
                holder.tvGif.setVisibility(View.VISIBLE);
            } else {
                Setting.imageEngine.loadPhoto(holder.ivPhoto.getContext(), path, holder.ivPhoto);
                holder.tvGif.setVisibility(View.GONE);
            }
        } else {
            Setting.imageEngine.loadPhoto(holder.ivPhoto.getContext(), path, holder.ivPhoto);
            holder.tvGif.setVisibility(View.GONE);
        }

        if (checkedPosition == p) {
            holder.frame.setVisibility(View.VISIBLE);
        } else {
            holder.frame.setVisibility(View.GONE);
        }
        holder.ivPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onPhotoClick(p);
            }
        });
    }

    @Override
    public int getItemCount() {
        return Result.count();
    }

    public void setChecked(int position) {
        if (checkedPosition == position) {
            return;
        }
        checkedPosition = position;
        notifyDataSetChanged();
    }

    class PreviewPhotoVH extends RecyclerView.ViewHolder {
        PressedImageView ivPhoto;
        View frame;
        TextView tvGif;

        public PreviewPhotoVH(View itemView) {
            super(itemView);
            ivPhoto = itemView.findViewById(R.id.iv_photo);
            frame = itemView.findViewById(R.id.v_selector);
            tvGif = itemView.findViewById(R.id.tv_gif);
        }
    }

    public interface OnClickListener {
        void onPhotoClick(int position);
    }
}
