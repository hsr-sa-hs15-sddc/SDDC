

import static org.junit.Assert.*;

import org.junit.Test;

public class TestMain {
	@Test
	public void testGetString() {
		assertEquals(Main.getString(), "Hallo Welt");
	}
	
	
	@Test
	public void testString() {
		assertEquals("Hallo Welt" , "Hallo Welt");
	}
}
