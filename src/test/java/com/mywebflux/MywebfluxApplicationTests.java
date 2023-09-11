package com.mywebflux;

import org.junit.jupiter.api.Test;
import reactor.test.StepVerifier;

import java.util.List;

class MywebfluxApplicationTests {
	FluxAndMonoGeneratorService service = new FluxAndMonoGeneratorService();

	@Test
	void testNamesFlux() {
		var namesFlux = service.namesFlux();
		StepVerifier .create(namesFlux)
				//.expectNext("kishor","vijay","kona")
				//.expectNextCount(3)
				.expectNext("kishor")
				.expectNextCount(2)
				.verifyComplete();
	}
	@Test
	void test_Map() {
		var namesFlux = service.example_Map();
		StepVerifier .create(namesFlux)
				.expectNext("KISHOR","VIJAY","KONA")
				.verifyComplete();
	}
	@Test
	void test_Immunitable() {
		var namesFlux = service.example_Immunitable();
		StepVerifier .create(namesFlux)
				.expectNext("kishor","vijay","kona")
				.verifyComplete();
	}

	@Test
	void test_Filter_Names() {
		var namesFlux = service.example_Filter_Names(4);
		StepVerifier .create(namesFlux)
				.expectNext("KISHOR","VIJAY")
				.verifyComplete();
	}

	@Test
	void test_Filter_AndConcnate() {
		var namesFlux = service.example_Filter_AndConcnate(4);
		StepVerifier .create(namesFlux)
				.expectNext("4-KISHOR","4-VIJAY")
				.verifyComplete();
	}

	@Test
	void test_FlatMap() {
		var namesFlux = service.example_FlatMap(4);
		StepVerifier .create(namesFlux)
				.expectNext("K","I","S","H","O","R","V","I","J","A","Y")
				.verifyComplete();
	}

	@Test
	void test_FlatMapAsync() {
		var namesFlux = service.example_FlatMapAsync(4);
		StepVerifier.create(namesFlux)
				.expectNextCount(11)
				.verifyComplete();
	}

	@Test
	void test_ConcatMapAsync() {
		var namesFlux = service.example_ConcatMapAsync(4);
		StepVerifier.create(namesFlux)
				.expectNext("K","I","S","H","O","R","V","I","J","A","Y")
				.verifyComplete();
	}

	@Test
	void test_FlatMapAsMono() {
		var namesFlux = service.example_FlatMapAsMono(4);
		StepVerifier.create(namesFlux)
				.expectNext(List.of("K","I","S","H","O","R"))
				.verifyComplete();
	}

	@Test
	void test_FlatMapMany() {
		var namesFlux = service.example_FlatMapMany(4);
		StepVerifier.create(namesFlux)
				.expectNext("K","I","S","H","O","R")
				.verifyComplete();
	}

	@Test
	void test_Transform() {
		var namesFlux = service.example_Transform(4);
		StepVerifier.create(namesFlux)
				.expectNext("K","I","S","H","O","R","V","I","J","A","Y")
				.verifyComplete();
	}

	@Test
	void test_DefaultIfEmpty() {
		var namesFlux = service.example_DefaultIfEmpty(10);
		StepVerifier.create(namesFlux)
				.expectNext("default")
				.verifyComplete();
	}

	@Test
	void test_SwitchIfEmpty() {
		var namesFlux = service.example_SwitchIfEmpty(6);
		StepVerifier.create(namesFlux)
				.expectNext("D","E","F","A","U","L","T")
				.verifyComplete();
	}

	@Test
	void test_Concat() {
		var namesFlux = service.example_Concat();
		StepVerifier.create(namesFlux)
				.expectNext("A","B","C","D","E","F")
				.verifyComplete();
	}

	@Test
	void test_ConcatWith() {
		var namesFlux = service.example_ConcatWith();
		/*
		StepVerifier.create(namesFlux)
				.expectNext("A","B","C","D","E","F")
				.verifyComplete();
		*/
		StepVerifier.create(namesFlux)
				.expectNext("A","B")
				.verifyComplete();
	}
}
