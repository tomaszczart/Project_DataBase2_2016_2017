package com.nowak01011111.damian.bunchoftools.api_client;


import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;


public class ApiConnectionFragment extends Fragment {

    public static final String TAG = "ApiConnectionFragment";
    public static final String ERROR_CONNECTION_CANCELED = "Error: connection canceled.";

    private static final String URL_API = "http://192.168.1.169:3000";
    private static final String URL_API_LOGIN_USER = "/post/login";
    private static final String URL_API_GET_EMPLOYEES = "/get/employees";
    private static final String URL_API_GET_ITEMS = "/get/items";
    private static final String URL_API_GET_MODELS = "/get/models";
    private static final String URL_API_GET_TRANSACTIONS = "/get/transactions";
    private static final String URL_API_REGISTER = "/post/register";

    private static final String URL_API_LOGIN_POST_DATA_KEY_1 = "employee";
    private static final String URL_API_LOGIN_POST_DATA_KEY_2 = "username";
    private static final String URL_API_LOGIN_POST_DATA_KEY_3 = "password";

    private ApiTaskCallback mCallback;
    private ApiTask mApiTask;

    public static ApiConnectionFragment getInstance(FragmentManager fragmentManager) {
        ApiConnectionFragment apiConnectionFragment = (ApiConnectionFragment) fragmentManager
                .findFragmentByTag(ApiConnectionFragment.TAG);
        if (apiConnectionFragment == null) {
            apiConnectionFragment = new ApiConnectionFragment();
            fragmentManager.beginTransaction().add(apiConnectionFragment, TAG).commit();
        }
        return apiConnectionFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Retain this Fragment across configuration changes in the host Activity.
        setRetainInstance(true);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        // Host Activity will handle callbacks from task.
        mCallback = (ApiTaskCallback) context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        // Clear reference to host Activity to avoid memory leak.
        mCallback = null;
    }

    @Override
    public void onDestroy() {
        // Cancel task when Fragment is destroyed.
        cancelDownload();
        super.onDestroy();
    }

    // api login
    public void login(String username, String password, boolean asEmployee) {
        cancelDownload();
        mApiTask = new ApiTask(mCallback);
        String postData = createLoginPostData(username, password, asEmployee);
        mApiTask.execute(ApiTask.REQUEST_METHOD_POST, URL_API + URL_API_LOGIN_USER, "", postData);
    }

    private String createLoginPostData(String username, String password, boolean asEmployee) {
        HashMap<String, String> params = new HashMap<>();
        params.put(URL_API_LOGIN_POST_DATA_KEY_1, Boolean.toString(asEmployee));
        params.put(URL_API_LOGIN_POST_DATA_KEY_2, username);
        params.put(URL_API_LOGIN_POST_DATA_KEY_3, password);
        try {
            return getPostDataString(params);
        } catch (UnsupportedEncodingException e) {
            return "";
        }
    }

    /**
     * Cancel (and interrupt if necessary) any ongoing ApiTask execution.
     */
    public void cancelDownload() {
        if (mApiTask != null) {
            mApiTask.cancel(true);
        }
    }

    private String getPostDataString(HashMap<String, String> params) throws UnsupportedEncodingException {
        StringBuilder result = new StringBuilder();
        boolean first = true;
        for (Map.Entry<String, String> entry : params.entrySet()) {
            if (first)
                first = false;
            else
                result.append("&");

            result.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
            result.append("=");
            result.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
        }

        return result.toString();
    }

    /**
     * Implementation of AsyncTask designed to fetch data from the network.
     */
    private class ApiTask extends AsyncTask<String, Integer, ApiTask.Result> {

        private static final String REQUEST_METHOD_POST = "POST";
        private static final String REQUEST_METHOD_GET = "GET";


        private ApiTaskCallback mCallback;

        ApiTask(ApiTaskCallback callback) {
            setCallback(callback);
        }

        void setCallback(ApiTaskCallback callback) {
            mCallback = callback;
        }

        class Result {
            public String mResultValue;
            public String mToken;
            public Exception mException;

            public Result() {
            }

            public Result(Exception exception) {
                mException = exception;
            }
        }

        /**
         * Cancel background network operation if we do not have network connectivity.
         */
        @Override
        protected void onPreExecute() {
            if (mCallback != null) {
                NetworkInfo networkInfo = mCallback.getActiveNetworkInfo();
                if (networkInfo == null || !networkInfo.isConnected() ||
                        (networkInfo.getType() != ConnectivityManager.TYPE_WIFI
                                && networkInfo.getType() != ConnectivityManager.TYPE_MOBILE)) {
                    // If no connectivity, cancel task and update Callback with null data.
                    mCallback.updateFromDownload(null, null, ApiConnectionFragment.ERROR_CONNECTION_CANCELED);
                    cancel(true);
                }
            }
        }

