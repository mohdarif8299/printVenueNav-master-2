package com.example.myapplication.Fragment;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.myapplication.R;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.Map;

public class SignUpFragment extends Fragment implements AdapterView.OnItemSelectedListener {

    private String[] gender = { "Male","Female"};
    private EditText user_name,user_email,user_password;
    private Button signup;
    private ProgressBar progressBar;
    private static String URL_REGISTER="https://colorpress.000webhostapp.com/vistaprint/Authentication/signup.php";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sign_up, container, false);
        user_name = view.findViewById(R.id.name);
        user_email = view.findViewById(R.id.email);
        user_password = view.findViewById(R.id.password);
        signup = view.findViewById(R.id.signup);
        Spinner spin=view.findViewById(R.id.spinner);
        progressBar = view.findViewById(R.id.progress);
        spin.setOnItemSelectedListener(this);
        ArrayAdapter aa = new ArrayAdapter(getContext(), android.R.layout.simple_spinner_item,gender);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin.setAdapter(aa);
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                register();
            }
        });
        return view;
    }
    private  void register() {
        progressBar.setVisibility(View.VISIBLE);
        final String name = user_name.getText().toString().trim();
        final String email = user_email.getText().toString().trim();
        final String password = user_password.getText().toString().trim();
        if(TextUtils.isEmpty(name)) {
            Toast.makeText(getActivity(),"Name cannot be empty",Toast.LENGTH_SHORT).show();
            user_name.findFocus();
            return;
        }
        else if(TextUtils.isEmpty(email)) {
            Toast.makeText(getActivity(),"Email cannot be empty",Toast.LENGTH_SHORT).show();
            user_email.findFocus();
            return;
        }
        else if(TextUtils.isEmpty(password)){
            Toast.makeText(getActivity(),"Password cannot be empty",Toast.LENGTH_SHORT).show();
            user_password.findFocus();
            return;
        }
        else {
            StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_REGISTER, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        String success = jsonObject.getString("success");
                        if (success.equals("1")) {
                            Toast.makeText(getActivity(), "Regsiter Success", Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.GONE);
                            user_name.setText("");
                            user_email.setText("");
                            user_password.setText("");
                            FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                            fragmentTransaction.replace(R.id.frame, new LoginFragment());
                            fragmentTransaction.commit();
                        } else if (success.equals("0")) {
                            Toast.makeText(getActivity(), "Something went wrong" + response, Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.GONE);
                        }
                    } catch (JSONException exception) {
                        exception.printStackTrace();
                        Toast.makeText(getActivity(), "Regsiter Error" + exception.toString(), Toast.LENGTH_SHORT).show();
                        progressBar.setVisibility(View.GONE);
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(getActivity(), "Regsiter Error" + error.toString(), Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.GONE);
                }
            }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
                    params.put("name", name);
                    params.put("email", email);
                    params.put("password", password);
                    return params;
                }
            };
            RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
            requestQueue.add(stringRequest);
        }
    }
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        //  Toast.makeText(getContext(), "success", Toast.LENGTH_SHORT).show();
    }
    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
