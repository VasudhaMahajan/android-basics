package com.s1.calculator;

import android.support.v4.view.ActionProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    public void add(View view)
    {
        TextView t=(TextView)findViewById(R.id.t3);
        EditText e1=(EditText)findViewById(R.id.t1);
        String s1=e1.getText().toString();
        int f1 = Integer.parseInt(s1);
        int f= 0;
        float f5= (float) 0.0;
        EditText e2=(EditText)findViewById(R.id.t2);
        String s2=e2.getText().toString();
        int f2 = Integer.parseInt(s2);
        switch(view.getId())
        {
            case R.id.button:
                f=f1+f2;
                t.setText(Integer.toString(f));
                break;
            case R.id.button2: 
                f=f1-f2;
                t.setText(Integer.toString(f));
                break;
            case R.id.button3:
                f=f1*f2;
                t.setText(Integer.toString(f));
                break;
            case R.id.button4:
                f5=(float)f1/f2;
                t.setText(Float.toString(f5));
                break;
        }
    }
}
