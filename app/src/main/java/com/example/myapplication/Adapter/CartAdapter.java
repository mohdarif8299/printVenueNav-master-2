package com.example.myapplication.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.myapplication.CartItems;
import com.example.myapplication.R;

import java.util.List;
public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ItemsViewHolder> {
    private Context context;
    private List<CartItems> list;

    public CartAdapter(Context context, List<CartItems> list) {
        this.context = context;
        this.list = list;
    }
    @NonNull
    @Override
    public CartAdapter.ItemsViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.custom_item, null);
        return new CartAdapter.ItemsViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull CartAdapter.ItemsViewHolder itemViewHolder, int i) {
        CartItems items = list.get(i);
        String quantity = itemViewHolder.set_quantity;
        itemViewHolder.item_name.setText(items.getName());
        Glide.with(context).load(items.getImage()).into(itemViewHolder.item_image);
        itemViewHolder.item_price.setText(items.getPrice());
        itemViewHolder.delete.setOnClickListener(v-> Toast.makeText(context,"Item Deleted",Toast.LENGTH_SHORT).show());
    }
    @Override
    public int getItemCount() { return list.size(); }
    class ItemsViewHolder extends RecyclerView.ViewHolder implements AdapterView.OnItemSelectedListener {
        LinearLayout delete;
        ImageView item_image;
        Spinner spinner;
        String set_quantity;
        TextView item_name,item_price;
        String [] quantity = {"1","2","3","4","5","6","7","8","9","10"};
        public ItemsViewHolder(@NonNull View itemView) {
            super(itemView);
            delete = itemView.findViewById(R.id.delete);
            item_image = itemView.findViewById(R.id.image);
            item_name = itemView.findViewById(R.id.item_name);
            item_price = itemView.findViewById(R.id.item_price);
            spinner = itemView.findViewById(R.id.spinner);
            ArrayAdapter arrayAdapter = new ArrayAdapter(itemView.getContext(),android.R.layout.simple_dropdown_item_1line,quantity);
            spinner.setAdapter(arrayAdapter);
        }
        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
            set_quantity = quantity[i];
        }
        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {
        }
    }
}
