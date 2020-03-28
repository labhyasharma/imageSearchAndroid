package com.joshtalks.joshtalksassignment.userInterface;

import com.joshtalks.joshtalksassignment.utils.networkUtils.BaseApiResponseDataModel;

import java.util.List;

/**
 * Data model class to handle list
 *
 * @author Labhya Sharma
 */
public class ImageTopDataModel extends BaseApiResponseDataModel {
    private List<ImageDataModel> hits;

    public List<ImageDataModel> getHits() {
        return hits;
    }

    public void setHits(List<ImageDataModel> hits) {
        this.hits = hits;
    }
}
