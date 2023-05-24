package ar.com.lrusso.taxicalculator;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.StringReader;
import java.util.Calendar;
import java.util.Collections;

import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.view.View;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

public class ThreadCargarElMes extends AsyncTask<String, Void, Bitmap>
	{
	ProgressDialog myDialog;
    GridView gridView;
    TextView textoNada;
    TextView textoHoy;
    TextView textoEnElMes;
    TextView titulo;
    Activity actividad;
    long start;    
    
    public ThreadCargarElMes(GridView a, TextView b, TextView c, TextView d, TextView e, Activity f)
    	{
    	start = System.currentTimeMillis();
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
    	myDialog.setMessage(actividad.getResources().getString(R.string.textoLeyendo));
        myDialog.setCancelable(false);
        myDialog.show();
    	}
    
    public void onPostExecute(Bitmap result)
    	{
    	try
    		{
            myDialog.dismiss();
    		gridView.setAdapter(new ImageAdapter(actividad, GlobalVars.depurada));
    		if (GlobalVars.depurada.size()==0)
    			{
    			gridView.setVisibility(View.GONE);
    			textoNada.setVisibility(View.VISIBLE);
    			}
    		else
    			{
    			gridView.setVisibility(View.VISIBLE);
    			textoNada.setVisibility(View.GONE);
    			}
    		cargarResultados();
    		cargarNuevoTitulo();

    		//SI TARDA MAS DE 5 SEGUNDOS, MUESTAR EL TOAST DE SUGERIR BORRAR MESES ANTIGUOS PARA MEJORAR RENDIMIENTO
    		long elapsedTimeMillis = System.currentTimeMillis()-start;
    		float elapsedTimeSec = elapsedTimeMillis/1000F;
    		if (elapsedTimeSec>=5)
    			{
        		Toast.makeText(actividad, GlobalVars.contexto.getResources().getString(R.string.textoSugerenciaEliminarMeses), Toast.LENGTH_LONG).show();
    			}
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
			Calendar c = Calendar.getInstance(); 
			String ano = (String) android.text.format.DateFormat.format("yyyy", c);
			String mes = (String) android.text.format.DateFormat.format("MM", c);
			String dia = (String) android.text.format.DateFormat.format("dd", c);
			GlobalVars.depurada.clear();
			GlobalVars.enElMes = 0;
			GlobalVars.hoy = 0;

			for(int i=0;i<GlobalVars.listado.size();i++)
				{
				String valor = GlobalVars.listado.get(i);
				if (valor.startsWith(GlobalVars.anoAct + "-" + GlobalVars.mesAct + "-" + dia + "|"))		//GANANCIA DEL DIA Y MES
					{
					agregarHoy(valor);
					}
				else if (valor.startsWith(GlobalVars.anoAct + "-" + GlobalVars.mesAct + "-"))				//GANANCIA DEL MES
					{
					agregarEnElMes(valor);
					}
				}
			}
			catch(NullPointerException e)
			{
			}
			catch(Exception e)
			{
			}
		return null;
    	}
	
	public void agregarHoy(String valor)
		{
		GlobalVars.depurada.add(valor);
		GlobalVars.hoy = GlobalVars.hoy + GlobalVars.devolverPrecio(valor);
		GlobalVars.enElMes = GlobalVars.enElMes + GlobalVars.devolverPrecio(valor);
		}

	public void agregarEnElMes(String valor)
		{
		GlobalVars.depurada.add(valor);
		GlobalVars.enElMes = GlobalVars.enElMes + GlobalVars.devolverPrecio(valor);
		}

	public void cargarResultados()
		{
		Calendar c = Calendar.getInstance(); 
		String ano = (String) android.text.format.DateFormat.format("yyyy", c);
		String mes = (String) android.text.format.DateFormat.format("MM", c);
		if (GlobalVars.mesAct.equals(mes) && GlobalVars.anoAct.equals(ano))
			{
			textoHoy.setText(GlobalVars.contexto.getResources().getString(R.string.textoHoy) + " " +  GlobalVars.moneda + GlobalVars.formatoNumeros.format(GlobalVars.hoy) + "   ");
			textoHoy.setVisibility(View.VISIBLE);
			}
			else
			{
			textoHoy.setVisibility(View.GONE);
			}
		textoEnElMes.setText(GlobalVars.contexto.getResources().getString(R.string.textoEnElMes) + " " +  GlobalVars.moneda + GlobalVars.formatoNumeros.format(GlobalVars.enElMes) + "   ");
		}
	
	public void cargarNuevoTitulo()
		{
		int mes =  Integer.valueOf(GlobalVars.mesAct);
		if (mes==1){titulo.setText(GlobalVars.anoAct + " - " + GlobalVars.contexto.getResources().getString(R.string.textoEnero));}
		else if (mes==2){titulo.setText(GlobalVars.anoAct + " - " + GlobalVars.contexto.getResources().getString(R.string.textoFebrero));}
		else if (mes==3){titulo.setText(GlobalVars.anoAct + " - " + GlobalVars.contexto.getResources().getString(R.string.textoMarzo));}
		else if (mes==4){titulo.setText(GlobalVars.anoAct + " - " + GlobalVars.contexto.getResources().getString(R.string.textoAbril));}
		else if (mes==5){titulo.setText(GlobalVars.anoAct + " - " + GlobalVars.contexto.getResources().getString(R.string.textoMayo));}
		else if (mes==6){titulo.setText(GlobalVars.anoAct + " - " + GlobalVars.contexto.getResources().getString(R.string.textoJunio));}
		else if (mes==7){titulo.setText(GlobalVars.anoAct + " - " + GlobalVars.contexto.getResources().getString(R.string.textoJulio));}
		else if (mes==8){titulo.setText(GlobalVars.anoAct + " - " + GlobalVars.contexto.getResources().getString(R.string.textoAgosto));}
		else if (mes==9){titulo.setText(GlobalVars.anoAct + " - " + GlobalVars.contexto.getResources().getString(R.string.textoSeptiembre));}
		else if (mes==10){titulo.setText(GlobalVars.anoAct + " - " + GlobalVars.contexto.getResources().getString(R.string.textoOctubre));}
		else if (mes==11){titulo.setText(GlobalVars.anoAct + " - " + GlobalVars.contexto.getResources().getString(R.string.textoNoviembre));}
		else if (mes==12){titulo.setText(GlobalVars.anoAct + " - " + GlobalVars.contexto.getResources().getString(R.string.textoDiciembre));}
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
	}