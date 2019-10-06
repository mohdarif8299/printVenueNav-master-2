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

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.myapplication.Adapter.Custom_Item_Adapter;
import com.example.myapplication.Helper.SpacesItemDecoration;
import com.example.myapplication.ItemsAdapter;
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
    String switchLabel="";
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
        switchLabel =  ItemsAdapter.label;
        switch (switchLabel){
            case "GB": loadItems("https://colorpress.000webhostapp.com/vistaprint/Authentication/custom_pendrives.php");
            item_name.setText(ItemsAdapter.name);
            break;
            case "Parker": loadItems("https://colorpress.000webhostapp.com/vistaprint/Authentication/custom_parker_pens.php");
                item_name.setText(ItemsAdapter.name);
            break;
        }
        return view;
    }
    public void loadItems(String URL){
        progressBar.setVisibility(View.VISIBLE);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL,
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
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(6000,DefaultRetryPolicy.DEFAULT_MAX_RETRIES,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        Volley.newRequestQueue(getContext()).add(stringRequest);
        progressBar.setVisibility(View.GONE);
    }
}
