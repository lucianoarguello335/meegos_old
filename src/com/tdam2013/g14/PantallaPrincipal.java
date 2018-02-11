package com.tdam2013.g14;

import java.net.URI;
import java.util.Locale;



import adaptadores.AdaptadorListaContacto;
import adaptadores.AdaptadorListaHistorial;
import android.app.ActionBar;
import android.app.Dialog;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.ContactsContract.Contacts;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

public class PantallaPrincipal extends FragmentActivity implements
		ActionBar.TabListener {

	/**
	 * The {@link android.support.v4.view.PagerAdapter} that will provide
	 * fragments for each of the sections. We use a
	 * {@link android.support.v4.app.FragmentPagerAdapter} derivative, which
	 * will keep every loaded fragment in memory. If this becomes too memory
	 * intensive, it may be best to switch to a
	 * {@link android.support.v4.app.FragmentStatePagerAdapter}.
	 */
	SectionsPagerAdapter mSectionsPagerAdapter;

	/**
	 * The {@link ViewPager} that will host the section contents.
	 */
	ViewPager mViewPager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_pantalla_principal);
		
		// Set up the action bar.
		final ActionBar actionBar = getActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

		// Create the adapter that will return a fragment for each of the three
		// primary sections of the app.
		mSectionsPagerAdapter = new SectionsPagerAdapter(
				getSupportFragmentManager());

		// Set up the ViewPager with the sections adapter.
		mViewPager = (ViewPager) findViewById(R.id.pager);
		mViewPager.setAdapter(mSectionsPagerAdapter);

		// When swiping betwe corresponding
		// tab. We can also use ActionBar.Tab#select() to do this if we have
		// a reference to the Tab.en different sections, select the
		mViewPager
				.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
					@Override
					public void onPageSelected(int position) {
						actionBar.setSelectedNavigationItem(position);
					}
				});

		// For each of the sections in the app, add a tab to the action bar.
		for (int i = 0; i < mSectionsPagerAdapter.getCount(); i++) {
			// Create a tab with text corresponding to the page title defined by
			// the adapter. Also specify this Activity object, which implements
			// the TabListener interface, as the callback (listener) for when
			// this tab is selected.
			actionBar.addTab(actionBar.newTab()
					.setText(mSectionsPagerAdapter.getPageTitle(i))
					.setTabListener(this));
		}
	}

	public void onStart(){
		try
		{
		super.onStart();
		
		DataBaseHelper dbHelper=new DataBaseHelper(this);
		
		}
		catch(Exception ex)
		{
			CatchError(ex.toString());
		}
	}
	
	void CatchError(String Exception)
	{
		Dialog diag=new Dialog(this);
		diag.setTitle("Error: base de datos no creada :(");
		TextView txt=new TextView(this);
		txt.setText(Exception);
		diag.setContentView(txt);
		diag.show();
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.pantalla_principal, menu);
		return true;
	}
	
	public boolean onOptionsItemSelected(MenuItem item) {
		startActivity(new Intent(this,SetPreference.class));
		return true;
	}

	@Override
	public void onTabSelected(ActionBar.Tab tab,
			FragmentTransaction fragmentTransaction) {
		// When the given tab is selected, switch to the corresponding page in
		// the ViewPager.
		mViewPager.setCurrentItem(tab.getPosition());
	}

	@Override
	public void onTabUnselected(ActionBar.Tab tab,
			FragmentTransaction fragmentTransaction) {
	}

	@Override
	public void onTabReselected(ActionBar.Tab tab,
			FragmentTransaction fragmentTransaction) {
	}

	/**
	 * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
	 * one of the sections/tabs/pages.
	 */
	public class SectionsPagerAdapter extends FragmentPagerAdapter {

		public SectionsPagerAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int position) {
			// getItem is called to instantiate the fragment for the given page.
			// Return a DummySectionFragment (defined as a static inner class
			// below) with the page number as its lone argument.
			Fragment listFragment = null;
			Bundle args=null;
			switch(position){
			case 0:
				listFragment = new Historial();
				args = new Bundle();
				args.putInt(Historial.ARG_SECTION_NUMBER, position + 1);
				listFragment.setArguments(args);
				break;
			case 1:
				listFragment = new Contactos();
				args = new Bundle();
				args.putInt(Contactos.ARG_SECTION_NUMBER, position + 1);
				listFragment.setArguments(args);
				break;
			default:
				break;
			}
			return listFragment;
		}

		@Override
		public int getCount() {
			// Show 2 total pages.
			return 2;
		}

		@Override
		public CharSequence getPageTitle(int position) {
			Locale l = Locale.getDefault();
			switch (position) {
			case 0:
				return getString(R.string.title_section1).toUpperCase(l);
			case 1:
				return getString(R.string.title_section2).toUpperCase(l);
			}
			return null;
		}
		
	}

	/**
	 * A dummy fragment representing a section of the app, but that simply
	 * displays dummy text.
	 */
	public static class Historial extends Fragment {
		/**
		 * The fragment argument representing the section number for this
		 * fragment.
		 */
		public static final String ARG_SECTION_NUMBER = "section_number";
		
		public Historial() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
			View vista = inflater.inflate(R.layout.fragment_pantalla_principal_historial, container,false);
			ListView lista = (ListView) vista.findViewById(R.id.listaHistorial);
			DataBaseHelper db = new DataBaseHelper(this.getActivity());
			db.eliminarTodasLasAcciones();
			db.agregarllamada("Abuelina", 2);
			db.agregarSMSWeb("Agustin Garcia", 1, "Quedo barbaro!");
			db.agregarllamada("Airena", 1);
			db.agregarSMSWeb("Airena", 1, "¿Estas solo?");
			db.agregarllamada("Ale ACT", 2);
			db.agregarSMSWeb("Ale ACT", 1, "Hola querido, nos vemos el Sabado?");
			db.agregarllamada("Airena", 2);
			db.agregarSMSWeb("Ale ACT", 2, "Ok, te espero");
			
			AdaptadorListaHistorial adaptador = new AdaptadorListaHistorial(this.getActivity(), db.getAllAcciones(), true);
			lista.setAdapter(adaptador);
			lista.setOnItemClickListener(new OnItemClickListener() {
				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
					TextView tv = (TextView) arg1.findViewById(R.id.nombreContacto);
					Intent inte = new Intent(getActivity(), PantallaHistorialContacto.class);
					inte.putExtra("nombreContacto", tv.getText());
					startActivity(inte);
				}
				
			});
			return vista;
		}
	}
	
	/**
	 * A dummy fragment representing a section of the app, but that simply
	 * displays dummy text.
	 */
	public static class Contactos extends Fragment{
		/**
		 * The fragment argument representing the section number for this
		 * fragment.
		 */
		public static final String ARG_SECTION_NUMBER = "section_number";
		
		/*
         * Although it's not necessary to include the
         * column twice, this keeps the number of
         * columns the same regardless of version
         */
		private static final String[] PROJECTION =
            {
                Contacts._ID,
                Contacts.LOOKUP_KEY,
                (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) ?
                        Contacts.DISPLAY_NAME_PRIMARY : Contacts.DISPLAY_NAME,
                (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) ?
                        Contacts.PHOTO_THUMBNAIL_URI : Contacts._ID
            };
		
		public Contactos() {
		}
		
		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
			
			View vista = inflater.inflate(R.layout.fragment_pantalla_principal_contactos, container, false);
			ListView listaContactos = (ListView) vista.findViewById(R.id.listaContactos);
			
			Uri uri = ContactsContract.Contacts.CONTENT_URI;
			
			
		    String[] projection = new String[] {
	    		ContactsContract.Contacts._ID,
	    		ContactsContract.Contacts.LOOKUP_KEY,
	    		ContactsContract.Contacts.PHOTO_THUMBNAIL_URI,
	    		ContactsContract.Contacts.DISPLAY_NAME};
		    
		    
		    
//		    ContactsContract.Contacts.HAS_PHONE_NUMBER
//		    ContactsContract.CommonDataKinds.Photo.CONTACT_ID;
		    
		    String selection = null;
		    String[] selectionArgs = null;
		    String sortOrder = ContactsContract.Contacts.DISPLAY_NAME
		        + " COLLATE LOCALIZED ASC";

		    Cursor cursorContactos = getActivity().getContentResolver().query(uri, PROJECTION, selection, selectionArgs, sortOrder);
		 
		    AdaptadorListaContacto adapter = new AdaptadorListaContacto(getActivity(), cursorContactos, true);

		    // Bind to our new adapter.
		    listaContactos.setAdapter(adapter);	

		    listaContactos.setOnItemLongClickListener(new OnItemLongClickListener() {

				@Override
				public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
						int arg2, long arg3) {
					TextView tv = (TextView) arg1.findViewById(R.id.nombreContacto);
					Intent inte = new Intent(getActivity(), PantallaHistorialContacto.class);
					inte.putExtra("nombreContacto", tv.getText());
					startActivity(inte);
					return false;
				}
			});
			
			return vista;
			
		}
		
		
		
	}
	
}
