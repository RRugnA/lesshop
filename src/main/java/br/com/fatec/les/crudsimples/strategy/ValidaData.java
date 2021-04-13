package br.com.fatec.les.crudsimples.strategy;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class ValidaData {

	public static LocalDate toDate(String data) {
		DateTimeFormatter f = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDate ld = LocalDate.parse(data, f);
		return ld;
	}
}		