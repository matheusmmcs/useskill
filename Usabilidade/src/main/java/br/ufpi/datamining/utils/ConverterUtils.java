package br.ufpi.datamining.utils;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;

public class ConverterUtils {
	
	public static BigDecimal stringToBigDecimal(String str) throws ParseException{
		DecimalFormatSymbols symbols = new DecimalFormatSymbols();
		symbols.setGroupingSeparator('.');
		symbols.setDecimalSeparator(',');
		String pattern = "#.##0,0#";
		DecimalFormat decimalFormat = new DecimalFormat(pattern, symbols);
		decimalFormat.setParseBigDecimal(true);

		BigDecimal bigDecimal = (BigDecimal) decimalFormat.parse(str);
		return bigDecimal;
	}
	
	public static double notNaN(Double d) {
		return d.equals(Double.NaN) || Double.isInfinite(d) ? 0d : d;
	}
	
}
