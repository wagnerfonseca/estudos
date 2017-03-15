package com.curso.brewer.etc;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParsePosition;
import java.util.Locale;

/** Um Exemplo de conversão de um bigDecimal */
public class TesteBigDecimalParseConverter {
	
	public static void main(String[] args) {
		String str = "10.657,03";
	    Locale pt_BR = new Locale("pt","BR");       

	    NumberFormat format =  NumberFormat.getInstance( pt_BR );
	    DecimalFormat decimalFormat = (DecimalFormat) format;
		decimalFormat.setParseBigDecimal(true);
		decimalFormat.applyPattern("#,##0.00"); // aplica o formato

	    //DecimalFormat nf = (DecimalFormat) NumberFormat.getInstance(pt_BR);       

		// Converte um BigDecimal em String
		System.out.println("String para BigDecimal" + decimalFormat.format(new BigDecimal("10258.67")));

		// String em BigDecimal
	    BigDecimal bd = (BigDecimal) decimalFormat.parse(str, new ParsePosition(0));

	    System.out.println("BigDecimal para String: " + bd);
	}

}
