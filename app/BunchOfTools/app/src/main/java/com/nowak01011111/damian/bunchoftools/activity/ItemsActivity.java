package com.nowak01011111.damian.bunchoftools.activity;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.graphics.Palette;
import android.transition.Slide;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.support.v7.widget.Toolbar;

import com.nowak01011111.damian.bunchoftools.R;
import com.nowak01011111.damian.bunchoftools.display.SimpleViewModel;
import com.nowak01011111.damian.bunchoftools.display.ViewModel;
import com.nowak01011111.damian.bunchoftools.fragments.ItemListFragment;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Callback;

public class ItemsActivity extends AppCompatActivity implements ItemListFragment.OnFragmentInteractionListener{
    private static final String EXTRA_IMAGE = "items.extraImage";
    private static final String EXTRA_TITLE = "items.extraTitle";
    private static final String EXTRA_MODEL_ID = "items.extraModelID";
    private static final String EXTRA_DESCRIPTION = "items.extraDescription";

    private CollapsingToolbarLayout collapsingToolbarLayout;
    private int modelId;

    public static void navigate(AppCompatActivity activity, View transitionImage, ViewModel viewModel) {
        Intent intent = new Intent(activity, ItemsActivity.class);
        intent.putExtra(EXTRA_IMAGE, viewModel.getBitmapPath());
        intent.putExtra(EXTRA_TITLE, viewModel.getName());
        intent.putExtra(EXTRA_MODEL_ID, viewModel.getId());
        intent.putExtra(EXTRA_DESCRIPTION, viewModel.getDescription());

        ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(activity, transitionImage, EXTRA_IMAGE);
        ActivityCompat.startActivity(activity, intent, options.toBundle());
    }

    @SuppressWarnings("ConstantConditions")
    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initActivityTransitions();
        setContentView(R.layout.activity_items);

        ViewCompat.setTransitionName(findViewById(R.id.app_bar_layout), EXTRA_IMAGE);
        supportPostponeEnterTransition();
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));

        String titleText = getIntent().getStringExtra(EXTRA_TITLE);
        String imageUrl = getIntent().getStringExtra(EXTRA_IMAGE);
        String descriptionText = getIntent().getStringExtra(EXTRA_DESCRIPTION);
        modelId  = getIntent().getIntExtra(EXTRA_MODEL_ID, -1);

        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbarLayout.setTitle(titleText);
        collapsingToolbarLayout.setExpandedTitleColor(getResources().getColor(android.R.color.transparent));

        ImageView image = (ImageView) findViewById(R.id.image);

        Picasso.with(this).load(imageUrl).into(image, new Callback() {
            @Override public void onSuccess() {
                Bitmap bitmap = ((BitmapDrawable) image.getDrawable()).getBitmap();
                Palette.from(bitmap).generate(palette -> applyPalette(palette));
            }
            @Override public void onError() {
            }
        });

        TextView title = (TextView) findViewById(R.id.title);
        title.setText(titleText);
        TextView description = (TextView) findViewById(R.id.description);
        description.setText(descriptionText);

        Fragment fragment = ItemListFragment.newInstance(modelId);
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.content_frame, fragment, "visible_fragment");
        ft.commit();
    }

    @Override public boolean dispatchTouchEvent(MotionEvent motionEvent) {
        try {
            return super.dispatchTouchEvent(motionEvent);
        } catch (NullPointerException e) {
            return false;
        }
    }

    private void initActivityTransitions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Slide transition = new Slide();
            transition.excludeTarget(android.R.id.statusBarBackground, true);
            getWindow().setEnterTransition(transition);
            getWindow().setReturnTransition(transition);
        }
    }

    private void applyPalette(Palette palette) {
        int primaryDark = getResources().getColor(R.color.blue_grey_700);
        int primary = getResources().getColor(R.color.blue_grey_500);
        collapsingToolbarLayout.setContentScrimColor(palette.getMutedColor(primary));
        collapsingToolbarLayout.setStatusBarScrimColor(palette.getDarkMutedColor(primaryDark));
        updateBackground((FloatingActionButton) findViewById(R.id.fab), palette);
        supportStartPostponedEnterTransition();
    }

    private void updateBackground(FloatingActionButton fab, Palette palette) {
        int lightVibrantColor = palette.getLightVibrantColor(getResources().getColor(android.R.color.white));
        int vibrantColor = palette.getVibrantColor(getResources().getColor(R.color.amber_500));

        fab.setRippleColor(lightVibrantColor);
        fab.setBackgroundTintList(ColorStateList.valueOf(vibrantColor));
    }

    @Override
    public void onFragmentInteraction(View view, SimpleViewModel simpleViewModel) {
        Snackbar.make(findViewById(R.id.content_frame), "FAB Clicked", Snackbar.LENGTH_SHORT).show();
    }
}