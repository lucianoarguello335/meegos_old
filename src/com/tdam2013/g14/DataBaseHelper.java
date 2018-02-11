package com.tdam2013.g14;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DataBaseHelper extends SQLiteOpenHelper {

	static final String nombreDB = "bdDroidMeegos.db";
	static final String tablaAcciones = "Acciones";
	static final String colID = "_id";
	static final String colTexto = "textoMsj";
	static final String colFecha = "Fecha";
	static final String colContacto = "Contacto";
	static final String colTipo = "Tipo"; // (1) Llamada o (2) SMS WEB
	static final String colOrigen = "Origen"; // (1) Entrante (2) Saliente

	public DataBaseHelper(Context context) {
		super(context, nombreDB, null, 10);

	}

	@Override
	public void onCreate(SQLiteDatabase db) {

		db.execSQL("CREATE TABLE " + tablaAcciones + " (" + colID
				+ " INTEGER PRIMARY KEY AUTOINCREMENT, " + colTexto
				+ " TEXT , " + colFecha + " INTEGER, " + colContacto
				+ " TEXT, " + colTipo + " " + "INTEGER, " + colOrigen + " "
				+ "INTEGER" + ")");

	}

	@Override
	public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
		// TODO Auto-generated method stub

	}

	void PRUEBAagregarAccion() {

		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues cv = new ContentValues();

		cv.put(colTexto, "¿Como estas?");
		// Calendar fecha = Calendar.getInstance();
		// cv.put(colFecha, fecha.getTimeInMillis()/1000);
		cv.put(colFecha, fechaUtil.getFechaActual());
		cv.put(colContacto, "Diego");
		cv.put(colTipo, 2);

		db.insert(tablaAcciones, colTexto, cv);
		db.close();
	}

	public void agregarllamada(String nombreContacto, int origen) {

		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues cv = new ContentValues();

		cv.put(colFecha, fechaUtil.getFechaActual());
		cv.put(colContacto, nombreContacto);
		cv.put(colTipo, 1);
		cv.put(colOrigen, origen);

		db.insert(tablaAcciones, colTexto, cv);

		db.close();
	}

	public void agregarSMSWeb(String nombreContacto, int origen, String texto) {

		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues cv = new ContentValues();

		cv.put(colFecha, fechaUtil.getFechaActual());
		cv.put(colContacto, nombreContacto);
		cv.put(colTipo, 2);
		cv.put(colOrigen, origen);
		cv.put(colTexto, texto);

		db.insert(tablaAcciones, colTexto, cv);

		db.close();
	}

	public void eliminarAccion(int idAccion) {

		SQLiteDatabase db = this.getWritableDatabase();

		db.delete(tablaAcciones, colID + "=?",
				new String[] { String.valueOf(idAccion) });

		db.close();
	}

	public void eliminarTodasLasAcciones() {

		SQLiteDatabase db = this.getWritableDatabase();

		db.delete(tablaAcciones, null, new String[] {});

		db.close();
	}

	public Cursor getAllAcciones() {

		SQLiteDatabase db = this.getReadableDatabase();

		Cursor cur = db.rawQuery("Select * from " + tablaAcciones, null);

		return cur;
	}

	public Cursor getAccionesContacto(String nombreContacto) {
		
		SQLiteDatabase db = this.getReadableDatabase();


		String[] params=new String[]{nombreContacto};
		
		Cursor c=db.rawQuery("SELECT * from "+ tablaAcciones +" WHERE "+colContacto+"=?",params);
		
		return c;
	}

	public Cursor getAccion(int idAccion) {

		SQLiteDatabase db = this.getReadableDatabase();
		
		String[] params=new String[]{String.valueOf(idAccion)};

		Cursor c=db.rawQuery("SELECT * from "+ tablaAcciones +" WHERE _id" + "=?",params);

		return c;

	}

	// SELECT:
	// - Todas las acciones
	// - Acciones para un contacto
	//
	// ADD:
	// - Accion de llamada: AgregarLlamada (<parametros de una llamada>)
	// - Accion de sms web AgregarSmsWeb(<parametros de un sms web>)
	//
	// DELETE:
	// - Borrar todas las acciones
	// - Borrar una accion determinada

}
