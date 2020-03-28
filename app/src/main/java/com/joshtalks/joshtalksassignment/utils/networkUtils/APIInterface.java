package com.joshtalks.joshtalksassignment.utils.networkUtils;

import com.joshtalks.joshtalksassignment.userInterface.ImageTopDataModel;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;

/**
 * Interface to list the APIs.
 *
 * @author Labhya Sharma
 */
public interface APIInterface {

    @GET("api/")
    Call<ImageTopDataModel> getImageList(@Query("key") String userKey, @Query("q") String searchKey, @Query("page") int page, @Query("per_page") int limit);
}
