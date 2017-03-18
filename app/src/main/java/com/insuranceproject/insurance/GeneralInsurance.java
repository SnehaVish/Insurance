package com.insuranceproject.insurance;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class GeneralInsurance extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_general_insurance);
    }
    public void submit(View view)
    {
        int i=view.getId();
        Intent intent=null;
        if(i==R.id.moneyBack)//VEHICLE INSURANCE
        {
            Toast.makeText(GeneralInsurance.this,"Vehicle Insurance",Toast.LENGTH_LONG).show();
           // intent= new Intent(this, GeneralInsurance.class);
        }
        if(i==R.id.childrens)//House Insurance
        {
            Toast.makeText(GeneralInsurance.this,"House Insurance",Toast.LENGTH_LONG).show();
          //  intent= new Intent(this, GeneralInsurance.class);
        }

        //startActivity(intent);
    }

}
