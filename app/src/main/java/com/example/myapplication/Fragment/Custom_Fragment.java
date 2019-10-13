package com.example.myapplication.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.myapplication.Adapter.Custom_Item_Adapter;
import com.example.myapplication.Helper.SpacesItemDecoration;
import com.example.myapplication.Adapter.ItemsAdapter;
import com.example.myapplication.Items;
import com.example.myapplication.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Custom_Fragment extends Fragment {
    RecyclerView recyclerView;
    List<String> list;
    TextView item_name;
    ProgressBar progressBar;
    String urlToLoad="";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_custom_, container, false);
        progressBar = view.findViewById(R.id.progress);
        item_name = view.findViewById(R.id.item_name);
        recyclerView =view.findViewById(R.id.recyclerview1);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(),2));
        recyclerView.addItemDecoration(new SpacesItemDecoration(10,2));
        recyclerView.setNestedScrollingEnabled(false);
        list = new ArrayList<>();
        item_name.setText(ItemsAdapter.item_name);
        if (ItemsAdapter.product_url==null){
           Toast.makeText(getActivity(),"Something Went Wrong Try Again!!!",Toast.LENGTH_SHORT).show();
        }
        else {
            urlToLoad = ItemsAdapter.item_url;
            urlToLoad.trim();
            loadItems();
        }
        return view;
    }
    public void loadItems(){
        progressBar.setVisibility(View.VISIBLE);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, urlToLoad,
                response -> {
                    try {
                        JSONArray array = new JSONArray(response);
                        for (int i = 0; i < array.length(); i++) {
                            JSONObject product = array.getJSONObject(i);
                            list.add(product.getString("item_image"));
                        }
                        Custom_Item_Adapter adapter = new Custom_Item_Adapter(getActivity(), list);
                        recyclerView.setAdapter(adapter);
                        //    recyclerView1.setAdapter(adapter);
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(getActivity(),"Error"+e.toString(),Toast.LENGTH_SHORT).show();
                    }
                },error->
                Toast.makeText(getActivity(),error.toString(),Toast.LENGTH_SHORT).show()
        );
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
      Volley.newRequestQueue(getContext()).add(stringRequest);
        progressBar.setVisibility(View.GONE);
    }
}
