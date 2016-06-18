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

    public void loadData(Context context, int screen_width) {
        String content = Application.get(context).getComponent().dataManager().getLocalFilesHelper().getFileContent(context, "sample-flicr.json");
        Gson gson = new Gson();
        QueryHolder data = gson.fromJson(content, QueryHolder.class);
        Log.d("content", data.getQuery().getCount() + "");
        List<Photo> photos = data.getQuery().getResults().getPhotos();

        arrangeImages(screen_width, photos);

        int avg_height = screen_width / 2;
        List<Integer> indecies = discoverLineBreaks();
        int row_index = 0;

        List<Photo>[] photos_rows = new List[indecies.size() + 1];
        for (int i = 0; i < photos.size(); i++) {
            if (row_index < indecies.size()) {
                if (i == indecies.get(row_index)) {
                    row_index++;
                    System.out.println();
                }
            }
            if (photos_rows[row_index] == null)
                photos_rows[row_index] = new LinkedList<>();
            photos_rows[row_index].add(photos.get(i));
            System.out.print(" _ " + i);
        }
        System.out.println();
        adjustImagesSize(avg_height, screen_width, photos_rows);
        getMvpView().showPhotos(photos_rows);
    }


    int[][] table;


    private void adjustImagesSize(int avg_height, int screen_width, List<Photo>[] photos_rows) {
        for (List<Photo> photos_row : photos_rows) {
            int width_sum = 0;
            for (Photo photo : photos_row) {
                width_sum += getImageWidth(avg_height, photo);
            }
            Log.d(TAG, "adjustImagesSize: before:: " + width_sum);
            float scaling_factor = screen_width / (float) width_sum;
            Log.d(TAG, "adjustImagesSize: scaling factor " + scaling_factor);
            width_sum = 0;
            for (Photo photo : photos_row) {
                photo.setWidthN(getImageWidth((int) (scaling_factor * avg_height), photo));
                photo.setHeightN((int) (avg_height * scaling_factor));
                width_sum += photo.getWidthN();
            }

            //adjust the size of the last photo
            Photo last_photo_in_row = photos_row.get(photos_row.size() - 1);
            last_photo_in_row.setWidthN(last_photo_in_row.getWidthN() + screen_width - width_sum);

            width_sum = 0;
            for (Photo photo : photos_row) {
                width_sum += photo.getWidthN();
            }

            Log.d(TAG, "adjustImagesSize: after:: " + width_sum);
        }

    }

    private void arrangeImages(int screen_width, List<Photo> photos) {
        int avg_height = screen_width / 2;
        table = new int[photos.size()][photos.size()];
        int max_s = 0;
        for (int i = photos.size() - 1; i >= 0; i--) {
            for (int s = max_s; s >= 0; s--) {
                if (i + (s + 1) < photos.size()) {
                    int Pis = calculatePenalty(i, s, screen_width, 0, photos, avg_height);
                    table[i][s] = Math.min(table[i][s + 1], Pis + table[i + s][1]);
                } else {
                    table[i][s] = calculatePenalty(i, s, screen_width, 0, photos, avg_height);
                }
            }
            max_s++;
        }
    }


    private List<Integer> discoverLineBreaks() {
        List<Integer> indecies = new LinkedList<>();
        for (int i = 0; i < table.length; i++) {
            for (int s = 1; s < table.length - 1; s++) {
                if (table[i][s] < table[i][s + 1]) {
                    indecies.add(i + s);
                    i = i + s;
                    s = table.length;
                }
            }
        }
        return indecies;
    }

    private int getImageWidth(int avg_height, Photo photo) {
        return (int) (photo.getWidthN() * (avg_height / (float) photo.getHeightN()));
    }


    private int calculatePenalty(int i, int s, int screen_width, int spacing, List<Photo> photos, int avg_height) {
        int sum_width = 0;
        for (int z = i; z < i + s; z++) {
            sum_width += getImageWidth(avg_height, photos.get(z));
        }
        return Math.abs(screen_width - sum_width + spacing * (s - 1));
    }


}
