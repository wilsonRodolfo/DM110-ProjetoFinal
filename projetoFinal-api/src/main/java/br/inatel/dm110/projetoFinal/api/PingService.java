package br.inatel.dm110.projetoFinal.api;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

@Path("/poller")
public interface PingService {
	
	@GET
	@Path("/{IP}/{Mask}")
	String scanNetwork(@PathParam("IP") String ip, @PathParam("Mask") String mask);
}
