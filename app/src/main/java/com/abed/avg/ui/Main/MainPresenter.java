package com.abed.avg.ui.Main;

import android.content.Context;
import android.util.Log;

import com.abed.avg.Application;
import com.abed.avg.data.model.Photo;
import com.abed.avg.data.model.QueryHolder;
import com.abed.avg.ui.base.BasePresenter;
import com.google.gson.Gson;


import java.util.LinkedList;
import java.util.List;

import javax.inject.Inject;

public class MainPresenter extends BasePresenter<MainMvpView> {
    private String TAG = getClass().getName();

    @Inject
    public MainPresenter() {
    }

    @Override
    public void attachView(MainMvpView mvpView) {
        super.attachView(mvpView);
    }

    @Override
    public void detachView() {
        super.detachView();
    }

    public void loadData(Context context) {
        String content = Application.get(context).getComponent().dataManager().getLocalFilesHelper().getFileContent(context, "sample-flicr.json");
        Gson gson = new Gson();
        QueryHolder data = gson.fromJson(content, QueryHolder.class);
        Log.d("content", data.getQuery().getCount() + "");
        List<Photo> photos = data.getQuery().getResults().getPhotos();
        getMvpView().showPhotos(photos);
    }


}
