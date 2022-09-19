package com.hinext.software;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hinext.software.ModelClass.Datum;

import java.util.Collections;
import java.util.List;

public class ImageGalleryAdapter2 extends RecyclerView.Adapter<ImageGalleryAdapter2.ViewHolder> {

    List<Datum> list = Collections.emptyList();
    Context context;
    ClickListiner listiner;

    public ImageGalleryAdapter2(List<Datum> list, Context context, ClickListiner listiner)
    {
        this.list = list;
        this.context = context;
        this.listiner = listiner;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem= layoutInflater.inflate(R.layout.item_list, parent, false);
        ViewHolder viewHolder = new ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        viewHolder.textView.setText("Vehicle No: " + list.get(position).getVehicleno());
        viewHolder.textViewCreatedAt.setText("Created At: " + list.get(position).getCreatedAt());
        //viewHolder.imageView.setText(list.get(position).getImages());
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                listiner.onClickItem(list.get(position));
            }
        });
    }



    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageView;
        public TextView textView;
        public TextView textViewCreatedAt;
        public ViewHolder(View itemView) {
            super(itemView);
            this.imageView = (ImageView) itemView.findViewById(R.id.imageView);
            this.textView = (TextView) itemView.findViewById(R.id.textView);
            this.textViewCreatedAt = (TextView) itemView.findViewById(R.id.textView2);
        }
    }
    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    interface ClickListiner{
        void onClickItem(Datum item);
    }


}
