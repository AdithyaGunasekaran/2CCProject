package com.example.urlshortener;

import org.json.JSONException;
import org.json.JSONObject;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.app.Activity;
import android.app.ProgressDialog;
import com.example.urlshortener.URLShortener;

public class MainActivity extends Activity {
    Button Submit;
    EditText longurl;
    TextView shortUrl;
    static String address="https://www.googleapis.com/urlshortener/v1/url?shortUrl=http://goo.gl/fbsS&key=AIzaSyBipRANA5c5aiLSIOm9pC-S6uR3_7W9x6M";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Submit = (Button)findViewById(R.id.submit);
        longurl = (EditText)findViewById(R.id.LongUrl);
        shortUrl = (TextView)findViewById(R.id.ShortUrl);
        Submit.setOnClickListener(new OnClickListener() {

            public void onClick(View v) {
                // TODO Auto-generated method stub
                new URLShort().execute();
            }
        });

    }

    private class URLShort extends AsyncTask<String, String, JSONObject> {
        private ProgressDialog pDialog;
        String longUrl;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            pDialog = new ProgressDialog(MainActivity.this);
            pDialog.setMessage("Contacting Google Servers ...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            longUrl = longurl.getText().toString();
            pDialog.show();

        }

        @Override
        protected JSONObject doInBackground(String... args) {
            URLShortener jParser = new URLShortener();
            JSONObject json = jParser.getJSONFromUrl(address,longUrl);
            return json;
        }
        String ShortUrl;
        @Override
        protected void onPostExecute(JSONObject json) {
            pDialog.dismiss();
            try {
                if (json != null){
                    ShortUrl = json.getString("id");
                    shortUrl.setText(ShortUrl);
                    pDialog.dismiss();
                }else{
                    shortUrl.setText("Network Error");
                    pDialog.dismiss();
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }


        }
    }
}