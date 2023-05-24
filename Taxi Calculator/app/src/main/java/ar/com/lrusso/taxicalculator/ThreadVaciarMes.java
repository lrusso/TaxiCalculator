package ar.com.lrusso.taxicalculator;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.StringReader;
import java.util.Collections;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.widget.GridView;
import android.widget.TextView;

public class ThreadVaciarMes extends AsyncTask<String, Void, Bitmap>
	{
	ProgressDialog myDialog;
    GridView gridView;
    TextView textoNada;
    TextView textoHoy;
    TextView textoEnElMes;
    TextView titulo;
    Activity actividad;
    
    public ThreadVaciarMes(GridView a, TextView b, TextView c, TextView d, TextView e, Activity f)
    	{
    	gridView     = a;
    	textoNada    = b;
    	textoHoy     = c;
    	textoEnElMes = d;
    	titulo       = e;
    	actividad    = f;
    	}
    
    @Override protected void onPreExecute()
    	{
    	super.onPreExecute();
    	myDialog = new ProgressDialog(actividad);
    	myDialog.setMessage(actividad.getResources().getString(R.string.textoEliminando));
        myDialog.setCancelable(false);
        myDialog.show();
    	}
    
    public void onPostExecute(Bitmap result)
    	{
    	try
    		{
            myDialog.dismiss();
			textoHoy.setText(GlobalVars.contexto.getResources().getString(R.string.textoHoy) + " " +  GlobalVars.moneda + GlobalVars.formatoNumeros.format(0) + "   ");
			textoEnElMes.setText(GlobalVars.contexto.getResources().getString(R.string.textoEnElMes) + " " +  GlobalVars.moneda + GlobalVars.formatoNumeros.format(0) + "   ");
    		}
    		catch(NullPointerException e)
    		{
    		}
    		catch(Exception e)
    		{
    		}
    	}
    
	public Bitmap doInBackground(String... urls)
    	{
		try
			{
			leerBase();
			GlobalVars.depurada.clear();
			for(int i=0;i<GlobalVars.listado.size();i++)
				{
				String valor = GlobalVars.listado.get(i);
				if (!(valor.startsWith(GlobalVars.anoAct + "-" + GlobalVars.mesAct)))
					{
					GlobalVars.depurada.add(valor);
					}
				}
			
			//VACIA LOS VALORES DEL LISTADO ORIGINALES Y SE PASAN LOS VALORES DE DEPURADA.
			//DEPURADA TIENE TODOS LOS REGISTROS, MENOS LOS DEL MES ACTUAL.
			//DESPUES, SE GUARDA LA BASE DE DATOS.
			GlobalVars.listado.clear();
			for (int i=0;i<GlobalVars.depurada.size();i++)
				{
				GlobalVars.listado.add(GlobalVars.depurada.get(i));
				}
			guardarListaEnBase();

			leerBase();
			GlobalVars.depurada.clear();
			GlobalVars.enElMes = 0;
			GlobalVars.hoy = 0;
			}
			catch(NullPointerException e)
			{
			}
			catch(Exception e)
			{
			}
		return null;
    	}
		
	public void leerBase()
		{
		GlobalVars.listado.clear();
		GlobalVars.enElMes = 0;
		GlobalVars.hoy = 0;
		String resultado = leerArchivo("base.cfg");
		BufferedReader rdr = null;
		try
			{
			rdr = new BufferedReader(new StringReader(resultado));
			for (String line = rdr.readLine(); line != null; line = rdr.readLine())
				{
				if (line.length()>3)
					{
					GlobalVars.listado.add(line);
					}
				}
			}
			catch(Exception e)
			{
			}
		try
			{
			rdr.close();
			}
			catch (Exception e)
			{
			}
		Collections.sort(GlobalVars.listado, Collections.reverseOrder());
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
		nuevoViaje(aGuardar);
		}

	public void nuevoViaje(String valor)
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
	
	public String leerArchivo(String archivo)
		{
		String resultado = "";
		DataInputStream in = null;
		try
    		{
			in = new DataInputStream(GlobalVars.contexto.openFileInput(archivo));
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
			DataOutputStream out = new DataOutputStream(GlobalVars.contexto.openFileOutput(archivo, Context.MODE_PRIVATE));
			out.writeUTF(texto);
			out.close();
			}
	    	catch(Exception e)
	    	{
	    	}
		}
	}