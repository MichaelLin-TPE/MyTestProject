package com.example.climbtogether.mountain_fragment.db_modle;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Locale;

public class DataBaseImpl implements DataBaseApi {
    private static final String DB_NAME="mountain_db.db";
    private Context ctx;

    public DataBaseImpl(Context ctx) {
        this.ctx = ctx;
        //複製DB
        File dbFile=ctx.getDatabasePath(DB_NAME);
        if(!dbFile.exists()){
            File parentDir = new File(dbFile.getParent());
            if(!parentDir.exists()){
                parentDir.mkdir();
            }
            InputStream is=null;
            OutputStream os=null;
            try{
                is=ctx.getAssets().open(DB_NAME);
                os=new FileOutputStream(dbFile);
                byte[] buffer = new byte[1024];
                int read=is.read(buffer);
                while(read>0){
                    os.write(buffer,0,read);
                    read=is.read(buffer);
                }
            }catch(Exception e){
                Log.e("Michael","複製DB失敗 : "+e.toString());
            }finally{
                if(is!=null){
                    try {
                        is.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if(os!=null){
                    try {
                        os.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
    private SQLiteDatabase getReadableDatatbase(){
        File dbFile=ctx.getDatabasePath(DB_NAME);
        return SQLiteDatabase.openDatabase(dbFile.getPath(),null,SQLiteDatabase.OPEN_READONLY);
    }
    private SQLiteDatabase getWriteableDatabase(){
        File dbFile=ctx.getDatabasePath(DB_NAME);
        return  SQLiteDatabase.openDatabase(dbFile.getPath(),null,SQLiteDatabase.OPEN_READWRITE);
    }


    @Override
    public ArrayList<DataDTO> getAllInformation() {
        ArrayList<DataDTO> data = new ArrayList<>();
        SQLiteDatabase db = getReadableDatatbase();
        Cursor cursor = db.rawQuery("SELECT * FROM mountain_table",null);
        if (cursor.moveToFirst()){
            do{
                DataDTO dataDTO = new DataDTO();
                dataDTO.fromCursor(cursor);
                data.add(dataDTO);
            }while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return data;
    }

    @Override
    public ArrayList<DataDTO> getLevelAInformation(String levelType) {
        ArrayList<DataDTO> data = new ArrayList<>();
        SQLiteDatabase db = getReadableDatatbase();
        Cursor cursor = db.rawQuery("SELECT * FROM mountain_table WHERE difficulty = '"+levelType+"'",null);
        if (cursor.moveToFirst()){
            do {
                DataDTO dataDTO = new DataDTO();
                dataDTO.fromCursor(cursor);
                data.add(dataDTO);
            }while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return data;
    }

    @Override
    public void update(DataDTO data) {
        SQLiteDatabase db = getWriteableDatabase();
        try{
            db.update("mountain_table",data.toContentValues(),"sid=?",new String[]{""+data.getSid()});
        }catch (Exception e){
            e.printStackTrace();
        }
        db.close();
    }

    @Override
    public DataDTO getDataBySid(int sid) {
        DataDTO data = null;
        SQLiteDatabase db = getReadableDatatbase();
        String sql = String.format(Locale.getDefault(),"SELECT * FROM mountain_table WHERE sid=%d",sid);
        Cursor cursor = db.rawQuery(sql,null);
        if (cursor.moveToFirst()){
            data = new DataDTO();
            data.fromCursor(cursor);
        }
        cursor.close();
        db.close();
        return data;
    }
}
