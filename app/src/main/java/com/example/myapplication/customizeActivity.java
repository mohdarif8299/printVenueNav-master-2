package com.example.myapplication;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

public class customizeActivity extends AppCompatActivity{

    Button bc;
    TextView display,Preview,plus,minus,price,addCart;
    int i=1;;
    int p;
    String in;
    ImageView imageView;
    Typeface typeface;
    AlertDialog alertDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customize);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle(ItemsAdapter.name);
        bc=findViewById(R.id.dialogfragment);
        display=findViewById(R.id.displayName);
        plus =  findViewById(R.id.plus);
        minus =  findViewById(R.id.minus);
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
//                FragmentTransaction f1=getSupportFragmentManager().beginTransaction();
//                PreviewFragment pf=new PreviewFragment(typeface);
//                Bundle b=new Bundle();
//                b.putString("TextView",in);
//               /* b.putString("TypeFace",typeface.toString());*/
//                pf.setArguments(b);
//                pf.show(f1,"peviewFragment");

            }
        });
        bc.setOnClickListener(v -> {
//            MyDialogFragment mb=new MyDialogFragment();
//            mb.show(getSupportFragmentManager(),"MyDialogFragment");
        });
        addCart.setOnClickListener((v)->{
//            Snackbar snackbar= Snackbar.make(findViewById(R.id.snackBar),"Item Added to Cart", Snackbar.LENGTH_LONG).setAction("View Cart", v1 -> {
//            });
//            snackbar.show();
        });
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
    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
