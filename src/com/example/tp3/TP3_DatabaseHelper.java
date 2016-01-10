package com.example.tp3;



import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;

public class TP3_DatabaseHelper extends SQLiteOpenHelper{

	public TP3_DatabaseHelper(Context context) {
		super(context, "db", null, 1);
		// TODO Auto-generated constructor stub
	}

	
	@Override
	public  void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		//exec: pas insert ni select
		db.execSQL("CREATE TABLE projets(id INTEGER PRIMARY KEY AUTOINCREMENT,nom TEXT, couleur INTEGER, temps INTEGER);");

	
	}
	
	
	

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		db.execSQL("DROP TABLE IF EXISTS projets");
		onCreate(db);
	}
	

	

}
