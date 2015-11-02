package sddc.persistence;

import static org.junit.Assert.*;

import java.util.Arrays;

import org.junit.*;
import sddc.dataaccess.IPersistenceFacade;
public class PersistenceFakeImplTest {
	
	IPersistenceFacade persistence;
	String[] str;
	@Before
	public void setUp() {
		persistence = new PersistenceFake();
	}
	
	@Test
	public void testStoreService() {
		assertEquals(-862545276,persistence.storeService("Hello World"));
		assertEquals(-862545276,persistence.storeService("Hello World"));
		assertEquals("Hello World",persistence.getService(-862545276));
		/* Same Hashcode gets replaced -> otherwise there would be a Duplication */
		persistence.storeService("Hello World123");
		String[] string = {"Hello World", "Hello World123"};
		assertTrue(Arrays.equals(string, persistence.getServices()));
	}
	
	
	
}
