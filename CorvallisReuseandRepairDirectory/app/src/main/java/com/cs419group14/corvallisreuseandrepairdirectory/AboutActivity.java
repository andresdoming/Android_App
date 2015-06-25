package com.cs419group14.corvallisreuseandrepairdirectory;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.widget.TextView;

public class AboutActivity extends ActionBarActivity {

    private TextView text2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        text2 = (TextView) findViewById(R.id.text2);

    }

}
