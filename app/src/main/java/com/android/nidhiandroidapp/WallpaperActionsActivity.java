
package com.android.nidhiandroidapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DownloadManager;
import android.app.WallpaperManager;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.android.nidhiandroidapp.Models.Photo;

import java.io.IOException;
public class WallpaperActionsActivity extends AppCompatActivity {

    ImageView imageView_wallpaper;
    FloatingActionButton f_btn_download, f_btn_wallpaper;
    Photo photo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wallpaper);

        imageView_wallpaper = findViewById(R.id.imageView_wallpaper);
        f_btn_download = findViewById(R.id.f_btn_download);
        f_btn_wallpaper = findViewById(R.id.f_btn_wallpaper);

        photo = (Photo) getIntent().getSerializableExtra("photo");

        // Load image using Picasso and resize it to fit within ImageView dimensions
        Picasso.get().load(photo.getSrc().getOriginal()).placeholder(R.drawable.placeholder).into(imageView_wallpaper, new Callback() {
            @Override
            public void onSuccess() {
                Bitmap bitmap = ((BitmapDrawable) imageView_wallpaper.getDrawable()).getBitmap();

                // Check bitmap size and scale it down if necessary
                int maxWidth = imageView_wallpaper.getWidth();
                int maxHeight = imageView_wallpaper.getHeight();

                if (bitmap.getWidth() > maxWidth || bitmap.getHeight() > maxHeight) {
                    float scaleFactor = Math.min((float) maxWidth / bitmap.getWidth(), (float) maxHeight / bitmap.getHeight());
                    int scaledWidth = (int) (bitmap.getWidth() * scaleFactor);
                    int scaledHeight = (int) (bitmap.getHeight() * scaleFactor);

                    Bitmap scaledBitmap = Bitmap.createScaledBitmap(bitmap, scaledWidth, scaledHeight, true);
                    imageView_wallpaper.setImageBitmap(scaledBitmap);
                }
            }

            @Override
            public void onError(Exception e) {
                e.printStackTrace();
                Toast.makeText(WallpaperActionsActivity.this, R.string.failed_to_load_image, Toast.LENGTH_SHORT).show();
            }
        });

        f_btn_download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                downloadWallpaper();
            }
        });

        f_btn_wallpaper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setWallpaper();
            }
        });
    }

    // Implement download wallpaper functionality
    private void downloadWallpaper() {
        DownloadManager downloadManager = null;
        downloadManager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);

        Uri uri = Uri.parse(photo.getSrc().getOriginal());

        DownloadManager.Request request = new DownloadManager.Request(uri);

        request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI | DownloadManager.Request.NETWORK_MOBILE)
                .setAllowedOverRoaming(false)
                .setTitle("Wallpaper_"+photo.getPhotographer())
                .setMimeType("image/jpeg")
                .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
                .setDestinationInExternalPublicDir(Environment.DIRECTORY_PICTURES, "Wallpaper_"+photo.getPhotographer()+".jpg");

        downloadManager.enqueue(request);

        Toast.makeText(WallpaperActionsActivity.this, R.string.download_completed, Toast.LENGTH_SHORT).show();

    }

    // Implement set wallpaper functionality
    private void setWallpaper() {
        WallpaperManager wallpaperManager = WallpaperManager.getInstance(WallpaperActionsActivity.this);
        Bitmap bitmap = ((BitmapDrawable) imageView_wallpaper.getDrawable()).getBitmap();

        AlertDialog.Builder builder = new AlertDialog.Builder(WallpaperActionsActivity.this);
        builder.setTitle(R.string.set_as);
        builder.setItems(new CharSequence[]{"Home Screen", "Lock Screen", "Both"}, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                try {
                    if (which == 0) {
                        wallpaperManager.setBitmap(bitmap, null, true, WallpaperManager.FLAG_SYSTEM);
                        Toast.makeText(WallpaperActionsActivity.this,  R.string.wallpaper_set_home, Toast.LENGTH_SHORT).show();
                    } else if (which == 1) {
                        wallpaperManager.setBitmap(bitmap, null, true, WallpaperManager.FLAG_LOCK);
                        Toast.makeText(WallpaperActionsActivity.this, R.string.wallpaper_set_lock, Toast.LENGTH_SHORT).show();
                    } else {
                        wallpaperManager.setBitmap(bitmap);
                        Toast.makeText(WallpaperActionsActivity.this, R.string.wallpaper_set_both, Toast.LENGTH_SHORT).show();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(WallpaperActionsActivity.this, R.string.something_went_wrong, Toast.LENGTH_SHORT).show();
                }
            }
        });
        builder.show();
    }
}
