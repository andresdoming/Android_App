package com.cs419group14.corvallisreuseandrepairdirectory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.Bundle;
import android.os.StrictMode;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.widget.Button;
import android.widget.TextView;

public class DetailActivity extends Activity {


    @SuppressLint("NewApi")
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        showInfo();

        final Button btnBack = (Button) findViewById(R.id.btnBack);

        btnBack.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent newActivity = new Intent(DetailActivity.this,BusinessListActivity.class);
                startActivity(newActivity);
            }
        });

    }

    public void showInfo()
    {
        final TextView tbiz_id = (TextView)findViewById(R.id.txtbiz_id);
        final TextView tbiz_name = (TextView)findViewById(R.id.txtbiz_name);
        final TextView taddress = (TextView)findViewById(R.id.txtaddress);
        final TextView taddress2 = (TextView)findViewById(R.id.txtaddress2);
        final TextView tcity = (TextView)findViewById(R.id.txtcity);
        final TextView tzipcode = (TextView)findViewById(R.id.txtzipcode);
        final TextView tphone = (TextView)findViewById(R.id.txtphone);
        final TextView twebsite = (TextView)findViewById(R.id.txtwebsite);
        final TextView thours = (TextView)findViewById(R.id.txthours);
        final TextView tcategory_id = (TextView)findViewById(R.id.txtcategory_id);

        String url = "http://web.engr.oregonstate.edu/~izunot/CS419/android/getByBusinessID.php";

        Intent intent= getIntent();
        final String biz_id = intent.getStringExtra("biz_id");

        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("sbiz_id", biz_id));

        String resultServer  = getHttpPost(url,params);

        String strbiz_id = "";
        String strbiz_name = "";
        String straddress = "";
        String straddress2 = "";
        String strcity = "";
        String strzipcode = "";
        String strphone = "";
        String strwebsite = "";
        String strhours = "";
        String strcategory_id = "";

        JSONObject c;
        try {
            c = new JSONObject(resultServer);
            strbiz_id = c.getString("biz_id");
            strbiz_name = c.getString("biz_name");
            straddress = c.getString("address");
            straddress2 = c.getString("address2");
            strcity = c.getString("city");
            strzipcode = c.getString("zipcode");
            strphone = c.getString("phone");
            strwebsite = c.getString("website");
            strhours = c.getString("hours");
            strcategory_id = c.getString("category_id");

            if(!strbiz_id.equals(""))
            {
                tbiz_id.setText(strbiz_id);
                tbiz_name.setText(strbiz_name);
                taddress.setText(straddress);
                taddress2.setText(straddress2);
                tcity.setText(strcity);
                tzipcode.setText(strzipcode);
                tphone.setText(strphone);
                twebsite.setText(strwebsite);
                thours.setText(strhours);
                tcategory_id.setText(strcategory_id);
            }
            else
            {
                tbiz_id.setText("-");
                tbiz_name.setText("-");
                taddress.setText("-");
                taddress2.setText("-");
                tcity.setText("-");
                tzipcode.setText("-");
                tphone.setText("-");
                twebsite.setText("-");
                thours.setText("-");
                tcategory_id.setText("-");
            }

        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    public String getHttpPost(String url,List<NameValuePair> params) {
        StringBuilder str = new StringBuilder();
        HttpClient client = new DefaultHttpClient();
        HttpPost httpPost = new HttpPost(url);

        try {
            httpPost.setEntity(new UrlEncodedFormEntity(params));
            HttpResponse response = client.execute(httpPost);
            StatusLine statusLine = response.getStatusLine();
            int statusCode = statusLine.getStatusCode();
            if (statusCode == 200) { // Status OK
                HttpEntity entity = response.getEntity();
                InputStream content = entity.getContent();
                BufferedReader reader = new BufferedReader(new InputStreamReader(content));
                String line;
                while ((line = reader.readLine()) != null) {
                    str.append(line);
                }
            } else {
                Log.e("Log", "Failed to download result..");
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return str.toString();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_reuse_repair, menu);
        return true;
    }

}

