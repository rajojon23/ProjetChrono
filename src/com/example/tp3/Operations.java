package com.example.tp3;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
public class Operations {
	private TP3_DatabaseHelper databaseHelper;
	private static SQLiteDatabase database;

	public Operations(Context c) {
		// TODO Auto-generated constructor stub
		databaseHelper = new TP3_DatabaseHelper(c);
	}
	public void ouvrirBD(Context c){
		database = databaseHelper.getWritableDatabase();
		Log.i("DATABASE OUVERT", "DATABASE OUVERT");
	}
	
	public void fermerBD(){
		database.close();
	}
	public void ajouterProjet(TP3_NouveauProjet p){

		ContentValues cv = new ContentValues();
		cv.put("nom", p.getNom());
		cv.put("couleur", p.getCouleur());
		cv.put("temps", p.getTemps());
		
		Log.w("Operations", "AJOUT DE PROJET AU DB");
		database.insert("projets", null, cv);//ajout reel du projet a la db
	
	}
	public String getPremier(){
		Cursor curs = database.rawQuery("SELECT * FROM projets ", null);
		curs.moveToFirst();
		return curs.getString(1);//1 est le nom
		
	}
	
	public String getDeuxieme(){
		Cursor curs = database.rawQuery("SELECT * FROM projets ", null);
		curs.moveToFirst();
		curs.moveToPosition(2);
		return curs.getString(1);//1 est le nom
	}
	public String getTroisieme(){
		Cursor curs = database.rawQuery("SELECT * FROM projets ", null);
		curs.moveToFirst();
		curs.moveToPosition(3);
		return curs.getString(1);//1 est le nom
	}
	
	public int getTaille(){
		Cursor curs = database.rawQuery("SELECT COUNT(nom) FROM projets ", null);
		curs.moveToFirst();
		return curs.getInt(0);//
	}
	
	public String getNomProjet(int index){
		Cursor curs = database.rawQuery("SELECT * FROM projets ", null);
		curs.moveToFirst();
		curs.moveToPosition(index);
		return curs.getString(1);//1 est le nom
	}
	public int getCouleurProjet(int index){
		Cursor curs = database.rawQuery("SELECT * FROM projets ", null);
		curs.moveToFirst();
		curs.moveToPosition(index);
		return curs.getInt(2);//2 est la couleur
	}
	public int getTempProjet(int index){
		Cursor curs = database.rawQuery("SELECT * FROM projets ", null);
		curs.moveToFirst();
		curs.moveToPosition(index);

		return curs.getInt(3);//3 est le temps
	}
	public void updateTempsProjet(String nom,String temps){
		ContentValues newTemps= new ContentValues();
		
		
		newTemps.put("temps",temps);
		String[] args = new String[]{nom};
		database.update("projets", newTemps, "nom=?", args );
		Log.w("NOUVEAU TEMP POUR", nom + " = " + temps);
		
	}
	
	private int ChronoToInt(String valeur){
		
		String[] parts = valeur.split(":");//un int ne prends pas de deux points
		String part1= parts[0];
		String part2= parts[1];
		int val = Integer.valueOf(part2)+(Integer.valueOf(part1)*60);
		
		return val;
	}
	
	public boolean estOuvert(){
		boolean e = false;
		
		if(database.isOpen())
			e=true;
		else
			e=false;
		
		return e;
		
	}
		
}
