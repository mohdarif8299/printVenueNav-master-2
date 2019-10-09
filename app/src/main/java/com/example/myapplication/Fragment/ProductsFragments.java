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

import com.android.volley.Cache;
import com.android.volley.Network;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.StringRequest;
import com.example.myapplication.Helper.SpacesItemDecoration;
import com.example.myapplication.Items;
import com.example.myapplication.Adapter.ItemsAdapter;
import com.example.myapplication.R;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;
public class ProductsFragments extends Fragment {
    RecyclerView recyclerView;
    List<Items> list;
    ProgressBar progressBar;
    RequestQueue requestQueue;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_pen_drive, container, false);
        progressBar = view.findViewById(R.id.progress);
        recyclerView =view.findViewById(R.id.recyclerview1);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(),2));
        recyclerView.addItemDecoration(new SpacesItemDecoration(10,2));
        recyclerView.setNestedScrollingEnabled(false);
        list = new ArrayList<>();
        switch (ItemsAdapter.item_name){
            case "Pen Drives":
                loadItems("https://colorpress.000webhostapp.com/vistaprint/Authentication/pendrives.php");
                break;
            case "Parker Pens":
                loadItems("https://colorpress.000webhostapp.com/vistaprint/Authentication/parker_pens.php");
                break;
        }
        return view;
    }
    public void loadItems(String URL){
        Cache cache = new DiskBasedCache(getActivity().getCacheDir(),1024*1024*5);
        Network network = new BasicNetwork(new HurlStack());
        requestQueue = new RequestQueue(cache,network);
        requestQueue.start();
        progressBar.setVisibility(View.VISIBLE);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL,
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
       requestQueue.add(stringRequest);
        progressBar.setVisibility(View.GONE);
    }
}
