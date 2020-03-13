package com.revature.expenses.services.helpers;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class MathHelperTest {
	@Test
	public void doubleOutTest1() {
		String number = MathHelper.doubleTextOut(156.396421);
		assertEquals("$156.39",number);
	}
	@Test
	public void doubleOutTest2() {
		String number = MathHelper.doubleTextOut(156_365_189.3336421);
		assertEquals("$156,365,189.33",number);
	}
	@Test
	public void doubleRound2_1() {
		Double number = MathHelper.doubleRound2(156.396421);
		assertEquals(156.40,number,.00001);
	}
	@Test
	public void doubleRound2_2() {
		Double number = MathHelper.doubleRound2(156_365_189.3336421);
		assertEquals(156365189.33,number,.00000001);
	}
	@Test
	public void doubleRound5_1() {
		Double number = MathHelper.doubleRound5(156.396421);
		assertEquals(156.39642,number,0.00000001);
	}
	@Test
	public void doubleRound5_2() {
		Double number = MathHelper.doubleRound5(156_365_189.3336461);
		assertEquals(156365189.33365,number,.0000000001);
	}
}
