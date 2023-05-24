package ar.com.lrusso.taxicalculator;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import android.app.Application;
import android.content.Context;

public class GlobalVars extends Application
	{
	public static String paraEditar = null;
	public static int    paraEditarPosition = -1;
	public static String anoAct = "2014";
	public static String mesAct = "01";
	public static String moneda = "";
	public static boolean actualizar = false;
	public static List<String> listado = new ArrayList<String>();
	public static List<String> depurada = new ArrayList<String>();
	public static DecimalFormat formatoNumeros = new DecimalFormat("###,###,##0.00");
	public static DecimalFormat formatoNumerosEst = new DecimalFormat("00.00");
	public static double hoy;
	public static double enElMes;
	public static Context contexto;
	
	public static String devolverNombre(String valor)
		{
		try
			{
			String nombre1 = valor.substring(valor.indexOf("|") + 1,valor.lastIndexOf("|"));
			return nombre1.substring(nombre1.indexOf("|") + 1, nombre1.length());
			}
		catch(Exception e)
			{
			return "";
			}
		}
	
	public static String devolverDia(String valor)
		{
		try
			{
			String valor1 = valor.substring(0,valor.indexOf("|"));
			DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd"); 
			Date date = formatter.parse(valor1);

			Calendar c = Calendar.getInstance();
			c.setTime(date);
			int dayOfMonth = c.get(Calendar.DAY_OF_MONTH);
			return String.valueOf(dayOfMonth);
			}
			catch(Exception e)
			{
			}
		return "01";
		}

	public static int devolverDiaChart(String valor)
		{
		try
			{
			String valor1 = valor.substring(0,valor.indexOf("|"));
			DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd"); 
			Date date = formatter.parse(valor1);

			Calendar c = Calendar.getInstance();
			c.setTime(date);
			int dayOfMonth = c.get(Calendar.DAY_OF_MONTH);
			return dayOfMonth;
			}
			catch(Exception e)
			{
			}
		return 01;
		}

	public static String devolverMes(String valor)
		{
		try
			{
			String valor1 = valor.substring(0,valor.indexOf("|"));
			DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd"); 
			Date date = formatter.parse(valor1);

			Calendar c = Calendar.getInstance();
			c.setTime(date);
			int month = c.get(Calendar.MONTH);
			return String.valueOf(month);
			}
			catch(Exception e)
			{
			}
		return "01";
		}
	
	public static String devolverDiaMes(String valor)
		{
		try
			{
			String valor1 = valor.substring(0,valor.indexOf("|"));
			DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd"); 
			Date date = formatter.parse(valor1);

			Calendar c = Calendar.getInstance();
			c.setTime(date);
			int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);
			int dayOfMonth = c.get(Calendar.DAY_OF_MONTH);
			if (dayOfWeek==1)
				{
				return contexto.getResources().getString(R.string.textoDomingo) + "   " + String.valueOf(dayOfMonth);
				}
			else if (dayOfWeek==2)
				{
				return contexto.getResources().getString(R.string.textoLunes) + "   " + String.valueOf(dayOfMonth);
				}
			else if (dayOfWeek==3)
				{
				return contexto.getResources().getString(R.string.textoMartes) + "   " + String.valueOf(dayOfMonth);
				}
			else if (dayOfWeek==4)
				{
				return contexto.getResources().getString(R.string.textoMiercoles) + "   " + String.valueOf(dayOfMonth);
				}
			else if (dayOfWeek==5)
				{
				return contexto.getResources().getString(R.string.textoJueves) + "   " + String.valueOf(dayOfMonth);
				}
			else if (dayOfWeek==6)
				{
				return contexto.getResources().getString(R.string.textoViernes) + "   " + String.valueOf(dayOfMonth);
				}
			else if (dayOfWeek==7)
				{
				return contexto.getResources().getString(R.string.textoSabado) + "   " + String.valueOf(dayOfMonth);
				}
			}
			catch(Exception e)
			{
			}
		return "00-00";
		}

	public static String devolverDiaMesPregEliminar(String valor)
		{
		try
			{
			String valor1 = valor.substring(0,valor.indexOf("|"));
			DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd"); 
			Date date = formatter.parse(valor1);

			Calendar c = Calendar.getInstance();
			c.setTime(date);
			int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);
			int dayOfMonth = c.get(Calendar.DAY_OF_MONTH);
			if (dayOfWeek==1)
				{
				return contexto.getResources().getString(R.string.textoDomingo) + " " + String.valueOf(dayOfMonth);
				}
			else if (dayOfWeek==2)
				{
				return contexto.getResources().getString(R.string.textoLunes) + " " + String.valueOf(dayOfMonth);
				}
			else if (dayOfWeek==3)
				{
				return contexto.getResources().getString(R.string.textoMartes) + " " + String.valueOf(dayOfMonth);
				}
			else if (dayOfWeek==4)
				{
				return contexto.getResources().getString(R.string.textoMiercoles) + " " + String.valueOf(dayOfMonth);
				}
			else if (dayOfWeek==5)
				{
				return contexto.getResources().getString(R.string.textoJueves) + " " + String.valueOf(dayOfMonth);
				}
			else if (dayOfWeek==6)
				{
				return contexto.getResources().getString(R.string.textoViernes) + " " + String.valueOf(dayOfMonth);
				}
			else if (dayOfWeek==7)
				{
				return contexto.getResources().getString(R.string.textoSabado) + " " + String.valueOf(dayOfMonth);
				}
			}
			catch(Exception e)
			{
			}
		return "00-00";
		}

	public static String devolverHoras(String valor)
		{
		try
			{
			String valor1 = valor.substring(valor.indexOf("|") + 1,valor.lastIndexOf("|"));
			String valor2 = valor1.substring(0,valor1.indexOf("|"));
			return valor2;
			}
			catch(Exception e)
			{
			}
		return "00";
		}

	public static int devolverHorasChart(String valor)
		{
		try
			{
			String valor1 = valor.substring(valor.indexOf("|") + 1,valor.lastIndexOf("|"));
			String valor2 = valor1.substring(0,valor1.indexOf("|"));
			return Integer.valueOf(valor2);
			}
			catch(Exception e)
			{
			}
		return 0;
		}

	public static String devolverMinutos(String valor)
		{
		try
			{
			String valor1 = valor.substring(valor.indexOf("|") + 1,valor.lastIndexOf("|"));
			String valor2 = valor1.substring(valor1.indexOf("|") + 1,valor1.lastIndexOf("|"));
			return valor2;
			}
			catch(Exception e)
			{
			}
		return "00";
		}
	
	public static double devolverPrecio(String valor)
		{
		try
			{
			String valor1 = valor.substring(valor.indexOf("|") + 1,valor.lastIndexOf("|") + 1);
			String valor2 = valor1.substring(valor1.indexOf("|") + 1,valor1.lastIndexOf("|") + 1);
			String valor3 = valor2.substring(valor2.indexOf("|") + 1,valor2.lastIndexOf("|"));
			return Double.valueOf(valor3);
			}
			catch(Exception e)
			{
			return 0.00;
			}
		}
	
	public static String devolverPrecioEliminar(String valor)
		{
		try
			{
			String valor1 = valor.substring(valor.indexOf("|") + 1,valor.lastIndexOf("|") + 1);
			String valor2 = valor1.substring(valor1.indexOf("|") + 1,valor1.lastIndexOf("|") + 1);
			String valor3 = valor2.substring(valor2.indexOf("|") + 1,valor2.lastIndexOf("|"));
			return formatoNumeros.format(Double.valueOf(valor3));
			}
			catch(Exception e)
			{
			return "0.00";
			}
		}

	public static double devolverPrecioChart(String valor)
		{
		try
			{
			String valor1 = valor.substring(valor.indexOf("|") + 1,valor.lastIndexOf("|") + 1);
			String valor2 = valor1.substring(valor1.indexOf("|") + 1,valor1.lastIndexOf("|") + 1);
			String valor3 = valor2.substring(valor2.indexOf("|") + 1,valor2.lastIndexOf("|"));
			return Double.valueOf(valor3);
			}
			catch(Exception e)
			{
			return 0.0;
			}
		}

	public static String devolverObservacion(String valor)
		{
		try
			{
			String valor1 = valor.substring(valor.lastIndexOf("|") + 1,valor.length());
			return valor1;
			}
			catch(Exception e)
			{
			}
		return "--";
		}
	
	public static String devolverPosicion(int position)
		{
		int contador = -1;
		String resultado = null;
		for(int i=0;i<GlobalVars.depurada.size();i++)
			{
			String valor = GlobalVars.depurada.get(i);
			if (valor.length()>4)
				{
				contador++;
				if (contador==position)
					{
					resultado = valor;
					}
				}
			}
		return resultado;
		}
	
	public static boolean isValidDate(String input)
		{
        String formatString = "MM/dd/yyyy";

        try
        	{
            SimpleDateFormat format = new SimpleDateFormat(formatString);
            format.setLenient(false);
            format.parse(input);
        	}
        	catch (ParseException e)
        	{
            return false;
        	}
        	catch (IllegalArgumentException e)
        	{
            return false;
        	}

        return true;
    	}
	}