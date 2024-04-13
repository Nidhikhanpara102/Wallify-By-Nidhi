package com.android.nidhiandroidapp;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.android.nidhiandroidapp.Adapters.CuratedPhotoAdapter;
import com.android.nidhiandroidapp.Listeners.CuratedResponseListener;
import com.android.nidhiandroidapp.Listeners.OnRecyclerClickListener;
import com.android.nidhiandroidapp.Models.APIResponse;
import com.android.nidhiandroidapp.Models.Photo;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements OnRecyclerClickListener {

    RecyclerView recyclerView_home;
    CuratedPhotoAdapter adapter;
    ProgressDialog dialog;
    WallpaperResponseManager manager;
    int page;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }
    private final CuratedResponseListener listener = new CuratedResponseListener() {
        @Override
        public void onFetch(APIResponse response, String message) {

            if (response.getPhotos().isEmpty()){
                Toast.makeText(MainActivity.this, "No Image Found!", Toast.LENGTH_SHORT).show();
                return;
            }
            page = response.getPage();
            showData(response.getPhotos());
        }

        @Override
        public void onError(String message) {
            dialog.dismiss();
            Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
        }
    };

    private void showData(ArrayList<Photo> photos) {
        recyclerView_home = findViewById(R.id.recycler_home);
        recyclerView_home.setHasFixedSize(true);
        recyclerView_home.setLayoutManager(new GridLayoutManager(this, 2));
        adapter = new CuratedPhotoAdapter(MainActivity.this, photos, this);
        recyclerView_home.setAdapter(adapter);
    }

    @Override
    public void onClick(Photo photo) {
        startActivity(new Intent(MainActivity.this, WallpaperActionsActivity.class)
                .putExtra("photo", photo));
    }
}