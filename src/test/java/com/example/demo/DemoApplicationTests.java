package com.example.demo;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.LinkedList;
import java.util.List;

class DemoApplicationTests {

	Calculator calculator;
	@BeforeEach
	public void setUp() {
		calculator = new Calculator();
	}

	@Test
	void testAddNumbers() {
		Assertions.assertEquals(10, calculator.add(6, 4));
		org.assertj.core.api.Assertions.assertThat(calculator.add(6, 4)).isEqualTo(10);

	}

	class Calculator{
		int add(int a, int b){
			return a+b;
		}
	}

	@Test
	void testMocking(){
		LinkedList mockedList = Mockito.mock(LinkedList.class);
		Mockito.when(mockedList.get(0)).thenReturn("Ramana");


		System.out.println(mockedList.size());
//		Assertions.assertEquals("one", mockedList.get(0));

	}
}
