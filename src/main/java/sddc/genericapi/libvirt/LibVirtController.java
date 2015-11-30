package sddc.genericapi.libvirt;

import java.util.UUID;
import org.libvirt.Connect;
import sddc.genericapi.ResourceController;
import sddc.services.domain.Category;
import sddc.services.domain.Provider;
import sddc.util.ConfigUtil;

public abstract class LibVirtController extends ResourceController {
	
	protected Connect connect;
	
	public LibVirtController(Category category, Connect connect) {
		super(category, Provider.LibVirt);
		this.connect = connect;
	}
	
	protected String replaceUUID(String config) {
		UUID uuid = UUID.randomUUID();
		return ConfigUtil.changeValue(config, "{{UUID}}", "" + uuid);
	}

}
