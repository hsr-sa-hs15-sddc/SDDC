package sddc.util;

import java.nio.charset.Charset;

import org.junit.Test;

import junit.framework.Assert;

public class FileUtilTest {
	
	private static final String storageConfig = "<pool type=\"disk\"><name>vdb-{{UUID}}</name><source><device path='/dev/vdb-{{UUID}}' /></source><target><path>/dev</path></target></pool>";

	@Test
	public void getContentOfFileTest() {
		String content = FileUtil.getContentOfFile("src/test/resources/LibVirtStorageConfigExample.xml", Charset.defaultCharset(), true);
		Assert.assertEquals(storageConfig.replaceAll("\\s",""), content);
	}
	
	@Test
	public void getValueOfAttributeFromFileTest() {
		String str = "name1=value1;name2=value2;";
		Assert.assertEquals("value1", FileUtil.getValueOfAttributeFromFile(str, "name1"));
		Assert.assertEquals("value2", FileUtil.getValueOfAttributeFromFile(str, "name2"));
	}

}
