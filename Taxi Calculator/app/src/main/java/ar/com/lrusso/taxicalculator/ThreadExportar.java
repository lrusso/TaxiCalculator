package ar.com.lrusso.taxicalculator;

import java.io.*;
import java.util.zip.*;
import java.io.DataInputStream;
import java.util.List;
import java.util.ArrayList;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.widget.Toast;

public class ThreadExportar extends AsyncTask<String, Void, Bitmap>
	{
    ProgressDialog myDialog;
    Activity actividad;
    boolean errorExportar = false;
    boolean errorBaseVacia = false;
	List<String> archivos = new ArrayList<String>();

    public ThreadExportar(Activity a)
		{
    	actividad = a;
		}

    @Override protected void onPreExecute()
		{
    	super.onPreExecute();
    	myDialog = new ProgressDialog(actividad);
    	myDialog.setMessage(actividad.getResources().getString(R.string.textoExportando));
        myDialog.setCancelable(false);
        myDialog.show();
		}

    public void onPostExecute(Bitmap result)
		{
		myDialog.dismiss();
		if (errorExportar==true)
			{
			Toast.makeText(actividad, actividad.getResources().getString(R.string.textoExpoFalla), Toast.LENGTH_LONG).show();
			}
		else if (errorBaseVacia==true)
			{
			Toast.makeText(actividad, actividad.getResources().getString(R.string.textoExpoFalla2), Toast.LENGTH_LONG).show();
			}
			else
			{
    		Toast.makeText(actividad, actividad.getResources().getString(R.string.textoExpoExito), Toast.LENGTH_LONG).show();
			}
		}

	public Bitmap doInBackground(String... urls)
		{
		String resultado = leerArchivo("base.cfg");
		if (resultado==null)
			{
			errorBaseVacia = true;
			}
		else if (resultado.length()<5)
			{
			errorBaseVacia = true;
			}
			else
			{
			archivos.clear();
			archivos.add("base.cfg");
			archivos.add("moneda.cfg");
			String ruta = Environment.getExternalStorageDirectory().toString();
			if (ruta.substring(ruta.length()-1, ruta.length())!="/")
				{
				ruta = ruta + "/";
				}
			ruta = ruta + "database.tax";

			// different logics for writing a file - thank you android
			if (Build.VERSION.SDK_INT>=29)
				{
				zipNew(archivos,ruta);
				}
			else
				{
				zipOld(archivos,ruta);
				}
			}
    	return null;
		}
	
	public void zipOld(List<String> files, String zipFile)
		{
		try
			{
			BufferedInputStream origin = null; 
			FileOutputStream dest = new FileOutputStream(zipFile); 
			ZipOutputStream out = new ZipOutputStream(new BufferedOutputStream(dest)); 

			byte data[] = new byte[2048];

			for(int i=0; i < files.size(); i++)
				{
				DataInputStream	fi = new DataInputStream(actividad.openFileInput(files.get(i)));
				origin = new BufferedInputStream(fi, 2048); 
				ZipEntry entry = new ZipEntry(files.get(i));
				out.putNextEntry(entry); 
				int count; 
				while ((count = origin.read(data, 0, 2048)) != -1)
					{ 
					out.write(data, 0, count); 
					} 
				origin.close(); 
				} 
			out.close();
			}
			catch(IOException e)
			{
			errorExportar=true;
			}
			catch(Exception e)
			{
			errorExportar=true;
			}
		}

	public void zipNew(List<String> files, String zipFile)
		{
		try
			{
			ContentValues values = new ContentValues();
			OutputStream dest;

			values.put(MediaStore.MediaColumns.DISPLAY_NAME, "database.tax");
			values.put(MediaStore.MediaColumns.MIME_TYPE, "application/octet-stream");
			values.put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_DOWNLOADS);

			Uri extVolumeUri = MediaStore.Files.getContentUri("external");
			Uri fileUri = actividad.getContentResolver().insert(extVolumeUri, values);

			dest = actividad.getContentResolver().openOutputStream(fileUri);

			BufferedInputStream origin = null;
			ZipOutputStream out = new ZipOutputStream(new BufferedOutputStream(dest));

			byte data[] = new byte[2048];

			for(int i=0; i < files.size(); i++)
				{
				DataInputStream	fi = new DataInputStream(actividad.openFileInput(files.get(i)));
				origin = new BufferedInputStream(fi, 2048);
				ZipEntry entry = new ZipEntry(files.get(i));
				out.putNextEntry(entry);
				int count;
				while ((count = origin.read(data, 0, 2048)) != -1)
					{
					out.write(data, 0, count);
					}
				origin.close();
				}
			out.close();
			}
			catch(IOException e)
			{
			errorExportar=true;
			}
			catch(Exception e)
			{
			errorExportar=true;
			}
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
	}