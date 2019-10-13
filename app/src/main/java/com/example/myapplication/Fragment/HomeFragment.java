package com.example.myapplication.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
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
    RecyclerView recyclerView1, recyclerView2, recyclerView3, recyclerView4, recyclerView5, recyclerView6, recyclerView7;
    List<Items> list, list1, list2, list3, list4, list5, list6, list7;
    RequestQueue requestQueue;
    private static String URL_IMAGE_SLIDER = "https://colorpress.000webhostapp.com/vistaprint/Authentication/image_slider.php";
    private static String URL_TRENDING = "https://colorpress.000webhostapp.com/vistaprint/Authentication/shop_by_trending.php";
    private static String URL_OFFICE_PRODUCTS = "https://colorpress.000webhostapp.com/vistaprint/Authentication/shop_by_officeproducts.php";
    private static String URL_OFFICE_SUPPLIES = "https://colorpress.000webhostapp.com/vistaprint/Authentication/shop_by_officesupplies.php";
    private static String URL_CLOTHING = "https://colorpress.000webhostapp.com/vistaprint/Authentication/shop_by_clothing.php";
    private static String URL_PHOTO_GIFTS = "https://colorpress.000webhostapp.com/vistaprint/Authentication/shop_by_photogifts.php";
    private static String URL_MARKETING_MATERIALS = "https://colorpress.000webhostapp.com/vistaprint/Authentication/shop_by_marketingmaterials.php";
    private static String URL_BUSINESS_CARDS = "https://colorpress.000webhostapp.com/vistaprint/Authentication/shop_by_businesscards.php";

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        recyclerView1 = view.findViewById(R.id.recyclerview1);
        recyclerView2 = view.findViewById(R.id.recyclerview2);
        recyclerView3 = view.findViewById(R.id.recyclerview3);
        recyclerView4 = view.findViewById(R.id.recyclerview4);
        recyclerView5 = view.findViewById(R.id.recyclerview5);
        recyclerView6 = view.findViewById(R.id.recyclerview6);
        recyclerView7 = view.findViewById(R.id.recyclerview7);
        recyclerView1.setHasFixedSize(true);
        recyclerView2.setHasFixedSize(true);
        recyclerView3.setHasFixedSize(true);
        recyclerView4.setHasFixedSize(true);
        recyclerView5.setHasFixedSize(true);
        recyclerView6.setHasFixedSize(true);
        recyclerView7.setHasFixedSize(true);
        //adding spacing
        recyclerView1.addItemDecoration(new SpacesItemDecoration(10, 2));
        recyclerView2.addItemDecoration(new SpacesItemDecoration(10, 2));
        recyclerView3.addItemDecoration(new SpacesItemDecoration(10, 2));
        recyclerView4.addItemDecoration(new SpacesItemDecoration(10, 2));
        recyclerView5.addItemDecoration(new SpacesItemDecoration(10, 2));
        recyclerView6.addItemDecoration(new SpacesItemDecoration(10, 2));
        recyclerView7.addItemDecoration(new SpacesItemDecoration(10, 2));
        // adding grid layout
        recyclerView1.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        recyclerView2.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        recyclerView3.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        recyclerView4.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        recyclerView5.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        recyclerView6.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        recyclerView7.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        //adding scroll enabled
        recyclerView1.setNestedScrollingEnabled(false);
        recyclerView2.setNestedScrollingEnabled(false);
        recyclerView3.setNestedScrollingEnabled(false);
        recyclerView4.setNestedScrollingEnabled(false);
        recyclerView5.setNestedScrollingEnabled(false);
        recyclerView6.setNestedScrollingEnabled(false);
        recyclerView7.setNestedScrollingEnabled(false);
        //initializing lists
        list = new ArrayList<>();
        list1 = new ArrayList<>();
        list2 = new ArrayList<>();
        list3 = new ArrayList<>();
        list4 = new ArrayList<>();
        list5 = new ArrayList<>();
        list6 = new ArrayList<>();
        list7 = new ArrayList<>();
        startImageSlider();
        trending_products();
        office_products();
        clothing_products();
        photo_gifts();
        marketing_materials();
        office_supplies();
        business_cards();
        sliderLayout = view.findViewById(R.id.imageSlider);
        sliderLayout.setIndicatorAnimation(IndicatorAnimations.SWAP); //set indicator animation by using SliderLayout.IndicatorAnimations. :WORM or THIN_WORM or COLOR or DROP or FILL or NONE or SCALE or SCALE_DOWN or SLIDE and SWAP!!
        sliderLayout.setSliderTransformAnimation(SliderAnimations.CUBEINROTATIONTRANSFORMATION);
        sliderLayout.setScrollTimeInSec(1); //set scroll delay in seconds :
        sliderLayout.setContentDescription("");
        return view;
    }

    public void startImageSlider() {
        Cache cache = new DiskBasedCache(getActivity().getCacheDir(), 1024 * 1024 * 5);
        Network network = new BasicNetwork(new HurlStack());
        requestQueue = new RequestQueue(cache, network);
        requestQueue.start();
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL_IMAGE_SLIDER,
                response -> {
                    try {
                        JSONArray array = new JSONArray(response);
                        for (int i = 0; i < array.length(); i++) {
                            JSONObject product = array.getJSONObject(i);
                            list.add(new Items("", product.getString("image_url"), "",""));
                            DefaultSliderView sliderView = new DefaultSliderView(getContext());
                            sliderView.setImageUrl(list.get(i).getUrl());
                            sliderView.setImageScaleType(ImageView.ScaleType.CENTER_CROP);
                            sliderLayout.addSliderView(sliderView);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                   Toast.makeText(getActivity(), "Error  1 " + e.toString(), Toast.LENGTH_SHORT).show();
                    }
                }, error ->
                Toast.makeText(getActivity(), "error 1 "+error.toString(), Toast.LENGTH_SHORT).show()
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
            public void retry(VolleyError error) throws VolleyError {
            }
        });
            requestQueue.add(stringRequest);

    }

    public void trending_products() {
        Cache cache = new DiskBasedCache(getActivity().getCacheDir(), 1024 * 1024 * 5);
        Network network = new BasicNetwork(new HurlStack());
        requestQueue = new RequestQueue(cache, network);
        requestQueue.start();
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL_TRENDING,
                response -> {
                    try {
                        JSONArray array = new JSONArray(response);
                        for (int i = 0; i < array.length(); i++) {
                            JSONObject product = array.getJSONObject(i);
                            list1.add(new Items(product.getString("item_name"), product.getString("item_image"), product.getString("item_price"),product.getString("item_category")));
                        }
                        ItemsAdapter adapter = new ItemsAdapter(getActivity(), list1);
                        recyclerView1.setAdapter(adapter);
                        //               recyclerView1.setAdapter(adapter);
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(getActivity(), "Error" + e.toString(), Toast.LENGTH_SHORT).show();
                    }
                }, error ->
                Toast.makeText(getActivity(), error.toString(), Toast.LENGTH_SHORT).show()
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
            public void retry(VolleyError error) throws VolleyError {
            }
        });
        requestQueue.add(stringRequest);
    }

    public void office_products() {
        Cache cache = new DiskBasedCache(getActivity().getCacheDir(), 1024 * 1024 * 5);
        Network network = new BasicNetwork(new HurlStack());
        requestQueue = new RequestQueue(cache, network);
        requestQueue.start();
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL_OFFICE_PRODUCTS,
                response -> {
                    try {
                        JSONArray array = new JSONArray(response);
                        for (int i = 0; i < array.length(); i++) {
                            JSONObject product = array.getJSONObject(i);
                            list2.add(new Items(product.getString("item_name"), product.getString("item_image"), product.getString("item_price"),product.getString("item_category")));
                        }
                        ItemsAdapter adapter = new ItemsAdapter(getActivity(), list2);
                        recyclerView2.setAdapter(adapter);
                        //               recyclerView1.setAdapter(adapter);
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(getActivity(), "Error" + e.toString(), Toast.LENGTH_SHORT).show();
                    }
                }, error ->
                Toast.makeText(getActivity(), error.toString(), Toast.LENGTH_SHORT).show()
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
            public void retry(VolleyError error) throws VolleyError {
            }
        });
        requestQueue.add(stringRequest);
    }

    private void clothing_products() {
        Cache cache = new DiskBasedCache(getActivity().getCacheDir(), 1024 * 1024 * 5);
        Network network = new BasicNetwork(new HurlStack());
        requestQueue = new RequestQueue(cache, network);
        requestQueue.start();
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL_CLOTHING, response -> {
            try {
                JSONArray array = new JSONArray(response);
                for (int i = 0; i < array.length(); i++) {
                    JSONObject product = array.getJSONObject(i);
                    list3.add(new Items(product.getString("item_name"), product.getString("item_image"), product.getString("item_price"),product.getString("item_category")));
                }
                ItemsAdapter adapter = new ItemsAdapter(getActivity(), list3);
                recyclerView3.setAdapter(adapter);
            } catch (JSONException ex) {
                ex.printStackTrace();
                Toast.makeText(getActivity(), "Error " + ex.toString(), Toast.LENGTH_SHORT).show();
            }
        }, error -> Toast.makeText(getActivity(), error.toString(), Toast.LENGTH_SHORT).show()
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
            public void retry(VolleyError error) throws VolleyError {
            }
        });
        requestQueue.add(stringRequest);
    }

    private void photo_gifts() {
        Cache cache = new DiskBasedCache(getActivity().getCacheDir(), 1024 * 1024 * 5);
        Network network = new BasicNetwork(new HurlStack());
        requestQueue = new RequestQueue(cache, network);
        requestQueue.start();
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL_PHOTO_GIFTS, response -> {
            try {
                JSONArray array = new JSONArray(response);
                for (int i = 0; i < array.length(); i++) {
                    JSONObject product = array.getJSONObject(i);
                    list5.add(new Items(product.getString("item_name"), product.getString("item_image"), product.getString("item_price"),product.getString("item_category")));
                }
                ItemsAdapter adapter = new ItemsAdapter(getActivity(), list5);
                recyclerView5.setAdapter(adapter);
            } catch (JSONException ex) {
                ex.printStackTrace();
                Toast.makeText(getActivity(), "Error " + ex.toString(), Toast.LENGTH_SHORT).show();
            }
        }, error -> Toast.makeText(getActivity(), error.toString(), Toast.LENGTH_SHORT).show()
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
            public void retry(VolleyError error) throws VolleyError {
            }
        });
        requestQueue.add(stringRequest);
    }

    private void marketing_materials() {
        Cache cache = new DiskBasedCache(getActivity().getCacheDir(), 1024 * 1024 * 5);
        Network network = new BasicNetwork(new HurlStack());
        requestQueue = new RequestQueue(cache, network);
        requestQueue.start();
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL_MARKETING_MATERIALS, response -> {
            try {
                JSONArray array = new JSONArray(response);
                for (int i = 0; i < array.length(); i++) {
                    JSONObject product = array.getJSONObject(i);
                    list7.add(new Items(product.getString("item_name"), product.getString("item_image"), product.getString("item_price"),product.getString("item_category")));
                }
                ItemsAdapter adapter = new ItemsAdapter(getActivity(), list7);
                recyclerView7.setAdapter(adapter);
            } catch (JSONException ex) {
                ex.printStackTrace();
                Toast.makeText(getActivity(), "Error " + ex.toString(), Toast.LENGTH_SHORT).show();
            }
        }, error -> Toast.makeText(getActivity(), error.toString(), Toast.LENGTH_SHORT).show()
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
            public void retry(VolleyError error) throws VolleyError {
            }
        });
        requestQueue.add(stringRequest);
    }

    private void office_supplies() {
        Cache cache = new DiskBasedCache(getActivity().getCacheDir(), 1024 * 1024 * 5);
        Network network = new BasicNetwork(new HurlStack());
        requestQueue = new RequestQueue(cache, network);
        requestQueue.start();
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL_OFFICE_SUPPLIES, response -> {
            try {
                JSONArray array = new JSONArray(response);
                for (int i = 0; i < array.length(); i++) {
                    JSONObject product = array.getJSONObject(i);
                    list4.add(new Items(product.getString("item_name"), product.getString("item_image"), product.getString("item_price"),product.getString("item_category")));
                }
                ItemsAdapter adapter = new ItemsAdapter(getActivity(), list4);
                recyclerView4.setAdapter(adapter);
            } catch (JSONException ex) {
                ex.printStackTrace();
                Toast.makeText(getActivity(), "Error " + ex.toString(), Toast.LENGTH_SHORT).show();
            }
        }, error -> Toast.makeText(getActivity(), error.toString(), Toast.LENGTH_SHORT).show()
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
            public void retry(VolleyError error) throws VolleyError {
            }
        });
        requestQueue.add(stringRequest);
    }

    private void business_cards() {
        Cache cache = new DiskBasedCache(getActivity().getCacheDir(), 1024 * 1024 * 5);
        Network network = new BasicNetwork(new HurlStack());
        requestQueue = new RequestQueue(cache, network);
        requestQueue.start();
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL_BUSINESS_CARDS, response -> {
            try {
                JSONArray array = new JSONArray(response);
                for (int i = 0; i < array.length(); i++) {
                    JSONObject product = array.getJSONObject(i);
                    list6.add(new Items(product.getString("item_name"), product.getString("item_image"), product.getString("item_price"),product.getString("item_category")));
                }
                ItemsAdapter adapter = new ItemsAdapter(getActivity(), list6);
                recyclerView6.setAdapter(adapter);
            } catch (JSONException ex) {
                ex.printStackTrace();
                Toast.makeText(getActivity(), "Error " + ex.toString(), Toast.LENGTH_SHORT).show();
            }
        }, error -> Toast.makeText(getActivity(), error.toString(), Toast.LENGTH_SHORT).show()
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
            public void retry(VolleyError error) throws VolleyError {
            }
        });
        requestQueue.add(stringRequest);
    }
}