        /**
         * Defines work to perform on the background thread.
         */

        // urls[0] requestMethod, urls[1] url, urls[2] token, urls[3] postData
        @Override
        protected ApiTask.Result doInBackground(String... urls) {
            Result result = null;
            if (!isCancelled() && urls != null && urls.length > 1) {
                String requestMethod = urls[0];
                String urlString = urls[1];
                String token = urls[2];
                String postData = "";
                if (urls.length > 3)
                    postData = urls[3];
                try {
                    URL url = new URL(urlString);
                    result = performTask(requestMethod, url, token, postData);
                } catch (Exception e) {
                    result = new Result(e);
                }
            }
            return result;
        }
        /**
         * Updates the ApiTaskCallback with the result.
         */
        @Override
        protected void onPostExecute(Result result) {
            if (result != null && mCallback != null) {
                if (result.mException != null) {
                    mCallback.updateFromDownload(null, null, result.mException.getMessage());
                } else if (result.mResultValue != null) {
                    mCallback.updateFromDownload(result.mResultValue, result.mToken, null);
                }
                mCallback.finishDownloading();
            }
        }

        /**
         * Override to add special behavior for cancelled AsyncTask.
         */
        @Override
        protected void onCancelled(Result result) {
        }


        private Result performTask(String requestMethod, URL url, String token, String postData) throws IOException {
            InputStream stream = null;
            HttpURLConnection connection = null;
            Result result = new Result();
            try {
                connection = (HttpURLConnection) url.openConnection();
                // Timeout for reading InputStream arbitrarily set to 3000ms.
                connection.setReadTimeout(3000);
                // Timeout for connection.connect() arbitrarily set to 3000ms.
                connection.setConnectTimeout(3000);
                // For this use case, set HTTP method to GET.
                connection.setRequestMethod(requestMethod);
                // Already true by default but setting just in case; needs to be true since this request
                // is carrying an input (response) body.
                connection.setDoInput(true);

                connection.setRequestProperty("x-auth", token);

                if (requestMethod.equals(REQUEST_METHOD_POST) && !postData.equals("")) {
                    connection.setDoOutput(true);
                    OutputStream os = connection.getOutputStream();
                    BufferedWriter writer = new BufferedWriter(
                            new OutputStreamWriter(os, "UTF-8"));
                    writer.write(postData);

                    writer.flush();
                    writer.close();
                }

                // Open communications link (network traffic occurs here).
                connection.connect();
                publishProgress(ApiTaskCallback.Progress.CONNECT_SUCCESS);
                int responseCode = connection.getResponseCode();
                if (responseCode != HttpsURLConnection.HTTP_OK) {
                    throw new IOException("HTTP error code: " + responseCode);
                }
                result.mToken = connection.getHeaderField("x-auth");

                // Retrieve the response body as an InputStream.
                stream = connection.getInputStream();
                publishProgress(ApiTaskCallback.Progress.GET_INPUT_STREAM_SUCCESS, 0);
                if (stream != null) {
                    result.mResultValue = readStream(stream, Integer.MAX_VALUE);
                }
            } finally {
                // Close Stream and disconnect HTTPS connection.
                if (stream != null) {
                    stream.close();
                }
                if (connection != null) {
                    connection.disconnect();
                }
            }
            return result;
        }

        /**
         * Converts the contents of an InputStream to a String.
         */
        private String readStream(InputStream stream, int maxLength) throws IOException {
            String result = null;
            // Read InputStream using the UTF-8 charset.
            InputStreamReader reader = new InputStreamReader(stream, "UTF-8");
            // Create temporary buffer to hold Stream data with specified max length.
            char[] buffer = new char[maxLength];
            // Populate temporary buffer with Stream data.
            int numChars = 0;
            int readSize = 0;
            while (numChars < maxLength && readSize != -1) {
                numChars += readSize;
                int pct = (100 * numChars) / maxLength;
                publishProgress(ApiTaskCallback.Progress.PROCESS_INPUT_STREAM_IN_PROGRESS, pct);
                readSize = reader.read(buffer, numChars, buffer.length - numChars);
            }
            if (numChars != -1) {
                // The stream was not empty.
                // Create String that is actual length of response body if actual length was less than
                // max length.
                numChars = Math.min(numChars, maxLength);
                result = new String(buffer, 0, numChars);
            }
            return result;
        }
    }
}