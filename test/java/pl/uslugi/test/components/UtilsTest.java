package pl.uslugi.test.components;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import pl.uslugi.components.Utils;

public class UtilsTest {

	@Before
	public void setup() {
	}

	@Test
	public void stringCointainsOnlyNumbersTest() {
		String nullString = null;
		String emptyString = "";
		String stringWithChars = "2312dsada3";
		String stringOnlyNumbers = "5434322";

		assertFalse(Utils.stringContainsOnlyNumbers(nullString));
		assertFalse(Utils.stringContainsOnlyNumbers(emptyString));
		assertFalse(Utils.stringContainsOnlyNumbers(stringWithChars));
		assertTrue(Utils.stringContainsOnlyNumbers(stringOnlyNumbers));
	}
}
