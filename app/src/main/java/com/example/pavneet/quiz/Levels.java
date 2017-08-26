package com.example.pavneet.quiz;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class Levels extends AppCompatActivity implements View.OnClickListener {

    Button level1,level2,level3,level4,level5,btn;
    Intent intent;
    String levels;
    SQLiteDatabase database;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_levels);
        level1=(Button)findViewById(R.id.level1);
        level2=(Button)findViewById(R.id.level2);
        level3=(Button)findViewById(R.id.level3);
        level4=(Button)findViewById(R.id.level4);
        level5=(Button)findViewById(R.id.level5);
        level1.setOnClickListener(this);
        level2.setOnClickListener(this);
        level3.setOnClickListener(this);
        level4.setOnClickListener(this);
        level5.setOnClickListener(this);
        level2.setEnabled(false);
        level3.setEnabled(false);
        level4.setEnabled(false);
        level5.setEnabled(false);

          database=openOrCreateDatabase(DatabaseHelper.DBNAME,MODE_PRIVATE,null);
        Cursor cursor=database.rawQuery("select * from Points",null);
        cursor.moveToFirst();
        if(cursor.getFloat(1)>=1){
            level2.setEnabled(true);
        }
        cursor.moveToNext();
        if(cursor.getFloat(1)>=1){
            level3.setEnabled(true);
        }
        cursor.moveToNext();
        if(cursor.getFloat(1)>=1){
            level4.setEnabled(true);

        }
        cursor.moveToNext();
        if(cursor.getFloat(1)>=1){
            level5.setEnabled(true);

        }


    }


    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.level1:
                intent=new Intent(this,Questions.class);
                intent.putExtra("Level_name","Level1");
                //intent.putExtra("l1pts",level1pts);
                startActivity(intent);
                break;

            case R.id.level2:
                intent=new Intent(this,Questions.class);
                intent.putExtra("Level_name","Level2");
                startActivity(intent);
                break;

            case R.id.level3:
                intent=new Intent(this,Questions.class);
                intent.putExtra("Level_name","Level3");
                startActivity(intent);
                break;

            case R.id.level4:
                intent=new Intent(this,Questions.class);
                intent.putExtra("Level_name","Level4");
                startActivity(intent);
                break;

            case R.id.level5:
                intent=new Intent(this,Questions.class);
                intent.putExtra("Level_name","Level5");
                startActivity(intent);
                break;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        database.execSQL("update Points set pts=0");
        finish();
    }
}
