package com.sinensia.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.processing.Generated;

@SpringBootApplication
@RestController
public class DemoProjectApplication {

	@Generated(value="org.springframework.boot")
	public static void main(String[] args) {
		SpringApplication.run(DemoProjectApplication.class, args);
	}

	@GetMapping("/")
	public String MetodoRaiz(@RequestParam(value = "nombreEmpresa", defaultValue = "GFT") String nombreEmpresa) {
		return String.format("Bienvenido a la pagina de %s!", nombreEmpresa);
	}

	@GetMapping("/hello")
	public String hello(@RequestParam(value = "name", defaultValue = "World") String name) {
		return String.format("Hello %s!", name);
	}


	@GetMapping("/add")
	public Object canAdd(@RequestParam (value="a", defaultValue = "0") Float a, @RequestParam (value="b", defaultValue = "0") Float b) {
		Float sum = a+b;
		Float decimals = sum - sum.intValue();
		if(decimals!=0){
			return sum;
		}
		return sum.intValue();
	}

	@GetMapping("/multiply")
	public Object multiply(@RequestParam (value="a", defaultValue = "0") Float a, @RequestParam (value="b", defaultValue = "0") Float b) {
		Float product = a * b;
		Float decimals = product - product.intValue();
		if(decimals!=0){
			return product;
		}
		return product.intValue();
	}

	@GetMapping("/div")
	public Object dividir(@RequestParam (value="a", defaultValue = "0") Float a, @RequestParam (value="b", defaultValue = "0") Float b) {
		Float product = a / b;
		Float decimals = product - product.intValue();
		if(decimals!=0){
			return product;
		}
		return product.intValue();
	}

	@GetMapping("/subtract")
	public Object subtract(@RequestParam (value="a", defaultValue = "0") Float a, @RequestParam (value="b", defaultValue = "0") Float b) {
		Float product = a - b;
		Float decimals = product - product.intValue();
		if(decimals!=0){
			return product;
		}
		return product.intValue();
	}

	//raiz cuadrada
	@GetMapping("/sqrt")
	public Object sqrt(@RequestParam (value="a", defaultValue = "0") Float a) {
		Float product = (float) Math.sqrt(a);
		Float decimals = product - product.intValue();
		if(decimals!=0){
			return product;
		}
		return product.intValue();
	}

	/*@GetMapping("/sqrt")
	public Float sqrt2(Float a){
		Float parseA = (float) Math.sqrt(a);
		return parseA;
	}*/

}
