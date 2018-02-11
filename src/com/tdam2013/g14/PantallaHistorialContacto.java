package com.tdam2013.g14;

import adaptadores.AdaptadorListaHistorial;
import adaptadores.AdaptadorListaHistorialContacto;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.widget.ListView;
import android.widget.TextView;

public class PantallaHistorialContacto extends Activity {

	private String nombreContacto;
	private DataBaseHelper db;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_pantalla_historial_contacto);
		ListView lista = (ListView) findViewById(R.id.list);
		TextView nombre = (TextView) findViewById(R.id.nombreContacto);
		Intent inte = getIntent();
		nombreContacto = inte.getStringExtra("nombreContacto");
		db = new DataBaseHelper(getApplication());
		AdaptadorListaHistorialContacto adapter = new AdaptadorListaHistorialContacto(getApplication(), db.getAccionesContacto(nombreContacto), true);
		nombre.setText(nombreContacto);
		lista.setAdapter(adapter);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.pantalla_historial_contacto, menu);
		return true;
	}

}
