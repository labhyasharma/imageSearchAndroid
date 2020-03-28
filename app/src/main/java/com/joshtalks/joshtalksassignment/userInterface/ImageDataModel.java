package com.joshtalks.joshtalksassignment.userInterface;

import android.widget.ImageView;

import androidx.databinding.BindingAdapter;

import com.bumptech.glide.Glide;
import com.joshtalks.joshtalksassignment.R;

public class ImageDataModel {
    private String previewURL;

    public String getPreviewURL() {
        return previewURL;
    }

    public void setPreviewURL(String previewURL) {
        this.previewURL = previewURL;
    }

    @BindingAdapter("imageUrl")
    public static void loadImage(ImageView view, String url) {
        Glide.with(view.getContext()).load(url).placeholder(R.drawable.placeholder).into(view);
    }
}

