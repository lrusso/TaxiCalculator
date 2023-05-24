package ar.com.lrusso.taxicalculator;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.util.Calendar;

import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.text.Html;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

public class Main extends Activity
	{
	private boolean yaPasoPorStart=false;
	
	private static final int MIN_DISTANCE = 150;

	private static final int PICK_TAX_FILE = 1;

	private Menu mainMenu;
	
	private static float x1,x2, deltaX;
	
	private static GridView gridView;
	private static TextView textoHoy;
	private static TextView textoEnElMes;
	private static TextView textoNada;
	private static TextView textoTitulo;
	private static Activity actividad;
	
	@Override protected void onCreate(Bundle savedInstanceState)
		{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		
		GlobalVars.contexto = this;
		
		actividad = this;
		
		gridView = (GridView) findViewById(R.id.gridview);
		textoHoy = (TextView) findViewById(R.id.hoy);
		textoEnElMes = (TextView) findViewById(R.id.enElMes);
		textoNada = (TextView) findViewById(R.id.textonada);
		textoTitulo = (TextView) findViewById(R.id.titulo);

		Calendar c = Calendar.getInstance(); 
		String ano = (String) android.text.format.DateFormat.format("yyyy", c);
		String mes = (String) android.text.format.DateFormat.format("MM", c);
		GlobalVars.anoAct = ano;
		GlobalVars.mesAct = mes;
		
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

		registerForContextMenu(gridView);
		gridView.setOnItemClickListener(new OnItemClickListener()
			{
			public void onItemClick(AdapterView<?> parent, View view,int position, long id)
				{
				clickEnAbrir(position);
				}
			});

		gridView.setOnTouchListener(new TextView.OnTouchListener()
			{
	        @Override public boolean onTouch(View v, MotionEvent event)
	        	{
	            switch (event.getAction())
	            	{
	            	case MotionEvent.ACTION_DOWN:
	    	    	x1 = event.getX();
	                break;
	            
	            	case MotionEvent.ACTION_MOVE:
	            	break;
	            
	            	case MotionEvent.ACTION_UP:
        	    	x2 = event.getX();
        	    	deltaX = x2 - x1;
        	    	cambiarMes();
	                break;
	            	}
	        	return false;
	        	}
			});
		
		iniciarlectura(actividad);
		if (Build.VERSION.SDK_INT>=23) //MARSHMALLOW
			{
			try
				{
				iniciarVerificacionMarshmallow();
				}
				catch(Exception e)
				{
				}
			}
		}
	
	@Override public boolean onCreateOptionsMenu(Menu menu)
		{
	    MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.main_activity_actions, menu);
	    mainMenu=menu;
	    return super.onCreateOptionsMenu(menu);
		}
	
	@Override public boolean onOptionsItemSelected(MenuItem item)
		{
	    switch (item.getItemId())
	    	{
	    	case R.id.action_settings:
	    	View menuItemView = findViewById(R.id.action_settings);
	    	PopupMenu popupMenu = new PopupMenu(this, menuItemView); 
	    	popupMenu.inflate(R.menu.popup_menu);
	    	popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener()
    			{  
	    		public boolean onMenuItemClick(MenuItem item)
        			{
	    			if (item.getTitle().toString().contains(getResources().getString(R.string.textoAgregar)))
	    				{
	    				clickEnAgregar();
	    				}
	    			else if (item.getTitle().toString().contains(getResources().getString(R.string.textoVaciar)))
						{
	    				clickEnVaciar();
						}
	    			else if (item.getTitle().toString().contains(getResources().getString(R.string.textoGraficoHoras)))
						{
	    				clickEnGraficoPorHoras();
						}
	    			else if (item.getTitle().toString().contains(getResources().getString(R.string.textoGraficoDias)))
						{
	    				clickEnGraficoPorDia();
						}
	    			else if (item.getTitle().toString().contains(getResources().getString(R.string.textoConfig)))
						{
	    				clickEnConfig();
						}
	    			else if (item.getTitle().toString().contains(getResources().getString(R.string.textoPolitica)))
						{
	    				clickEnPolitica();
						}
	    			else if (item.getTitle().toString().contains(getResources().getString(R.string.textoAcercaDe)))
	    				{
	    				clickEnAbout();
	    				}
	    			return true;  
        			}  
    			});              
	    	popupMenu.show();
	    	return true;
		
	    	default:
	    	return super.onOptionsItemSelected(item);
	    	}
		}
	
	@Override public void onCreateContextMenu(ContextMenu menu, View v,ContextMenuInfo menuInfo)
		{
		super.onCreateContextMenu(menu, v, menuInfo);
		menu.setHeaderTitle(R.string.textoOpciones);
		AdapterContextMenuInfo cmi = (AdapterContextMenuInfo) menuInfo;
		menu.add(0, cmi.position, 0, R.string.textoAbrir);
		menu.add(0, cmi.position, 0, R.string.textoEliminar);
		}
	
	@Override public boolean onContextItemSelected(MenuItem item)
		{
		if(item.getTitle()==getResources().getString(R.string.textoAbrir))
			{
			clickEnAbrir(item.getItemId());
			return true;
			}
		if(item.getTitle()==getResources().getString(R.string.textoEliminar))
			{
			clickEnEliminar(item.getItemId());
			return true;
			}
		return false; 
		}
	

	@Override public boolean onTouchEvent(MotionEvent event)
		{
	    switch(event.getAction())
	    	{
	    	case MotionEvent.ACTION_DOWN:
	    	x1 = event.getX();                         
	    	break;         

	    	case MotionEvent.ACTION_UP:
	    	x2 = event.getX();
	    	deltaX = x2 - x1;
	    	cambiarMes();
	    	break;   
	    	}           
	    return super.onTouchEvent(event);  		
		}
	
	@Override public void onResume()
		{
		if (GlobalVars.actualizar==true)
			{
			GlobalVars.actualizar=false;
			iniciarlectura(actividad);
			}
	    super.onResume();
		}
	
	@Override public void onDestroy()
		{
		yaPasoPorStart=false;
	    super.onDestroy();
		}
	
	@Override public boolean onKeyUp(int keyCode, KeyEvent event)
		{
		try
			{
			if (keyCode == KeyEvent.KEYCODE_MENU)
				{
				if (mainMenu!=null)
        			{
					mainMenu.performIdentifierAction(R.id.action_settings, 0);
        			}				
				}
			}
			catch(NullPointerException e)
			{
			}
		return super.onKeyUp(keyCode, event);
		}
	
	@Override public void onStart ()
		{
		final Intent intent = getIntent ();
		if (intent != null)
			{
			final android.net.Uri data = intent.getData ();
			if (data != null)
				{
				if (yaPasoPorStart==false)
					{
					yaPasoPorStart=true;
					final String filePath = data.getEncodedPath ();
					clickEnConfigImportar(filePath);
					}
				}
			}
		super.onStart ();
		}

	@Override public void onActivityResult(int requestCode, int resultCode, Intent resultData)
		{
		if (requestCode == PICK_TAX_FILE && resultCode == Activity.RESULT_OK)
			{
			try
				{
				Uri uri = null;
				if (resultData != null)
					{
					uri = resultData.getData();
					new ThreadImportarNew(actividad, uri).execute();
					}
				}
				catch(Exception e)
				{
				}
			}
		}

		public void myClickHandler(View view)
    	{
    	try
			{
    		if (view.getId()==R.id.botonagregar)
				{
    			clickEnAgregar();
				}
			}
			catch(NoClassDefFoundError e)
			{
			}
			catch(IllegalStateException e)
			{
			}
			catch(NullPointerException e)
			{
			}
			catch(Exception e)
			{
			}
    	}

	public void clickEnAbrir(int position)
		{
		GlobalVars.paraEditar = GlobalVars.devolverPosicion(position);
		GlobalVars.paraEditarPosition = position;
		
		if (GlobalVars.paraEditar!=null)
			{
			Intent intent = new Intent(this, Editar.class);
			startActivity(intent);
			}
		}

	public void clickEnGraficoPorHoras()
		{
		try
			{
			Intent intent = new Intent(this, GraficoPorHoras.class);
			startActivity(intent);
			}
			catch(Exception e)
			{
			}
		}

	public void clickEnGraficoPorDia()
		{
		try
			{
			Intent intent = new Intent(this, GraficoPorDia.class);
			startActivity(intent);
			}
			catch(Exception e)
			{
			}
		}
	
	public void clickEnEliminar(int position)
		{
		final String valor = GlobalVars.devolverPosicion(position);
		final String aeliminar = GlobalVars.devolverDiaMesPregEliminar(valor) + " " +
				 				 GlobalVars.devolverHoras(valor) + ":" +
				 				 GlobalVars.devolverMinutos(valor) + " " +
				 				 getResources().getString(R.string.textoAgregarHoraTexto) + " " +
				 				 GlobalVars.moneda +
				 				 GlobalVars.devolverPrecioEliminar(valor);
		
		final Activity actividad = this;
    	DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener()
    		{
    		public void onClick(DialogInterface dialog, int which)
    			{
    			switch (which)
    				{
    				case DialogInterface.BUTTON_POSITIVE:
	   	    		new ThreadEliminar(actividad, valor).execute();
    				break;
    				
    				case DialogInterface.BUTTON_NEGATIVE:
    				break;
    				}
    			}
    		};
    	AlertDialog.Builder builder = new AlertDialog.Builder(this);
    	builder.setMessage(getResources().getString(R.string.textoDeseaEliminar) + aeliminar + "'?").setPositiveButton(getResources().getString(R.string.textoSi), dialogClickListener).setNegativeButton(getResources().getString(R.string.textoNo), dialogClickListener).show();
		}

	public void clickEnAgregar()
		{
		Intent intent = new Intent(this, Agregar.class);
		startActivity(intent);
		}
	
	public void clickEnVaciar()
		{
		DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener()
			{
			public void onClick(DialogInterface dialog, int which)
				{
				switch (which)
					{
					case DialogInterface.BUTTON_POSITIVE:
					try
						{
						gridView.setAdapter(null);
						}
						catch(NullPointerException e)
						{
						}
						catch(Exception e)
						{
						}
					try
						{
						gridView.removeAllViews();
						}
						catch(NullPointerException e)
						{
						}
						catch(Exception e)
						{
						}
		    		gridView.setVisibility(View.GONE);
		    		textoNada.setVisibility(View.VISIBLE);
					new ThreadVaciarMes(gridView, textoNada, textoHoy, textoEnElMes, textoTitulo, actividad).execute();
					break;
				
					case DialogInterface.BUTTON_NEGATIVE:
					break;
					}
				}
			};
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage(getResources().getString(R.string.textoVaciarMes)).setPositiveButton(getResources().getString(R.string.textoSi), dialogClickListener).setNegativeButton(getResources().getString(R.string.textoNo), dialogClickListener).show();
		}
	
	public void clickEnConfig()
		{
    	final AlertDialog.Builder singlechoicedialog = new AlertDialog.Builder(this);
    	final CharSequence[] Report_items = {getResources().getString(R.string.textoExportarBase),getResources().getString(R.string.textoImportarBase),getResources().getString(R.string.textoVaciarBase),getResources().getString(R.string.textoCambiarMoneda)};
    	singlechoicedialog.setTitle(R.string.textoSeleccioneOpcion);
    	singlechoicedialog.setItems(Report_items, new DialogInterface.OnClickListener()
    		{
    		public void onClick(DialogInterface dialog, int item)
    			{
    			String value = Report_items[item].toString();
    			if (value==getResources().getString(R.string.textoExportarBase))
    				{
    				clickEnConfigExportar();    				
    				}
    			if (value==getResources().getString(R.string.textoImportarBase))
    				{
					// two notifications because of two different and mandatory ways to write a file - thank you google
					if (Build.VERSION.SDK_INT>=29)
						{
						clickEnConfigImportarNew();
						}
					else
						{
						String valor = Environment.getExternalStorageDirectory().toString();
						if (valor.substring(valor.length()-1, valor.length())!="/")
							{
							valor = valor + "/";
							}
						valor = valor + "database.tax";
						clickEnConfigImportar(valor);
						}
					}
    			if (value==getResources().getString(R.string.textoVaciarBase))
    				{
    				clickEnConfigVaciar();
    				}
    			if (value==getResources().getString(R.string.textoCambiarMoneda))
    				{
    				clickEnConfigCambiarMoneda();
    				}
    			dialog.cancel();
    			}
    		});
    	AlertDialog alert_dialog = singlechoicedialog.create();
    	alert_dialog.show();
		}
	
	public void clickEnConfigExportar()
		{
    	DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener()
			{
    		public void onClick(DialogInterface dialog, int which)
				{
    			switch (which)
    				{
    				case DialogInterface.BUTTON_POSITIVE:
    				clickEnConfigExportarEjecutar();
    				break;
				
    				case DialogInterface.BUTTON_NEGATIVE:
    				break;
    				}
				}
			};
		AlertDialog.Builder builder = new AlertDialog.Builder(this);

		// two notifications because of two different and mandatory ways to write a file - thank you google
		if (Build.VERSION.SDK_INT>=29)
			{
			builder.setMessage(getResources().getString(R.string.textoExpoPregNew)).setPositiveButton(getResources().getString(R.string.textoExpoPregSi), dialogClickListener).setNegativeButton(getResources().getString(R.string.textoExpoPregNo), dialogClickListener).show();
			}
		else
			{
			builder.setMessage(getResources().getString(R.string.textoExpoPregOld)).setPositiveButton(getResources().getString(R.string.textoExpoPregSi), dialogClickListener).setNegativeButton(getResources().getString(R.string.textoExpoPregNo), dialogClickListener).show();
			}
		}
	
	public void clickEnConfigExportarEjecutar()
		{
   		new ThreadExportar(this).execute();
		}
	
	public void clickEnConfigImportar(final String valor)
		{
    	DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener()
			{
    		public void onClick(DialogInterface dialog, int which)
				{
    			switch (which)
    				{
    				case DialogInterface.BUTTON_POSITIVE:
    				clickEnConfigImportarEjecutar(valor);
    				break;
				
    				case DialogInterface.BUTTON_NEGATIVE:
    				break;
    				}
				}
			};
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage(getResources().getString(R.string.textoImpoPreg) + valor).setPositiveButton(getResources().getString(R.string.textoImpoPregSi), dialogClickListener).setNegativeButton(getResources().getString(R.string.textoImpoPregNo), dialogClickListener).show();
		}

	public void clickEnConfigImportarEjecutar(String valor)
		{
   		new ThreadImportar(this, valor).execute();
		}

	public void clickEnConfigImportarNew()
		{
		DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener()
			{
			public void onClick(DialogInterface dialog, int which)
				{
				switch (which)
					{
					case DialogInterface.BUTTON_POSITIVE:
						Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
						intent.addCategory(Intent.CATEGORY_OPENABLE);
						intent.setType("application/octet-stream");
						startActivityForResult(intent, PICK_TAX_FILE);
					break;
				}
			}
		};
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage(getResources().getString(R.string.textoImpoNewMensaje)).setPositiveButton(getResources().getString(R.string.textoImpoNewAceptar), dialogClickListener).show();
		}

	public void clickEnConfigVaciar()
		{
    	DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener()
    		{
    		public void onClick(DialogInterface dialog, int which)
    			{
    			switch (which)
    				{
    				case DialogInterface.BUTTON_POSITIVE:
    				GlobalVars.listado.clear();
    				GlobalVars.depurada.clear();
					try
						{
						deleteFile("moneda.cfg");
						}
						catch(IllegalArgumentException e)
						{
						}
						catch(Exception e)
						{
						}
					escribirArchivo("moneda.cfg","$");
					GlobalVars.moneda = "$";
    				guardarListaEnBase();
    				break;
    				
    				case DialogInterface.BUTTON_NEGATIVE:
    				break;
    				}
    			}
    		};
    	AlertDialog.Builder builder = new AlertDialog.Builder(this);
    	builder.setMessage(getResources().getString(R.string.textoVaciarBasePregunta)).setPositiveButton(getResources().getString(R.string.textoSi), dialogClickListener).setNegativeButton(getResources().getString(R.string.textoNo), dialogClickListener).show();
		}
	
	public void clickEnConfigCambiarMoneda()
		{
    	final AlertDialog.Builder singlechoicedialog = new AlertDialog.Builder(this);
    	final CharSequence[] Report_items = {getResources().getString(R.string.textoMonedaDolar),getResources().getString(R.string.textoMonedaEuro),getResources().getString(R.string.textoMonedaLibraEsterlina)};
    	singlechoicedialog.setTitle(R.string.textoSeleccioneMoneda);
    	int seleccion = -1;
    	if (GlobalVars.moneda=="$"){seleccion=0;}
    	if (GlobalVars.moneda=="€"){seleccion=1;}
    	if (GlobalVars.moneda=="£"){seleccion=2;}
    	singlechoicedialog.setSingleChoiceItems(Report_items, seleccion,new DialogInterface.OnClickListener()
    		{
    		public void onClick(DialogInterface dialog, int item)
    			{
    			String value = Report_items[item].toString();
    			if (value==getResources().getString(R.string.textoMonedaDolar))
    				{
    				escribirArchivo("moneda.cfg","$");
    				GlobalVars.moneda = "$";
    				iniciarlectura(actividad);
    				}
    			if (value==getResources().getString(R.string.textoMonedaEuro))
    				{
    				escribirArchivo("moneda.cfg","€");
    				GlobalVars.moneda = "€";
    				iniciarlectura(actividad);
    				}
    			if (value==getResources().getString(R.string.textoMonedaLibraEsterlina))
    				{
    				escribirArchivo("moneda.cfg","£");
    				GlobalVars.moneda = "£";
    				iniciarlectura(actividad);
    				}
    			dialog.cancel();
    			}
    		});
    	AlertDialog alert_dialog = singlechoicedialog.create();
    	alert_dialog.show();
		}

	public void clickEnPolitica()
		{
		LayoutInflater inflater = LayoutInflater.from(this);
		View view=inflater.inflate(R.layout.politicas, null);

		AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);  
		alertDialog.setTitle(getResources().getString(R.string.textoPolitica));  
		alertDialog.setView(view);
		alertDialog.setPositiveButton(getResources().getString(R.string.textoAceptar), new DialogInterface.OnClickListener()
			{
			public void onClick(DialogInterface dialog, int whichButton)
				{
				}
			});
		alertDialog.show();
		}
	
	public void clickEnAbout()
		{
		String value = getResources().getString(R.string.textAboutMessage);
		value = value.replace("APPNAME",getResources().getString(R.string.app_name));

		TextView msg = new TextView(this);
		msg.setText(Html.fromHtml(value));
		msg.setPadding(10, 20, 10, 25);
		msg.setGravity(Gravity.CENTER);
		float scaledDensity = getResources().getDisplayMetrics().scaledDensity;
		float size = new EditText(this).getTextSize() / scaledDensity;
		msg.setTextSize(size);

		new AlertDialog.Builder(this).setTitle(getResources().getString(R.string.textoAcercaDe)).setView(msg).setIcon(R.drawable.ic_launcher).setPositiveButton(getResources().getString(R.string.textoAceptar),new DialogInterface.OnClickListener()
			{
			public void onClick(DialogInterface dialog,int which)
				{
				}
			}).show();
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
		iniciarlectura(actividad);
		}
	
	public static void iniciarlectura(Activity actividad)
		{
		try
			{
			gridView.setAdapter(null);
			}
			catch(NullPointerException e)
			{
			}
			catch(Exception e)
			{
			}
		try
			{
			gridView.removeAllViews();
			}
			catch(NullPointerException e)
			{
			}
			catch(Exception e)
			{
			}
		textoTitulo.setText(GlobalVars.contexto.getResources().getString(R.string.textoCargando));
		new ThreadCargarElMes(gridView, textoNada, textoHoy, textoEnElMes, textoTitulo, actividad).execute();
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
	
	public void cambiarMes()
		{
		if (Math.abs(deltaX) > MIN_DISTANCE)
			{
			int mes = Integer.valueOf(GlobalVars.mesAct);
			int ano = Integer.valueOf(GlobalVars.anoAct);
			if (x2 > x1)
				{
				if (mes==1)
					{
					if (ano-1==2012)
						{
						Toast.makeText(this, R.string.textoPeriodoMinimo, Toast.LENGTH_LONG).show();
						}
						else
						{
						GlobalVars.mesAct="12";
						GlobalVars.anoAct = String.valueOf(ano - 1);
						iniciarlectura(actividad);
						}
					}
					else
					{
					if (mes-1<=9)
						{
						GlobalVars.mesAct= "0" + String.valueOf(mes - 1);
						}
						else
						{
						GlobalVars.mesAct= String.valueOf(mes - 1);
						}
					iniciarlectura(actividad);
					}
				}
				else 
				{
				if (mes==12)
					{
					Calendar calendar = Calendar.getInstance();
					int year = calendar.get(Calendar.YEAR);
					if (ano+1==year+3)
						{
						Toast.makeText(this, R.string.textoPeriodoMaximo, Toast.LENGTH_LONG).show();
						}
						else
						{
						GlobalVars.mesAct="01";
						GlobalVars.anoAct = String.valueOf(ano + 1);
						iniciarlectura(actividad);
						}
					}
					else
					{
					if (mes+1<=9)
						{
						GlobalVars.mesAct= "0" + String.valueOf(mes + 1);
						}
						else
						{
						GlobalVars.mesAct= String.valueOf(mes + 1);
						}	
					iniciarlectura(actividad);
					}
				}
			}
		}

	public void mensaje(String a, String b, String c)
		{
		new AlertDialog.Builder(this).setTitle(b).setMessage(a).setPositiveButton(c,new DialogInterface.OnClickListener()
			{
			public void onClick(DialogInterface dialog,int which)
				{
				}
			}).show();
		}
	
	public String leerArchivo(String archivo)
		{
		String resultado = "";
		DataInputStream in = null;
	    try
	    	{
		    in = new DataInputStream(openFileInput(archivo));
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
		    DataOutputStream out = new DataOutputStream(openFileOutput(archivo, Context.MODE_PRIVATE));
	        out.writeUTF(texto);
		    out.close();
			}
		    catch(Exception e)
		    {
		    }
		}

	@TargetApi(Build.VERSION_CODES.M)
	public void iniciarVerificacionMarshmallow()
		{
		if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
			{
			String[] PERMISSIONS_STORAGE = {Manifest.permission.READ_EXTERNAL_STORAGE,
											Manifest.permission.WRITE_EXTERNAL_STORAGE};
			requestPermissions(PERMISSIONS_STORAGE, 123);
			}
		}
		
	@TargetApi(Build.VERSION_CODES.M)
	@Override public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults)
		{
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode==123)
        	{
        	if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
        		{
        		}
        	}
		}
	}