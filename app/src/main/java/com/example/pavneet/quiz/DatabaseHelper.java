package com.example.pavneet.quiz;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by Pavneet on 25-05-2017.
 */
public class DatabaseHelper extends SQLiteOpenHelper {

public static final String DBNAME="quiz.sqlite";
public static final String DBLOCATION="/data/data/com.example.pavneet.quiz/databases/";
private Context mcontext;
    private SQLiteDatabase msqLiteDatabase;

    public DatabaseHelper(Context context) {
        super(context,DBNAME,null,1);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {

    }
    public boolean copyDb(Context context){
        try{
            InputStream inputStream=context.getAssets().open(DBNAME);
            String outFilename=DBLOCATION+DBNAME;
            OutputStream outputStream=new FileOutputStream(outFilename);
            byte []buff=new byte[1024];
            int length=0;
            while((length=inputStream.read(buff))>0){
                outputStream.write(buff,0,length);

            }
            outputStream.flush();
            outputStream.close();
            Log.v("pvnt","DB Copied");
            return true;
        }catch (Exception e){
            Log.v("pvnt","domt know");
            e.printStackTrace();
            return false;
        }
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void openDatabase(){
        String dbPath=mcontext.getDatabasePath(DBNAME).getPath();
        if(msqLiteDatabase!=null && msqLiteDatabase.isOpen()){
            return;
        }
        msqLiteDatabase=SQLiteDatabase.openDatabase(dbPath,null,SQLiteDatabase.OPEN_READONLY);
    }
    public void closeDatabase(){
        if(msqLiteDatabase!=null){
            msqLiteDatabase.close();
        }
    }


}
