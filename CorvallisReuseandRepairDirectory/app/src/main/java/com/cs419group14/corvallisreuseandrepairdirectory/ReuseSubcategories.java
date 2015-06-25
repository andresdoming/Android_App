package com.cs419group14.corvallisreuseandrepairdirectory;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.cs419group14.corvallisreuseandrepairdirectory.helper.AlertDialogManager;
import com.cs419group14.corvallisreuseandrepairdirectory.helper.ConnectionDetector;
import com.cs419group14.corvallisreuseandrepairdirectory.helper.JSONParser;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class ReuseSubcategories extends ListActivity {
    ConnectionDetector cd;
    AlertDialogManager alert = new AlertDialogManager();
    private ProgressDialog pDialog;
    JSONParser jsonParser = new JSONParser();

    ArrayList<HashMap<String, String>> businessList;
    JSONArray categories = null;
    String categories_id, name;

    private static final String URL_CATEGORIES = "http://web.engr.oregonstate.edu/~izunot/CS419/android/categories_subcategories2.php";

    private static final String TAG_SUBCAT = "subcategories";
    private static final String TAG_ID = "id";
    private static final String TAG_SUBCAT_NAME = "subcat_name";
    private static final String TAG_CAT_NAME = "catname";
    private static final String TAG_CAT_ID = "catid";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_repairsubcategories);

        cd = new ConnectionDetector(getApplicationContext());

        if (!cd.isConnectingToInternet()) {
            alert.showAlertDialog(ReuseSubcategories.this, "Internet Connection Error",
                    "Please connect to working Internet connection", false);
            return;
        }

        Intent i = getIntent();
        categories_id = i.getStringExtra("categories_id");
        businessList = new ArrayList<HashMap<String, String>>();
        new LoadBusinesses().execute();

        ListView lv = getListView();

        lv.setOnItemClickListener(new android.widget.AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View view, int arg2,
                                    long arg3) {

                Intent i = new Intent(getApplicationContext(), BusinessListActivity.class);

                String categories_id = ((TextView) view.findViewById(R.id.categories_id)).getText().toString();
                String subcategories_id = ((TextView) view.findViewById(R.id.subcategories_id)).getText().toString();

                Toast.makeText(getApplicationContext(), "Categories Id: " + categories_id + ", Subcategories Id: " + subcategories_id, Toast.LENGTH_SHORT).show();

                i.putExtra("categories_id", categories_id);
                i.putExtra("subcategories_id", subcategories_id);

                startActivity(i);
            }
        });

    }

    class LoadBusinesses extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(ReuseSubcategories.this);
            pDialog.setMessage("Loading subcategories ...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        protected String doInBackground(String... args) {
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair(TAG_ID, categories_id));
            String json = jsonParser.makeHttpRequest(URL_CATEGORIES, "GET",
                    params);
            Log.d("Business List JSON: ", json);

            try {
                JSONObject jObj = new JSONObject(json);
                if (jObj != null) {
                    String categories_id = jObj.getString(TAG_ID);
                    name = jObj.getString(TAG_CAT_NAME);
                    categories = jObj.getJSONArray(TAG_SUBCAT);

                    if (categories != null) {
                        for (int i = 0; i < categories.length(); i++) {
                            JSONObject c = categories.getJSONObject(i);

                            String subcategories_id = c.getString(TAG_ID);
                            String business_no = String.valueOf(i + 1);
                            String name = c.getString(TAG_SUBCAT_NAME);
                            String category = c.getString(TAG_CAT_ID);
                            HashMap<String, String> map = new HashMap<String, String>();

                            map.put("categories_id", categories_id);
                            map.put(TAG_ID, subcategories_id);
                            map.put("business_no", business_no + ".");
                            map.put(TAG_SUBCAT_NAME, name);
                            map.put(TAG_CAT_ID, category);

                            businessList.add(map);
                        }
                    } else {
                        Log.d("Categories: ", "null");
                    }
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }

        protected void onPostExecute(String file_url) {
            pDialog.dismiss();
            runOnUiThread(new Runnable() {
                public void run() {
                    ListAdapter adapter = new SimpleAdapter(
                            ReuseSubcategories.this, businessList,
                            R.layout.list_item_subcategories, new String[] { "categories_id", TAG_ID, "business_no",
                            TAG_SUBCAT_NAME, TAG_CAT_ID }, new int[] {
                            R.id.categories_id, R.id.subcategories_id, R.id.business_no, R.id.name, R.id.category_id });

                    setListAdapter(adapter);
                    setTitle(name);
                }
            });

        }

    }
}