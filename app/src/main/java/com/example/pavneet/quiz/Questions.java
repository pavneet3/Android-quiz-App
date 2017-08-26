package com.example.pavneet.quiz;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.Drawable;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.github.lzyzsd.circleprogress.DonutProgress;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

public class Questions extends AppCompatActivity {

    DonutProgress progressbar;
    private DatabaseHelper databaseHelper;
    RadioGroup radioGroup;
    RadioButton option1,option2,option3,option4,selectedradio,skip;
    TextView question,namelevel,points;
    Button next;
    String LEV,ans,correct;
    Cursor cursor,crs;
    float pts1;
    SQLiteDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questions);
        LEV=getIntent().getStringExtra("Level_name");
        points=(TextView)findViewById(R.id.ptscnt);
        skip=(RadioButton)findViewById(R.id.skip);
        progressbar=(DonutProgress)findViewById(R.id.Progressbar);
        radioGroup=(RadioGroup)findViewById(R.id.radiogrp);
        option1=(RadioButton)findViewById(R.id.opt1);
        option2=(RadioButton)findViewById(R.id.opt2);
        option3=(RadioButton)findViewById(R.id.opt3);
        option4=(RadioButton)findViewById(R.id.opt4);
        question=(TextView)findViewById(R.id.question);
        namelevel=(TextView)findViewById(R.id.namelevel);
        namelevel.setText(LEV);
        next=(Button)findViewById(R.id.nxt);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(radioGroup.getCheckedRadioButtonId()==-1){
                    AlertDialog.Builder alertdialogbuilder=new AlertDialog.Builder(Questions.this);
                    alertdialogbuilder.setMessage("Select an option");
                    alertdialogbuilder.setPositiveButton("Done", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Toast.makeText(Questions.this, "Done", Toast.LENGTH_SHORT).show();
                        }
                    });
                    AlertDialog ale=alertdialogbuilder.create();
                    ale.show();
                }else
                {
                    int radioid=radioGroup.getCheckedRadioButtonId();
                    selectedradio=(RadioButton)findViewById(radioid);
                    correct=selectedradio.getText().toString();
                    if(selectedradio!=skip) {
                        if (correct.equals(ans)) {
                            pts1++;
                        } else {
                            pts1= (float) (pts1-0.25);
                        }
                        points.setText(""+pts1);

                    }

                    if(cursor.isLast()){
                        if(crs.getFloat(1)<pts1)
                            database.execSQL("update Points set Pts="+pts1+" where Level='"+LEV+"';");
                        if(LEV.equals("Level5")){
                            crs=database.rawQuery("select * from Points",null);
                            crs.moveToFirst();
                            pts1=crs.getFloat(1);
                            while(crs.moveToNext()){
                                pts1+=crs.getFloat(1);
                            }
                            Intent intent=new Intent(Questions.this,Result.class);
                            intent.putExtra("Score",pts1);
                            startActivity(intent);
                        }else {

                            Intent intent = new Intent(Questions.this, Levels.class);
                            //intent.putExtra("val",pts1);
                            //intent.putExtra("lev",LEV);
                            startActivity(intent);
                            onStop();
                        }
                    }

                    if(cursor.moveToNext()){
                        if(cursor.isLast()){
                            next.setText("Finish");

                        }
                       dbnav();
                    }

                }
            }
        });


        databaseHelper=new DatabaseHelper(this);
        File databse=getApplicationContext().getDatabasePath(DatabaseHelper.DBNAME);
        if(false==databse.exists()){
            databaseHelper.getReadableDatabase();
            if(databaseHelper.copyDb(this)){
                Toast.makeText(Questions.this, "Copy db success", Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(Questions.this, "Copy Db error", Toast.LENGTH_SHORT).show();
                return;
            }

        }

        database=openOrCreateDatabase(DatabaseHelper.DBNAME,MODE_PRIVATE,null);
         cursor=database.rawQuery("select * from "+LEV+";",null);
        cursor.moveToFirst();
        dbnav();
        crs=database.rawQuery("select * from Points where Level='"+LEV+"';",null);
        crs.moveToFirst();
        pts1=crs.getFloat(1);
       // levels.level3.setEnabled(true);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    public void dbnav(){

        String ques=cursor.getString(0);
        String opt1=cursor.getString(1);
        String opt2=cursor.getString(2);
        String opt3=cursor.getString(3);
        String opt4=cursor.getString(4);
        ans=cursor.getString(5);
        option1.setText(opt1);
        question.setText(ques);
        option2.setText(opt2);
        option3.setText(opt3);
        option4.setText(opt4);
        progressbar.setProgress(progressbar.getProgress()+10);
        radioGroup.clearCheck();
    }


}
