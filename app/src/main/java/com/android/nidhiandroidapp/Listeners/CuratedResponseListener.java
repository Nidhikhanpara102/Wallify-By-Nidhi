package com.android.nidhiandroidapp.Listeners;

import com.android.nidhiandroidapp.Models.APIResponse;

public interface CuratedResponseListener {
    void onFetch(APIResponse response, String message);
    void onError(String message);
}
