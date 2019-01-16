package com.example.fletch.databaseproject;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;

public class SchemaHelper extends SQLiteOpenHelper {

    static private int VERSION = 1;
    static private String DBNAME = "datas.db";

    public SchemaHelper(@Nullable Context context) {
        super(context, DBNAME, null, VERSION);
    }

    private void insertInitialData(SQLiteDatabase db){
        db.execSQL("INSERT INTO enseignant(nom) " +
                "VALUES ('Arnold'),('Gérald'),('Erménésime'),('Gontran') ");
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE enseignant (enseignant_id INTEGER PRIMARY KEY AUTOINCREMENT" +
                ", nom TEXT)");
        db.execSQL("CREATE TABLE cours (cours_id INTEGER PRIMARY KEY AUTOINCREMENT" +
                ", nom TEXT, enseignant_id INTEGER," +
                "FOREIGN KEY(enseignant_id) REFERENCES enseignant(enseignant_id))");
        db.execSQL("CREATE TABLE etudiant (etudiant_id INTEGER PRIMARY KEY AUTOINCREMENT" +
                ", nom TEXT, cours_id INTEGER," +
                "FOREIGN KEY(cours_id) REFERENCES cours(cours_id))");
        insertInitialData(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
