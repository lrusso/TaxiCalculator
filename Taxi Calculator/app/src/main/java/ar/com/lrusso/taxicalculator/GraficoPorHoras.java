package ar.com.lrusso.taxicalculator;

import android.os.Bundle;
import android.widget.TextView;
import android.app.Activity;

import com.github.mikephil.charting.charts.LineChart;

public class GraficoPorHoras extends Activity 
	{
	private LineChart mChart;

	@Override protected void onCreate(Bundle savedInstanceState)
		{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.graficoporhoras);
        cargarNuevoTitulo();
        mChart = (LineChart) findViewById(R.id.chart1);
        new ThreadGraficoPorHoras(mChart,this).execute();
		}
	
    public void cargarNuevoTitulo()
		{
    	int mes =  Integer.valueOf(GlobalVars.mesAct);
    	TextView titulo = (TextView) findViewById(R.id.titulo2);
    	if (mes==1){titulo.setText(getResources().getString(R.string.textoGraficoHoras) + " - " +  GlobalVars.anoAct + " - " + getResources().getString(R.string.textoEnero));}
    	else if (mes==2){titulo.setText(getResources().getString(R.string.textoGraficoHoras) + " - " +  GlobalVars.anoAct + " - " + getResources().getString(R.string.textoFebrero));}
    	else if (mes==3){titulo.setText(getResources().getString(R.string.textoGraficoHoras) + " - " +  GlobalVars.anoAct + " - " + getResources().getString(R.string.textoMarzo));}
    	else if (mes==4){titulo.setText(getResources().getString(R.string.textoGraficoHoras) + " - " +  GlobalVars.anoAct + " - " + getResources().getString(R.string.textoAbril));}
    	else if (mes==5){titulo.setText(getResources().getString(R.string.textoGraficoHoras) + " - " +  GlobalVars.anoAct + " - " + getResources().getString(R.string.textoMayo));}
    	else if (mes==6){titulo.setText(getResources().getString(R.string.textoGraficoHoras) + " - " +  GlobalVars.anoAct + " - " + getResources().getString(R.string.textoJunio));}
    	else if (mes==7){titulo.setText(getResources().getString(R.string.textoGraficoHoras) + " - " +  GlobalVars.anoAct + " - " + getResources().getString(R.string.textoJulio));}
    	else if (mes==8){titulo.setText(getResources().getString(R.string.textoGraficoHoras) + " - " +  GlobalVars.anoAct + " - " + getResources().getString(R.string.textoAgosto));}
    	else if (mes==9){titulo.setText(getResources().getString(R.string.textoGraficoHoras) + " - " +  GlobalVars.anoAct + " - " + getResources().getString(R.string.textoSeptiembre));}
    	else if (mes==10){titulo.setText(getResources().getString(R.string.textoGraficoHoras) + " - " +  GlobalVars.anoAct + " - " + getResources().getString(R.string.textoOctubre));}
    	else if (mes==11){titulo.setText(getResources().getString(R.string.textoGraficoHoras) + " - " +  GlobalVars.anoAct + " - " + getResources().getString(R.string.textoNoviembre));}
    	else if (mes==12){titulo.setText(getResources().getString(R.string.textoGraficoHoras) + " - " +  GlobalVars.anoAct + " - " + getResources().getString(R.string.textoDiciembre));}
		}
	}