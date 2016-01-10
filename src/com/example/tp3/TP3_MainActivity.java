package com.example.tp3;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Vector;




import com.example.tp3.TP3_ChoisirCouleur.MonAdapter;



import android.R.color;
import android.os.Bundle;
import android.os.SystemClock;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class TP3_MainActivity extends Activity {
	
	/***********************************************/
	/*	Nom: Jonathan Rajarison
	 * 	Date: 08/12/13
	 * 	Création choisie: faites un long clic sur un projet pour y ajouter du temp
	 * 
	 * 
	 * 
	 * */
	/**********************************************/
	private TextView total;
	private ImageView logo;
	private ListView listeprojets;
	private Button bouton;
	final static int projetData = 0;
	final static int timeData = 2;
	private String nomP;
	private int coulP;
	private String tempsP;
	private ArrayList<Hashtable> list; 
	private VueDuneLigne ancien;
	private int tempsprojet = 0;
	MonAdapter adapter;
	Operations op = new Operations(this);
	
	private VueDuneLigne vlc;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		op.ouvrirBD(this);
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tp3__main);
		total = (TextView)findViewById(R.id.total);
		logo = (ImageView)findViewById(R.id.logo);

		listeprojets = (ListView)findViewById(R.id.listeprojets);
		bouton = (Button)findViewById(R.id.bouton);
		remplirArrayList();

		Ecouteur ec = new Ecouteur();
		bouton.setOnClickListener(ec);
		
		
		Ecouteur2 ec2 = new Ecouteur2();
		adapter = new MonAdapter(this, list);//adapter personnel
		listeprojets.setAdapter(adapter);
		listeprojets.setOnItemClickListener(ec2);
		listeprojets.setOnItemLongClickListener(ec);
		int entierTotal = 0;
		//ajout des projets à la liste
		for(int i=0; i<op.getTaille();i++){
			remplirArrayList2(op.getNomProjet(i), op.getCouleurProjet(i),op.getTempProjet(i));
			entierTotal += op.getTempProjet(i);
			Log.w("ENTIER TOTAL DE ONCREATE", String.valueOf(entierTotal));
		}
		setTotal();//calculer le total dès le debut
		
	}
	
	private void remplirArrayList() {
		// TODO Auto-generated method stub
		list = new ArrayList<Hashtable>();
		//une hashtable pour chaque projet de la liste
		Hashtable projet = new Hashtable();
		projet.put("id", 5);
		projet.put("nom", "projetdefault");
		projet.put("couleur", Color.GRAY);
		//projet.put("temp", "00:10");
		projet.put("temp", 10);
		projet.put("chrono", 1);

		list.add(projet);
	}
	private void remplirArrayList2(String nom, int couleur,int temp) {
		// TODO Auto-generated method stub
		//une hashtable pour chaque projet de la liste
		Hashtable projet = new Hashtable();
		projet.put("nom", nom);
		projet.put("couleur", couleur);
		projet.put("temp", temp);
		projet.put("chrono", 1);

		list.add(projet);
		adapter.notifyDataSetChanged();//ajout dynamique de la liste

	}
	

	private void remplacerAncienTempsProjet(String NomProjet, String ancienTemps){
		//Parcourir la liste de hashtable à la recherche de l'item 
		//qui correspond avec ancien
		// Récupération d'un Iterator sur maListe
		
		Iterator iter = list.iterator();
		while (iter.hasNext()) {
			//on cherche l'element dans la liste qui doit etre mis à jour
			Hashtable elem = (Hashtable) iter.next();
			if(elem.get("nom").equals(NomProjet)){
				int a = (Integer) elem.get("temp");
				elem.put("temp",a+ ChronoToInt(ancienTemps));
				
				if(ancien==null){
				Log.w("SPLIT","ANCIEN EST MAUVAIS");
				}
				//mise a jour du temps du projet choisi dans la bd
				op.updateTempsProjet(elem.get("nom").toString(), String.valueOf((a+ ChronoToInt(ancienTemps))));
			}
		}
		setTotal();
	}
	
	//fonction pour convertir un entier en chrono( string de format "XX:XX")
	private String intToChrono(int valeur){
		DecimalFormat numberFormat = new DecimalFormat("#.00");
		float minutes = (float)valeur/60;
		
		String stringVal = String.valueOf(numberFormat.format(minutes));		
		String[] parts = stringVal.split("\\.");// le point est un special char dans regexes et DOIT etre précédé des deux backslash
		String entier= parts[0];
		String decimal= "0."+parts[1];
		float intDecimal = Float.valueOf(decimal);

		int secondes=(int) (intDecimal*60);
		
		return entier + ":" +secondes;
	}
	//fonction pour convertir chrono en entier
	private int ChronoToInt(String valeur){
	
		String[] parts = valeur.split(":");//un int ne prends pas de deux points
		String part1= parts[0];

		String part2= parts[1];
		
		int val = Integer.valueOf(part2)+(Integer.valueOf(part1)*60);
		
		return val;
	}
	

	private void setTotal(){
		int entierTotal = 0;
		int tempoTotal=0;//qui permet d'additioner chaque temp qui se trouve dans la liste
		Iterator iter = list.iterator();
		while (iter.hasNext()) {
			Hashtable elem = (Hashtable) iter.next();
			entierTotal =  (Integer) elem.get("temp");
			entierTotal+=tempoTotal;
			Log.i("TEMP DE L'ELEMENT", String.valueOf((Integer) elem.get("temp")));
			tempoTotal = entierTotal;

		}

		total.setText(String.valueOf(convertirTemps(entierTotal)));
	}
	private String convertirTemps(int temp){
		int minute = temp/60;
		int seconds = temp - minute * 60;
		return minute + ":" + seconds;
	}
	
	int clic=0;
	int ancienclic=0;//permet de savoir combien de fois tel projet a été cliqué consécutivement
	private class Ecouteur2 implements OnItemClickListener{

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			clic++;
			VueDuneLigne v = 	(VueDuneLigne)arg1.getTag();//chercher le nouveau chrono, arg1 est l'item selectionne
			// TODO Auto-generated method stub
			//arreter ancien Chrono 
			if ( ancien != null ){
				
				ancien.getChrono().stop();
				// TODO Auto-generated method stub
				//Si on clique sur un autre projet, le chrono du premier item cliqué s'arrete et on stocke les secondes passées sur ce projet
				if((v.getChampTexte().getText().toString())!=ancien.getChampTexte().getText().toString()){
					remplacerAncienTempsProjet(ancien.getChampTexte().getText().toString(),ancien.getChrono().getText().toString());				
				}

				adapter.notifyDataSetChanged();

			}
			if(ancien!=null && clic>1){
				if((v.getChampTexte().getText().toString())!=ancien.getChampTexte().getText().toString()){
					v.getChrono().setBase(SystemClock.elapsedRealtime()+67);//repart a 0
					v.getChrono().start();
					ancienclic=0;//si un projet different est choisi, fait comme si c'est la premiere fois que le projet est cliqué
				}
				else{
					//Log.w("ANCIEN EST V", "ANCIEN EST V");
					ancienclic++;
					if(ancienclic==2){
						ancien.getChrono().setBase(SystemClock.elapsedRealtime()+67);//repart a 0
						v.getChrono().start();
						ancienclic=0;//compter combien de fois consecutivement le projet a été choisi	
					}
				}
			}
			else{

				v.getChrono().setBase(SystemClock.elapsedRealtime()+67);//repart a 0
				v.getChrono().start();
			}
			
			ancien=v;

		}

	
	}


	private class Ecouteur implements OnClickListener,OnItemLongClickListener{

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
		
				Intent i = new Intent("NOUVEAUPROJET");
				startActivityForResult(i, projetData);

		}

		
		@Override
		public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
				int arg2, long arg3) {
			// TODO Auto-generated method stub
			// TODO Auto-generated method stub
			vlc =(VueDuneLigne)arg1.getTag();//arg1 est le projet selectionne
			Intent i = new Intent("AJOUTTEMPSPROJET");
			Log.w("NOM DU PROJET LONGCLICKÉ",vlc.getChampTexte().getText().toString().toString());
			startActivityForResult(i, timeData);
			return false;
		}
	
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		
		
	}
	

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		
		super.onActivityResult(requestCode, resultCode, data);
		if(resultCode ==  RESULT_OK){
			Bundle extras = data.getExtras();
			nomP =  extras.getString("MESSAGE");
			coulP = extras.getInt("COULEUR");

			//ajout du projet à la bd
			TP3_NouveauProjet p = new TP3_NouveauProjet(nomP,coulP,0);
			

			op.ajouterProjet(p);
			if(p.getCouleur()== coulP){
				Log.w("VERIF DE COULEUR", "Les couleurs sont les mêmes");
			}
			remplirArrayList2(p.getNom(), p.getCouleur(),p.getTemps());
		}
		//pour le cas du time picker
		if(resultCode ==  2){
			Bundle extras1 = data.getExtras();
			tempsP =  extras1.getString("MESSAGE");
			//stocke les secondes passées sur ce projet dans l'objet Projet associé
			remplacerAncienTempsProjet(vlc.getChampTexte().getText().toString(),tempsP );
			adapter.notifyDataSetChanged();
		}
	}
	
	public class MonAdapter extends BaseAdapter{
		
		public ArrayList<Hashtable> list;
		Context context;
		public MonAdapter(Context context, ArrayList<Hashtable> list) {
			super();
			this.context = context;
			this.list = list;
			// TODO Auto-generated constructor stub
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return list.size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return list.get(position);
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			VueDuneLigne hold;
			 View v = convertView;

			if (v == null) {

		        LayoutInflater vi;
		        vi = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				
		        v = vi.inflate(R.layout.itemprojet,parent,false);
		        hold = new VueDuneLigne();
		        hold.setChampTexte((TextView)v.findViewById(R.id.nomprojet));
		        hold.setChampCouleur((TextView)v.findViewById(R.id.couleurprojet));
		        hold.setChampTemp((TextView)v.findViewById(R.id.tempprojet));

		        hold.setChrono((Chronometer)v.findViewById(R.id.chronometer1));
		        v.setTag(hold);

		    }
				else{
					hold = (VueDuneLigne)v.getTag();
				}
				 Hashtable p = list.get(position);
			     hold.getChampTexte().setText((String)p.get("nom"));
			     hold.getChampTemp().setText(p.get("temp").toString());
			     hold.getChampCouleur().setBackgroundColor((Integer) p.get("couleur"));
			     hold.getChampCouleur().setTextColor((Integer) p.get("couleur"));//pour pas que le texte se voit
				return v;
		}
		
	}
	
//	@Override
//	protected void onStop() {
//		// TODO Auto-generated method stub
//		super.onStop();
//		op.fermerBD();
//	}
	
	
}
