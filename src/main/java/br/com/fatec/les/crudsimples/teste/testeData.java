package br.com.fatec.les.crudsimples.teste;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.sql.Date;

import br.com.fatec.les.crudsimples.strategy.ValidaData;

public class testeData {

	public static void main(String[] args) throws ParseException {
		String data = "21-02-2021";
		
		DateTimeFormatter f = DateTimeFormatter.ofPattern("dd-MM-yyyy");
		LocalDate ld = LocalDate.parse(data, f);
		System.out.println(ld);
	}

}
