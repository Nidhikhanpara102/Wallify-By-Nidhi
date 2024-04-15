package com.android.nidhiandroidapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.SearchView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;
import android.widget.ProgressBar;

import com.android.nidhiandroidapp.Adapters.CuratedPhotoAdapter;
import com.android.nidhiandroidapp.Listeners.CuratedResponseListener;
import com.android.nidhiandroidapp.Listeners.OnRecyclerClickListener;
import com.android.nidhiandroidapp.Listeners.SearchResponseListener;
import com.android.nidhiandroidapp.Models.APIResponse;
import com.android.nidhiandroidapp.Models.Photo;
import com.android.nidhiandroidapp.Models.SearchApiResponse;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements OnRecyclerClickListener {

    RecyclerView recyclerView_home;
    CuratedPhotoAdapter adapter;
    WallpaperResponseManager manager;
    int page;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressBar = findViewById(R.id.progress_bar);
        progressBar.setVisibility(View.VISIBLE);

        manager = new WallpaperResponseManager(this);
        manager.getCuratedWallpapers(listener, "1");
    }
// Check if the image is found or not and if images are there it will show the data
    private final CuratedResponseListener listener = new CuratedResponseListener() {
        @Override
        public void onFetch(APIResponse response, String message) {
            if (response.getPhotos().isEmpty()){
                Toast.makeText(MainActivity.this, R.string.no_image_found, Toast.LENGTH_SHORT).show();
                return;
            }
            page = response.getPage();
            showData(response.getPhotos());
        }

        @Override
        public void onError(String message) {
            progressBar.setVisibility(View.GONE);
            Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
        }
    };

    //Display images
    private void showData(ArrayList<Photo> photos) {
        recyclerView_home = findViewById(R.id.recycler_home);
        recyclerView_home.setHasFixedSize(true);
        recyclerView_home.setLayoutManager(new GridLayoutManager(this, 2));
        adapter = new CuratedPhotoAdapter(MainActivity.this, photos, this);
        recyclerView_home.setAdapter(adapter);
        progressBar.setVisibility(View.GONE);
    }

    //Navigate to wallpaper actions activity with the data of selected images (Photo model)
    @Override
    public void onClick(Photo photo) {
        startActivity(new Intent(MainActivity.this, WallpaperActionsActivity.class)
                .putExtra("photo", photo));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        MenuItem menuItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) menuItem.getActionView();
        searchView.setQueryHint(getString(R.string.search_hint));
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                manager.searchCuratedWallpapers(searchResponseListener, "1", query);
                progressBar.setVisibility(View.VISIBLE);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }
//CHeck if the image is found or not and display the data accordingly
    private final SearchResponseListener searchResponseListener = new SearchResponseListener() {
        @Override
        public void onFetch(SearchApiResponse response, String message) {
            progressBar.setVisibility(View.GONE);
            if (response.getPhotos().isEmpty()){
                Toast.makeText(MainActivity.this, R.string.no_image_search, Toast.LENGTH_SHORT).show();
                return;
            }
            showData(response.getPhotos());
        }

        @Override
        public void onError(String message) {
            progressBar.setVisibility(View.GONE);
            Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
        }
    };

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_logout) {
            logoutUser();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    //Loged out user
    private void logoutUser() {
        Toast.makeText(this, R.string.logged_out, Toast.LENGTH_SHORT).show();
        startActivity(new Intent(MainActivity.this, LoginActivity.class));
    }
}
