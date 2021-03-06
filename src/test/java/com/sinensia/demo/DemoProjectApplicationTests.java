package com.sinensia.demo;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.web.client.RestClientException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
class DemoProjectApplicationTests {

	@Autowired transient TestRestTemplate restTemplate;

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




	@Nested
	@DisplayName("Application tests")
	class apptests {

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
		void canAddFraction(@Autowired TestRestTemplate restTemplate) {
			assertThat(restTemplate.getForObject("/add?a=1.5&b=2", String.class)).isEqualTo("3.5");
		}

		@Test
		void canAddMultiple(@Autowired TestRestTemplate restTemplate) {
			assertThat(restTemplate.getForObject("/add?a=1&b=2", String.class))
					.isEqualTo("3");
			assertThat(restTemplate.getForObject("/add?a=0&b=2", String.class))
					.isEqualTo("2");
			assertThat(restTemplate.getForObject("/add?a=1&b=-2", String.class))
					.isEqualTo("-1");
			assertThat(restTemplate.getForObject("/add?a=&b=2", String.class))
					.isEqualTo("2");
			assertThat(restTemplate.getForObject("/add?a=1.5&b=2", String.class))
					.isEqualTo("3.5");
			assertThat(restTemplate.getForObject("/add?a=1&b=", String.class))
					.isEqualTo("1");
		}

		@DisplayName("multiple additions")
		@ParameterizedTest(name="[{index}] {0} + {1} = {2}")
		@CsvSource({
				"1,   2,   3",
				"1,   1,   2",
				"1.0, 1.0, 2",
				"1,  -2,  -1",
				"1.5, 2,   3.5",
				"'',  2,   2",
				"1.5, 1.5, 3"
		})
		void canAddCsvParameterized(String a, String b, String expected) {
			assertThat(restTemplate.getForObject("/add?a="+a+"&b="+b, String.class))
					.isEqualTo(expected);
		}

		@Test
		void canAddExceptionJsonString() {
			assertThat(restTemplate.getForObject("/add?a=string&b=1", String.class).indexOf("Bad Request"))
					.isGreaterThan(-1);
		}

		@Test
		void canAddFloat() {
			assertThat(restTemplate.getForObject("/add?a=1.5&b=2", Float.class))
					.isEqualTo(3.5f);
		}

		@Test
		void canAddFloatException() {
			assertThrows(RestClientException.class, () -> restTemplate.getForObject("/add?a=hola&b=2", Float.class));
		}

		@Autowired
		private transient DemoProjectApplication app;

		@Test
		void appCanAddReturnsInteger(){
			assertThat(app.canAdd(1f, 2f)).isEqualTo(3);
		}

		@Test
		void appCanAddReturnsFloat(){
			assertThat(app.canAdd(1.5f, 2f)).isEqualTo(3.5f);
		}

	}

	@Nested
	@DisplayName("Multiplication Tests")
	class MultiplicationTests {
		@DisplayName("multiplication")
		@ParameterizedTest(name="[{index}] {0} + {1} = {2}")
		@CsvSource({
				"1,   2,   2",
				"1,   1,   1",
				"1.0, 1.0, 1",
				"1,  -2,  -2",
				"1.5, 2,   3",
				"'',  2,   0",
				"1.5, 1.5, 2.25"
		})
		void canMultiply(String a, String b, String expected) {
			assertThat(restTemplate.getForObject("/multiply?a="+a+"&b="+b, String.class))
					.isEqualTo(expected);

		}
	}

	@Nested
	@DisplayName("Dividir Tests")
	class DividirTests {
		@DisplayName("dividir")
		@ParameterizedTest(name="[{index}] {0} + {1} = {2}")
		@CsvSource({
				"1,   2,   0.5",
				"1,   1,   1",
				"1.0, 1.0, 1",
				"1,  -2,  -0.5",
				"1.5, 2,   0.75",
				"'',  2,   0",
				"1.5, 1.5, 1"
		})
		void canDividir(String a, String b, String expected) {
			assertThat(restTemplate.getForObject("/div?a="+a+"&b="+b, String.class))
					.isEqualTo(expected);
		}

	}

	@Nested
	@DisplayName("Subtract Tests")
	class SubtractTests {
		@DisplayName("Subtract")
		@ParameterizedTest(name="[{index}] {0} + {1} = {2}")
		@CsvSource({
				"1,   2,   -1",
				"1,   1,   0",
				"1.0, 1.0, 0",
				"1,  -2,  3",
				"1.5, 2,   -0.5",
				"'',  2,   -2",
				"1.5, 1.5, 0"
		})
		void canSubtract(String a, String b, String expected) {
			assertThat(restTemplate.getForObject("/subtract?a="+a+"&b="+b, String.class))
					.isEqualTo(expected);

		}
	}

	@Nested
	@DisplayName("Sqrt Tests")
	class SqrtTests {
		@DisplayName("sqrt")
		@ParameterizedTest(name="[{index}] {0} = {1}")
		@CsvSource({
				"25,	5",
				"100,	10",
				"0,	0"

		})
		void canSqrt(String a, String expected) {
			assertThat(restTemplate.getForObject("/sqrt?a="+a, String.class))
					.isEqualTo(expected);

		}
	}

}