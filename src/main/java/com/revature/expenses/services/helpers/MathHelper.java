package com.revature.expenses.services.helpers;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;

public class MathHelper {
	private MathHelper() {
	}
	/**
	 * Takes double and returns a monetary string.
	 * @param input double
	 * @return String, monetary
	 */
	public static String doubleTextOut(double input) {
		input = doubleRoundDown(input,2);
		DecimalFormat newFormatter = new DecimalFormat("#,###.00");
		return("$" + newFormatter.format(input));
	}
	/**
	 * Handles rounding of doubles using a Big Decimal to avoid edge case crashes
	 * @param input
	 * @return double with hundredths precision 
	 */
	public static double doubleRound2(double input) {
		return doubleRound(input,2);
	}
	public static double doubleRoundDown2(double input) {
		return doubleRoundDown(input,2);
	}
	/**
	 * Handles rounding of doubles using a Big Decimal to avoid edge case crashes
	 * @param input
	 * @return double with hundred thousandths precision
	 */
	public static double doubleRound5(double input) {
		return doubleRound(input,5);
	}
	public static double doubleRoundDown5(double input) {
		return doubleRoundDown(input,5);
	}
	private static double doubleRound(double input, int precision) {
		BigDecimal bigDecimal = BigDecimal.valueOf(input);
		bigDecimal = bigDecimal.setScale(precision, RoundingMode.HALF_EVEN);
		return bigDecimal.doubleValue();
	}
	private static double doubleRoundDown(double input, int precision) {
		BigDecimal bigDecimal = BigDecimal.valueOf(input);
		bigDecimal = bigDecimal.setScale(precision, RoundingMode.DOWN);
		return bigDecimal.doubleValue();
	}
}
