package com.example.kamil.godzinypracownikow;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TabHost;

@SuppressWarnings("deprecation")
public class Glowna extends TabActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_glowna);

        TabHost tabHost = (TabHost) findViewById(android.R.id.tabhost);

        TabHost.TabSpec tab1 = tabHost.newTabSpec("Tab1");
        TabHost.TabSpec tab2 = tabHost.newTabSpec("Tab2");


        tab1.setIndicator("Tab1");
        tab1.setContent(new Intent(this, Tab1.class));

        tab2.setIndicator("Tab2");
        tab2.setContent(new Intent(this, Tab2.class));



        tabHost.addTab(tab1);
        tabHost.addTab(tab2);

    }

    @Override
    public boolean onMenuItemSelected(int featureId, MenuItem item) {
        return super.onMenuItemSelected(featureId, item);
    }
}
