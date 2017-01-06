package com.nowak01011111.damian.bunchoftools.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nowak01011111.damian.bunchoftools.R;
import com.nowak01011111.damian.bunchoftools.display.SimpleAdapter;
import com.nowak01011111.damian.bunchoftools.display.SimpleViewModel;
import com.nowak01011111.damian.bunchoftools.display.ViewModel;
import com.nowak01011111.damian.bunchoftools.entity.Item;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class ItemListFragment extends Fragment implements SimpleAdapter.OnItemClickListener {
    private ItemListFragment.OnFragmentInteractionListener mListener;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        RecyclerView itemsListRecycler = (RecyclerView)inflater.inflate(R.layout.fragment_item_list, container, false);

        //TODO: chnage data source
        ArrayList<SimpleViewModel> simpleViewModels = new ArrayList<>();
        for(int i = 0; i < Item.items.length; i++){
            SimpleViewModel simpleViewModel = new SimpleViewModel( Integer.toString(Item.items[i].getId()));
            simpleViewModels.add(simpleViewModel);
        }

        SimpleAdapter adapter = new SimpleAdapter(simpleViewModels);
        adapter.setOnItemClickListener(this);
        itemsListRecycler.setAdapter(adapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        itemsListRecycler.setLayoutManager(layoutManager);
        return itemsListRecycler;
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
    public void onItemClick(View view, SimpleViewModel simpleViewModel) {
        if (mListener != null) {
            mListener.onFragmentInteraction(view.findViewById(R.id.info_text), simpleViewModel);
        }
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(View app_bar_layout, SimpleViewModel simpleViewModel);
    }
}