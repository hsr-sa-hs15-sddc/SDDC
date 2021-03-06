package sddc.util;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;

public class FileUtil {

	public static String getContentOfFile(String path, Charset encoding, boolean removeWhitespaces) {
		
		byte[] content;
		try {
			content = Files.readAllBytes(Paths.get(path));
		} catch (IOException e) {
			return "";
		}
		String s = new String(content, encoding);
		
		if(removeWhitespaces)
			s = s.replaceAll("\\s","");
		
		return s;
	}
	
	//Syntax: "name=value; ...
	public static String getValueOfAttributeFromFile(String content, String attribute) {
		
		content = content.replaceAll("\\s","");
		String str = attribute + "=";
		String s = content.substring(content.lastIndexOf(str) + str.length());
		return s.substring(0, s.indexOf(';'));

	}
	
}
