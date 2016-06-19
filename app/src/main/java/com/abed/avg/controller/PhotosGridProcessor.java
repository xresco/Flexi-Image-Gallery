package com.abed.avg.controller;

import com.abed.avg.data.model.Photo;
import com.abed.avg.data.model.PhotosGridRow;
import com.abed.avg.ui.Main.MainAdapter;

import java.util.LinkedList;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by mindvalley on 19/06/2016.
 */

public class PhotosGridProcessor {

    /**
     * The average height of a row inside the image grid
     */
    private int avg_height;

    /**
     * The width of the image_grid
     */
    private int grid_width;

    private int item_spacing;


    @Inject
    public PhotosGridProcessor() {
    }

    public void setAvgHeight(int avg_height) {
        this.avg_height = avg_height;
    }

    public void setGridWidth(int grid_width) {
        this.grid_width = grid_width;
    }


    public void setItemSpacing(int item_spacing) {
        this.item_spacing = item_spacing;
    }

    /**
     * Scales evey image inside the rows to fill the height of the row and calculate the
     * corresponding width accordingly while keeping the aspect ratio.
     * It also adjust the last photo in the row in case the sum of the widths of the photos
     * isn't equal to the grid_width
     *
     * @param photos_rows
     */
    public void adjustImagesSize(PhotosGridRow[] photos_rows) {
        for (PhotosGridRow photos_row : photos_rows) {
            int width_sum = 0;
            for (Photo photo : photos_row.photos) {
                width_sum += getImageWidth(avg_height, photo);
            }
            float scaling_factor = grid_width / (float) width_sum;
            width_sum = 0;
            for (Photo photo : photos_row.photos) {
                photo.setWidthN(getImageWidth((int) (scaling_factor * avg_height), photo));
                photo.setHeightN((int) (avg_height * scaling_factor));
                width_sum += photo.getWidthN();
            }

            // Adjust the last photo in the row in case the sum of the widths of the photos
            // isn't equal to the grid_width
            Photo last_photo_in_row = photos_row.get(photos_row.size() - 1);
            last_photo_in_row.setWidthN(last_photo_in_row.getWidthN() + grid_width - width_sum);
        }

    }


    /**
     * For this we need a way to determine the contents a row. I have chosen to represent possible
     * rows as a combination of starting image (i) and how many images are in a row (spree or s).
     * We can also determine how “bad” a row is, by calculating the difference between the desired
     * grid_width (d) and how wide a row would be. We call this difference the penalty. The penalty of
     * the row starting with image i, containing s images is denoted Pi,s.
     * We also have notion of the combined penalty or the total cost of a packing. We call this
     * cost Ci,s, which describe the cost of a packing that starts at image i, where the first
     * row contains at least s images.
     * <p>
     * for more info checkout this link http://fangel.github.io/packing-images-in-a-grid/
     *
     * @param photos
     */
    public int[][] calculatePenaltyTable(List<Photo> photos) {

        int[][] penaltyTable = new int[photos.size()][photos.size()];
        int max_s = 0;
        for (int i = photos.size() - 1; i >= 0; i--) {
            for (int s = max_s; s >= 0; s--) {
                if (i + (s + 1) < photos.size()) {
                    int Pis = calculatePenalty(i, s, photos);
                    penaltyTable[i][s] = Math.min(penaltyTable[i][s + 1], Pis + penaltyTable[i + s][1]);
                } else {
                    penaltyTable[i][s] = calculatePenalty(i, s, photos);
                }
            }
            max_s++;
        }
        return penaltyTable;
    }


    /**
     * Analyzes the penaltyTable and returns the the indexies of the images where line breaks should happen
     * for more info checkout this link http://fangel.github.io/packing-images-in-a-grid/
     *
     * @param penaltyTable
     * @return
     */
    public List<Integer> discoverLineBreaks(int[][] penaltyTable) {
        List<Integer> indecies = new LinkedList<>();
        for (int i = 0; i < penaltyTable.length; i++) {
            for (int s = 1; s < penaltyTable.length - 1; s++) {
                if (penaltyTable[i][s] < penaltyTable[i][s + 1]) {
                    indecies.add(i + s);
                    i = i + s;
                    s = penaltyTable.length;
                }
            }
        }
        return indecies;
    }


    /**
     * Returns the image's width after scaling the image according to the ration (new_height divided by the height of the image)
     * for more info checkout this link http://fangel.github.io/packing-images-in-a-grid/
     *
     * @param new_height
     * @param photo
     * @return
     */
    public int getImageWidth(int new_height, Photo photo) {
        return (int) (photo.getWidthN() * (new_height / (float) photo.getHeightN()));
    }


    /**
     * Calculating the penalty of the row starting with image i, containing s images.
     * for more info checkout this link http://fangel.github.io/packing-images-in-a-grid/
     *
     * @param i
     * @param s
     * @param photos
     * @return
     */
    public int calculatePenalty(int i, int s, List<Photo> photos) {
        int sum_width = 0;
        for (int index = i; index < i + s; index++) {
            sum_width += getImageWidth(avg_height, photos.get(index));
        }
        return Math.abs(grid_width - sum_width + item_spacing * (s - 1));
    }


    /**
     * Divide a list of photos into an array of lists of photos where each list represent a row of photos
     * inside the grid. It uses the indexes paramter to know where to divide
     *
     * @param photos
     * @param indexes
     * @return
     */
    public PhotosGridRow[] getPhotosGridRows(List<Photo> photos, List<Integer> indexes) {
        int row_index = 0;
        PhotosGridRow[] photos_rows = new PhotosGridRow[indexes.size() + 1];
        for (int i = 0; i < photos.size(); i++) {
            if (row_index < indexes.size()) {
                if (i == indexes.get(row_index)) {
                    row_index++;
                }
            }
            if (photos_rows[row_index] == null)
                photos_rows[row_index] = new PhotosGridRow(i);
            photos_rows[row_index].add(photos.get(i));
        }
        return photos_rows;
    }

}
