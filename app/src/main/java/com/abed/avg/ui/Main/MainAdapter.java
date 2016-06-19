package com.abed.avg.ui.Main;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.abed.avg.R;
import com.abed.avg.controller.PhotosGridProcessor;
import com.abed.avg.data.model.Photo;
import com.abed.avg.data.model.PhotosGridRow;
import com.abed.avg.ui.view.ResizableImageView;
import com.bumptech.glide.Glide;

import java.util.LinkedList;
import java.util.List;

import javax.inject.Inject;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.CustomViewHolder> {

    private PhotosGridRow[] photos_rows;
    private ViewHolderClicks clicksListener;
    private int item_spacing;
    @Inject
    PhotosGridProcessor photosGridProcessor;


    @Inject
    public MainAdapter() {
        this.photos_rows = new PhotosGridRow[0];

    }

    public void setClicksListener(ViewHolderClicks clicksListener) {
        this.clicksListener = clicksListener;
    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_main_activity_img, parent, false);
        return new CustomViewHolder(view, item_spacing, clicksListener);
    }

    @Override
    public void onBindViewHolder(CustomViewHolder holder, int position) {
        if (photos_rows == null || photos_rows.length == 0) {
            return;
        }
        holder.setPhotosGridRow(photos_rows[position]);
    }

    @Override
    public int getItemCount() {
        if (photos_rows == null) {
            return 0;
        }

        return photos_rows.length;
    }

    public void updateList(List<Photo> photos, int grid_width, int avg_height, int item_spacing) {
        this.item_spacing = item_spacing;
        photosGridProcessor.setAvgHeight(avg_height);
        photosGridProcessor.setGridWidth(grid_width);
        photosGridProcessor.setItemSpacing(item_spacing);
        this.photos_rows = photosGridProcessor.process(photos);
        notifyDataSetChanged();
    }


    public static class CustomViewHolder extends RecyclerView.ViewHolder {

        LinearLayout layout_photos_row;
        ViewHolderClicks clicksListener;
        LinearLayout.LayoutParams layoutParams;

        public CustomViewHolder(View view, int item_spacing, ViewHolderClicks clicksListener) {
            super(view);
            this.clicksListener = clicksListener;
            layout_photos_row = (LinearLayout) view.findViewById(R.id.layout_photos_row);
            layout_photos_row.setPadding(item_spacing, 0, item_spacing, item_spacing);
            layoutParams = new LinearLayout.LayoutParams(0, 0);
            layoutParams.setMargins(0, 0, item_spacing, 0);
        }


        public void setPhotosGridRow(final PhotosGridRow photosRow) {
            layout_photos_row.removeAllViews();
            int index = 0;
            for (final Photo photo : photosRow.photos) {
                final ResizableImageView imageView = new ResizableImageView(layout_photos_row.getContext());
                imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                imageView.setWidth(photo.getWidthN());
                imageView.setHeight(photo.getHeightN());
                imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        clicksListener.onImageClick(imageView, photosRow.starting_number + (int) v.getTag());
                    }
                });

                imageView.setLayoutParams(layoutParams);

                Glide.with((imageView).getContext())
                        .load(photo.getUrlN())
                        .into(imageView);
                imageView.setTag(index++);
                layout_photos_row.addView(imageView);
            }

        }
    }


    public interface ViewHolderClicks {
        void onImageClick(View view, int position);
    }


    /**
     * A class that encapsulate the information in a row of photos
     */

}
