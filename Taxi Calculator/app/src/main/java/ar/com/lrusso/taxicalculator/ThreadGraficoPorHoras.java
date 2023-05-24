package ar.com.lrusso.taxicalculator;

import java.text.DecimalFormat;
import java.util.ArrayList;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.components.Legend.LegendForm;
import com.github.mikephil.charting.components.YAxis.AxisDependency;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.AsyncTask;

public class ThreadGraficoPorHoras extends AsyncTask<String, Void, Bitmap>
	{
	ProgressDialog myDialog;
    Activity actividad;
	LineChart mChart;
	double valor00 = 0;
	double valor01 = 0;
	double valor02 = 0;
	double valor03 = 0;
	double valor04 = 0;
	double valor05 = 0;
	double valor06 = 0;
	double valor07 = 0;
	double valor08 = 0;
	double valor09 = 0;
	double valor10 = 0;
	double valor11 = 0;
	double valor12 = 0;
	double valor13 = 0;
	double valor14 = 0;
	double valor15 = 0;
	double valor16 = 0;
	double valor17 = 0;
	double valor18 = 0;
	double valor19 = 0;
	double valor20 = 0;
	double valor21 = 0;
	double valor22 = 0;
	double valor23 = 0;
    
    public ThreadGraficoPorHoras(LineChart a, Activity b)
    	{
    	mChart = a;
    	actividad = b;
    	}
    
    @Override protected void onPreExecute()
    	{
    	super.onPreExecute();
    	myDialog = new ProgressDialog(actividad);
    	myDialog.setMessage(actividad.getResources().getString(R.string.textoProcesandoDatos));
        myDialog.setCancelable(false);
        myDialog.show();
    	}
    
    public void onPostExecute(Bitmap result)
    	{
        myDialog.dismiss();
        try
        	{
            mChart.animateX(2500);
            Legend l = mChart.getLegend();
            l.setForm(LegendForm.LINE);
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
	        mChart.setDrawGridBackground(false);
	        mChart.getDescription().setEnabled(false);
	        mChart.setTouchEnabled(false);
	        mChart.setDragEnabled(false);
	        mChart.setScaleEnabled(false);
	        mChart.setPinchZoom(false);
	        mChart.getLegend().setEnabled(false);
	        mChart.getAxisLeft().setDrawGridLines(false);
	        mChart.getXAxis().setDrawGridLines(false);

	        XAxis xAxis = mChart.getXAxis();
	        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
	        xAxis.setLabelCount(24);
	        
	        YAxis leftAxis = mChart.getAxisLeft();
	        leftAxis.removeAllLimitLines();
	        leftAxis.setDrawZeroLine(false);
	        leftAxis.setDrawLimitLinesBehindData(true);
	        leftAxis.setAxisMinimum(0);
	        IAxisValueFormatter formatter = new IAxisValueFormatter()
	        	{
	            @Override public String getFormattedValue(float value, AxisBase axis)
	            	{
	            	DecimalFormat df = new DecimalFormat();
	            	df.setMaximumFractionDigits(2);
	            	return GlobalVars.moneda + df.format(value);
	            	}
	        	};
			leftAxis.setValueFormatter(formatter);
	        mChart.getAxisRight().setEnabled(false);
	        setData();		
			}
			catch(NullPointerException e)
			{
			}
			catch(Exception e)
			{
			}
        return null;
    	}
	
    private void setData()
		{
    	try
    		{
        	for (int i=0;i<GlobalVars.listado.size();i++)
    			{
        		String valor = GlobalVars.listado.get(i);
        		if (valor.startsWith(GlobalVars.anoAct + "-" + GlobalVars.mesAct + "-"))
    				{
        			if (GlobalVars.devolverHorasChart(valor)==0){valor00 = valor00 + GlobalVars.devolverPrecioChart(valor);}
        			else if (GlobalVars.devolverHorasChart(valor)==1){valor01 = valor01 + GlobalVars.devolverPrecioChart(valor);}
        			else if (GlobalVars.devolverHorasChart(valor)==2){valor02 = valor02 + GlobalVars.devolverPrecioChart(valor);}
        			else if (GlobalVars.devolverHorasChart(valor)==3){valor03 = valor03 + GlobalVars.devolverPrecioChart(valor);}
        			else if (GlobalVars.devolverHorasChart(valor)==4){valor04 = valor04 + GlobalVars.devolverPrecioChart(valor);}
        			else if (GlobalVars.devolverHorasChart(valor)==5){valor05 = valor05 + GlobalVars.devolverPrecioChart(valor);}
        			else if (GlobalVars.devolverHorasChart(valor)==6){valor06 = valor06 + GlobalVars.devolverPrecioChart(valor);}
        			else if (GlobalVars.devolverHorasChart(valor)==7){valor07 = valor07 + GlobalVars.devolverPrecioChart(valor);}
        			else if (GlobalVars.devolverHorasChart(valor)==8){valor08 = valor08 + GlobalVars.devolverPrecioChart(valor);}
        			else if (GlobalVars.devolverHorasChart(valor)==9){valor09 = valor09 + GlobalVars.devolverPrecioChart(valor);}
        			else if (GlobalVars.devolverHorasChart(valor)==10){valor10 = valor10 + GlobalVars.devolverPrecioChart(valor);}
        			else if (GlobalVars.devolverHorasChart(valor)==11){valor11 = valor11 + GlobalVars.devolverPrecioChart(valor);}
        			else if (GlobalVars.devolverHorasChart(valor)==12){valor12 = valor12 + GlobalVars.devolverPrecioChart(valor);}
        			else if (GlobalVars.devolverHorasChart(valor)==13){valor13 = valor13 + GlobalVars.devolverPrecioChart(valor);}
        			else if (GlobalVars.devolverHorasChart(valor)==14){valor14 = valor14 + GlobalVars.devolverPrecioChart(valor);}
        			else if (GlobalVars.devolverHorasChart(valor)==15){valor15 = valor15 + GlobalVars.devolverPrecioChart(valor);}
        			else if (GlobalVars.devolverHorasChart(valor)==16){valor16 = valor16 + GlobalVars.devolverPrecioChart(valor);}
        			else if (GlobalVars.devolverHorasChart(valor)==17){valor17 = valor17 + GlobalVars.devolverPrecioChart(valor);}
        			else if (GlobalVars.devolverHorasChart(valor)==18){valor18 = valor18 + GlobalVars.devolverPrecioChart(valor);}
        			else if (GlobalVars.devolverHorasChart(valor)==19){valor19 = valor19 + GlobalVars.devolverPrecioChart(valor);}
        			else if (GlobalVars.devolverHorasChart(valor)==20){valor20 = valor20 + GlobalVars.devolverPrecioChart(valor);}
        			else if (GlobalVars.devolverHorasChart(valor)==21){valor21 = valor21 + GlobalVars.devolverPrecioChart(valor);}
        			else if (GlobalVars.devolverHorasChart(valor)==22){valor22 = valor22 + GlobalVars.devolverPrecioChart(valor);}
        			else if (GlobalVars.devolverHorasChart(valor)==23){valor23 = valor23 + GlobalVars.devolverPrecioChart(valor);}
    				}
    			}
    	
        	LineDataSet set1;
        	ArrayList<Entry> yVals1 = new ArrayList<Entry>();

        	yVals1.add(new Entry(0, (float)valor00));
        	yVals1.add(new Entry(1, (float)valor01));
        	yVals1.add(new Entry(2, (float)valor02));
        	yVals1.add(new Entry(3, (float)valor03));
        	yVals1.add(new Entry(4, (float)valor04));
        	yVals1.add(new Entry(5, (float)valor05));
        	yVals1.add(new Entry(6, (float)valor06));
        	yVals1.add(new Entry(7, (float)valor07));
        	yVals1.add(new Entry(8, (float)valor08));
        	yVals1.add(new Entry(9, (float)valor09));
        	yVals1.add(new Entry(10, (float)valor10));
        	yVals1.add(new Entry(11, (float)valor11));
        	yVals1.add(new Entry(12, (float)valor12));
        	yVals1.add(new Entry(13, (float)valor13));
        	yVals1.add(new Entry(14, (float)valor14));
        	yVals1.add(new Entry(15, (float)valor15));
        	yVals1.add(new Entry(16, (float)valor16));
        	yVals1.add(new Entry(17, (float)valor17));
        	yVals1.add(new Entry(18, (float)valor18));
        	yVals1.add(new Entry(19, (float)valor19));
        	yVals1.add(new Entry(20, (float)valor20));
        	yVals1.add(new Entry(21, (float)valor21));
        	yVals1.add(new Entry(22, (float)valor22));
        	yVals1.add(new Entry(23, (float)valor23));
        	
        	if (mChart.getData() != null && mChart.getData().getDataSetCount() > 0)
        		{
        		set1 = (LineDataSet) mChart.getData().getDataSetByIndex(0);
        		set1.setValues(yVals1);
        		mChart.getData().notifyDataChanged();
        		mChart.notifyDataSetChanged();
        		}
        		else
        		{
        		set1 = new LineDataSet(yVals1, "DataSet 1");
        		set1.setAxisDependency(AxisDependency.LEFT);
        		set1.setColor(ColorTemplate.getHoloBlue());
        		set1.setCircleColor(ColorTemplate.getHoloBlue());
        		set1.setLineWidth(2f);
        		set1.setCircleRadius(3f);
        		set1.setFillAlpha(65);
        		set1.setFillColor(ColorTemplate.getHoloBlue());
        		set1.setHighLightColor(Color.rgb(244, 117, 117));
        		set1.setDrawCircleHole(false);
            	/*
        		IValueFormatter formatter = new IValueFormatter()
            		{
            		@Override
            		public String getFormattedValue(float value, Entry entry, int dataSetIndex, ViewPortHandler viewPortHandler)
            			{
            			return GlobalVars.moneda + value;
            			}
            		};
            	set1.setValueFormatter(formatter);
            	*/
            	set1.setValueFormatter(new MyValueFormatter());

        		LineData data = new LineData(set1);
        		data.setValueFormatter(new MyValueFormatter());
        		data.setValueTextColor(Color.BLACK);
        		data.setValueTextSize(9f);
        		mChart.setData(data);
        		}
    		}
    		catch(NullPointerException e)
    		{
    		}
    		catch(Exception e)
    		{
    		}
		}
	}