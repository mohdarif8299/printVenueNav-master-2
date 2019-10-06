package com.example.myapplication;

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
import com.example.myapplication.Fragment.PenDrive;
import com.example.myapplication.Fragment.PensFragment;

import java.util.List;

public class ItemsAdapter extends RecyclerView.Adapter<ItemsAdapter.ItemsViewHolder> {
    private Context context;
    private List<Items> list;
    public  static  String label,name;
    public ItemsAdapter(Context context,List list){
        this.context= context;
        this.list = list;
    }
    @NonNull
    @Override
    public ItemsAdapter.ItemsViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.items, null);
        return new ItemsViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull ItemsAdapter.ItemsViewHolder itemViewHolder, int i) {
        itemViewHolder.progressBar.setVisibility(View.VISIBLE);
        Items items = list.get(i);
        Glide.with(context).load(items.getUrl()).into(itemViewHolder.item_image);
        itemViewHolder.item_name.setText(items.getName());
        itemViewHolder.item_price.setText("Rs "+items.getPrice()+".00");
        itemViewHolder.linearLayout.setOnClickListener(v->{
            if(items.getName().equals("Pen Drives")) {
                FragmentTransaction fragmentTransaction = ((FragmentActivity) context).getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.container, new PenDrive());
                fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                fragmentTransaction.commit();
            }
            else if (items.getName().contains("GB")) {
                label = "GB";
                name = items.getName();
                FragmentTransaction fragmentTransaction = ((FragmentActivity) context).getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.container, new Custom_Fragment());
                fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                fragmentTransaction.commit();
            }
           if (items.getName().equals("Parker Pens")) {
                FragmentTransaction fragmentTransaction = ((FragmentActivity) context).getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.container, new PensFragment());
               fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                fragmentTransaction.commit();
            }
            else if (items.getName().contains("Parker")) {
                label = "Parker";
                name = items.getName();
                FragmentTransaction fragmentTransaction = ((FragmentActivity) context).getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.container, new Custom_Fragment());
               fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
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
            progressBar = itemView.findViewById(R.id.progress);
            linearLayout = itemView.findViewById(R.id.pendrive);
            item_image = itemView.findViewById(R.id.image);
            item_name = itemView.findViewById(R.id.name);
            item_price = itemView.findViewById(R.id.price);
        }
    }
}
