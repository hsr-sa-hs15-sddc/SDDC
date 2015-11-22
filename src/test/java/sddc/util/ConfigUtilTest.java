package sddc.util;

import org.junit.Test;
import junit.framework.Assert;

public class ConfigUtilTest {

	@Test
	public void changeValueTest() {
		String xml = "<root><name>{{name}}</name></root>";
		String result = ConfigUtil.changeValue(xml, "{{name}}", "newName");
		Assert.assertEquals("<root><name>newName</name></root>", result);
	}
	
	@Test(expected = ConfigUtilIdentifierNotInConfigException.class)
	public void ConfigUtilIdentifierNotInConfigExceptionTest() {
		String xml = "<root><name>{{name}}</name></root>";
		ConfigUtil.changeValue(xml, "{{nam}}", "newName");
	}

}
