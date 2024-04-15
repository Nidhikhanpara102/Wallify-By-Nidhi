package com.android.nidhiandroidapp;

import android.content.Context;
import android.widget.Toast;

import com.android.nidhiandroidapp.Listeners.CuratedResponseListener;
import com.android.nidhiandroidapp.Listeners.SearchResponseListener;
import com.android.nidhiandroidapp.Models.APIResponse;
import com.android.nidhiandroidapp.Models.SearchApiResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

public class WallpaperResponseManager {
    Context context;
    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://api.pexels.com/v1/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    public WallpaperResponseManager(Context context) {
        this.context = context;
    }
    public void getCuratedWallpapers(CuratedResponseListener listener, String page){
        CallWallpaperList callWallpaperList = retrofit.create(CallWallpaperList.class);
        Call<APIResponse> call = callWallpaperList.getWallpapers(page, "10");
        call.enqueue(new Callback<APIResponse>() {
            @Override
            public void onResponse(Call<APIResponse> call, Response<APIResponse> response) {
                if (!response.isSuccessful()){
                    Toast.makeText(context, R.string.error_occurred, Toast.LENGTH_SHORT).show();
                    return;
                }
                listener.onFetch(response.body(), response.message());
            }

            @Override
            public void onFailure(Call<APIResponse> call, Throwable t) {
                listener.onError(t.getMessage());
            }
        });
    }

    public void searchCuratedWallpapers(SearchResponseListener listener, String page, String query){
        CallWallpaperListSeaarch callWallpaperListSeaarch = retrofit.create(CallWallpaperListSeaarch.class);
        Call<SearchApiResponse> call = callWallpaperListSeaarch.searchWallpapers(query, page, "20");
        call.enqueue(new Callback<SearchApiResponse>() {
            @Override
            public void onResponse(Call<SearchApiResponse> call, Response<SearchApiResponse> response) {
                if (!response.isSuccessful()){
                    Toast.makeText(context, R.string.error_occurred, Toast.LENGTH_SHORT).show();
                    return;
                }
                listener.onFetch(response.body(), response.message());
            }

            @Override
            public void onFailure(Call<SearchApiResponse> call, Throwable t) {
                listener.onError(t.getMessage());
            }
        });
    }

    private interface CallWallpaperList {
        @Headers({
                "Accept: application/json",
                "Authorization: yniudTczqlsB54ZqOMEfwhAW986hk3GLczgVbmlwBjNBT9uyTiRC9w4a"
        })
        @GET("curated/")
        Call<APIResponse> getWallpapers(
                @Query("page") String page,
                @Query("per_page") String per_page
        );
    }

    private interface CallWallpaperListSeaarch {
        @Headers({
                "Accept: application/json",
                "Authorization: yniudTczqlsB54ZqOMEfwhAW986hk3GLczgVbmlwBjNBT9uyTiRC9w4a"
        })
        @GET("search")
        Call<SearchApiResponse> searchWallpapers(
                @Query("query") String query,
                @Query("page") String page,
                @Query("per_page") String per_page
        );
    }
}
