package com.android.nidhiandroidapp.Listeners;

import com.android.nidhiandroidapp.Models.SearchApiResponse;

public interface SearchResponseListener {
    void onFetch(SearchApiResponse response, String message);
    void onError(String message);
}
