package com.example.myapplication;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
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
import com.example.myapplication.Fragment.LoginFragment;
import com.example.myapplication.Fragment.MyDialogFragment;
import com.example.myapplication.Fragment.PreviewFragment;
import com.example.myapplication.PreferenceManager.MyPreference;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class customizeActivity extends AppCompatActivity implements MyDialogFragment.OninputListener {

    Button bc;
    TextView display,Preview,plus,minus,price,addCart;
    int i=1;
    int p;
    private static String URL_ADD_TO_CART = "https://colorpress.000webhostapp.com/vistaprint/Authentication/cart_file.php";
    String in,item_name;
    ImageView imageView;
    Typeface typeface;
    AlertDialog alertDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customize);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        item_name = ItemsAdapter.name;
        setTitle(item_name);
        bc=findViewById(R.id.dialogfragment);
        display=findViewById(R.id.displayName);
        Preview=findViewById(R.id.previewT);
        imageView=findViewById(R.id.preview);
        price=findViewById(R.id.price);
        addCart =findViewById(R.id.addToCart);
        price.setText("550");
        p=Integer.parseInt(price.getText()+"");
        Intent intent =getIntent();
        String image_url = intent.getStringExtra("image");
        Glide.with(getApplicationContext()).load(image_url).into(imageView);
        Preview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction f1=getSupportFragmentManager().beginTransaction();
                PreviewFragment pf=new PreviewFragment(typeface);
                Bundle b=new Bundle();
                b.putString("TextView",in);
               /* b.putString("TypeFace",typeface.toString());*/
                pf.setArguments(b);
                pf.show(f1,"peviewFragment");

            }
        });
        bc.setOnClickListener(v -> {
            MyDialogFragment mb=new MyDialogFragment();
            mb.show(getSupportFragmentManager(),"MyDialogFragment");
        });
        addCart.setOnClickListener((v)->{
            StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_ADD_TO_CART, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        String success = jsonObject.getString("success");
                        if (success.equals("1")) {
                            Snackbar snackbar= Snackbar.make(findViewById(R.id.snackBar),"Item Added to Cart", Snackbar.LENGTH_LONG).setAction("View Cart", v1 -> {
                                Intent intent1 = new Intent(getApplicationContext(),CartActivity.class);
                                startActivity(intent1);
                            });
                            snackbar.show();
                        } else if (success.equals("0")) {
                            Toast.makeText(getApplicationContext(),"Something went wrong",Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException exception) {
                        exception.printStackTrace();
                        Toast.makeText(getApplicationContext(),exception.toString(),Toast.LENGTH_SHORT).show();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                }
            }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
                    params.put("email",new MyPreference(getApplicationContext()).getEmail());
                    params.put("item_name", item_name);
                    params.put("item_image", image_url);
                    params.put("item_price", p+"");
                    params.put("item_quantity", i+"");
                    params.put("total_price", (i*p)+"");
                    return params;
                }
            };
            RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
            requestQueue.add(stringRequest);
        });
    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }
    @Override
    public void sendInput(String input, Typeface tf) {
        display.setTypeface(tf);
        display.setText(input);
        in=input;
        typeface=tf;
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
