package com.joshtalks.joshtalksassignment.userInterface;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.joshtalks.joshtalksassignment.R;
import com.joshtalks.joshtalksassignment.databinding.ActivityMainBinding;
import com.joshtalks.joshtalksassignment.utils.BaseAdapter;

import java.util.List;

/**
 * Activity to handle the list of Image
 *
 * @author Labhya Sharma
 */
public class ImageSearchActivity extends AppCompatActivity implements BaseAdapter.Listener<ImageDataModel> {

    private ImageSearchViewModel mImageSearchViewModel;
    private ActivityMainBinding mActivityMainBinding;
    private MutableLiveData<List<ImageDataModel>> clientDataList;
    private String searchString = "";
    private boolean loading = true;
    int pastVisiblesItems, visibleItemCount, totalItemCount, limit = 20 , page = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        mActivityMainBinding.setLifecycleOwner(this);
        mImageSearchViewModel = ViewModelProviders.of(this).get(ImageSearchViewModel.class);

        mActivityMainBinding.setViewModel(mImageSearchViewModel);
        mImageSearchViewModel.init(this);

        LinearLayoutManager mLayoutManager = new LinearLayoutManager(mActivityMainBinding.mRecyclerView.getContext(), LinearLayoutManager.VERTICAL, false);
        mActivityMainBinding.mRecyclerView.setLayoutManager(mLayoutManager);
        mActivityMainBinding.mRecyclerView.setAdapter(mImageSearchViewModel.getAdapter());

        clientDataList = mImageSearchViewModel.getImageListData();
        clientDataList.observe(this, clientList -> mImageSearchViewModel.setAdapter(clientList));

        mImageSearchViewModel.getClientListFetchSuccess().observe(this, apiObserverDataModel -> {
            hideLoader();
            loading = true;
            ImageTopDataModel imageTopDataModel = (ImageTopDataModel) apiObserverDataModel.getResponseDataModel();
            if (imageTopDataModel != null) {
                @SuppressWarnings("unchecked") List<ImageDataModel> clientData = imageTopDataModel.getHits();
                mImageSearchViewModel.setDataInViewModel(clientData,page);
            }
        });

        mImageSearchViewModel.getResponseError().observe(this, error -> {
            hideLoader();
            loading = true;
            Toast toast = Toast.makeText(this, error, Toast.LENGTH_LONG);
            toast.show();
        });

        mActivityMainBinding.etsearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                showLoader();
                afterEmailTextChanged(charSequence);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        mActivityMainBinding.mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if (dy > 0) { //check for scroll down
                    visibleItemCount = mLayoutManager.getChildCount();
                    totalItemCount = mLayoutManager.getItemCount();
                    pastVisiblesItems = mLayoutManager.findFirstVisibleItemPosition();

                    Log.d("TAG", "onScrolled: 0");
                    if (loading) {
                        if ((visibleItemCount + pastVisiblesItems) >= totalItemCount) {
                            Log.d("TAG", "onScrolled: 0");
                            loading = false;
                            page++;
                            fetchDataFromServer(searchString);
                        }
                    }
                }
            }
        });

        fetchDataFromServer("");

        showLoader();
    }

    private void fetchDataFromServer(String s) {
        mImageSearchViewModel.fetchImageList(s,page,limit);
    }

    @Override
    public void onItemClick(View view, ImageDataModel item, int clickType) {
        //TODO Handle the click.
    }

    /**
     * This function is used to hide the loader
     */
    private void showLoader() {
        mActivityMainBinding.progressBar.setVisibility(View.VISIBLE);
    }

    /**
     * This function is used to show the loader
     */
    private void hideLoader() {
        mActivityMainBinding.progressBar.setVisibility(View.GONE);
    }

    /**
     * This function is used to call the Image list.
     */
    public void afterEmailTextChanged(CharSequence s) {
        searchString = s.toString();
        page = 1;
       fetchDataFromServer(s.toString());
    }
}
