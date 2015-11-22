package sddc.services.gernericapi;

import static org.junit.Assert.*;

import org.junit.Test;

import junit.framework.Assert;
import sddc.services.genericapi.IGenericAPIFacade;
import sddc.services.genericapi.factory.GenericAPILibVirtFactory;

public class GenericAPILibVirtFactoryTest {

	@Test
	public void getInstanceTest() {
		IGenericAPIFacade api = GenericAPILibVirtFactory.getInstance();
		Assert.assertNotNull(api);
	}

}
