package com.example.myapplication;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class CardCustomizeActivity extends AppCompatActivity implements View.OnClickListener {

    TextView Cname,Cmessage,name,Add1,Add2,phone,email,website,designation;
    Button BCname,BCmessage,Bname,BAdd1,BAdd2,Bphone,Bemail,Bwebsite,Bdesignation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_customize);


        Cname=findViewById(R.id.compnyname);
        BCname=findViewById(R.id.one1);

        Cmessage=findViewById(R.id.compnymessage);
        BCmessage=findViewById(R.id.one2);

        name=findViewById(R.id.name01);
        Bname=findViewById(R.id.one3);

        designation=findViewById(R.id.discription01);
        Bdesignation=findViewById(R.id.one4);

        Add1=findViewById(R.id.Address01);
        BAdd1=findViewById(R.id.one5);

        Add2=findViewById(R.id.Address02);
        BAdd2=findViewById(R.id.one6);

        phone=findViewById(R.id.phone01);
        Bphone=findViewById(R.id.one7);

        email=findViewById(R.id.email01);
        Bemail=findViewById(R.id.one8);

        website=findViewById(R.id.website01);
        Bwebsite=findViewById(R.id.one9);


    }

    @Override
    public void onClick(View view) {

    }
}
