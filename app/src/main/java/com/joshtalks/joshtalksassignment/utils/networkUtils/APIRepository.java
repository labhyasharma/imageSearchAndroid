package com.joshtalks.joshtalksassignment.utils.networkUtils;

import com.joshtalks.joshtalksassignment.AppRoot;
import com.joshtalks.joshtalksassignment.userInterface.ImageTopDataModel;

import retrofit2.Call;

import static com.joshtalks.joshtalksassignment.utils.Constants.API_KEY;

/**
 * API repository to handle the implementation of
 * the apis and passing the relevant data to them.
 *
 * @author Labhya Sharma
 */
public class APIRepository {

    private static APIRepository mApiRepository;
    private static APIInterface mApiInterface;

    /**
     * Method to get instance of this class
     */
    public static APIRepository getRepositoryInstance() {
        if (mApiRepository == null) {
            mApiRepository = new APIRepository();
        }
        if (mApiInterface == null) {
            mApiInterface = RetrofitClientInstance.getRetrofitInstance(AppRoot.getInstance()).create(APIInterface.class);
        }
        return mApiRepository;
    }

    private APIRepository() {
    }

    public Call<ImageTopDataModel> getImageList(String searchKey, int page, int limit) {
        return mApiInterface.getImageList(API_KEY, searchKey, page, limit);
    }
}
