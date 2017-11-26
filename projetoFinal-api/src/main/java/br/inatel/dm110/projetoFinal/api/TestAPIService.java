package br.inatel.dm110.projetoFinal.api;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/test")
public interface TestAPIService {
	
	@GET
	@Path("/isUp")
	@Produces(MediaType.TEXT_HTML)
	String apiIsUp();
}
