package com.example.pavneet.quiz;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class Result extends AppCompatActivity {
    Float Score;
    TextView scr;
    Button res;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        Score=getIntent().getFloatExtra("Score",0);
        scr=(TextView)findViewById(R.id.score);
        scr.setText(""+Score);
        res=(Button)findViewById(R.id.res);
        res.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Result.this,MainActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
