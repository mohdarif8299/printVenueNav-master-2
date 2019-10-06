package com.example.myapplication.Fragment;


import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.myapplication.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class CardFragment extends Fragment {

    LinearLayout cardLayout;


    public CardFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_card, container, false);
        cardLayout= view.findViewById(R.id.card01);

        cardLayout.setOnClickListener(view1 ->{
            FragmentTransaction tf=getFragmentManager().beginTransaction();
            CardProduct cardProduct=new CardProduct();
            tf.replace(R.id.container,cardProduct);
            tf.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
            tf.commit();
            tf.addToBackStack(null);

        } );

        return view;
    }

}
