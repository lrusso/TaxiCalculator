package ar.com.lrusso.taxicalculator;

import java.util.ArrayList;
import java.util.List;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
 
public class ImageAdapter extends BaseAdapter
	{
	private Context context;
	private List<String> listado = new ArrayList<String>();
 	
	public ImageAdapter(Context context, List<String> listadofinal)
		{
		this.context = context;
		this.listado = listadofinal;
		}
	
	public int getCount()
		{
		return listado.size();
		}
	
	public Object getItem(int position)
		{
		return null;
		}
	
	public long getItemId(int position)
		{
		return 0;
		}
 	
	public View getView(int position, View convertView, ViewGroup parent)
		{
		try
			{
			LayoutInflater inflater = LayoutInflater.from(context);
			if (convertView == null)
				{
				convertView = inflater.inflate(R.layout.row, null);
				}

			TextView nombre = (TextView) convertView.findViewById(R.id.nombre);
			String nombreFinal = " " + GlobalVars.devolverDiaMes(listado.get(position)) + "   " +
								 GlobalVars.devolverHoras(listado.get(position)) + ":" +
								 GlobalVars.devolverMinutos(listado.get(position)) + "   ";
			String observacion = GlobalVars.devolverObservacion(listado.get(position));
			
			if (observacion.length()==0)
				{
				observacion = context.getResources().getString(R.string.textoViaje) + String.valueOf(GlobalVars.depurada.size() - position);
				}
			
			nombre.setText(nombreFinal + observacion);

			TextView precio = (TextView) convertView.findViewById(R.id.precio);
			precio.setText(GlobalVars.moneda + GlobalVars.formatoNumeros.format(GlobalVars.devolverPrecio(listado.get(position))) + "  ");

			ImageView imageView = (ImageView) convertView.findViewById(R.id.imagen);
			imageView.setImageResource(R.drawable.money);
			return convertView;
			}
			catch(Exception e)
			{
			return null;
			}
		}
	}