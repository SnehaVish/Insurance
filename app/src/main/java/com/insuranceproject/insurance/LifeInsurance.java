package com.insuranceproject.insurance;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class LifeInsurance extends AppCompatActivity {

    String type;
    ProgressDialog dialog;
    public static  final String REGISTER_url="http://snehasv.esy.es/getinsurance.php";
    int socketTimeout = 60000; // 60 seconds.
    RetryPolicy policy = new DefaultRetryPolicy(socketTimeout,DefaultRetryPolicy.DEFAULT_MAX_RETRIES,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        type="";
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_life_insurance);
        dialog=new ProgressDialog(this);
        dialog.setMessage("Loading...");
        dialog.setCancelable(false);
        dialog.setInverseBackgroundForced(false);

    }
    public void submit(View view)
    {
        int i=view.getId();
        final Intent intent=new Intent(this, PolicySelect.class);

        if(i==R.id.moneyBack)//Money back
        {
                type="2";// These are the type-codes in database in the types table
        }
        if(i==R.id.childrens)//Childrens
        {
                type="3";
        }
        if(i==R.id.endowment)//Endowment
        {
                type="1";
        }
        if(i==R.id.pension)//Pension
        {
                type="4";
        }
        dialog.show();
        StringRequest stringRequest=new StringRequest(Request.Method.POST,REGISTER_url,
                new Response.Listener<String>(){
                    @Override
                    public void onResponse(String response){
                        dialog.hide();
                        Toast.makeText(LifeInsurance.this,response,Toast.LENGTH_LONG).show();
                        //intent.putExtra("result",response);
                        //startActivity(intent);
                    }
                },
                new Response.ErrorListener(){
                    @Override
                    public void onErrorResponse(VolleyError error){
                        Toast.makeText(LifeInsurance.this,"Unstable connection,please try again later.",Toast.LENGTH_LONG).show();
                    }
                }){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params=new HashMap<String,String>();
                params.put("type",type);
                return params;


            }
        };
        stringRequest.setRetryPolicy(policy);
        RequestQueue requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
}
