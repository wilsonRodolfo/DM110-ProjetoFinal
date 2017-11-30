package br.inatel.dm110.projetoFinal.impl;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;

import br.inatel.dm100.projetoFinal.interfaces.PingRemote;
import br.inatel.dm110.projetoFinal.api.PingService;

@RequestScoped
public class PingServiceImpl implements PingService {

	@EJB(lookup = "java:app/projetoFinal-ejb-0.1-SNAPSHOT/PingBean!br.inatel.dm100.projetoFinal.interfaces.PingRemote")
	private PingRemote ping;
	
	@Override
	public String scanNetwork(String ip, String mask) {
		String msg = "<h1> " + ping.scanNetwork(ip, mask) + " </h1>";
		return msg;
	}

	@Override
	public String getStatus(String ip) {
		String msg = "<h1> " + ping.getStatus(ip) + " </h1>";
		return msg;
	}

}
