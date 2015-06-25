package com.cs419group14.corvallisreuseandrepairdirectory;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;


public class ReuseRepairActivity extends ActionBarActivity {

    private Button mReuseButton;
    private Button mRepairButton;
    private Button mDepotButton;
    private Button mGuideButton;
    private Button mAboutButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reuse_repair);

        mReuseButton = (Button)findViewById(R.id.reuseButton);
        mRepairButton = (Button)findViewById(R.id.repairButton);
        mDepotButton = (Button)findViewById(R.id.depotButton);
        mGuideButton = (Button)findViewById(R.id.guideButton);
        mAboutButton = (Button)findViewById(R.id.aboutButton);

        mReuseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startReuse();
            }
        });
        mRepairButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startRepair();
            }
        });
        mDepotButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startDepot();
            }
        });
        mGuideButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startGuide();
            }
        });
        mAboutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startAbout();
            }
        });

    }

    private void startReuse(){
        Intent intentreuse = new Intent(this, ReuseActivity.class);
        startActivity(intentreuse);
    }

    private void startRepair(){
        Intent intentrepair = new Intent(this, RepairActivity.class);
        startActivity(intentrepair);
    }

    private void startDepot(){
        Uri uri = Uri.parse("http://site.republicservices.com/site/corvallis-or/en/documents/corvallisrecycledepot.pdf");
        Intent intentdepot = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(intentdepot);
    }

    private void startGuide(){
        Uri uri_guide = Uri.parse("http://site.republicservices.com/site/corvallis-or/en/documents/detailedrecyclingguide.pdf");
        Intent intentguide = new Intent(Intent.ACTION_VIEW, uri_guide);
        startActivity(intentguide);
    }

    private void startAbout(){
        Intent intentabout = new Intent(this, AboutActivity.class);
        startActivity(intentabout);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_reuse_repair, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
