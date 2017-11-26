package br.inatel.dm110.projetoFinal.rest;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

import br.inatel.dm110.projetoFinal.impl.PingServiceImpl;
import br.inatel.dm110.projetoFinal.impl.TestAPIServiceImpl;

@ApplicationPath("/api")
public class RestApplication extends Application {
	
	@Override
	public Set<Class<?>> getClasses() {
		Set<Class<?>> classes = new HashSet<>();
		classes.add(TestAPIServiceImpl.class);
		classes.add(PingServiceImpl.class);
		return classes;
	}
}
