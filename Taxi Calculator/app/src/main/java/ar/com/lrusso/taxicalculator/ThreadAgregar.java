package ar.com.lrusso.taxicalculator;

import java.io.DataInputStream;
import java.io.DataOutputStream;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;

public class ThreadAgregar extends AsyncTask<String, Void, Bitmap>
	{
	ProgressDialog myDialog;
    String paraguardar;
    Activity actividad;
    
    public ThreadAgregar(String a, Activity b)
    	{
    	paraguardar = a;
    	actividad = b;
    	}
    
    @Override protected void onPreExecute()
    	{
    	super.onPreExecute();
    	myDialog = new ProgressDialog(actividad);
    	myDialog.setMessage(actividad.getResources().getString(R.string.textoAgregando));
        myDialog.setCancelable(false);
        myDialog.show();
    	}
    
    public void onPostExecute(Bitmap result)
    	{
        myDialog.dismiss();
     	GlobalVars.actualizar=true;
     	actividad.finish();
    	}
    
	public Bitmap doInBackground(String... urls)
    	{
    	nuevoPago(paraguardar);
    	return  null;
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
			for (;;)
				{
				resultado = resultado + in.readUTF();
				}
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
			out.writeUTF(texto);
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