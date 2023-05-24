package ar.com.lrusso.taxicalculator;

import java.text.DecimalFormat;

import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.formatter.IValueFormatter;
import com.github.mikephil.charting.utils.ViewPortHandler;

public class MyValueFormatter implements IValueFormatter
	{
    private DecimalFormat mFormat;

    public MyValueFormatter()
    	{
        mFormat = new DecimalFormat("###,###,##0.00");
    	}

	@Override public String getFormattedValue(float value, Entry entry, int dataSetIndex, ViewPortHandler viewPortHandler)
		{
        return GlobalVars.moneda + mFormat.format(value);
		}
	}