package com.sinensia.demo;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
class DemoProjectApplicationTests {

	@Test
	void contextLoads() {
	}

	@Test
	void rootTest(@Autowired TestRestTemplate restTemplate) {
		assertThat(restTemplate.getForObject("/", String.class)).isEqualTo("Bienvenido a la pagina de GFT!");
	}

	@Test
	void TestHello(@Autowired TestRestTemplate restTemplate) {
		assertThat(restTemplate.getForObject("/hello", String.class)).isEqualTo("Hello World!");
	}

	@Test
	void TestHelloArg(@Autowired TestRestTemplate restTemplate) {
		assertThat(restTemplate.getForObject("/hello?name=Israel", String.class)).isEqualTo("Hello Israel!");
	}

	@Test
	void TestHelloNamesArg(@Autowired TestRestTemplate restTemplate) {
		String[] arr = {"Javier","Rodriguez"};
		for(String name:arr){
			assertThat(restTemplate.getForObject("/hello?name="+name, String.class)).isEqualTo("Hello "+name+"!");
		}
	}

	@Autowired TestRestTemplate restTemplate;
	@ParameterizedTest
	@ValueSource(strings = {"Javier","Javier+Arturo","Arturo","Rodriguez"})
	void helloParaNames(String name) {
		assertThat(restTemplate.getForObject("/hello?name="+name, String.class)).isEqualTo("Hello "+name+"!");
	}

	@Test
	void canAdd(@Autowired TestRestTemplate restTemplate) {
		assertThat(restTemplate.getForObject("/add?a=1&b=2", String.class)).isEqualTo("3");
		assertThat(restTemplate.getForObject("/add?a=0&b=2", String.class)).isEqualTo("2");
	}

	@Test
	void canAddZero(@Autowired TestRestTemplate restTemplate) {
		assertThat(restTemplate.getForObject("/add?a=0&b=2", String.class)).isEqualTo("2");
	}

	@Test
	void canAddNegative(@Autowired TestRestTemplate restTemplate) {
		assertThat(restTemplate.getForObject("/add?a=1&b=-2", String.class)).isEqualTo("-1");
	}

	@Test
	void canAddNullA(@Autowired TestRestTemplate restTemplate) {
		assertThat(restTemplate.getForObject("/add?a=&b=2", String.class)).isEqualTo("2");
	}

	@Test
	void canAddNullB(@Autowired TestRestTemplate restTemplate) {
		assertThat(restTemplate.getForObject("/add?a=10&b=0", String.class)).isEqualTo("10");
	}

	@Test
	void canAddMultiple(@Autowired TestRestTemplate restTemplate) {
		assertThat(restTemplate.getForObject("/add?a=2&b=2", String.class)).isEqualTo("4");
	}

	@Test
	void canAddFraction(@Autowired TestRestTemplate restTemplate) {
		assertThat(restTemplate.getForObject("/add?a=1.0&b=2.0", String.class)).isEqualTo("3.0");
	}

}