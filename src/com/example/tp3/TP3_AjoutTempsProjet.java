package com.example.tp3;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TimePicker;

public class TP3_AjoutTempsProjet extends Activity{
	private TimePicker tp;
	private Button okbouton;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activitetimepicker);
		tp = (TimePicker)findViewById(R.id.tp);
		tp.setIs24HourView(true);
		okbouton =  (Button)findViewById(R.id.okbouton);
		Ecouteur ec = new Ecouteur();
		okbouton.setOnClickListener(ec);
	}
	private class Ecouteur implements OnClickListener {

		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			Log.w("TIMEPICKER DATA", tp.getCurrentHour().toString() + ":" + tp.getCurrentMinute().toString());
			
			String t =  tp.getCurrentHour().toString() + ":" + tp.getCurrentMinute().toString();
	     	String tempsP[]={t};  
	     	Intent intent=new Intent(TP3_AjoutTempsProjet.this,TP3_MainActivity.class);  
            intent.putExtra("MESSAGE",t);  

            
            setResult(2,intent); 
            finish();
		}
		
		
	}

}
