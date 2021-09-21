package com.s1.gps_geolocator;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;


public class MainActivity extends AppCompatActivity {

    String str;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    public void geo(View view)
    {
        switch(view.getId())
        {
            case R.id.viewall:
            str = "1";
                break;
            case R.id.bayakarve:
                str = "2";
                break;
            case R.id.yashlaxmi:
                str = "3";
                break;
            case R.id.divekar:
                str = "4";
                break;
        }
        Intent intent=new Intent(this,MapsActivity.class);
        intent.putExtra("first",str);
        startActivity(intent);
    }
}
