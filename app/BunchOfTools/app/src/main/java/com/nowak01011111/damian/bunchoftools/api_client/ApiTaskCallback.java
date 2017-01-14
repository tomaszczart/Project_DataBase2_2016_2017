package com.nowak01011111.damian.bunchoftools.api_client;

import android.net.NetworkInfo;

/**
 * Created by utche on 07.01.2017.
 */

public interface ApiTaskCallback{
    interface Progress {
        int ERROR = -1;
        int CONNECT_SUCCESS = 0;
        int GET_INPUT_STREAM_SUCCESS = 1;
        int PROCESS_INPUT_STREAM_IN_PROGRESS = 2;
        int PROCESS_INPUT_STREAM_SUCCESS = 3;
    }
    void updateFromDownload(String result, String error);
    NetworkInfo getActiveNetworkInfo();
    void onProgressUpdate(int progressCode, int percentComplete);
    void finishDownloading();
}