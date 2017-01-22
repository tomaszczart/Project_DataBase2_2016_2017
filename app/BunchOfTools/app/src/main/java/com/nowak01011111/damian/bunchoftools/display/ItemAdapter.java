package com.nowak01011111.damian.bunchoftools.display;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.nowak01011111.damian.bunchoftools.R;
import com.nowak01011111.damian.bunchoftools.entity.Item;

import java.util.ArrayList;

/**
 * Created by utche on 21.01.2017.
 */

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ViewHolder> implements View.OnClickListener{

    private ArrayList<ItemViewModel> itemViewModels;

    private ItemAdapter.OnItemClickListener onItemClickListener;

    public ItemAdapter(ArrayList<ItemViewModel> itemViewModels) {
        this.itemViewModels = itemViewModels;
    }

    @Override
    public ItemAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        CardView cv = (CardView) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_item, parent, false);
        cv.setOnClickListener(this);
        return  new ItemAdapter.ViewHolder(cv);
    }

    @Override
    public void onBindViewHolder(ItemAdapter.ViewHolder holder, int position) {
        CardView cardView = holder.cardView;
        TextView information1 = (TextView)cardView.findViewById(R.id.information1_text);
        TextView information2 = (TextView)cardView.findViewById(R.id.information2_text);
        TextView information3 = (TextView)cardView.findViewById(R.id.information3_text);
        String reservationInformation = itemViewModels.get(position).getInformation2();
        information1.setText(Integer.toString(itemViewModels.get(position).getId()));
        information2.setText(itemViewModels.get(position).getInformation1());
        information3.setText(itemViewModels.get(position).getInformation2());

        Button bookItem = (Button) cardView.findViewById(R.id.button_reserve);
        bookItem.setOnClickListener(v -> {
            String id  = information1.getText().toString();
            onItemClickListener.onReservationClick(Integer.parseInt(id));
        });

        if(reservationInformation.equals(Item.Status.Reserved.toString()) || reservationInformation.equals(Item.Status.Rent.toString()))
            bookItem.setEnabled(false);
        else
            bookItem.setEnabled(true);

        holder.cardView.setTag(itemViewModels.get(position));
    }

    @Override
    public int getItemCount() {
        return itemViewModels.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private CardView cardView;
        public ViewHolder(CardView v) {
            super(v);
            cardView = v;
        }
    }

    @Override public void onClick(final View v) {
        //onItemClickListener.onItemClick(v, (ItemViewModel) v.getTag());
    }

    public void setOnItemClickListener(ItemAdapter.OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {

        void onItemClick(View view, ItemViewModel viewModel);
        void onReservationClick(int itemId);
    }
}
