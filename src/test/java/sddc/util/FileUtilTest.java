package sddc.util;

import static org.junit.Assert.*;

import java.nio.charset.Charset;

import org.junit.Test;

import junit.framework.Assert;

public class FileUtilTest {
	
	private static final String storageConfig = "<pool type=\"disk\"><name>vdb2</name><source><device path='/dev/vdb2'/></source><target><path>/dev</path></target></pool>";

	@Test
	public void getContentOfFileTest() {
		String content = FileUtil.getContentOfFile("configfiles/LibVirtStorageConfigExample.xml", Charset.defaultCharset(), true);
		Assert.assertEquals(storageConfig.replaceAll("\\s",""), content);
	}

}
