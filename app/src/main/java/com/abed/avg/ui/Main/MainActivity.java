package com.abed.avg.ui.Main;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.abed.avg.R;
import com.abed.avg.data.model.Photo;
import com.abed.avg.ui.base.BaseActivity;
import com.abed.flexiimagegallery.controller.ImageLoader;
import com.abed.flexiimagegallery.data.GalleryPhoto;
import com.abed.flexiimagegallery.ui.adapter.FlexiGalleryAdapter;
import com.abed.flexiimagegallery.ui.view.FlexiGallery;
import com.bumptech.glide.Glide;

import java.util.LinkedList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity implements MainMvpView {

    @Inject
    MainPresenter mMainPresenter;

    @BindView(R.id.flexi_gallery)
    FlexiGallery flexi_gallery;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivityComponent().inject(this);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        mMainPresenter.attachView(this);
        mMainPresenter.loadData(this);
        flexi_gallery.setPhotoClickListener(new FlexiGalleryAdapter.PhotoClickListener() {
            @Override
            public void onPhotoClick(View view, int position, GalleryPhoto photo) {
                Toast.makeText(view.getContext(), position + "", Toast.LENGTH_SHORT).show();
            }
        });

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        mMainPresenter.detachView();
    }

    @Override
    public void showPhotos(List<Photo> photos) {
        List<GalleryPhoto> galleryPhotos = new LinkedList<>();
        for (Photo photo : photos) {
            galleryPhotos.add(photo.convertToGalleryPhoto());
        }

        flexi_gallery.configure(galleryPhotos, new ImageLoader() {
            @Override
            public void loadImage(String url, ImageView imageView) {
                Glide.with(imageView.getContext()).load(url).into(imageView);
            }
        });
    }


}
