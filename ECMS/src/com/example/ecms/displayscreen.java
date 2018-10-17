package com.example.ecms;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Iterator;
import java.text.*;

import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class displayscreen extends Activity{
	String result;
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.displayscreen);

        try {
            result = new HTTP().execute("http://emoscsit.cloudapp.net/emoncms/feed/average.json?&id=81&start=1291390051102&end=1449070051102&interval=20").get();
            Log.i("EmonLog", "Result: "+result);
            result = result.replaceAll("\"","");
            JSONArray jArray1 = new JSONArray(result);
            JSONArray json_array2 = (JSONArray) jArray1.get(jArray1.length()-1);          
            result = json_array2.getString(1);
            TextView powerval = (TextView) findViewById(R.id.powervalue);
            powerval.setText(result.substring(0,4) +"kWh");
            double energy = Double.parseDouble(result);
            double unit_price = 8.11;
            double total_price = unit_price * energy;
            String cost = new Double(total_price).toString();
            TextView costval = (TextView) findViewById(R.id.costvalue);
            costval.setText(cost.substring(0,4) + "PKR");
        } catch (Exception e) {
            Log.i("EmonLog", "Error: "+e);
        }
	}
	
	public void txtviewclick(View view)
	{
		Intent openMainActivity = new Intent("android.intent.action.LOGINSCREEN");
		startActivity(openMainActivity);
		this.finish();
	}
}

	class HTTP extends AsyncTask<String, Void, String>
	{
	    @Override
	    protected String doInBackground(String... params) {
	        String result = "";
	        try {
	            String urlstring = params[0];
	            Log.i("EmonLog", "HTTP Connecting: "+urlstring);
	            URL url = new URL(urlstring);
	            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

	            try {
	                InputStream reader = new BufferedInputStream(urlConnection.getInputStream());

	                String text = "";
	                int i = 0;
	                while((i=reader.read())!=-1)
	                {
	                    text += (char)i;
	                }
	                Log.i("EmonLog", "HTTP Response: "+text);
	                result = text;

	            } catch (Exception e) {
	                Log.i("EmonLog", "HTTP Exception: "+e);
	            }
	            finally {
	                Log.i("EmonLog", "HTTP Disconnecting");
	                urlConnection.disconnect();
	            }

	        } catch (Exception e) {
	            e.printStackTrace();
	            Log.i("EmonLog", "HTTP Exception: "+e);
	        }

	        return result;
	    }
	}