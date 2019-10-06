package com.example.myapplication.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.myapplication.R;
import com.example.myapplication.customizeActivity;

import java.util.List;

public class Custom_Item_Adapter extends RecyclerView.Adapter<Custom_Item_Adapter.ItemsViewHolder>{
    private Context context;
    private List<String> list;
    public Custom_Item_Adapter(Context context, List list){
        this.context= context;
        this.list = list;
    }
    @NonNull
    @Override
    public Custom_Item_Adapter.ItemsViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.custom_item, null);
        return new Custom_Item_Adapter.ItemsViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull Custom_Item_Adapter.ItemsViewHolder itemViewHolder, int i) {
        itemViewHolder.progressBar.setVisibility(View.VISIBLE);
        String items = list.get(i);
        Glide.with(context).load(items).into(itemViewHolder.item_image);
        itemViewHolder.button.setOnClickListener(v->{
            Intent intent = new Intent(context, customizeActivity.class);
            intent.putExtra("image",items);
            context.startActivity(intent);
        });
        itemViewHolder.progressBar.setVisibility(View.GONE);

    }
    @Override
    public int getItemCount() { return list.size(); }
    class ItemsViewHolder extends RecyclerView.ViewHolder{
     //   LinearLayout linearLayout;
        ImageView item_image;
        ProgressBar progressBar;
        Button button;
        public ItemsViewHolder(@NonNull View itemView) {
            super(itemView);
            progressBar = itemView.findViewById(R.id.progress);
        //    linearLayout = itemView.findViewById(R.id.pendrive);
            item_image = itemView.findViewById(R.id.image);
            button = itemView.findViewById(R.id.cusotmize);
        }
    }
}
