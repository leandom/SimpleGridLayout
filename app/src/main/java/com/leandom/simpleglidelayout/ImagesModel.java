package com.leandom.simpleglidelayout;

import java.io.Serializable;
import java.util.List;

/**
 * Created by leandom on 2017/6/7.
 */

public class ImagesModel implements Serializable {


    private List<Row> images;

    public List<Row> getImages() {
        return images;
    }

    public void setImages(List<Row> images) {
        this.images = images;
    }

    public static class Row extends ItemBean {
        private int height;

        public int getHeight() {
            return height;
        }

        public void setHeight(int height) {
            this.height = height;
        }
    }


    public static class ItemBean implements Serializable {

        private String orientation;
        private String image;
        private List<ItemBean> children;
        private int weight;

        public String getOrientation() {
            return orientation;
        }

        public void setOrientation(String orientation) {
            this.orientation = orientation;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public List<ItemBean> getChildren() {
            return children;
        }

        public void setChildren(List<ItemBean> children) {
            this.children = children;
        }

        public boolean hasChildren() {
            return children != null && !children.isEmpty();
        }

        public int getWeight() {
            return weight;
        }

        public void setWeight(int weight) {
            this.weight = weight;
        }
    }

}
