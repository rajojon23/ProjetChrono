package com.example.tp3;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Vector;

import android.app.Activity;
import android.content.ClipData.Item;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;

public class TP3_ChoisirCouleur extends Activity implements OnClickListener {
	GridView gridcouleur;
	Vector<String> list;
	TextView couleur, texttest;
	private Hashtable<String,String> tablecouleur = new Hashtable<String,String>();
	private VueDuneLigne ancien, v;
	Button bouton;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activitecouleur);
		gridcouleur = (GridView)findViewById(R.id.gridcouleur);
		bouton = (Button)findViewById(R.id.okbouton);
		
		remplirArrayList();
	
		
		MonAdapter adapter = new MonAdapter(this, list);//adapter personnel
		gridcouleur.setAdapter(adapter);
		
		Ecouteur ec = new Ecouteur();
		gridcouleur.setOnItemClickListener(ec);
		
		bouton.setOnClickListener(this);
	}
	

	private class Ecouteur implements OnItemClickListener{

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			
			//recolorer l'ancien couleur choisi
			if(ancien != null){
				ColorDrawable cd2 = (ColorDrawable) v.getChampTexte().getBackground();//prendre le background color du texte
		     	int colorCode2 = cd2.getColor();
				ancien.getChampTexte().setTextColor(colorCode2);
			}
			// TODO Auto-generated method stub

			
				
			v = (VueDuneLigne)arg1.getTag();
			//mettre en evidence la couleur choisie
			v.getChampTexte().setTextColor(Color.GREEN);
			
			
	     	Intent intent=new Intent(TP3_ChoisirCouleur.this,TP3_NouveauProjet.class);  
	     	ColorDrawable cd = (ColorDrawable) v.getChampTexte().getBackground();//prendre le background color du texte
	     	int colorCode = cd.getColor();//seulement compatible avec API 11 et plus
            intent.putExtra("MESSAGE",colorCode);  
            
            setResult(RESULT_OK,intent);  
            ancien = v;
		}
		
	}
	private void remplirArrayList() {
		// TODO Auto-generated method stub
		list = new Vector<String>();
		
		tablecouleur.put("Ivory", 		"#FFFFF0"            ) ;
		tablecouleur.put("Beige", 		"#F5F5DC"            ) ;
		tablecouleur.put("Wheat", 		"#F5DEB3"            ) ;
		tablecouleur.put("Tan"	, 		"#D2B48C"             ) ;
		tablecouleur.put("Khaki", 		"#C3B091"            ) ;
		tablecouleur.put("Silver", 		"#C0C0C0"           ) ;
		tablecouleur.put("Gray", 		"#808080"             ) ;
		tablecouleur.put("Charcoal", 	"#464646"         ) ;
		tablecouleur.put("Navy Blue", 	"#000080"        ) ;
		tablecouleur.put("Royal Blue", 	"#084C9E"       ) ;
		tablecouleur.put("Cyan", 		"#00FFFF"             ) ;
		tablecouleur.put("Aquamarine", 	"#7FFFD4"     ) ;
		tablecouleur.put("Teal", 		"#008080"             ) ;
		tablecouleur.put("Forest Green","#228B22"    ) ;
		tablecouleur.put("Olive", 		"#808000"            ) ;
		tablecouleur.put("Chartreuse", 	"#7FFF00"     ) ;
		tablecouleur.put("Lime", 		"#BFFF00"             ) ;
		tablecouleur.put("Golden", 		"#FFD700"           ) ;
		tablecouleur.put("Goldenrod", 	"#DAA520"        ) ;
		tablecouleur.put("Coral", 		"#FF7F50"            ) ;
		tablecouleur.put("Salmon", 		"#FA8072"           ) ;
		tablecouleur.put("Hot Pink", 	"#FC0FC0"         ) ;
		tablecouleur.put("Fuchsia"	, 	"#FF77FF"         ) ;
		tablecouleur.put("Puce", 		"#CC8899"             ) ;
		tablecouleur.put("Mauve", 		"#E0B0FF"             ) ;
		tablecouleur.put("Lavender", 	"#B57EDC"         ) ;
		tablecouleur.put("Plum", 		"#843179"             ) ;
		tablecouleur.put("Indigo", 		"#4B0082"           ) ;
		tablecouleur.put("Maroon", 		"#800000"    		) ;
		tablecouleur.put("Crimson", 	"#DC143C"			) ;
		
		
		list.add(tablecouleur.get("Ivory"           )       ) ;
		list.add(tablecouleur.get("Beige"           )       ) ;
		list.add(tablecouleur.get("Wheat"          )        ) ;
		list.add(tablecouleur.get("Tan"	           )     ) ;
		list.add(tablecouleur.get("Khaki"          )        ) ;
		list.add(tablecouleur.get("Silver"          )       ) ;
		list.add(tablecouleur.get("Gray"         )         ) ;
		list.add(tablecouleur.get("Charcoal"       )        ) ;
		list.add(tablecouleur.get("Navy Blue"       )       ) ;
		list.add(tablecouleur.get("Royal Blue"     )        ) ;
		list.add(tablecouleur.get("Cyan"          )         ) ;
		list.add(tablecouleur.get("Aquamarine"	    )        ) ;
		list.add(tablecouleur.get("Teal"           )        ) ;
		list.add(tablecouleur.get("Forest Green"    )       ) ;
		list.add(tablecouleur.get("Olive"        )          ) ;
		list.add(tablecouleur.get("Chartreuse"	   )         ) ;
		list.add(tablecouleur.get("Lime"            )       ) ;
		list.add(tablecouleur.get("Golden"         )        ) ;
		list.add(tablecouleur.get("Goldenrod"       )       ) ;
		list.add(tablecouleur.get("Coral"           )       ) ;
		list.add(tablecouleur.get("Salmon"         )        ) ;
		list.add(tablecouleur.get("Hot Pink"        )       ) ;
		list.add(tablecouleur.get("Fuchsia"	         )   ) ;
		list.add(tablecouleur.get("Puce"            )       ) ;
		list.add(tablecouleur.get("Mauve"            )      ) ;
		list.add(tablecouleur.get("Lavender"        )       ) ;
		list.add(tablecouleur.get("Plum"            )       ) ;
		list.add(tablecouleur.get("Indigo"          )       ) ;
		list.add(tablecouleur.get("Maroon"    		)        ) ;
		list.add(tablecouleur.get("Crimson"		) 		  );
	}
	
	public class MonAdapter extends BaseAdapter{

		
		
		
		Context context;
		Vector<String> list;

		public MonAdapter(Context context, Vector<String> list) {
			super();
			// TODO Auto-generated constructor stub
			this.context = context;
			this.list = list;
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
			 View v = convertView;
			VueDuneLigne hold;
			if (v == null) {

			        LayoutInflater vi;
			        vi = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
					
			        v = vi.inflate(R.layout.itemcouleur,parent,false);
			        hold = new VueDuneLigne();
			        hold.setChampTexte((TextView)v.findViewById(R.id.couleurtext));
			        v.setTag(hold);
			    }
			else{
				hold = (VueDuneLigne)v.getTag();
			}
			 String p = list.get(position);

			 hold.getChampTexte().setBackgroundColor(Color.parseColor(p.toString()));
			 //le texte à l'interieur de la color "disparait"
			 hold.getChampTexte().setTextColor(Color.parseColor(p.toString()));

					Log.w("VALUER COULEUR",p);

					Log.w("LISTVIEW", "GETVIEW");

			return v;
		}
		
		
		
	}
	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		finish();
	}
	
	
}
