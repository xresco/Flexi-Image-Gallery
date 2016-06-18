package com.abed.avg.ui.Main;

import com.abed.avg.data.model.Photo;
import com.abed.avg.ui.base.MvpView;

import java.util.List;

public interface MainMvpView extends MvpView {
    
    void showPhotos(List<Photo>[]  photos_rows);

}
