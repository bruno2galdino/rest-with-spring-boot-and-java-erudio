package br.com.erudio.controller;

import java.util.concurrent.atomic.AtomicLong;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.erudio.conversores.ConvertNumber;
import br.com.erudio.exceptions.UnsupportedMathOperationException;
import br.com.erudio.math.SimpleMath;

@RestController
public class MathController {

	private final AtomicLong counter = new AtomicLong();

	private SimpleMath math = new SimpleMath();
	
	@RequestMapping(value = "/sum/{numberOne}/{numberTwo}", 
		method=RequestMethod.GET)
	public Double sum(
			@PathVariable(value = "numberOne") String numberOne,
			@PathVariable(value = "numberTwo") String numberTwo
			) throws Exception{
		
		if(!ConvertNumber.isNumeric(numberOne) || !ConvertNumber.isNumeric(numberTwo)) {
			throw new UnsupportedMathOperationException("Please set a numeric value!");
		}

		return math.sum(ConvertNumber.convertToDouble(numberOne),ConvertNumber.convertToDouble(numberTwo));
	}
	
	@RequestMapping(value = "/sub/{numberOne}/{numberTwo}", 
		method=RequestMethod.GET)
	public Double sub(
			@PathVariable(value = "numberOne") String numberOne,
			@PathVariable(value = "numberTwo") String numberTwo
			) throws Exception{
		
		if(!ConvertNumber.isNumeric(numberOne) || !ConvertNumber.isNumeric(numberTwo)) {
			throw new UnsupportedMathOperationException("Please set a numeric value!");
		}

		return math.sub(ConvertNumber.convertToDouble(numberOne),ConvertNumber.convertToDouble(numberTwo));
	}

	@RequestMapping(value = "/multi/{numberOne}/{numberTwo}", 
		method=RequestMethod.GET)
	public Double multi(
			@PathVariable(value = "numberOne") String numberOne,
			@PathVariable(value = "numberTwo") String numberTwo
			) throws Exception{
		
		if(!ConvertNumber.isNumeric(numberOne) || !ConvertNumber.isNumeric(numberTwo)) {
			throw new UnsupportedMathOperationException("Please set a numeric value!");
		}

		return math.multi(ConvertNumber.convertToDouble(numberOne),ConvertNumber.convertToDouble(numberTwo));
	}

	@RequestMapping(value = "/div/{numberOne}/{numberTwo}", 
		method=RequestMethod.GET)
	public Double div(
			@PathVariable(value = "numberOne") String numberOne,
			@PathVariable(value = "numberTwo") String numberTwo
			) throws Exception{
		
		if(!ConvertNumber.isNumeric(numberOne) || !ConvertNumber.isNumeric(numberTwo)) {
			throw new UnsupportedMathOperationException("Please set a numeric value!");
		}

		return math.div(ConvertNumber.convertToDouble(numberOne),ConvertNumber.convertToDouble(numberTwo));
	}

	@RequestMapping(value = "/med/{numberOne}/{numberTwo}", 
		method=RequestMethod.GET)
	public Double med(
			@PathVariable(value = "numberOne") String numberOne,
			@PathVariable(value = "numberTwo") String numberTwo
			) throws Exception{
		
		if(!ConvertNumber.isNumeric(numberOne) || !ConvertNumber.isNumeric(numberTwo)) {
			throw new UnsupportedMathOperationException("Please set a numeric value!");
		}

		return math.med(ConvertNumber.convertToDouble(numberOne),ConvertNumber.convertToDouble(numberTwo));
	}
	
	@RequestMapping(value = "/qua/{numberOne}/{numberTwo}", 
		method=RequestMethod.GET)
	public Double qua(
			@PathVariable(value = "numberOne") String numberOne,
			@PathVariable(value = "numberTwo") String numberTwo
			) throws Exception{
		
		if(!ConvertNumber.isNumeric(numberOne) || !ConvertNumber.isNumeric(numberTwo)) {
			throw new UnsupportedMathOperationException("Please set a numeric value!");
		}

		return math.qua(ConvertNumber.convertToDouble(numberOne), ConvertNumber.convertToDouble(numberTwo));
	}
}
