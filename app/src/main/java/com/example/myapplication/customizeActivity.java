package com.example.myapplication;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Base64;
import android.view.View;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.example.myapplication.Adapter.ItemsAdapter;
import com.example.myapplication.Fragment.MyDialogFragment;
import com.example.myapplication.Fragment.PreviewFragment;
import com.example.myapplication.PreferenceManager.MyPreference;
import com.google.android.material.snackbar.Snackbar;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;


import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
// implements MyDialogFragment.OninputListener
public class customizeActivity extends AppCompatActivity {
    Button bc;
    TextView display, Preview, plus, minus, price, addCart;
    int i = 1;
    int p;
    RelativeLayout relativeLayout;
    String captionString = "Arif";
    private static String URL_ADD_TO_CART = "https://colorpress.000webhostapp.com/vistaprint/Authentication/cart_file.php";
    String in, item_name;
    ImageView imageView;
    Typeface typeface;
    AlertDialog alertDialog;
    String temp;
    Bitmap bitmap;
    WebView myWebView;
    Document document;
    String url = "https://www.vistaprint.in/studio.aspx?template=574875_AFD_AGK&ag=True&xnav=HSG_Design_Image&rd=1";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customize);
         myWebView = findViewById(R.id.webview);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        item_name = ItemsAdapter.name;
//        setTitle(item_name);
         new MyAsynTask().execute();
//        relativeLayout = findViewById(R.id.customized_image);
//        relativeLayout.setDrawingCacheEnabled(true);
//        bitmap = loadBitmapFromView(relativeLayout, relativeLayout.getWidth(), relativeLayout.getHeight());
//        relativeLayout.setDrawingCacheEnabled(false);
//        try {
//           ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
//           bitmap.compress(Bitmap.CompressFormat.PNG,100,byteArrayOutputStream);
//           byte [] b = byteArrayOutputStream.toByteArray();
//            temp = Base64.encodeToString(b,Base64.DEFAULT);
//            Toast.makeText(getApplicationContext(),temp,Toast.LENGTH_SHORT).show();
//       }
//       catch (Exception e){
//           Toast.makeText(getApplicationContext(),e.toString(),Toast.LENGTH_SHORT).show();
//       }
//        bc = findViewById(R.id.dialogfragment);
//        display = findViewById(R.id.displayName);
//        Preview = findViewById(R.id.previewT);
//        imageView = findViewById(R.id.preview);
//        price = findViewById(R.id.price);
//        addCart = findViewById(R.id.addToCart);
//        price.setText("550");
////        p=Integer.parseInt(price.getText()+"");
//        Intent intent = getIntent();
//        String image_url = intent.getStringExtra("image");
//        Glide.with(getApplicationContext()).load(image_url).into(imageView);
//
//        Preview.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                FragmentTransaction f1=getSupportFragmentManager().beginTransaction();
//                PreviewFragment pf=new PreviewFragment(typeface);
//                Bundle b=new Bundle();
//                b.putString("TextView",temp);
//                /* b.putString("TypeFace",typeface.toString());*/
//                pf.setArguments(b);
//                pf.show(f1,"peviewFragment");
//
//            }
//        });
//        bc.setOnClickListener(v -> {
//            MyDialogFragment mb=new MyDialogFragment();
//            mb.show(getSupportFragmentManager(),"MyDialogFragment");
//        });
//        addCart.setOnClickListener((v)->{
//            StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_ADD_TO_CART, new Response.Listener<String>() {
//                @Override
//                public void onResponse(String response) {
//                    try {
//                        JSONObject jsonObject = new JSONObject(response);
//                        String success = jsonObject.getString("success");
//                        if (success.equals("1")) {
//                            Snackbar snackbar= Snackbar.make(findViewById(R.id.snackBar),"Item Added to Cart", Snackbar.LENGTH_LONG).setAction("View Cart", v1 -> {
//                                Intent intent1 = new Intent(getApplicationContext(),CartActivity.class);
//                                startActivity(intent1);
//                            });
//                            snackbar.show();
//                        } else if (success.equals("0")) {
//                            Toast.makeText(getApplicationContext(),"Something went wrong",Toast.LENGTH_SHORT).show();
//                        }
//                    } catch (JSONException exception) {
//                        exception.printStackTrace();
//                        Toast.makeText(getApplicationContext(),exception.toString(),Toast.LENGTH_SHORT).show();
//                    }
//                }
//            }, new Response.ErrorListener() {
//                @Override
//                public void onErrorResponse(VolleyError error) {
//                }
//            }) {
//                @Override
//                protected Map<String, String> getParams() throws AuthFailureError {
//                    Map<String, String> params = new HashMap<>();
//                    params.put("email",new MyPreference(getApplicationContext()).getEmail());
//                    params.put("item_name", item_name);
//                    params.put("item_image", image_url);
//                    params.put("item_price", p+"");
//                    params.put("item_quantity", i+"");
//                    params.put("total_price", (i*p)+"");
//                    return params;
//                }
//            };
//            RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
//            requestQueue.add(stringRequest);
//        });
    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }
//    @Override
//    public void sendInput(String input, Typeface tf) {
//        display.setTypeface(tf);
//        display.setText(input);
//        in=input;
//        typeface=tf;
//    }
//    @Override
//    public void onBackPressed() {
//        super.onBackPressed();
//    }
//    public static Bitmap loadBitmapFromView(View v, int width, int height) {
//        Bitmap b = Bitmap.createBitmap(width , height, Bitmap.Config.ARGB_8888);
//        Canvas c = new Canvas(b);
//        v.layout(0, 0, width, height);
////Get the view’s background
//        Drawable bgDrawable =v.getBackground();
//        if (bgDrawable!=null)
////has background drawable, then draw it on the canvas
//            bgDrawable.draw(c);
//        else
////does not have background drawable, then draw white background on the canvas
//            c.drawColor(Color.WHITE);
//        v.draw(c);
//        return b;
private class MyAsynTask extends AsyncTask<Void, Void, Document> {
    @Override
    protected Document doInBackground(Void... voids) {

        Document document = null;
        try {
            document= Jsoup.connect(url).get();
            document.getElementsByClass("main-nav").remove();
            document.getElementsByClass("site-footer").remove();
            document.getElementsByClass("filter-container accordion accordion-multiple").remove();
            document.getElementsByClass("widgetBox filter-container").remove();
            //document.getElementsByClass("footer-container").remove();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return document;
    }

    @Override
    protected void onPostExecute(Document document) {
        super.onPostExecute(document);
        myWebView.loadDataWithBaseURL(url, document.toString(), "text/html", "utf-8", "");
        myWebView.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        myWebView.getSettings().setJavaScriptEnabled(true);
        myWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                view.loadUrl(url);
                return super.shouldOverrideUrlLoading(view, request);
            }
        });
    }}
    }

