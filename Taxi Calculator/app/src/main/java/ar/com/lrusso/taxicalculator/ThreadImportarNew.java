package ar.com.lrusso.taxicalculator;

import java.io.*;
import java.util.zip.*;
import java.util.Collections;
import java.util.Comparator;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.widget.Toast;

public class ThreadImportarNew extends AsyncTask<String, Void, Bitmap>
{
    ProgressDialog myDialog;
    Activity actividad;
    Uri valor;
    boolean errorImportar=false;

    public ThreadImportarNew(Activity a, Uri b)
        {
        actividad = a;
        valor = b;
        System.out.println(b);
        }

    @Override protected void onPreExecute()
        {
        super.onPreExecute();
        myDialog = new ProgressDialog(actividad);
        myDialog.setMessage(actividad.getResources().getString(R.string.textoImportando));
        myDialog.setCancelable(false);
        myDialog.show();
    }

    public void onPostExecute(Bitmap result)
        {
        myDialog.dismiss();
        if (errorImportar==true)
            {
            Toast.makeText(actividad, actividad.getResources().getString(R.string.textoImpoFalla), Toast.LENGTH_LONG).show();
            }
            else
            {
            Toast.makeText(actividad, actividad.getResources().getString(R.string.textoImpoExito), Toast.LENGTH_LONG).show();
            }
        Main.iniciarlectura(actividad);
        }

    public Bitmap doInBackground(String... urls)
        {
        unzip();
        leerBase();
        return null;
        }

    public void leerBase()
        {
        GlobalVars.listado.clear();
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

        Collections.sort(GlobalVars.listado, new Comparator<String>(){public int compare(String s1, String s2){return s1.compareToIgnoreCase(s2);}});
        String moneda = leerArchivo("moneda.cfg");
        if (moneda=="")
            {
            escribirArchivo("moneda.cfg","$");
            GlobalVars.moneda = "$";
            }
        else if(moneda.contains("$"))
            {
            escribirArchivo("moneda.cfg","$");
            GlobalVars.moneda = "$";
            }
        else if(moneda.contains("€"))
            {
            escribirArchivo("moneda.cfg","€");
            GlobalVars.moneda = "€";
            }
        else if(moneda.contains("£"))
            {
            escribirArchivo("moneda.cfg","£");
            GlobalVars.moneda = "£";
            }
        else
            {
            escribirArchivo("moneda.cfg","$");
            GlobalVars.moneda = "$";
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

    public void unzip()
        {
        try
            {
            InputStream inputStream = actividad.getContentResolver().openInputStream(valor);
            ZipInputStream zin = new ZipInputStream(inputStream);
            ZipEntry ze = null;
            while ((ze = zin.getNextEntry()) != null)
                {
                String path = ze.getName();
                DataOutputStream fout = new DataOutputStream(actividad.openFileOutput(path, Context.MODE_PRIVATE));
                for (int c = zin.read(); c != -1; c = zin.read())
                    {
                    fout.write(c);
                    }
                zin.closeEntry();
                fout.close();
                }
            zin.close();
            }
            catch(IOException e)
            {
            Toast.makeText(actividad, e.toString(),Toast.LENGTH_LONG).show();
            errorImportar=true;
            }
            catch(Exception e)
            {
            Toast.makeText(actividad, e.toString(),Toast.LENGTH_LONG).show();
            errorImportar=true;
            }
        }
    }