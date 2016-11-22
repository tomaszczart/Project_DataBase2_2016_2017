package com.nowak01011111.damian.bunchoftools;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

public class ToolsFragment extends Fragment implements ImageCaptionedAdapter.OnItemClickListener {
    private Activity parentActivity;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        RecyclerView toolsRecycler = (RecyclerView)inflater.inflate(R.layout.fragment_tools, container, false);
        //TODO: chnage data source
        ArrayList<ViewModel> viewModels = new ArrayList<>();
        String[] toolsNames = new String[Tools.tools.length];
        for(int i = 0; i <toolsNames.length; i++){
            ViewModel viewModel = new ViewModel(Tools.tools[i].getName(), Tools.tools[i].getImageResourceId());
            viewModels.add(viewModel);
        }

        ImageCaptionedAdapter adapter = new ImageCaptionedAdapter(viewModels);
        adapter.setOnItemClickListener(this);
        toolsRecycler.setAdapter(adapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        toolsRecycler.setLayoutManager(layoutManager);
        return toolsRecycler;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        parentActivity = activity;
    }

    @Override
    public void onItemClick(View view, ViewModel viewModel) {
        ItemsActivity.navigate( (android.support.v7.app.AppCompatActivity)parentActivity, view.findViewById(R.id.info_image), viewModel);
    }
}
