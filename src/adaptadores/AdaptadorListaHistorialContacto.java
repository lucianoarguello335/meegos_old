package adaptadores;

import java.util.Calendar;
import java.util.Locale;

import com.tdam2013.g14.R;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class AdaptadorListaHistorialContacto extends CursorAdapter {
	private int dias;
	public AdaptadorListaHistorialContacto(Context context, Cursor c,
			boolean autoRequery) {
		super(context, c, autoRequery);
		dias=1;
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void bindView(View view, Context context, Cursor cursor) {
		// [_id, textoMsj, Fecha, Contacto, Tipo, Origen]
		// 0 1 2 3 4 5
		int count = cursor.getCount();

		final ViewHolder holder = (ViewHolder) view.getTag();
//		holder.nombreContacto.setText(cursor.getString(3));
		holder.nombreContacto.setVisibility(TextView.GONE);

		if (cursor.getInt(4) == 1) {
			holder.imageTipoAccion.setImageResource(R.drawable.phone);
			holder.tipoAccion.setText("Llamada");
//			holder.txtSMS.setText("No SMS");
			holder.txtSMS.setVisibility(TextView.GONE);
		} else {
			holder.imageTipoAccion.setImageResource(R.drawable.dm);
			holder.tipoAccion.setText("MeeGo");
			holder.txtSMS.setText(cursor.getString(1));
			holder.txtSMS.setVisibility(TextView.VISIBLE);
		}

		if (cursor.getInt(5) == 1) {
			holder.imageOrigenAccion.setImageResource(R.drawable.entrante_icon);
			holder.origenAccion.setText("Entrante");
		} else {
			holder.imageOrigenAccion.setImageResource(R.drawable.saliente_icon);
			holder.origenAccion.setText("Saliente");
		}
		
		Calendar fecha = Calendar.getInstance();
		long t = cursor.getLong(2)-(12345*cursor.getLong(0));
		fecha.setTimeInMillis(t*1000);
		
		holder.fechaAccion.setText(fecha.getTime().toLocaleString());
	}

	/**
	 * Defines a class that hold resource IDs of each item layout row to prevent
	 * having to look them up each time data is bound to a row.
	 */
	private class ViewHolder {
		TextView nombreContacto;
		ImageView imageTipoAccion;
		TextView tipoAccion;
		ImageView imageOrigenAccion;
		TextView origenAccion;
		TextView fechaAccion;
		TextView txtSMS;
	}

	@Override
	public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {

		LayoutInflater inflater = LayoutInflater.from(context);
		final View itemView = inflater.inflate(R.layout.pantalla_principal_historial_item, viewGroup, false);
		final ViewHolder holder = new ViewHolder();
		holder.nombreContacto = (TextView) itemView.findViewById(R.id.nombreContacto);
		holder.imageTipoAccion = (ImageView) itemView.findViewById(R.id.imageTipoAccion);
		holder.tipoAccion = (TextView) itemView.findViewById(R.id.tipoAccion);
		holder.imageOrigenAccion = (ImageView) itemView.findViewById(R.id.imageOrigenAccion);
		holder.origenAccion = (TextView) itemView.findViewById(R.id.origenAccion);
		holder.fechaAccion = (TextView) itemView.findViewById(R.id.fechaAccion);
		holder.nombreContacto = (TextView) itemView.findViewById(R.id.nombreContacto);
		holder.txtSMS = (TextView) itemView.findViewById(R.id.txtSMS);
		itemView.setTag(holder);
		return itemView;
	}

}
