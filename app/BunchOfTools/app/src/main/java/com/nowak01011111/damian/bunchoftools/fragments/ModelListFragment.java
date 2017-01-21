package com.nowak01011111.damian.bunchoftools.fragments;

import android.app.ProgressDialog;
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
import android.content.Context;

import com.nowak01011111.damian.bunchoftools.activity.MainActivity;
import com.nowak01011111.damian.bunchoftools.api_client.ApiConnectionFragment;
import com.nowak01011111.damian.bunchoftools.api_client.ApiTaskCallback;
import com.nowak01011111.damian.bunchoftools.api_client.functionalities.Models;
import com.nowak01011111.damian.bunchoftools.display.ImageCaptionedAdapter;
import com.nowak01011111.damian.bunchoftools.R;
import com.nowak01011111.damian.bunchoftools.entity.Model;
import com.nowak01011111.damian.bunchoftools.display.ViewModel;

import java.util.ArrayList;
import java.util.List;

public class ModelListFragment extends Fragment implements ImageCaptionedAdapter.OnItemClickListener, ApiTaskCallback {
    private OnModelListFragmentInteractionListener mListener;

    private static final String ARG_CATEGORY_ID = "category_id";
    private int mCategoryId;
    private ApiConnectionFragment mApiConnectionFragment;
    private ProgressDialog progressDialog;

    private boolean mConnectionInProgress = false;
    private List<Model> models;
    private ArrayList<ViewModel> viewModels;
    private ImageCaptionedAdapter adapter;


    public static ModelListFragment newInstance(int categoryId) {
        ModelListFragment fragment = new ModelListFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_CATEGORY_ID, categoryId);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        RecyclerView modelsRecycler = (RecyclerView) inflater.inflate(R.layout.fragment_model_list, container, false);
        if (getArguments() != null) {
            mCategoryId = getArguments().getInt(ARG_CATEGORY_ID);
        } else {
            mCategoryId = -1;
        }
        viewModels = new ArrayList<>();
        models = new ArrayList<>();
        adapter = new ImageCaptionedAdapter(viewModels);
        adapter.setOnItemClickListener(this);
        modelsRecycler.setAdapter(adapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        modelsRecycler.setLayoutManager(layoutManager);

        mApiConnectionFragment = ApiConnectionFragment.getInstance(getChildFragmentManager(), this);

        getModelsFromApi();

        return modelsRecycler;
    }

    private void getModelsFromApi() {
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setTitle(MainActivity.LOADING_TITLE);
        progressDialog.setMessage(MainActivity.LOADING_MESSAGE);
        progressDialog.setCancelable(false);
        progressDialog.show();
        if (!mConnectionInProgress && mApiConnectionFragment != null) {
            if (mCategoryId != -1) {
                mApiConnectionFragment.getModels(getActivity(), mCategoryId);
            } else {
                mApiConnectionFragment.getModels(getActivity());
            }
            mConnectionInProgress = true;
        }
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnModelListFragmentInteractionListener) {
            mListener = (OnModelListFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onItemClick(View view, ViewModel viewModel) {
        if (mListener != null) {
            mListener.onModelListFragmentItemClick(view.findViewById(R.id.info_image), viewModel);
        }

    }

    @Override
    public void updateFromDownload(String result, String error) {
        if (error != null && !error.isEmpty()) {
            Snackbar.make(getView(), error, Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
        } else {
            models = Models.parseResult(result);
            for (Model model: models ){
                ViewModel viewModel = new ViewModel(model.getName(), model.getDescription(), Float.toString(model.getPricePerHour()), model.getImageUrl(), model.getId());
                viewModels.add(viewModel);
            }
            progressDialog.dismiss();
            adapter.notifyDataSetChanged();
        }
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
        switch (progressCode) {
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

    public interface OnModelListFragmentInteractionListener {
        void onModelListFragmentItemClick(View view, ViewModel viewModel);
    }


}
