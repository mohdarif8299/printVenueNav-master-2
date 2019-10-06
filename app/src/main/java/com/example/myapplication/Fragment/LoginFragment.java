package com.example.myapplication.Fragment;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.myapplication.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginFragment extends Fragment {
    CheckBox checkbox;
    EditText email,password;
    Button login;
    ProgressBar progressBar;
    SharedPreferences pref;
    static String URL_LOGIN="https://colorpress.000webhostapp.com/vistaprint/Authentication/login.php";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        checkbox =view.findViewById(R.id.checkbox);
        password =  view.findViewById(R.id.password);
        email = view.findViewById(R.id.email);
        login = view.findViewById(R.id.login);
        progressBar = view.findViewById(R.id.progress);
        pref = getActivity().getSharedPreferences("user_credentials", 0); // 0 - for private mode
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login();
            }
        });
        checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (!isChecked) {
                    password.setTransformationMethod(PasswordTransformationMethod.getInstance());

                } else {
                    password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }
            }
        });

        return view;
    }
    private void login(){
        progressBar.setVisibility(View.VISIBLE);
        final String username = email.getText().toString().trim();
        final String userpassword = password.getText().toString().trim();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_LOGIN, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try{
                    JSONObject jsonObject =new JSONObject(response);
                    String success = jsonObject.getString("success");
                    JSONArray jsonArray = jsonObject.getJSONArray("login");
                    if (success.equals("1")){
                        for (int i=0;i<jsonArray.length();i++){
                            JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                            String name = jsonObject1.getString("name");
                            String email = jsonObject1.getString("email");
                            SharedPreferences.Editor editor = pref.edit();
                            editor.putString("name",name);
                            editor.commit();
                            Toast.makeText(getContext(),"Committed",Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.GONE);
                        }
                        email.setText("");
                        password.setText("");
                    }else if(success.equals("0")) {
                        Toast.makeText(getActivity(),"Something went wrong"+response,Toast.LENGTH_SHORT).show();
                        progressBar.setVisibility(View.GONE);
                    }
                }
                catch(JSONException exception){
                    exception.printStackTrace();
                    Toast.makeText(getActivity(),"Login Error"+exception.toString(),Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.GONE);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(),"Login Error"+error.toString(),Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.GONE);
            }
        })
        {
            @Override
            protected Map<String,String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("email",username);
                params.put("password",userpassword);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);
    }
}
