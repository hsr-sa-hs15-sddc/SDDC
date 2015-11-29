package sddc.genericapi.libvirt;

import java.util.UUID;

import org.libvirt.Connect;

import sddc.genericapi.IResourceController;
import sddc.util.ConfigUtil;

public abstract class LibVirtController implements IResourceController {
	
	protected Connect connect;
	
	public LibVirtController(Connect connect) {
		this.connect = connect;
	}
	
	protected String replaceUUID(String config) {
		UUID uuid = UUID.randomUUID();
		return ConfigUtil.changeValue(config, "{{UUID}}", "" + uuid);
	}

}
