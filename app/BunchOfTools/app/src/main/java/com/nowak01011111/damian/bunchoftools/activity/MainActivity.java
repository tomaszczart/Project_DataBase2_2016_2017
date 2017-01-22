package com.nowak01011111.damian.bunchoftools.activity;


import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;

import com.nowak01011111.damian.bunchoftools.R;
import com.nowak01011111.damian.bunchoftools.api_client.ApiConnectionFragment;
import com.nowak01011111.damian.bunchoftools.api_client.ApiTaskCallback;
import com.nowak01011111.damian.bunchoftools.api_client.functionalities.Login;
import com.nowak01011111.damian.bunchoftools.authorization.InAppAuthorization;
import com.nowak01011111.damian.bunchoftools.display.SimpleViewModel;
import com.nowak01011111.damian.bunchoftools.display.ViewModel;
import com.nowak01011111.damian.bunchoftools.fragments.CategoryListFragment;
import com.nowak01011111.damian.bunchoftools.fragments.LoginFragment;
import com.nowak01011111.damian.bunchoftools.fragments.ModelListFragment;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, ModelListFragment.OnModelListFragmentInteractionListener, LoginFragment.OnLoginFragmentInteractionListener, ApiTaskCallback, CategoryListFragment.OnFragmentInteractionListener {

    public static final int SIGN_UP_REQUEST = 10;
    public static final String LOADING_TITLE = "Loading";
    public static final String LOADING_MESSAGE = "Wait while loading...";

    private static final String SIGN_UP_RESULT_CODE_OK_MESSAGE = "Sign up successful";
    private static final String SIGN_UP_RESULT_CODE_NOT_OK_MESSAGE = "Sign up failed.";

    private Menu menu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        mApiConnectionFragment = ApiConnectionFragment.getInstance(getSupportFragmentManager(), this);


        if(menu!=null){
            if(InAppAuthorization.isEmployeeLoggedIn(this))
                menu.findItem(R.id.action_cms).setVisible(true);
            else
                menu.findItem(R.id.action_cms).setVisible(false);
        }

        if (InAppAuthorization.isUserLoggedIn(this) || InAppAuthorization.isEmployeeLoggedIn(this)) {
            showCategoryListFragment();
        } else {
            showLoginFragment();
        }
    }

    private void showLoginFragment() {
        Fragment fragment = new LoginFragment();
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.content_frame, fragment, "login_fragment");
        ft.commit();
    }

    private void showCategoryListFragment() {
        Fragment fragment = new CategoryListFragment();
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.content_frame, fragment, "category_fragment");
        ft.commit();
    }

    private void showModelListFragment(int categoryId) {
        Fragment fragment = ModelListFragment.newInstance(categoryId);
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.content_frame, fragment, "model_fragment");
        ft.addToBackStack(null);
        ft.commit();
    }

    private void startSignUpActivity() {
        Intent intent = new Intent(this, SignUpActivity.class);
        startActivityForResult(intent, SIGN_UP_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == SIGN_UP_REQUEST) {
            if (resultCode == RESULT_OK) {
                Snackbar.make(findViewById(android.R.id.content), SIGN_UP_RESULT_CODE_OK_MESSAGE, Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            } else {
                Snackbar.make(findViewById(android.R.id.content), SIGN_UP_RESULT_CODE_NOT_OK_MESSAGE, Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        FragmentManager fm = getFragmentManager();
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        if (id == R.id.action_logout) {
            logout();
        }
        if (id == R.id.action_cms) {
            //TODO Employee CMS
        }
        return super.onOptionsItemSelected(item);
    }

    private void logout(){
        Login.logout(this);
        if(menu!=null){
            menu.findItem(R.id.action_cms).setVisible(false);
        }
        showLoginFragment();

    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        this.menu = menu;
        if (InAppAuthorization.isEmployeeLoggedIn(this))
            menu.findItem(R.id.action_cms).setVisible(true);
        else
            menu.findItem(R.id.action_cms).setVisible(false);
        return true;
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onModelListFragmentItemClick(View view, ViewModel viewModel) {
        ItemsActivity.navigate(this, view, viewModel);
    }


    private boolean asEmployee;

    @Override
    public void onLoginOperation(String login, String password, boolean asEmployee) {
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle(LOADING_TITLE);
        progressDialog.setMessage(LOADING_MESSAGE);
        progressDialog.setCancelable(false);
        progressDialog.show();
        this.asEmployee = asEmployee;
        if (!mLoginInInProgress && mApiConnectionFragment != null) {
            mApiConnectionFragment.login(login, password, asEmployee);
            mLoginInInProgress = true;
        }
    }


    @Override
    public void onSignUp() {
        startSignUpActivity();
    }

    private ApiConnectionFragment mApiConnectionFragment;

    private boolean mLoginInInProgress = false;

    ProgressDialog progressDialog;

    @Override
    public void updateFromDownload(String result, String error) {
        if (error != null && !error.isEmpty()) {
            Snackbar.make(findViewById(android.R.id.content), error, Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
        } else {
            Login.saveToken(result, asEmployee, this);
            showCategoryListFragment();
        }
        progressDialog.dismiss();
    }

    @Override
    public NetworkInfo getActiveNetworkInfo() {
        ConnectivityManager connectivityManager =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
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
        mLoginInInProgress = false;
        if (mApiConnectionFragment != null) {
            mApiConnectionFragment.cancelDownload();
        }
    }


    @Override
    public void onFragmentInteraction(SimpleViewModel simpleViewModel) {
        showModelListFragment(simpleViewModel.getId());
    }
}
