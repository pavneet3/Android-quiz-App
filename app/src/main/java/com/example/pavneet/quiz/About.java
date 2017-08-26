package com.example.pavneet.quiz;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.widget.TextView;

public class About extends AppCompatActivity {

    TextView  tlink;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        tlink=(TextView)findViewById(R.id.textView6);
        tlink.setMovementMethod(LinkMovementMethod.getInstance());
    }
}
