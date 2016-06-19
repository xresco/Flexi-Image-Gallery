package com.abed.avg.data.model;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by mindvalley on 19/06/2016.
 */

public class PhotosGridRow {

    public List<Photo> photos;
    public int starting_number;

    public PhotosGridRow(int starting_number) {
        this.starting_number = starting_number;
        photos = new LinkedList<>();
    }

    public void add(Photo photo) {
        photos.add(photo);
    }

    public Photo get(int index) {
        return photos.get(index);
    }

    public int size() {
        return photos.size();
    }
}
