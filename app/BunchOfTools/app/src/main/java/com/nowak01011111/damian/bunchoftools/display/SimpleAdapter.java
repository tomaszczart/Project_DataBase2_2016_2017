package com.nowak01011111.damian.bunchoftools.display;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.nowak01011111.damian.bunchoftools.R;

import java.util.ArrayList;

/**
 * Created by utche on 06.01.2017.
 */

public class SimpleAdapter extends RecyclerView.Adapter<SimpleAdapter.ViewHolder> implements View.OnClickListener{

    private ArrayList<SimpleViewModel> simpleViewModels;

    private OnItemClickListener onItemClickListener;

    public SimpleAdapter(ArrayList<SimpleViewModel> simpleViewModels) {
        this.simpleViewModels = simpleViewModels;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        CardView cv = (CardView) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_simple, parent, false);
        cv.setOnClickListener(this);
        return  new ViewHolder(cv);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        CardView cardView = holder.cardView;
        TextView nameText = (TextView)cardView.findViewById(R.id.name_text);
        TextView descriptionText = (TextView)cardView.findViewById(R.id.description_text);

        nameText.setText(simpleViewModels.get(position).getName());
        descriptionText.setText(simpleViewModels.get(position).getDescription());

        holder.cardView.setTag(simpleViewModels.get(position));
    }

    @Override
    public int getItemCount() {
        return simpleViewModels.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private CardView cardView;
        public ViewHolder(CardView v) {
            super(v);
            cardView = v;
        }
    }

    @Override public void onClick(final View v) {
        onItemClickListener.onItemClick(v, (SimpleViewModel) v.getTag());
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {

        void onItemClick(View view, SimpleViewModel viewModel);

    }
}
