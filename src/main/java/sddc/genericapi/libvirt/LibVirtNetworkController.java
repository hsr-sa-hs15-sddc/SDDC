package sddc.genericapi.libvirt;

import java.util.Map;

import org.libvirt.Connect;

public class LibVirtNetworkController extends LibVirtController {

	public LibVirtNetworkController(Connect connect) {
		super(connect);
	}

	@Override
	public String createResource(String config) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteResource(String identifier) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Map<String, String> getInformations(String identifier) {
		// TODO Auto-generated method stub
		return null;
	}

}
