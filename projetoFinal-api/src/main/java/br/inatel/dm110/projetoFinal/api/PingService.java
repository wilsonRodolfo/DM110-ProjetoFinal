package br.inatel.dm110.projetoFinal.api;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

@Path("/poller")
public interface PingService {
	
	@GET
	@Path("/start/{IP}/{Mask}")
	String scanNetwork(@PathParam("IP") String ip, @PathParam("Mask") String mask);
	
	@GET
	@Path("/status/{IP}")
	String getStatus(@PathParam("IP") String ip);
}
