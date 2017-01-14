package com.nowak01011111.damian.bunchoftools.fragments;


import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nowak01011111.damian.bunchoftools.R;
import com.nowak01011111.damian.bunchoftools.activity.MainActivity;
import com.nowak01011111.damian.bunchoftools.api_client.ApiConnectionFragment;
import com.nowak01011111.damian.bunchoftools.api_client.ApiTaskCallback;
import com.nowak01011111.damian.bunchoftools.api_client.functionalities.Categories;
import com.nowak01011111.damian.bunchoftools.display.SimpleAdapter;
import com.nowak01011111.damian.bunchoftools.display.SimpleViewModel;
import com.nowak01011111.damian.bunchoftools.entity.Category;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class CategoryListFragment extends Fragment implements SimpleAdapter.OnItemClickListener, ApiTaskCallback {
    private CategoryListFragment.OnFragmentInteractionListener mListener;
    private ApiConnectionFragment mApiConnectionFragment;
    private ProgressDialog progressDialog;
    private boolean mConnectionInProgress = false;
    private List<Category> categories;
    private ArrayList<SimpleViewModel> simpleViewModels;
    private SimpleAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        RecyclerView categoryListRecycler = (RecyclerView)inflater.inflate(R.layout.fragment_category_list, container, false);

        categories = new ArrayList<>();
        simpleViewModels = new ArrayList<>();

        adapter = new SimpleAdapter(simpleViewModels);
        adapter.setOnItemClickListener(this);
        categoryListRecycler.setAdapter(adapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        categoryListRecycler.setLayoutManager(layoutManager);

        mApiConnectionFragment = ApiConnectionFragment.getInstance(getChildFragmentManager(),this);

        getCategoryFromApi();
        return categoryListRecycler;
    }

    private void getCategoryFromApi(){
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setTitle(MainActivity.LOADING_TITLE);
        progressDialog.setMessage(MainActivity.LOADING_MESSAGE);
        progressDialog.setCancelable(false);
        progressDialog.show();
        if (!mConnectionInProgress && mApiConnectionFragment != null) {
            mApiConnectionFragment.getCategories(getActivity());
            mConnectionInProgress = true;
        }
    }

    private void updateViewModels(){
        for (Category category:categories) {
            SimpleViewModel simpleViewModel = new SimpleViewModel(category.getName(),category.getDescription(), category.getId());
            simpleViewModels.add(simpleViewModel);
        }
        adapter.notifyDataSetChanged();
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof CategoryListFragment.OnFragmentInteractionListener) {
            mListener = (CategoryListFragment.OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onItemClick(View view, SimpleViewModel simpleViewModel) {
        if (mListener != null) {
            mListener.onFragmentInteraction(simpleViewModel);
        }
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(SimpleViewModel simpleViewModel);
    }

    @Override
    public void updateFromDownload(String result, String error) {
        if(error != null && !error.isEmpty()){
            Snackbar.make(getView(), error, Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
        }else{
            categories = Categories.parseResult(result);
            updateViewModels();
        }
        progressDialog.dismiss();
    }

    @Override
    public NetworkInfo getActiveNetworkInfo() {
        ConnectivityManager connectivityManager =
                (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return networkInfo;
    }

    @Override
    public void onProgressUpdate(int progressCode, int percentComplete) {
        switch(progressCode) {
            case Progress.ERROR:
                Log.d("LoginProgress", "ERROR");
                break;
            case Progress.CONNECT_SUCCESS:
                Log.d("LoginProgress", "CONNECT_SUCCESS");
                break;
            case Progress.GET_INPUT_STREAM_SUCCESS:
                Log.d("LoginProgress", "GET_INPUT_STREAM_SUCCESS");
                break;
            case Progress.PROCESS_INPUT_STREAM_IN_PROGRESS:
                Log.d("LoginProgress", "PROCESS_INPUT_STREAM_IN_PROGRESS");
                break;
            case Progress.PROCESS_INPUT_STREAM_SUCCESS:
                Log.d("LoginProgress", "PROCESS_INPUT_STREAM_SUCCESS");
                break;
        }
        Log.d("LoginProgress", "percentComplete");
    }

    @Override
    public void finishDownloading() {
        mConnectionInProgress = false;
        if (mApiConnectionFragment != null) {
            mApiConnectionFragment.cancelDownload();
        }
    }
}