package sddc.util;

public class ConfigUtil {
	
	public static String changeValue(String config, String identifier, String newValue) {
		
		if(config.contains(identifier)) {
			return config.replace(identifier, newValue);
		} else {
			return config;
		}
	}
	
	
	
	
}
