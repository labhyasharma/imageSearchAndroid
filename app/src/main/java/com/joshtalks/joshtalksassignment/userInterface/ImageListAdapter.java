package com.joshtalks.joshtalksassignment.userInterface;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;

import com.joshtalks.joshtalksassignment.BR;
import com.joshtalks.joshtalksassignment.R;
import com.joshtalks.joshtalksassignment.databinding.ImageItemBinding;
import com.joshtalks.joshtalksassignment.utils.BaseAdapter;
import com.joshtalks.joshtalksassignment.utils.BaseViewModel;

import java.util.List;

/**
 * This is the Adapter class to handle the
 * list of Image and display them.
 *
 * @author Labhya Sharma
 */
public class ImageListAdapter extends BaseAdapter<ImageDataModel> {

    private List<ImageDataModel> imageData;
    private final ImageSearchViewModel viewModel;

    public ImageListAdapter(ImageSearchViewModel viewModel, List<ImageDataModel> imageData, BaseAdapter.Listener listener) {
        super(viewModel, imageData, listener);
        this.viewModel = viewModel;
        this.imageData = imageData;
    }

    @Override
    public int getItemCount() {
        return imageData == null ? 0 : imageData.size();
    }

    @Override
    public int getItemViewType(int position) {
        return R.layout.image_item;
    }

    @NonNull
    public GenericViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        ImageItemBinding binding = DataBindingUtil.inflate(layoutInflater, viewType, parent, false);

        return new ImageViewHolder(binding, listener);

    }

    void setImageData(List<ImageDataModel> imageData) {
        this.imageData = imageData;
    }

    class ImageViewHolder extends GenericViewHolder {
        final ImageItemBinding binding;

        ImageViewHolder(ImageItemBinding binding, Listener l) {
            super(binding, l);
            this.binding = binding;
        }

        @SuppressWarnings("unused")
        @Override
        public void bind(BaseViewModel baseViewModel, Integer position) {
            binding.setVariable(BR.viewModel, viewModel);
            binding.executePendingBindings();
            binding.setListener(listener);
            binding.setModel(imageData.get(position));
        }
    }
}
