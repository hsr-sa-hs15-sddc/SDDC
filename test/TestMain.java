package test.java;

import static org.junit.Assert.*;

import org.junit.Test;

import main.java.Main;

public class TestMain {
	@Test
	public void testGetString() {
		assertEquals(Main.getString(), "Hallo Welt");
	}
}
