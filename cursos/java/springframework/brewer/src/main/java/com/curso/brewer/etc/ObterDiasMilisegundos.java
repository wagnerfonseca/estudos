package com.curso.brewer.etc;

import java.time.LocalDate;
import java.time.Month;

public class ObterDiasMilisegundos {

	

	public static void main(String[] args) {
		LocalDate now = LocalDate.of(2017, Month.APRIL, 12); 
		System.out.println(now.toEpochDay() * 24 *60 *60);
	}

}
