package com.abed.avg.ui.Main;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.abed.avg.R;
import com.abed.avg.data.model.Photo;
import com.abed.avg.ui.view.ResizableImageView;
import com.bumptech.glide.Glide;

import java.util.List;

import javax.inject.Inject;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.CustomViewHolder> {
    private List<Photo>[] photos_rows;
    private ViewHolderClicks clicksListener;

    @Inject
    public MainAdapter() {
        this.photos_rows = new List[0];

    }

    public void setClicksListener(ViewHolderClicks clicksListener) {
        this.clicksListener = clicksListener;
    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_main_activity_img, parent, false);
        return new CustomViewHolder(view, clicksListener);
    }

    @Override
    public void onBindViewHolder(CustomViewHolder holder, int position) {
        if (photos_rows == null || photos_rows.length == 0) {
            return;
        }
        holder.setPhotosRow(photos_rows[position]);
        Log.d("count::", "onBindViewHolder: " + position + ":: " + photos_rows[position].size());

    }

    @Override
    public int getItemCount() {
        if (photos_rows == null) {
            return 0;
        }

        return photos_rows.length;
    }

    public void updateList(List<Photo>[] itemsList) {
        this.photos_rows = itemsList;
        notifyDataSetChanged();
    }


    public static class CustomViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {

        LinearLayout layout_photos_row;
        ViewHolderClicks clicksListener;
        List<Photo> photosRow;

        public CustomViewHolder(View view, ViewHolderClicks clicksListener) {
            super(view);
            this.clicksListener = clicksListener;
            layout_photos_row = (LinearLayout) view.findViewById(R.id.layout_photos_row);
//            imageView.setOnClickListener(this);
        }


        public void setPhotosRow(List<Photo> photosRow) {
            layout_photos_row.removeAllViews();
            for (Photo photo : photosRow) {
                ResizableImageView imageView = new ResizableImageView(layout_photos_row.getContext());
                imageView.setWidth(photo.getWidthN());
                imageView.setHeight(photo.getHeightN());

                Glide.with((imageView).getContext())
                        .load(photo.getUrlN())
                        .into(imageView);

                layout_photos_row.addView(imageView);
            }
//            this.image_url = image_url;

        }

        @Override
        public void onClick(View v) {
//            if (clicksListener != null)
//                this.clicksListener.onImageClick(v, getLayoutPosition(), image_url);
        }
    }


    public interface ViewHolderClicks {
        void onImageClick(View view, int position, String image_url);
    }
}
