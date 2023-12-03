// Liam Major 30223023
package test.utils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import org.junit.Before;
import org.junit.Test;

import utils.Pair;

public class TestPair {

	Pair<Integer, String> p;
	Integer key = 10;
	String val = "fortnite";

	@Before
	public void setup() {
		p = new Pair<Integer, String>(key, val);
	}

	//test case to make sure getKey does not return a value
	@Test
	public void testGetKeyDoesntReturnValue() {
		assertNotEquals(p.getKey(), val);
	}

	//test case to make sure getValue does not return a key
	@Test
	public void testGetValueDoesntReturnKey() {
		assertNotEquals(p.getValue(), key);
	}

	//test case to make sure getKey gives the right key
	@Test
	public void testCanGetKey() {
		assertEquals(p.getKey(), key);
	}

	//test case to make sure getValue gives correct value
	@Test
	public void testCanGetValue() {
		assertEquals(p.getValue(), val);
	}

}
