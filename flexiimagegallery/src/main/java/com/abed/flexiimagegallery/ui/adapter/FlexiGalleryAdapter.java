package com.abed.flexiimagegallery.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.abed.flexiimagegallery.R;
import com.abed.flexiimagegallery.controller.ImageLoader;
import com.abed.flexiimagegallery.controller.PhotosGridProcessor;
import com.abed.flexiimagegallery.data.GalleryPhoto;
import com.abed.flexiimagegallery.data.PhotosGridRow;
import com.abed.flexiimagegallery.ui.view.ResizableImageView;

import java.util.List;


public class FlexiGalleryAdapter extends RecyclerView.Adapter<FlexiGalleryAdapter.CustomViewHolder> {

    private PhotosGridRow[] photos_rows;
    private PhotoClickListener clickListener;
    private int horizontal_spacing;
    private int vertical_spacing;
    private PhotosGridProcessor photosGridProcessor;
    private ImageLoader imageLoader;

    public FlexiGalleryAdapter() {
        this.photos_rows = new PhotosGridRow[0];
        photosGridProcessor = new PhotosGridProcessor();

    }

    public void setPhotoClickListener(PhotoClickListener clickListener) {
        this.clickListener = clickListener;
    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_photos_row, parent, false);
        return new CustomViewHolder(view, horizontal_spacing, vertical_spacing, imageLoader, clickListener);
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

    public void updateList(List<GalleryPhoto> photos, int grid_width, int avg_height, int horizontal_spacing, int vertical_spacing, ImageLoader imageLoader) {
        this.horizontal_spacing = horizontal_spacing;
        this.vertical_spacing = vertical_spacing;
        this.imageLoader = imageLoader;
        photosGridProcessor.setAvgHeight(avg_height);
        photosGridProcessor.setGridWidth(grid_width);
        photosGridProcessor.setItemSpacing(horizontal_spacing);
        this.photos_rows = photosGridProcessor.process(photos);
        notifyDataSetChanged();
    }


    public static class CustomViewHolder extends RecyclerView.ViewHolder {

        LinearLayout layout_photos_row;
        PhotoClickListener clickListener;
        LinearLayout.LayoutParams layoutParams;
        ImageLoader imageLoader;

        public CustomViewHolder(View view, int horizontal_spacing, int vertical_spacing, ImageLoader imageLoader, PhotoClickListener clickListener) {
            super(view);
            this.clickListener = clickListener;
            this.imageLoader = imageLoader;
            layout_photos_row = (LinearLayout) view.findViewById(R.id.layout_photos_row);
            layout_photos_row.setPadding(horizontal_spacing, 0, horizontal_spacing, vertical_spacing);
            layoutParams = new LinearLayout.LayoutParams(0, 0);
            layoutParams.setMargins(0, 0, horizontal_spacing, 0);
        }


        public void setPhotosGridRow(final PhotosGridRow photosRow) {
            layout_photos_row.removeAllViews();
            int index = 0;
            for (final GalleryPhoto photo : photosRow.photos) {
                final ResizableImageView imageView = new ResizableImageView(layout_photos_row.getContext());
                imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                imageView.setWidth(photo.getWidth());
                imageView.setHeight(photo.getHeight());
                imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        clickListener.onPhotoClick(imageView, photosRow.starting_number + (int) v.getTag(), photo);
                    }
                });

                imageView.setLayoutParams(layoutParams);
                imageLoader.loadImage(photo.getUrl(), imageView);
                imageView.setTag(index++);
                layout_photos_row.addView(imageView);
            }

        }
    }


    public interface PhotoClickListener {
        void onPhotoClick(View view, int position, GalleryPhoto photo);
    }


    /**
     * A class that encapsulate the information in a row of photos
     */

}
