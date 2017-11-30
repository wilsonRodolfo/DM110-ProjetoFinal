package br.inatel.dm100.projetoFinal.interfaces;

public interface Ping {
	
	String scanNetwork(String ip, String mask);
	String getStatus(String ip);
}
