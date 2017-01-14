package com.nowak01011111.damian.bunchoftools.display;

import android.graphics.drawable.Drawable;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nowak01011111.damian.bunchoftools.R;

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
        //TODO delete / replace
        Drawable drawable = cardView.getResources().getDrawable(R.drawable.saw);//viewModels.get(position).getImage()); //TODO: replace deprecated
        imageView.setImageDrawable(drawable);
        imageView.setContentDescription(viewModels.get(position).getText());
        TextView textView = (TextView)cardView.findViewById(R.id.info_text);
        textView.setText(viewModels.get(position).getText());
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
