
package com.abed.flexiimagegallery.data;

public class GalleryPhoto {

    private int height;
    private String url;
    private int width;

    public GalleryPhoto(String url, int width, int height) {
        this.height = height;
        this.url = url;
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public String getUrl() {
        return url;
    }


    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }
}
