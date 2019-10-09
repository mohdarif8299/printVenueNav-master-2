package com.example.myapplication.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
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
import com.example.myapplication.Adapter.ItemsAdapter;
import com.example.myapplication.Helper.SpacesItemDecoration;
import com.example.myapplication.Items;
import com.example.myapplication.R;
import com.smarteist.autoimageslider.DefaultSliderView;
import com.smarteist.autoimageslider.IndicatorAnimations;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderLayout;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;
public class HomeFragment extends Fragment {
    SliderLayout sliderLayout;
    LinearLayout li,cardslayout,pendrive;
    SwipeRefreshLayout swipeRefreshLayout;
    RecyclerView recyclerView,recyclerView1,recyclerView3,recyclerView4,recyclerView5;
    List<Items> list,list1,list2,list3,list4,list5;
    ProgressBar progressBar;
    RequestQueue requestQueue;
    private static String URL_IMAGE_SLIDER="https://colorpress.000webhostapp.com/vistaprint/Authentication/image_slider.php";
    private static String URL_TRENDING="https://colorpress.000webhostapp.com/vistaprint/Authentication/trending_products.php";
    private static String URL_OFFICE_PRODUCTS="https://colorpress.000webhostapp.com/vistaprint/Authentication/OfficeProducts.php";
    private static String URL_CLOTHING="https://colorpress.000webhostapp.com/vistaprint/Authentication/clothing.php";
    private static String URL_PHOTO_ALBUM="https://colorpress.000webhostapp.com/vistaprint/Authentication/Photos_Album.php";
    private static String URL_GIFTS="https://colorpress.000webhostapp.com/vistaprint/Authentication/Gifts.php";
    public HomeFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_home, container, false);
      //  swipeRefreshLayout = view.findViewById(R.id.refreshLayout);
        recyclerView =view.findViewById(R.id.recyclerview1);
        recyclerView1 =view.findViewById(R.id.recyclerview2);
        recyclerView3 =view.findViewById(R.id.recyclerview3);
        recyclerView4 =view.findViewById(R.id.recyclerview4);
        recyclerView5 =view.findViewById(R.id.recyclerview5);
        progressBar = view.findViewById(R.id.progress);
        recyclerView.setHasFixedSize(true);
        recyclerView1.setHasFixedSize(true);
        recyclerView3.setHasFixedSize(true);
        recyclerView4.setHasFixedSize(true);
        recyclerView5.setHasFixedSize(true);
        recyclerView.addItemDecoration(new SpacesItemDecoration(10,2));
        recyclerView1.addItemDecoration(new SpacesItemDecoration(10,2));
        recyclerView3.addItemDecoration(new SpacesItemDecoration(10,2));
        recyclerView4.addItemDecoration(new SpacesItemDecoration(10,2));
        recyclerView5.addItemDecoration(new SpacesItemDecoration(10,2));
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(),2));
        recyclerView1.setLayoutManager(new GridLayoutManager(getActivity(),2));
        recyclerView3.setLayoutManager(new GridLayoutManager(getActivity(),2));
        recyclerView4.setLayoutManager(new GridLayoutManager(getActivity(),2));
        recyclerView5.setLayoutManager(new GridLayoutManager(getActivity(),2));
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView1.setNestedScrollingEnabled(false);
        recyclerView3.setNestedScrollingEnabled(false);
        recyclerView4.setNestedScrollingEnabled(false);
        recyclerView5.setNestedScrollingEnabled(false);
        list = new ArrayList<>();
        list1 = new ArrayList<>();
        list2 = new ArrayList<>();
        list3 = new ArrayList<>();
        list4 = new ArrayList<>();
        list5 = new ArrayList<>();
        loadItems();
        trending_products();
        office_products();
        clothing_products();
        gifts();
        photo_albums();
        sliderLayout = view.findViewById(R.id.imageSlider);
        sliderLayout.setIndicatorAnimation(IndicatorAnimations.SWAP); //set indicator animation by using SliderLayout.IndicatorAnimations. :WORM or THIN_WORM or COLOR or DROP or FILL or NONE or SCALE or SCALE_DOWN or SLIDE and SWAP!!
        sliderLayout.setSliderTransformAnimation(SliderAnimations.CUBEINROTATIONTRANSFORMATION);
        sliderLayout.setScrollTimeInSec(1); //set scroll delay in seconds :
        sliderLayout.setContentDescription("");
        //  setSliderViews();
        return view;
    }
    public void loadItems(){
        Cache cache = new DiskBasedCache(getActivity().getCacheDir(),1024*1024*5);
        Network network = new BasicNetwork(new HurlStack());
        requestQueue = new RequestQueue(cache,network);
        requestQueue.start();
        progressBar.setVisibility(View.VISIBLE);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL_IMAGE_SLIDER,
                response -> {
                    try {
                        JSONArray array = new JSONArray(response);
                        for (int i = 0; i < array.length(); i++) {
                            JSONObject product = array.getJSONObject(i);
                            list.add(new Items("", product.getString("image_url"), ""));
                            DefaultSliderView sliderView = new DefaultSliderView(getContext());
                            sliderView.setImageUrl(list.get(i).getUrl());
                            sliderView.setImageScaleType(ImageView.ScaleType.CENTER_CROP);
                            sliderLayout.addSliderView(sliderView);
                        }
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
    public void trending_products(){
        Cache cache = new DiskBasedCache(getActivity().getCacheDir(),1024*1024*5);
        Network network = new BasicNetwork(new HurlStack());
        requestQueue = new RequestQueue(cache,network);
        requestQueue.start();
        progressBar.setVisibility(View.VISIBLE);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL_TRENDING,
                response -> {
                    try {
                        JSONArray array = new JSONArray(response);
                        for (int i = 0; i < array.length(); i++) {
                            JSONObject product = array.getJSONObject(i);
                            list1.add(new Items(product.getString("item_name"), product.getString("item_image"), product.getString("item_price")));
                        }
                        ItemsAdapter adapter = new ItemsAdapter(getActivity(), list1);
                        recyclerView.setAdapter(adapter);
                        //               recyclerView1.setAdapter(adapter);
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
    public void office_products(){
        Cache cache = new DiskBasedCache(getActivity().getCacheDir(),1024*1024*5);
        Network network = new BasicNetwork(new HurlStack());
        requestQueue = new RequestQueue(cache,network);
        requestQueue.start();
        progressBar.setVisibility(View.VISIBLE);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL_OFFICE_PRODUCTS,
                response -> {
                    try {
                        JSONArray array = new JSONArray(response);
                        for (int i = 0; i < array.length(); i++) {
                            JSONObject product = array.getJSONObject(i);
                            list2.add(new Items(product.getString("item_name"), product.getString("item_image"), product.getString("item_price")));
                        }
                        ItemsAdapter adapter = new ItemsAdapter(getActivity(), list2);
                        recyclerView1.setAdapter(adapter);
                        //               recyclerView1.setAdapter(adapter);
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

    private void clothing_products() {
        Cache cache = new DiskBasedCache(getActivity().getCacheDir(),1024*1024*5);
        Network network = new BasicNetwork(new HurlStack());
        requestQueue = new RequestQueue(cache,network);
        requestQueue.start();
        progressBar.setVisibility(View.VISIBLE);
        StringRequest stringRequest=new StringRequest(Request.Method.GET,URL_CLOTHING, response -> {
            try{
                JSONArray array=new JSONArray(response);
                for(int i=0;i<array.length();i++){
                    JSONObject product=array.getJSONObject(i);
                    list3.add(new Items(product.getString("item_name"),product.getString("item_image"),product.getString("item_price")));
                }
                ItemsAdapter adapter = new ItemsAdapter(getActivity(), list3);
                recyclerView3.setAdapter(adapter);
            }
            catch (JSONException ex){
                ex.printStackTrace();
                Toast.makeText(getActivity(),"Error "+ex.toString(),Toast.LENGTH_SHORT).show();
            }
        },error -> Toast.makeText(getActivity(),error.toString(),Toast.LENGTH_SHORT).show()
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
    private void gifts(){
        Cache cache = new DiskBasedCache(getActivity().getCacheDir(),1024*1024*5);
        Network network = new BasicNetwork(new HurlStack());
        requestQueue = new RequestQueue(cache,network);
        requestQueue.start();
        progressBar.setVisibility(View.VISIBLE);
        StringRequest stringRequest=new StringRequest(Request.Method.GET,URL_GIFTS, response -> {
            try{
                JSONArray array=new JSONArray(response);
                for(int i=0;i<array.length();i++){
                    JSONObject product=array.getJSONObject(i);
                    list4.add(new Items(product.getString("item_name"),product.getString("item_image"),product.getString("item_price")));
                }
                ItemsAdapter adapter = new ItemsAdapter(getActivity(), list4);
                recyclerView4.setAdapter(adapter);
            }
            catch (JSONException ex){
                ex.printStackTrace();
                Toast.makeText(getActivity(),"Error "+ex.toString(),Toast.LENGTH_SHORT).show();
            }
        },error -> Toast.makeText(getActivity(),error.toString(),Toast.LENGTH_SHORT).show()
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
    private void photo_albums(){
        Cache cache = new DiskBasedCache(getActivity().getCacheDir(),1024*1024*5);
        Network network = new BasicNetwork(new HurlStack());
        requestQueue = new RequestQueue(cache,network);
        requestQueue.start();
        progressBar.setVisibility(View.VISIBLE);
        StringRequest stringRequest=new StringRequest(Request.Method.GET,URL_PHOTO_ALBUM, response -> {
            try{
                JSONArray array=new JSONArray(response);
                for(int i=0;i<array.length();i++){
                    JSONObject product=array.getJSONObject(i);
                    list5.add(new Items(product.getString("item_name"),product.getString("item_image"),product.getString("item_price")));
                }
                ItemsAdapter adapter = new ItemsAdapter(getActivity(), list5);
                recyclerView5.setAdapter(adapter);
            }
            catch (JSONException ex){
                ex.printStackTrace();
                Toast.makeText(getActivity(),"Error "+ex.toString(),Toast.LENGTH_SHORT).show();
            }
        },error -> Toast.makeText(getActivity(),error.toString(),Toast.LENGTH_SHORT).show()
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