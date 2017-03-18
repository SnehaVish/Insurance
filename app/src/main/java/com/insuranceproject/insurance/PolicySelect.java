package com.insuranceproject.insurance;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.Charset;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

public class PolicySelect extends AppCompatActivity {
    String result;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_policy_select);
        Intent intent = getIntent();
        result=intent.getStringExtra("result");
        final String [] idArray=null;
        final String [] nameArray=null;


        try {
            SAXParserFactory factory = SAXParserFactory.newInstance();

            SAXParser saxParser = factory.newSAXParser();


            DefaultHandler handler = new DefaultHandler() {

                boolean policyId = false;
                int i=0;
                boolean policyName=false;
                String id="",name="";

                public void startElement(String uri, String localName,String qName,
                                         Attributes attributes) throws SAXException {
                    if (qName.equalsIgnoreCase("policyId"))
                    {
                        policyId = true;
                    }
                    if(qName.equalsIgnoreCase("policyName"))
                    {
                        policyName=true;
                    }
                }//end of startElement method
                public void endElement(String uri, String localName,
                                       String qName) throws SAXException {
                }

                public void characters(char ch[], int start, int length) throws SAXException {
                    if (policyId) {

                        id= new String(ch, start, length);
                        idArray[i]=id;
                        policyId = false;
                    }
                    if (policyName) {

                        name= new String(ch, start, length);
                        nameArray[i++]=name;
                        policyName = false;
                    }

                }//end of characters


            };//end of DefaultHandler object

            //InputStream is = getAssets().open("file.xml");
            InputStream is= null;

            is = new ByteArrayInputStream(result.getBytes(Charset.forName("UTF-8")));

            saxParser.parse(is, handler);
        } catch (Exception e) {e.printStackTrace();}

        Spinner sp=(Spinner)findViewById(R.id.spinner);
        ArrayAdapter<String> adapter = new ArrayAdapter(PolicySelect.this, android.R.layout.simple_spinner_item, nameArray);
        sp.setAdapter(adapter);

    }
}
