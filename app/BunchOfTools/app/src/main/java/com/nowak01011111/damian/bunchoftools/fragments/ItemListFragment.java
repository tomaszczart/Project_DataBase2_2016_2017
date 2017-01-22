package com.nowak01011111.damian.bunchoftools.fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
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
import com.nowak01011111.damian.bunchoftools.api_client.functionalities.Items;
import com.nowak01011111.damian.bunchoftools.display.ItemAdapter;
import com.nowak01011111.damian.bunchoftools.display.ItemViewModel;
import com.nowak01011111.damian.bunchoftools.display.SimpleViewModel;
import com.nowak01011111.damian.bunchoftools.entity.Item;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class ItemListFragment extends Fragment implements ItemAdapter.OnItemClickListener, ApiTaskCallback {
    private ItemListFragment.OnFragmentInteractionListener mListener;

    private static final String ARG_MODEL_ID = "model_id";
    private int mModelId;
    private ApiConnectionFragment mApiConnectionFragment;
    private ProgressDialog progressDialog;

    private boolean mConnectionInProgress = false;
    private List<Item> items;
    private ArrayList<ItemViewModel> itemViewModels;
    private ItemAdapter adapter;


    public static ItemListFragment newInstance(int modelId) {
        ItemListFragment fragment = new ItemListFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_MODEL_ID, modelId);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        RecyclerView itemsListRecycler = (RecyclerView) inflater.inflate(R.layout.fragment_item_list, container, false);
        if (getArguments() != null) {
            mModelId = getArguments().getInt(ARG_MODEL_ID);
        } else {
            mModelId = -1;
        }
        itemViewModels = new ArrayList<>();
        items = new ArrayList<>();
        adapter = new ItemAdapter(itemViewModels);
        adapter.setOnItemClickListener(this);
        itemsListRecycler.setAdapter(adapter);
        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(),2);
        itemsListRecycler.setLayoutManager(layoutManager);

        mApiConnectionFragment = ApiConnectionFragment.getInstance(getChildFragmentManager(), this);

        getItemsFromApi();

        return itemsListRecycler;
    }

    private void getItemsFromApi() {
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setTitle(MainActivity.LOADING_TITLE);
        progressDialog.setMessage(MainActivity.LOADING_MESSAGE);
        progressDialog.setCancelable(false);
        progressDialog.show();
        if (!mConnectionInProgress && mApiConnectionFragment != null) {
            if (mModelId != -1) {
                mApiConnectionFragment.getItems(getActivity(), mModelId);
            } else {
                mApiConnectionFragment.getItems(getActivity());
            }
            mConnectionInProgress = true;
        }
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof ItemListFragment.OnFragmentInteractionListener) {
            mListener = (ItemListFragment.OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void updateFromDownload(String result, String error) {
        if (error != null && !error.isEmpty()) {
            Snackbar.make(getView(), error, Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
        } else {
            items = Items.parseResult(result);
            for (Item item : items) {
                ItemViewModel itemViewModel = new ItemViewModel(item.getCondition().toString(),
                        item.getStatus().toString(),item.getId());
                itemViewModels.add(itemViewModel);
            }
            adapter.notifyDataSetChanged();
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

    @Override
    public void onItemClick(View view, ItemViewModel itemViewModel) {
      //  mListener.onFragmentInteraction(mModelId, itemViewModel);
    }

    @Override
    public void onReservationClick(int itemId) {
        mListener.onFragmentInteraction(itemId);
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(int itemId);

    }
}