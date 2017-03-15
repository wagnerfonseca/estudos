package com.curso.brewer.config.format;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;

import org.springframework.format.Formatter;

public class BigDecimalFormatter implements Formatter<BigDecimal> {
	
	private DecimalFormat decimalFormat;

	public BigDecimalFormatter(String pattern) {
		NumberFormat format =  NumberFormat.getInstance( new Locale("pt", "BR") );
		decimalFormat = (DecimalFormat) format;
		decimalFormat.setParseBigDecimal(true);
		decimalFormat.applyPattern(pattern); // aplica o formato
	}

	/* Quando o Spring quer mostrar algo na tela */
	@Override
	public String print(BigDecimal out, Locale locale) {
		return decimalFormat.format(out);
	}

	/* recebe os dados para converter */
	@Override
	public BigDecimal parse(String in, Locale locale) throws ParseException {
		// Recebe: 10.564,03
		// Saida: 10564.03
		return (BigDecimal) decimalFormat.parse(in);
	}

}
