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
public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ItemsViewHolder> implements  AdapterView.OnItemSelectedListener {
    private Context context;
    private List<CartItems> list;
    String quantites;
    int total = 0;
    int final_price;
    int qnt;
    String[] quantity = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10"};
    public CartAdapter(Context context, List<CartItems> list) {
        this.context = context;
        this.list = list;
    }
    @NonNull
    @Override
    public CartAdapter.ItemsViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.cart_items, null);
        return new CartAdapter.ItemsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartAdapter.ItemsViewHolder itemViewHolder, int i) {
        ArrayAdapter arrayAdapter = new ArrayAdapter(context,android.R.layout.simple_spinner_dropdown_item,quantity);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        itemViewHolder.spinner.setAdapter(arrayAdapter);
        CartItems items = list.get(i);
        quantites  = itemViewHolder.set_quantity;
        itemViewHolder.item_name.setText(items.getName());
        Glide.with(context).load(items.getImage()).into(itemViewHolder.item_image);
        total = Integer.parseInt(items.getPrice());
        final_price= qnt*total;
        itemViewHolder.item_price.setText(items.getPrice());
        itemViewHolder.delete.setOnClickListener(v -> Toast.makeText(context, "Item Deleted", Toast.LENGTH_SHORT).show());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        qnt =  Integer.parseInt(quantity[i]);
        final_price= qnt*total;
          Toast.makeText(context,final_price+"",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    class ItemsViewHolder extends RecyclerView.ViewHolder{
        LinearLayout delete;
        ImageView item_image;
        Spinner spinner;
        String set_quantity;
        TextView item_name, item_price;

        public ItemsViewHolder(@NonNull View itemView) {
            super(itemView);
            delete = itemView.findViewById(R.id.delete);
            item_image = itemView.findViewById(R.id.image);
            item_name = itemView.findViewById(R.id.item_name);
            item_price = itemView.findViewById(R.id.item_price);
            spinner = itemView.findViewById(R.id.spinner);
        }
        }}


