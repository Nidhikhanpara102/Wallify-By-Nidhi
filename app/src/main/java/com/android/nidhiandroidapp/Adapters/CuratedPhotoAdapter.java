package com.android.nidhiandroidapp.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.android.nidhiandroidapp.Listeners.OnRecyclerClickListener;
import com.android.nidhiandroidapp.Models.Photo;
import com.android.nidhiandroidapp.R;

import java.util.List;

public class CuratedPhotoAdapter extends RecyclerView.Adapter<CuratedPhotoViewHolder>{

    Context context;
    List<Photo> list;
    OnRecyclerClickListener listener;

    public CuratedPhotoAdapter(Context context, List<Photo> list, OnRecyclerClickListener listener) {
        this.context = context;
        this.list = list;
        this.listener = listener;
    }

    @NonNull
    @Override
    public CuratedPhotoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CuratedPhotoViewHolder(LayoutInflater.from(context).inflate(R.layout.home_list, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull CuratedPhotoViewHolder holder, int position) {
        Picasso.get().load(list.get(position).getSrc().getMedium()).placeholder(R.drawable.placeholder).into(holder.imageView_list);
        holder.home_list_container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onClick(list.get(holder.getAdapterPosition()));
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}

class CuratedPhotoViewHolder extends RecyclerView.ViewHolder {
    CardView home_list_container;
    ImageView imageView_list;
    public CuratedPhotoViewHolder(@NonNull View itemView) {
        super(itemView);
        home_list_container = itemView.findViewById(R.id.home_list_container);
        imageView_list = itemView.findViewById(R.id.imageView_list);
    }
}
