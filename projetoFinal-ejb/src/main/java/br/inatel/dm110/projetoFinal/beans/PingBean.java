package br.inatel.dm110.projetoFinal.beans;

import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Stateless;

import br.inatel.dm100.projetoFinal.interfaces.PingLocal;
import br.inatel.dm100.projetoFinal.interfaces.PingRemote;
import br.inatel.dm110.projetoFinal.api.IpTO;
import br.inatel.dm110.projetoFinal.dao.IpStatusDAO;

@Stateless
@Remote(PingRemote.class)
@Local(PingLocal.class)
public class PingBean implements PingRemote, PingLocal {

	@EJB
	private IpStatusDAO ipStatusDAO;
	
	@EJB
	private PingMessageSender sender;

	@Override
	public String getStatus(String ip) {
		return ipStatusDAO.getStatus(ip);
	}
	
	@Override
	public String scanNetwork(String ip, String mask) {
		String[] ips = calculateIps(ip, mask);

		String retMsg = "";

		if (ips == null) {
			retMsg = "Erro ao calcular faixa de Ips. Ip Received: " + ip + " | Mask Received: " + mask;
		} else {
			retMsg = "Recibido com sucesso! Ip Received: " + ip + " | Mask Received: " + mask + " | Calculated Ips: " + ips.length;;

			IpTO ipsSender = new IpTO();
			int count = 0;
			
			for (String i : ips) {
				ipsSender.getIps().add(i);
				
				if(++count == 10) {
					count = 0;
					sender.sendIps(ipsSender);
					ipsSender.getIps().clear();
				}
				
				System.out.println(i);
			}
			
			if(!ipsSender.getIps().isEmpty()) {
				sender.sendIps(ipsSender);
			}
		}

		return retMsg;
	}
	
	private String[] calculateIps(String ip, String mask) {
		int rangeSize = 0, cidr;

		long networkAddress = convertIP(ip);

		if (networkAddress == -1) {
			System.out.println("Erro ao converter o ip! Ip Received: " + ip);
			return null;
		}

		cidr = convertMask(mask);

		if (cidr == -1) {
			System.out.println("Erro ao converter a mascara! Mask Received: " + mask);
			return null;
		}

		for (int i = 0; i < 32 - cidr; i++) {
			rangeSize |= 1 << i;
		}

		String[] ips = new String[rangeSize - 1];

		for (int i = 1; i < rangeSize; i++) {
			ips[i - 1] = toIp(networkAddress + i);
		}

		return ips;
	}

	private static String toIp(long value) {
		return String.format("%s.%s.%s.%s", value >> 24, (value >> 16) & 255, (value >> 8) & 255, value & 255);
	}
	
	private long convertIP(String ip) {
		boolean erro = false;
		long[] octs = new long[4];

		try {
			String[] sOcts = ip.split("\\.");

			if (sOcts.length == 4) {
				octs[0] = Long.parseLong(sOcts[0]);
				octs[1] = Long.parseLong(sOcts[1]);
				octs[2] = Long.parseLong(sOcts[2]);
				octs[3] = Long.parseLong(sOcts[3]);
				
				for (int i = 0; i < 4; i++) {
					if (octs[i] < 0 || octs[i] > 255) {
						erro = true;
						break;
					}
				}
			} else {
				erro = true;
			}

		} catch (NumberFormatException e) {
			erro = true;
		}

		if (erro)
			return -1;
		
		return (octs[0] << 24) | (octs[1] << 16) | (octs[2] << 8) | octs[3];
	}
	
	private int convertMask(String mask) {
		int ret;
		try {
			ret = Integer.parseInt(mask);

			if (ret < 0 || ret > 32) {
				ret = -1;
			}
		} catch (Exception e) {
			ret = -1;
		}

		return ret;
	}
}
