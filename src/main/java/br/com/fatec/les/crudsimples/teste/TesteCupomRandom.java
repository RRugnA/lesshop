package br.com.fatec.les.crudsimples.teste;

import java.nio.charset.Charset;
import java.util.Random;

public class TesteCupomRandom {

	public static void main(String[] args) {
		int leftLimit = 97; // letter 'a'
	    int rightLimit = 122; // letter 'z'
	    int targetStringLength = 3;
	    Random random = new Random();

	    String generatedString = random.ints(leftLimit, rightLimit + 1)
	      .limit(targetStringLength)
	      .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
	      .toString();

	    String cupom = generatedString + "2021";
	    System.out.println(cupom);
	}

}
