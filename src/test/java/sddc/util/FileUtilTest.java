package sddc.util;

import static org.junit.Assert.*;

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

}
