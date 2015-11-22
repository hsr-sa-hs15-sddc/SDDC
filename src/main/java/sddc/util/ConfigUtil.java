package sddc.util;

public class ConfigUtil {
	
	public static String changeValue(String config, String identifier, String newValue) 
		throws ConfigUtilIdentifierNotInConfigException {
		
		if(config.contains(identifier)) {
			return config.replace(identifier, newValue);
		} else {
			throw new ConfigUtilIdentifierNotInConfigException("Identifier: " + identifier + " not in Config");
		}
	}
	
	
	
	
}
