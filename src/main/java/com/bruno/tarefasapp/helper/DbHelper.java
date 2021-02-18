package com.bruno.tarefasapp.helper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import java.util.concurrent.ExecutionException;

public class DbHelper extends SQLiteOpenHelper {

    public static int VERSION = 1;
    public static String NOME_DB = "DB_TAREFAS";
    public static String TABELA_TAREFAS = "tarefas";

    public DbHelper(@Nullable Context context) {
        super(context, NOME_DB, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

       String sql = " CREATE TABLE IF NOT EXISTS " + TABELA_TAREFAS
                    +"(id INTEGER PRIMARY KEY AUTOINCREMENT, "
                    +" nome TEXT NOT NULL ,"
                    +" status INTEGER(1))";

       try{
           db.execSQL(sql);
           Log.i("INFO DB", "Sucesso ao criar tabela");

       }catch (Exception e){
           Log.i("INFO DB", "erro ao criar a tabela"+ e.getMessage());
       }

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
