package ar.com.lrusso.taxicalculator;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;

public class Editar extends Activity
	{
	private TextView montoOrig;
 	private Spinner minutos;
 	private Spinner horas;
	private Spinner dias;
	private Spinner mess;
 	private Spinner anos;
 	private TextView observacionesOrig;
	private Activity actividad;
	private boolean huboCambios = false;
	private boolean spinnerFix1 = false;
	private boolean spinnerFix2 = false;
	private boolean spinnerFix3 = false;
	private boolean spinnerFix4 = false;
	private boolean spinnerFix5 = false;
	
	@Override protected void onCreate(Bundle savedInstanceState)
		{
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);		
		setContentView(R.layout.editar);
		actividad = this;
		montoOrig = (TextView) findViewById(R.id.monto);
	 	minutos = (Spinner) findViewById(R.id.minutos);
	 	horas = (Spinner) findViewById(R.id.horas);
	 	dias = (Spinner) findViewById(R.id.dia);
	 	mess = (Spinner) findViewById(R.id.mes);
	 	anos = (Spinner) findViewById(R.id.ano);
	 	observacionesOrig = (TextView) findViewById(R.id.observacion);

        TextWatcher fieldValidatorTextWatcher = new TextWatcher()
			{
    		@Override public void afterTextChanged(Editable s)
				{
    			huboCambios = true;
				}
		
    		@Override public void beforeTextChanged(CharSequence s, int start, int count, int after)
				{
				}

    		@Override public void onTextChanged(CharSequence s, int start, int before, int count)
				{
				}
			};

		minutos.setOnItemSelectedListener(new OnItemSelectedListener()
			{
			public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3)
				{
				if (spinnerFix1==false)
					{
					spinnerFix1=true;
					}
					else
					{
					huboCambios = true;
					}
				}
		
			public void onNothingSelected(AdapterView<?> arg0)
				{
				}
			});
			
		horas.setOnItemSelectedListener(new OnItemSelectedListener()
			{
			public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3)
				{
				if (spinnerFix2==false)
					{
					spinnerFix2=true;
					}
					else
					{
					huboCambios = true;
					}
				}
		
			public void onNothingSelected(AdapterView<?> arg0)
				{
				}
			});
			
		dias.setOnItemSelectedListener(new OnItemSelectedListener()
			{
			public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3)
				{
				if (spinnerFix3==false)
					{
					spinnerFix3=true;
					}
					else
					{
					huboCambios = true;
					}
				}
		
			public void onNothingSelected(AdapterView<?> arg0)
				{
				}
			});
			
		mess.setOnItemSelectedListener(new OnItemSelectedListener()
			{
			public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3)
				{
				if (spinnerFix4==false)
					{
					spinnerFix4=true;
					}
					else
					{
					huboCambios = true;
					}
				}
		
			public void onNothingSelected(AdapterView<?> arg0)
				{
				}
			});
		
		anos.setOnItemSelectedListener(new OnItemSelectedListener()
			{
			public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3)
				{
				if (spinnerFix5==false)
					{
					spinnerFix5=true;
					}
					else
					{
					huboCambios = true;
					}
				}
		
			public void onNothingSelected(AdapterView<?> arg0)
				{
				}
			});

		cargarImporte();
		cargarObservaciones();
		seleccionarDia();
		seleccionarMes();
		seleccionarAno();
		seleccionarMinuto();
		seleccionarHora();
		
		montoOrig.addTextChangedListener(fieldValidatorTextWatcher);		
		observacionesOrig.addTextChangedListener(fieldValidatorTextWatcher);		
		}
	
	@Override public boolean onKeyUp(int keyCode, KeyEvent event)
		{
		try
			{
			if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0)
				{
				clickEnMenuSalir();
				return false;
				}
			}
			catch(NullPointerException e)
			{
			}
		return super.onKeyUp(keyCode, event);
		}

	public void clickEnMenuSalir()
		{
		if (huboCambios==true)
			{
			DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener()
				{
				public void onClick(DialogInterface dialog, int which)
					{
					switch (which)
						{
						case DialogInterface.BUTTON_POSITIVE:
						if (actualizarTodo()==true)
							{
							actividad.finish();
							}
						break;
		
						case DialogInterface.BUTTON_NEGATIVE:
						actividad.finish();
						break;
						}
					}
				};
				AlertDialog.Builder builder = new AlertDialog.Builder(this);
				builder.setMessage(R.string.textoPreguntaEditarAgregar).setPositiveButton(R.string.textoPreguntaSi, dialogClickListener).setNegativeButton(R.string.textoPreguntaNo, dialogClickListener).show();
			}
			else
			{
			this.finish();
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
	
	public void cargarImporte()
		{
		montoOrig.setText("" + GlobalVars.formatoNumerosEst.format(GlobalVars.devolverPrecio(GlobalVars.paraEditar)));
		}

	public void cargarObservaciones()
		{
		observacionesOrig.setText(String.valueOf(GlobalVars.devolverObservacion(GlobalVars.paraEditar)));
		}
		
	public void seleccionarDia()
		{
		dias.setSelection(Integer.valueOf(GlobalVars.devolverDia(GlobalVars.paraEditar)) - 1);
		}

	public void seleccionarMes()
		{
		mess.setSelection(Integer.valueOf(GlobalVars.devolverMes(GlobalVars.paraEditar)));
		}

	public void seleccionarAno()
		{
		Spinner anos = (Spinner) findViewById(R.id.ano);
		
		Calendar c = Calendar.getInstance(); 
		String year = (String) android.text.format.DateFormat.format("yyyy", c);
		
		List<String> list = new ArrayList<String>();
		for (int i=2016;i<=Integer.valueOf(year);i++)
			{
			list.add(String.valueOf(i));
			}
		ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, list);
		dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		anos.setAdapter(dataAdapter);

		for (int i=0;i<anos.getCount();i++)
			{
			if (anos.getItemAtPosition(i).toString().contains(GlobalVars.anoAct))
				{
				anos.setSelection(i);
				}
			}
		}

	public void seleccionarMinuto()
		{
		minutos.setSelection(Integer.valueOf(GlobalVars.devolverMinutos(GlobalVars.paraEditar)));
		}

	public void seleccionarHora()
		{
		horas.setSelection(Integer.valueOf(GlobalVars.devolverHoras(GlobalVars.paraEditar)));
		}

    public void myClickHandler(View view)
    	{
    	switch (view.getId())
			{
			case R.id.boton:
			actualizarTodo();
			break;
			}
    	}
    
    public boolean actualizarTodo()
    	{
    	TextView montoOrig = (TextView) findViewById(R.id.monto);
    	String monto = montoOrig.getText().toString();
    	String observaciones = observacionesOrig.getText().toString();
    	
    	if (monto.length()==0)
    		{
    		mensaje(getResources().getString(R.string.textoAgregarTodosLosCampos),getResources().getString(R.string.textoAgregarError),getResources().getString(R.string.textoAceptar));
        	return false;
    		}
    		else
    		{
    		if (monto.contains(".") && monto.length()<2)
    			{
        		mensaje(getResources().getString(R.string.textoAgregarPunto),getResources().getString(R.string.textoAgregarError),getResources().getString(R.string.textoAceptar));
            	return false;
    			}
    			else
    			{
        		try
    				{
        			if (Double.valueOf(monto)<=0)
    					{
        				mensaje(getResources().getString(R.string.textoAgregarNumeroPositivo),getResources().getString(R.string.textoAgregarError),getResources().getString(R.string.textoAceptar));
        				return false;
    					}
    					else
    					{
   	   	   	    		String dia = dias.getSelectedItem().toString();
   	   	   	    		String mes = mess.getSelectedItem().toString();
   	   	   	    		String ano = anos.getSelectedItem().toString();
   	   	   	    		if (mes==getResources().getString(R.string.textoEnero)){mes="01";}
   	   	   	    		else if (mes==getResources().getString(R.string.textoFebrero)){mes="02";}
   	   	   	    		else if (mes==getResources().getString(R.string.textoMarzo)){mes="03";}
   	   	   	    		else if (mes==getResources().getString(R.string.textoAbril)){mes="04";}
   	   	   	    		else if (mes==getResources().getString(R.string.textoMayo)){mes="05";}
   	   	   	    		else if (mes==getResources().getString(R.string.textoJunio)){mes="06";}
   	   	   	    		else if (mes==getResources().getString(R.string.textoJulio)){mes="07";}
   	   	   	    		else if (mes==getResources().getString(R.string.textoAgosto)){mes="08";}
   	   	   	    		else if (mes==getResources().getString(R.string.textoSeptiembre)){mes="09";}
   	   	   	    		else if (mes==getResources().getString(R.string.textoOctubre)){mes="10";}
   	   	   	    		else if (mes==getResources().getString(R.string.textoNoviembre)){mes="11";}
   	   	   	    		else if (mes==getResources().getString(R.string.textoDiciembre)){mes="12";}
   	   	   	    		if (GlobalVars.isValidDate(mes + "/" + dia + "/" + ano)==false)
   	   	   	    			{
   	   	   	    			mensaje(getResources().getString(R.string.textoAgregarFecha),getResources().getString(R.string.textoAgregarError),getResources().getString(R.string.textoAceptar));
   	   	   	    			return false;
   	   	   	    			}
   	   	   	    			else
   	   	   	    			{
   	   	   	    			String minuto = minutos.getSelectedItem().toString();
   	   	   	    			String hora = horas.getSelectedItem().toString();
   	   	   	    			new ThreadEditar(ano + "-" + mes + "-" + dia + "|" +
   	    	   	   	    				  	 hora + "|" +
   	    	   	   	    				  	 minuto + "|" +
   	    	   	   	    				  	 monto + "|" +
   	    	   	   	    				  	 observaciones,this).execute();
   	   	   	    			}
    					}
    				}
        			catch(Exception e)
        			{
            		mensaje(getResources().getString(R.string.textoNumeroNoValido),getResources().getString(R.string.textoAgregarError),getResources().getString(R.string.textoAceptar));
            		return false;
        			}
    			}
    		}
    	return true;
    	}
	}