package com.nowak01011111.damian.bunchoftools.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.content.Context;

import com.nowak01011111.damian.bunchoftools.api_client.ApiConnectionFragment;
import com.nowak01011111.damian.bunchoftools.display.ImageCaptionedAdapter;
import com.nowak01011111.damian.bunchoftools.R;
import com.nowak01011111.damian.bunchoftools.entity.Model;
import com.nowak01011111.damian.bunchoftools.display.ViewModel;

import java.util.ArrayList;

public class ModelListFragment extends Fragment implements ImageCaptionedAdapter.OnItemClickListener {
    private OnModelListFragmentInteractionListener mListener;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        RecyclerView modelsRecycler = (RecyclerView)inflater.inflate(R.layout.fragment_model_list, container, false);
        //TODO: chnage data source
        ArrayList<ViewModel> viewModels = new ArrayList<>();
        for(int i = 0; i <10; i++){
            ViewModel viewModel = new ViewModel(Integer.toString(i), 0);
            viewModels.add(viewModel);
        }

        ImageCaptionedAdapter adapter = new ImageCaptionedAdapter(viewModels);
        adapter.setOnItemClickListener(this);
        modelsRecycler.setAdapter(adapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        modelsRecycler.setLayoutManager(layoutManager);



        return modelsRecycler;
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

    public interface OnModelListFragmentInteractionListener {
        void onModelListFragmentItemClick(View view, ViewModel viewModel);
    }


}
