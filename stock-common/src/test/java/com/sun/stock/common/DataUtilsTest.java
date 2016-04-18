package com.sun.stock.common;

import org.junit.Test;

/**
 * Created by zksun on 16-4-18.
 */
public class DataUtilsTest {

	@Test
	public void shiftByteTest() {
		int i = Integer.numberOfLeadingZeros(-1);
		System.out.println(i);
		i = Integer.numberOfTrailingZeros(-1);
		System.out.println(i);
		String s = Integer.toBinaryString(-1);
		System.out.println(s);
		s = Integer.toBinaryString(-1 << 7);
		System.out.println(s);
		s = Integer.toBinaryString(-1 << 14);
		System.out.println(s);
		s = Integer.toBinaryString(-1 << 21);
		System.out.println(s);
		s = Integer.toBinaryString(-1 << 28);
		System.out.println(s);
	}
}
