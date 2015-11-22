package sddc.util;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;

public class FileUtil {

	public static String getContentOfFile(String path, Charset encoding, boolean removeWhitespaces) {
		
		byte[] content = null;
		try {
			content = Files.readAllBytes(Paths.get(path));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		String s = new String(content, encoding);
		
		if(removeWhitespaces)
			s = s.replaceAll("\\s","");
		
		return s;
	}
	
}
