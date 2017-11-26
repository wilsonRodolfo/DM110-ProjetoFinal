package br.inatel.dm110.projetoFinal.beans;

import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Stateless;

import br.inatel.dm100.projetoFinal.interfaces.PingLocal;
import br.inatel.dm100.projetoFinal.interfaces.PingRemote;

@Stateless
@Remote(PingRemote.class)
@Local(PingLocal.class)
public class PingBean implements PingRemote, PingLocal {

	@Override
	public String scanNetwork(String ip, String mask) {
		return "Inside EJB! Ip Received: " + ip + " | Mask Received: " + mask;
	}

}
