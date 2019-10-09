package com.example.myapplication.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.myapplication.Fragment.Custom_Fragment;
import com.example.myapplication.Fragment.ProductsFragments;
import com.example.myapplication.Items;

import java.util.List;

public class ItemsAdapter extends RecyclerView.Adapter<ItemsAdapter.ItemsViewHolder> {
    private Context context;
    private List<Items> list;
    public  static  String label,name,item_name;
    public ItemsAdapter(Context context,List list){
        this.context= context;
        this.list = list;
    }
    @NonNull
    @Override
    public ItemsAdapter.ItemsViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(com.example.myapplication.R.layout.items, null);
        return new ItemsViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull ItemsAdapter.ItemsViewHolder itemViewHolder, int i) {
        FragmentTransaction fragmentTransaction = ((FragmentActivity) context).getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        itemViewHolder.progressBar.setVisibility(View.VISIBLE);
        Items items = list.get(i);
        Glide.with(context).load(items.getUrl()).into(itemViewHolder.item_image);
        itemViewHolder.item_name.setText(items.getName());
        itemViewHolder.item_price.setText("Rs "+items.getPrice()+".00");
        itemViewHolder.linearLayout.setOnClickListener(v->{
            if(items.getName().equals("Pen Drives")) {
                item_name = items.getName();
                fragmentTransaction.replace(com.example.myapplication.R.id.container, new ProductsFragments());
                fragmentTransaction.commit();
            }
            else if (items.getName().contains("GB")) {
                label = "GB";
                name = items.getName();
                fragmentTransaction.replace(com.example.myapplication.R.id.container, new Custom_Fragment());
                fragmentTransaction.commit();
            }
           if (items.getName().equals("Parker Pens")) {
               item_name = items.getName();
                fragmentTransaction.replace(com.example.myapplication.R.id.container, new ProductsFragments());
                fragmentTransaction.commit();
            }
            else if (items.getName().contains("Parker")) {
                label = "Parker";
                name = items.getName();
                fragmentTransaction.replace(com.example.myapplication.R.id.container, new Custom_Fragment());
                fragmentTransaction.commit();
            }
        });
        itemViewHolder.progressBar.setVisibility(View.GONE);
    }
    @Override
    public int getItemCount() { return list.size(); }
    class ItemsViewHolder extends RecyclerView.ViewHolder{
        TextView item_name,item_price;
        LinearLayout linearLayout;
        ImageView item_image;
        ProgressBar progressBar;
        public ItemsViewHolder(@NonNull View itemView) {
            super(itemView);
            progressBar = itemView.findViewById(com.example.myapplication.R.id.progress);
            linearLayout = itemView.findViewById(com.example.myapplication.R.id.pendrive);
            item_image = itemView.findViewById(com.example.myapplication.R.id.image);
            item_name = itemView.findViewById(com.example.myapplication.R.id.name);
            item_price = itemView.findViewById(com.example.myapplication.R.id.price);
        }
    }
}
