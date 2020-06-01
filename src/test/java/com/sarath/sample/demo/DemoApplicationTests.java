package com.sarath.sample.demo;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class DemoApplicationTests {

	@Test
	public void returnClassicMenu() {
		assertEquals("banana,honey,mango,peach,pineapple,strawberry", Smoothie.ingredients("Classic"));
	}

	@Test
	public void returnTrimForClassic() {
		assertEquals("banana,honey,mango,peach,pineapple", Smoothie.ingredients("Classic,-strawberry"));
	}

	@Test(expected = IllegalArgumentException.class)
	public void returnExceptionTest() {
		Smoothie.ingredients("Classic,chocolate");
	}



}
