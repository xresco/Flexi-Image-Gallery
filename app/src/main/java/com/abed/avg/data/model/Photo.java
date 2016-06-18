
package com.abed.avg.data.model;

public class Photo {

    private String farm;
    private String height_n;
    private String id;
    private String isfamily;
    private String isfriend;
    private String ispublic;
    private String owner;
    private String secret;
    private String server;
    private String title;
    private String url_n;
    private String width_n;

    /**
     * @return The farm
     */
    public String getFarm() {
        return farm;
    }

    /**
     * @return The heightN
     */
    public int getHeightN() {
        return Integer.parseInt(height_n);
    }

    /**
     * @return The id
     */
    public String getId() {
        return id;
    }

    /**
     * @return The isfamily
     */
    public String getIsfamily() {
        return isfamily;
    }

    /**
     * @return The isfriend
     */
    public String getIsfriend() {
        return isfriend;
    }

    /**
     * @return The ispublic
     */
    public String getIspublic() {
        return ispublic;
    }

    /**
     * @return The owner
     */
    public String getOwner() {
        return owner;
    }

    /**
     * @return The secret
     */
    public String getSecret() {
        return secret;
    }

    /**
     * @return The server
     */
    public String getServer() {
        return server;
    }

    /**
     * @return The title
     */
    public String getTitle() {
        return title;
    }


    /**
     * @return The urlN
     */
    public String getUrlN() {
        return url_n;
    }


    /**
     * @return The widthN
     */
    public int getWidthN() {
        return Integer.parseInt(width_n);
    }


    public void setWidthN(int width_n) {
        this.width_n = String.valueOf(width_n);
    }

    public void setHeightN(int height_n) {
        this.height_n = String.valueOf(height_n);
    }
}
