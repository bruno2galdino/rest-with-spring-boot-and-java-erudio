package br.com.erudio.math;

public class SimpleMath {

	public Double sum(Double numberOne,Double numberTwo){
		return numberOne + numberTwo;
	}
	
	public Double sub(Double numberOne,Double numberTwo){
		return numberOne - numberTwo;
	}

	public Double multi(Double numberOne, Double numberTwo){
		return numberOne * numberTwo;
	}

	public Double div(Double numberOne, Double numberTwo){
		return numberOne / numberTwo;
	}

	public Double med(Double numberOne,Double numberTwo){
        Double soma = (numberOne + numberTwo);
		return soma / 2;
	}

	public Double qua(Double numberOne,Double numberTwo){
		return Math.sqrt(numberOne + numberTwo);
	}
}
