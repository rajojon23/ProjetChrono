package com.example.tp3;

import java.util.Hashtable;
import java.util.Vector;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class TP3_NouveauProjet extends Activity{
	private Button boutonconfirmer;
	private EditText champprojet;
	private TextView test;
	private ImageView couleurprojet;
	final static int couleurData = 0;
	private int temp;
	private int couleur;
	private String nom;
	private String nomProjet;
	private int couleurProjet;
	private Hashtable<String,String> tablecouleur = new Hashtable<String,String>();
	int colorCode;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.nouveauprojet);

		boutonconfirmer = (Button)findViewById(R.id.boutonconfirmer);
		initTest();
		champprojet = (EditText)findViewById(R.id.champprojet);
		couleurprojet = (ImageView)findViewById(R.id.couleurprojet);
		
		Log.w("ici", "ICICICICICICICCI");
		boutonconfirmer.setOnClickListener(new OnClickListener() {  
	          

				@Override
				public void onClick(View v) {
//					// TODO Auto-generated method stub
				     	String message[]={champprojet.getText().toString()};  
				     	Intent intent=new Intent(TP3_NouveauProjet.this,TP3_MainActivity.class);  
			            intent.putExtra("MESSAGE",champprojet.getText().toString());  
			            
			            ColorDrawable cd = (ColorDrawable) test.getBackground();//prendre le background color du texte
			            colorCode = cd.getColor();
			            
			            intent.putExtra("COULEUR", colorCode);
			            
			            setResult(RESULT_OK,intent);  
			            //attribuer toutes les valeurs necessaires
			            nomProjet = champprojet.getText().toString();

			            finish();//finishing activity  
				}  
		    });  
		
		couleurprojet.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent("CHOISIRCOULEUR");

				startActivityForResult(i, couleurData);
			}
			
		});
		
		
	
	}
	private void initTest(){
		test = (TextView)findViewById(R.id.test);
	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		Log.w("ACTIVITYRESULT CALLED", "REQUEST CODE = " + requestCode + " RESULTOK = " + RESULT_OK);
		super.onActivityResult(requestCode, resultCode, data);
		if(resultCode ==  RESULT_OK){
			Log.w("rREQUESTCODE", "REQUESTCODEOK");
			Bundle extras = data.getExtras();

			couleurProjet = extras.getInt("MESSAGE");
			Log.w("couleurProjet", couleurProjet + "");

			test.setBackgroundColor(couleurProjet);
			test.setTextColor(couleurProjet);
		}
	}
	
	
	
	
	
	/************************TOUT CE QUI CONCERNE LA BD*******************************/
	public TP3_NouveauProjet() {
		super();
		// TODO Auto-generated constructor stub
	}
	public TP3_NouveauProjet(String nom, int couleur,int temp) {
		
		this.nom = nom;
		this.couleur = couleur;
		this.temp = temp;
	}
	

	public String getNom() {
		// TODO Auto-generated method stub
		return nom;
	}
	public int getCouleur() {
		// TODO Auto-generated method stub
		return couleur;
	}
	public int getTemps() {
		// TODO Auto-generated method stub
		return temp;
	}

	public void setTemps(int temp){
		this.temp = temp;
	}

}
