package ar.com.lrusso.taxicalculator;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStreamReader;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;

public class ThreadEditar extends AsyncTask<String, Void, Bitmap>
	{
	ProgressDialog myDialog;
    String paraguardar;
    Activity actividad;
    
    public ThreadEditar(String a, Activity b)
    	{
    	paraguardar = a;
    	actividad = b;
    	}
    
    @Override protected void onPreExecute()
    	{
    	super.onPreExecute();
    	myDialog = new ProgressDialog(actividad);
    	myDialog.setMessage(actividad.getResources().getString(R.string.textoActualizando));
        myDialog.setCancelable(false);
        myDialog.show();
    	}
    
    public void onPostExecute(Bitmap result)
    	{
  	   	GlobalVars.actualizar=true;
        myDialog.dismiss();
        actividad.finish();
    	}
    
	public Bitmap doInBackground(String... urls)
    	{
    	eliminarPago(GlobalVars.paraEditarPosition);
    	nuevoPago(paraguardar);
    	return  null;
    	}
	
	public void eliminarPago(int position)
		{
		final String aeliminar = GlobalVars.devolverPosicion(position);
		int borrar = -1;
		for(int i=0;i<GlobalVars.listado.size();i++)
			{
			if (GlobalVars.listado.get(i)==aeliminar)
				{
				borrar=i;
				i=GlobalVars.listado.size();
				}
			}
		if (borrar>-1)
			{
			GlobalVars.listado.remove(borrar);
			}
		guardarListaEnBase();
		}

	public void guardarListaEnBase()
		{
		escribirArchivo("base.cfg","");
		String aGuardar = "";
		for (int i=0;i<GlobalVars.listado.size();i++)
			{
			if (GlobalVars.listado.get(i).length()>3)
				{
				if (aGuardar=="")
					{
					aGuardar = GlobalVars.listado.get(i);
					}
					else
					{
					aGuardar = aGuardar + "\r\n" + GlobalVars.listado.get(i);
					}
				}
			}
		nuevoPago(aGuardar);
		}

	public String leerArchivo(String archivo)
		{
		String resultado = "";
		DataInputStream in = null;
		try
			{
			in = new DataInputStream(actividad.openFileInput(archivo));
			BufferedReader br = new BufferedReader(new InputStreamReader(in, "utf-8"));
			StringBuilder sb = new StringBuilder();
			String line;
			while(( line = br.readLine()) != null )
				{
				sb.append(line);
				sb.append("\n");
				}
			resultado = sb.toString();
			}
			catch (Exception e)
			{
			}
		try
			{
			in.close();
			}
			catch(Exception e)
			{
			}
		return resultado;
		}

	public void escribirArchivo(String archivo, String texto)
		{
		try
			{
			DataOutputStream out = new DataOutputStream(actividad.openFileOutput(archivo, Context.MODE_PRIVATE));
			byte[] bytes = texto.getBytes();
			out.write(bytes);
			out.close();
			}
	    	catch(Exception e)
	    	{
	    	}
		}
	
	public void nuevoPago(String valor)
		{
		String original = leerArchivo("base.cfg");
		if (original==null)
			{
			escribirArchivo("base.cfg",valor);
			}
			else
			{
			escribirArchivo("base.cfg", original + "\r\n" + valor);
			}
		}
	}