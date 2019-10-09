package com.example.myapplication;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.example.myapplication.Adapter.CartAdapter;
import com.example.myapplication.Helper.SpacesItemDecoration;
import com.example.myapplication.PreferenceManager.MyPreference;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CartActivity extends AppCompatActivity {
    private static String URL_GET_FROM_CART = "https://colorpress.000webhostapp.com/vistaprint/Authentication/get_cart_items.php";
    private String itemName,itemImage,itemPrice,itemQuantity,toalPrice;
    private TextView item_price,product_name,total_price,item_quantity;
    private ImageView imageView;
    private RecyclerView recyclerView;
    private List<CartItems> list;
    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
    @Override
    public boolean onSupportNavigateUp() {
      //  Toast.makeText(this,"back pressed",Toast.LENGTH_LONG).show();
        onBackPressed();
        return super.onSupportNavigateUp();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        setTitle("Cart");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        recyclerView =findViewById(R.id.recyclerview1);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
         recyclerView.addItemDecoration(new SpacesItemDecoration(10,2));
        recyclerView.setNestedScrollingEnabled(false);
        list = new ArrayList<>();
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL_GET_FROM_CART,
                response -> {
                    try {
                        JSONArray array = new JSONArray(response);
                        for (int i = 0; i < array.length(); i++) {
                            JSONObject jsonObject = array.getJSONObject(i);
                            list.add(new CartItems(
                                    jsonObject.getString("item_image"),
                                    jsonObject.getString("item_name"),
                                    jsonObject.getString("item_quantity"),
                                    jsonObject.getString("item_price")
                            ));
                        }
                        CartAdapter adapter = new CartAdapter(getApplicationContext(), list);
                        recyclerView.setAdapter(adapter);
                        //    recyclerView1.setAdapter(adapter);
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(getApplicationContext(),"Error"+e.toString(),Toast.LENGTH_SHORT).show();
                    }
                },error->
                Toast.makeText(getApplicationContext(),error.toString(),Toast.LENGTH_SHORT).show()) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("email", new MyPreference(getApplicationContext()).getEmail());
                return params;
            }
        };
        stringRequest.setRetryPolicy(new RetryPolicy() {
            @Override
            public int getCurrentTimeout() {
                return 50000;
            }
            @Override
            public int getCurrentRetryCount() {
                return 50000;
            }
            @Override
            public void retry(VolleyError error) throws VolleyError { }
        });
        Volley.newRequestQueue(getApplicationContext()).add(stringRequest);
    }
}
