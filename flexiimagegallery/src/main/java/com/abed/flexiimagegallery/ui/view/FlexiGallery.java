package com.abed.flexiimagegallery.ui.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;

import com.abed.flexiimagegallery.R;
import com.abed.flexiimagegallery.controller.ImageLoader;
import com.abed.flexiimagegallery.data.GalleryPhoto;
import com.abed.flexiimagegallery.ui.adapter.FlexiGalleryAdapter;

import java.util.List;

/**
 * Created by Abed on 19/06/2016.
 */

public class FlexiGallery extends RecyclerView {

    private FlexiGalleryAdapter adapter;
    private float average_row_height;
    private float horizontal_spacing;
    private float vertical_spacing;
    private List<GalleryPhoto> photos;
    private ImageLoader imageLoader;
    private boolean configured;


    public FlexiGallery(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        configure(attrs);
    }

    public FlexiGallery(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        configure(attrs);
    }

    public FlexiGallery(Context context) {
        super(context);
        configure(null);
    }

    private void configure(@Nullable AttributeSet attrs) {
        //Deflate the custom XML attributes
        if (attrs != null) {
            TypedArray typedArray = getContext().getTheme().obtainStyledAttributes(
                    attrs,
                    R.styleable.FlexiGallery,
                    0, 0);

            horizontal_spacing = typedArray.getDimension(R.styleable.FlexiGallery_gallery_items_horizontal_spacing, 0.0f);
            vertical_spacing = typedArray.getDimension(R.styleable.FlexiGallery_gallery_items_vertical_spacing, 0.0f);
            average_row_height = typedArray.getDimension(R.styleable.FlexiGallery_gallery_average_row_height, 0.0f);
            typedArray.recycle();
        }


        adapter = new FlexiGalleryAdapter();
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        setLayoutManager(mLayoutManager);
        setAdapter(adapter);
    }

    @Override
    public void setAdapter(Adapter adapter) {
        if (!(adapter instanceof FlexiGalleryAdapter))
            throw new RuntimeException("You can't set the adapter of FlexiGallery to something other than FlexiGalleryAdapter");
        super.setAdapter(adapter);
        this.adapter = (FlexiGalleryAdapter) adapter;
    }

    public void configure(List<GalleryPhoto> photos, ImageLoader imageLoader) {
        this.photos = photos;
        this.imageLoader = imageLoader;
        if (getWidth() > 0){
            adapter.updateList(photos, getMeasuredWidth(), (int) average_row_height, (int) horizontal_spacing, (int) vertical_spacing, imageLoader);
            configured = true;
        }
    }

    @Override
    protected void onMeasure(int widthSpec, int heightSpec) {
        super.onMeasure(widthSpec, heightSpec);
        if (average_row_height <= 0)
            average_row_height = getMeasuredWidth() / 2;
        if (!configured && getMeasuredWidth() > 0 && photos != null) {
            adapter.updateList(photos, getMeasuredWidth(), (int) average_row_height, (int) horizontal_spacing, (int) vertical_spacing, imageLoader);
            configured = true;
        }
    }


    public void setPhotoClickListener(FlexiGalleryAdapter.PhotoClickListener clickListener) {
        adapter.setPhotoClickListener(clickListener);
    }
}
