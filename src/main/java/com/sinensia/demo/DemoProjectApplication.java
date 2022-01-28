package com.sinensia.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class DemoProjectApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoProjectApplication.class, args);
	}

	@GetMapping("/hello")
	public String hello(@RequestParam(value = "name", defaultValue = "World") String name) {
		return String.format("Hello %s!", name);
	}

	@GetMapping("/")
	public String MetodoRaiz(@RequestParam(value = "nombreEmpresa", defaultValue = "GFT") String nombreEmpresa) {
		return String.format("Bienvenido a la pagina de %s!", nombreEmpresa);
	}

	@GetMapping("/add")
	public int canAdd(@RequestParam int a,@RequestParam int b) {
		return a + b;
	}

}
