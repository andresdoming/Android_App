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

import com.cs419group14.corvallisreuseandrepairdirectory.helper.AlertDialogManager;
import com.cs419group14.corvallisreuseandrepairdirectory.helper.ConnectionDetector;
import com.cs419group14.corvallisreuseandrepairdirectory.helper.JSONParser;

import org.apache.http.NameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ReuseActivity extends ListActivity {
    ConnectionDetector cd;
    AlertDialogManager alert = new AlertDialogManager();
    private ProgressDialog pDialog;
    JSONParser jsonParser = new JSONParser();

    ArrayList<HashMap<String, String>> categoriesList;
    JSONArray categories = null;

    private static final String URL_CATEGORIES = "http://web.engr.oregonstate.edu/~izunot/CS419/android/categories2.php";

    private static final String TAG_ID = "id";
    private static final String TAG_CAT_NAME = "name";
    private static final String TAG_SUBCAT_COUNT = "subcategories_count";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_repair);

        cd = new ConnectionDetector(getApplicationContext());

        if (!cd.isConnectingToInternet()) {
            alert.showAlertDialog(ReuseActivity.this, "Internet Connection Error",
                    "Please connect to working Internet connection", false);
            return;
        }

        categoriesList = new ArrayList<HashMap<String, String>>();
        new LoadCategories().execute();
        ListView lv = getListView();

        lv.setOnItemClickListener(new android.widget.AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View view, int arg2,
                                    long arg3) {
                Intent i = new Intent(getApplicationContext(), ReuseSubcategories.class);
                String categories_id = ((TextView) view.findViewById(R.id.categories_id)).getText().toString();
                i.putExtra("categories_id", categories_id);

                startActivity(i);
            }
        });
    }

    class LoadCategories extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(ReuseActivity.this);
            pDialog.setMessage("Listing Categories ...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        protected String doInBackground(String... args) {
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            String json = jsonParser.makeHttpRequest(URL_CATEGORIES, "GET",
                    params);

            Log.d("Categories JSON: ", "> " + json);

            try {
                categories = new JSONArray(json);

                if (categories != null) {
                    for (int i = 0; i < categories.length(); i++) {
                        JSONObject c = categories.getJSONObject(i);
                        String id = c.getString(TAG_ID);
                        String name = c.getString(TAG_CAT_NAME);
                        String subcategories_count = c.getString(TAG_SUBCAT_COUNT);

                        HashMap<String, String> map = new HashMap<String, String>();

                        map.put(TAG_ID, id);
                        map.put(TAG_CAT_NAME, name);
                        map.put(TAG_SUBCAT_COUNT, subcategories_count);
                        categoriesList.add(map);
                    }
                }else{
                    Log.d("Categories: ", "null");
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
                            ReuseActivity.this, categoriesList,
                            R.layout.list_item_categories, new String[] { TAG_ID,
                            TAG_CAT_NAME, TAG_SUBCAT_COUNT }, new int[] {
                            R.id.categories_id, R.id.name, R.id.subcategories_count });

                    // updating listview
                    setListAdapter(adapter);
                }
            });

        }

    }
}