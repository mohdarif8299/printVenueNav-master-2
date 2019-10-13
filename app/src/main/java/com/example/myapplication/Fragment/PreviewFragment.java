package com.example.myapplication.Fragment;
import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.fragment.app.DialogFragment;
import com.example.myapplication.R;
public class PreviewFragment extends DialogFragment {
    TextView previewDisplay;
    String abc;
    Typeface t;
    public PreviewFragment(){
    }

    @SuppressLint("ValidFragment")
    public PreviewFragment(Typeface t) {
        this.t=t;

        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =inflater.inflate(R.layout.fragment_preview, container, false);

        Bundle b=new Bundle();
     //   b.getString("TextView");
           //abc=getArguments().getString("TextView");
        try {
            byte[] encodeByte = Base64.decode(   b.getString("TextView"),Base64.DEFAULT);
            Bitmap bitmap = BitmapFactory.decodeByteArray(encodeByte,0,encodeByte.length);
            ImageView imageView = v.findViewById(R.id.preview);
            imageView.setImageBitmap(bitmap);
        }
        catch(Exception e){
            Toast.makeText(getActivity(),e.toString(),Toast.LENGTH_SHORT).show();

        }
        /* t= getArguments().getString("TypeFace");*/


      //  previewDisplay=v.findViewById(R.id.displayName2);
      //  Toast.makeText(getContext(),t+"hiiii",Toast.LENGTH_LONG).show();

//        previewDisplay.setText(abc);
   //     previewDisplay.setTypeface(t);
        return v;
    }

    /*@Override
    public void sendInput(String input, Typeface tf) {
        previewDisplay.setText(input);
        previewDisplay.setTypeface(tf);
    }*/
}