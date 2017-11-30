package br.inatel.dm110.projetoFinal.api;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class IpTO implements Serializable {

	private static final long serialVersionUID = 4734301886895791582L;
	
	private List<String> ips;

	public IpTO() {
		ips = new ArrayList<String>();
	}
	
	public List<String> getIps() {
		return ips;
	}

	public void setIps(List<String> ips) {
		this.ips = ips;
	}
}
