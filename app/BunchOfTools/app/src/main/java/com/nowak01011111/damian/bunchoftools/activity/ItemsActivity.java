package com.nowak01011111.damian.bunchoftools.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
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
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.support.v7.widget.Toolbar;

import com.nowak01011111.damian.bunchoftools.R;
import com.nowak01011111.damian.bunchoftools.api_client.ApiConnectionFragment;
import com.nowak01011111.damian.bunchoftools.api_client.ApiTaskCallback;
import com.nowak01011111.damian.bunchoftools.authorization.InAppAuthorization;
import com.nowak01011111.damian.bunchoftools.display.ItemViewModel;
import com.nowak01011111.damian.bunchoftools.display.ViewModel;
import com.nowak01011111.damian.bunchoftools.fragments.ItemListFragment;
import com.nowak01011111.damian.bunchoftools.activity.MainActivity;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Callback;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class ItemsActivity extends AppCompatActivity implements ItemListFragment.OnFragmentInteractionListener, ApiTaskCallback {
    private static final String ERROR_SIGN_IN = "Error: You need to be signed in.";
    private static final String ERROR_CUSTOMER_AUTHORIZATION = "Error: Only customers can book item.";
    private static final String MESSAGE_BOOK_OK = "Item booked to do date: ";
    private static final String EXTRA_IMAGE = "items.extraImage";
    private static final String EXTRA_TITLE = "items.extraTitle";
    private static final String EXTRA_MODEL_ID = "items.extraModelID";
    private static final String EXTRA_DESCRIPTION = "items.extraDescription";

    private CollapsingToolbarLayout collapsingToolbarLayout;
    private int modelId;
    private ApiConnectionFragment mApiConnectionFragment;
    private boolean mConnectionInProgress = false;
    ProgressDialog progressDialog;
    private String lastReservationDate;
    private boolean reloadData;

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

        mApiConnectionFragment = ApiConnectionFragment.getInstance(getSupportFragmentManager(),this);

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
    public void onFragmentInteraction(int itemId) {
        if(InAppAuthorization.isUserLoggedIn(this))
        {
            Calendar cal = Calendar.getInstance();
            cal.add(Calendar.DATE, 7);
            SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S");
            lastReservationDate = format1.format(cal.getTime());
            makeReservation(itemId, lastReservationDate);
        }
        else if (InAppAuthorization.isEmployeeLoggedIn(this))
        {
            Snackbar.make(findViewById(R.id.content_frame), ERROR_CUSTOMER_AUTHORIZATION, Snackbar.LENGTH_LONG).show();
        }else{
            Snackbar.make(findViewById(R.id.content_frame), ERROR_SIGN_IN, Snackbar.LENGTH_LONG).show();
        }

    }

    private void makeReservation(int itemId, String date ){
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle(MainActivity.LOADING_TITLE);
        progressDialog.setMessage(MainActivity.LOADING_MESSAGE);
        progressDialog.setCancelable(false);
        progressDialog.show();
        if (!mConnectionInProgress && mApiConnectionFragment != null) {
            mApiConnectionFragment.makeReservation(this, itemId, date);
            mConnectionInProgress = true;
        }
    }


    @Override
    public void updateFromDownload(String result, String error) {
        if (error != null && !error.isEmpty()) {
            Snackbar.make(findViewById(R.id.content_frame), error, Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
        } else {
            Snackbar.make(findViewById(R.id.content_frame), MESSAGE_BOOK_OK + lastReservationDate, Snackbar.LENGTH_LONG).show();
            reloadData = true;
        }
        progressDialog.dismiss();
    }

    @Override
    public NetworkInfo getActiveNetworkInfo() {
        ConnectivityManager connectivityManager =
                (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
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

    private void loadItemListFragemnt(){
        Fragment fragment = ItemListFragment.newInstance(modelId);
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.content_frame, fragment, "visible_fragment");
        ft.commit();
    }

    @Override
    public void finishDownloading() {
        mConnectionInProgress = false;
        if (mApiConnectionFragment != null) {
            mApiConnectionFragment.cancelDownload();
        }
        if(reloadData){
            reloadData = false;
            loadItemListFragemnt();
        }
    }
}