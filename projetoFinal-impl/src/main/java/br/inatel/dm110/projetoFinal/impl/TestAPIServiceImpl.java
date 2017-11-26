package br.inatel.dm110.projetoFinal.impl;

import br.inatel.dm110.projetoFinal.api.TestAPIService;

public class TestAPIServiceImpl implements TestAPIService {

	@Override
	public String apiIsUp() {
		return "<h1>Api is Up!!!</h1>";
	}
}
