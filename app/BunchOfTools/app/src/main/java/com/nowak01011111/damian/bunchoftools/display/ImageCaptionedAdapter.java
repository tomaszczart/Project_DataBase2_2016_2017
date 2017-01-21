package com.nowak01011111.damian.bunchoftools.display;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nowak01011111.damian.bunchoftools.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by utche on 30.10.2016.
 */

public class ImageCaptionedAdapter extends RecyclerView.Adapter<ImageCaptionedAdapter.ViewHolder> implements View.OnClickListener{

    private ArrayList<ViewModel> viewModels;

    private OnItemClickListener onItemClickListener;

    public ImageCaptionedAdapter(ArrayList<ViewModel> viewModels) {
        this.viewModels = viewModels;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        CardView cv = (CardView) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_image_captioned, parent, false);
        cv.setOnClickListener(this);
        return  new ViewHolder(cv);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        CardView cardView = holder.cardView;
        ImageView imageView = (ImageView)cardView.findViewById(R.id.info_image);
        TextView nameText = (TextView)cardView.findViewById(R.id.name_text);
        TextView descriptionText = (TextView)cardView.findViewById(R.id.description_text);
        TextView information1Text = (TextView)cardView.findViewById(R.id.information1_text);
        Picasso.with(holder.cardView.getContext() ).load(viewModels.get(position).getBitmapPath()).into(imageView);
        imageView.setContentDescription(viewModels.get(position).getName());

        nameText.setText(viewModels.get(position).getName());
        descriptionText.setText(viewModels.get(position).getDescription());
        information1Text.setText(viewModels.get(position).getInformation1());

        holder.cardView.setTag(viewModels.get(position));
    }

    @Override
    public int getItemCount() {
        return viewModels.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private CardView cardView;
        public ViewHolder(CardView v) {
            super(v);
            cardView = v;
        }
    }

    @Override public void onClick(final View v) {
        onItemClickListener.onItemClick(v, (ViewModel) v.getTag());
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {

        void onItemClick(View view, ViewModel viewModel);

    }
}
