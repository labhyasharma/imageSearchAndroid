package com.joshtalks.joshtalksassignment.userInterface;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.joshtalks.joshtalksassignment.utils.BaseAdapter;
import com.joshtalks.joshtalksassignment.utils.BaseViewModel;
import com.joshtalks.joshtalksassignment.utils.Constants;
import com.joshtalks.joshtalksassignment.utils.networkUtils.APIRepository;
import com.joshtalks.joshtalksassignment.utils.networkUtils.APIUtil;
import com.joshtalks.joshtalksassignment.utils.networkUtils.ApiObserverDataModel;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Viewmodel class to handle the business logic
 * of Image list.
 *
 * @author Labhya Sharma
 */
public class ImageSearchViewModel extends BaseViewModel {

    private ImageListAdapter adapter;
    private List<ImageDataModel> imageListData = new ArrayList<>();
    private MutableLiveData<List<ImageDataModel>> imageList;
    private final MutableLiveData<String> searchText = new MutableLiveData<>();

    private Callback<ImageTopDataModel> apiGetImageListCallBack;
    private final MutableLiveData<ApiObserverDataModel> imageListFetchSuccess = new MutableLiveData<>();

    public void init(BaseAdapter.Listener listener) {
        adapter = new ImageListAdapter(this, imageListData, listener);
    }

    public ImageSearchViewModel() {
        apiGetImageListCallBack = new Callback<ImageTopDataModel>() {
            @Override
            @SuppressWarnings("unchecked")
            public void onResponse(@NonNull Call<ImageTopDataModel> call, @NonNull Response<ImageTopDataModel> response) {
                if (response.isSuccessful()) {
                    imageListFetchSuccess.setValue(new ApiObserverDataModel(response.body(), ""));
                } else {
                    String errorMessage = APIUtil.parseError(response);
                    setResponseError(errorMessage);
                }
            }

            @Override
            public void onFailure(@NonNull Call<ImageTopDataModel> call, @NonNull Throwable t) {
                handleFailure(t);
                setResponseError(Constants.SERVER_ERROR);
            }
        };

    }

    /**
     * This function is used to hit the Api.
     */
    public void fetchImageList(String searchKey, int page, int count) {
        APIRepository.getRepositoryInstance().getImageList(searchKey, page, count).enqueue(apiGetImageListCallBack);
    }

    public MutableLiveData<List<ImageDataModel>> getImageListData() {
        if (imageList == null) {
            imageList = new MutableLiveData<>();
        }
        return imageList;
    }

    public ImageListAdapter getAdapter() {
        return adapter;
    }

    /**
     * This function is set the data to adapter after the
     * sorting. Also, iterating the map and creating the
     * header based on their key.
     */
    public void setAdapter(List<ImageDataModel> imageList) {

        this.adapter.setImageData(imageList);
        this.adapter.notifyDataSetChanged();
    }

    public MutableLiveData<String> getSearchText() {
        return searchText;
    }

    public MutableLiveData<ApiObserverDataModel> getClientListFetchSuccess() {
        return imageListFetchSuccess;
    }

    /**
     * This method is used fetch data from server
     */
    public void setDataInViewModel(List<ImageDataModel> imageData, int page) {
        if (imageList == null) {
            imageList = new MutableLiveData<>();
        }

        if (page == 1) {
            imageListData = imageData;
        } else {
            imageListData.addAll(imageData);
        }

        imageList.setValue(imageListData);
    }

}
