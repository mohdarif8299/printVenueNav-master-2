package com.example.myapplication.Fragment;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.myapplication.Helper.SpacesItemDecoration;
import com.example.myapplication.Items;
import com.example.myapplication.ItemsAdapter;
import com.example.myapplication.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class PensFragment extends Fragment {
    RecyclerView recyclerView;
    List<Items> list;
    ProgressBar progressBar;
    private static String URL_PENDRIVES="https://colorpress.000webhostapp.com/vistaprint/Authentication/parker_pens.php";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_pens, container, false);
        progressBar = view.findViewById(R.id.progress);
        recyclerView =view.findViewById(R.id.recyclerview1);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(),2));
        recyclerView.addItemDecoration(new SpacesItemDecoration(10,2));
        recyclerView.setNestedScrollingEnabled(false);
        list = new ArrayList<>();
        loadItems();
        return view;
    }
    public void loadItems(){
        progressBar.setVisibility(View.VISIBLE);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL_PENDRIVES,
                response -> {
                    try {
                        JSONArray array = new JSONArray(response);
                        for (int i = 0; i < array.length(); i++) {
                            JSONObject product = array.getJSONObject(i);
                            list.add(new Items(
                                    product.getString("item_name"),
                                    product.getString("item_image"),
                                    product.getString("item_price")
                            ));
                        }
                        ItemsAdapter adapter = new ItemsAdapter(getActivity(), list);
                        recyclerView.setAdapter(adapter);
                        //    recyclerView1.setAdapter(adapter);
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(getActivity(),"Error"+e.toString(),Toast.LENGTH_SHORT).show();
                    }
                },error->
                Toast.makeText(getActivity(),error.toString(),Toast.LENGTH_SHORT).show()
        );
        Volley.newRequestQueue(getContext()).add(stringRequest);
        progressBar.setVisibility(View.GONE);
    }

}
