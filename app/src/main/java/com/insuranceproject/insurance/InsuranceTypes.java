package com.insuranceproject.insurance;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.auth.FirebaseAuth;

import java.util.HashMap;
import java.util.Map;

public class InsuranceTypes extends AppCompatActivity {

    SharedPreferences sharedpreferences;
    public static final String MyPREFERENCES = "MyPrefs";
    public static final String user = "user";
    FirebaseAuth mAuth;
    String name,uid,email;
    TextView tv;
    ProgressDialog dialog;
    public static  final String REGISTER_url="http://snehasv.esy.es/saveuser.php";
    int socketTimeout = 60000; // 60 seconds.
    RetryPolicy policy = new DefaultRetryPolicy(socketTimeout,DefaultRetryPolicy.DEFAULT_MAX_RETRIES,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insurance_types);
        name="";
        uid="";
        email="";
        tv=(TextView)findViewById(R.id.textView);
        dialog=new ProgressDialog(this);
        dialog.setMessage("Loading...");
        dialog.setCancelable(false);
        dialog.setInverseBackgroundForced(false);

        mAuth=FirebaseAuth.getInstance();
        name=mAuth.getCurrentUser().getDisplayName();
        uid=mAuth.getCurrentUser().getUid();
        email=mAuth.getCurrentUser().getEmail();
        tv.setText("Welcome "+name+"\nPlease choose an insurance type:");

        dialog.show();

        StringRequest stringRequest=new StringRequest(Request.Method.POST,REGISTER_url,
                new Response.Listener<String>(){
                    @Override
                    public void onResponse(String response){
                        dialog.hide();
                        Toast.makeText(InsuranceTypes.this,response,Toast.LENGTH_LONG).show();
                    }
                },
                new Response.ErrorListener(){
                    @Override
                    public void onErrorResponse(VolleyError error){
                        Toast.makeText(InsuranceTypes.this,error.toString(),Toast.LENGTH_LONG).show();
                    }
                }){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params=new HashMap<String,String>();
                params.put("uid",uid);
                params.put("name",name);
                params.put("email",email);
                return params;


            }
        };
        stringRequest.setRetryPolicy(policy);
        RequestQueue requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString(user, uid);
        editor.commit();


    }
    public void SignOut(View view)
    {


        FirebaseAuth.getInstance().signOut();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);

    }
    public void generalInsurance(View view)
    {
        Intent intent = new Intent(this, GeneralInsurance.class);
        startActivity(intent);
    }
    public void lifeInsurance(View view)
    {
        Intent intent = new Intent(this, LifeInsurance.class);
        startActivity(intent);
    }
}
