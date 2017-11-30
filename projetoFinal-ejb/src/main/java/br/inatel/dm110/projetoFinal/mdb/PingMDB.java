package br.inatel.dm110.projetoFinal.mdb;

import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.EJB;
import javax.ejb.MessageDriven;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;

import br.inatel.dm110.projetoFinal.api.IpTO;
import br.inatel.dm110.projetoFinal.dao.IpStatusDAO;
import br.inatel.dm110.projetoFinal.entities.IpStatus;

@MessageDriven(activationConfig  = {
		@ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue"),
		@ActivationConfigProperty(propertyName = "destination", propertyValue = "java:/jms/queue/dm110projetofinalqueue")
})
public class PingMDB implements MessageListener {

	@EJB
	private IpStatusDAO ipStatusDAO;
	
	@Override
	public void onMessage(Message message) {
		try {
			if (message instanceof ObjectMessage) {
				ObjectMessage obgMsg = (ObjectMessage) message;
				Object object = obgMsg.getObject();
				if (object instanceof IpTO) {
					IpTO to = (IpTO) object;
					for(String ip : to.getIps()) {
						saveIP(ip, pingIP(ip));
					}
				} else {
					System.out.println("[ERROR] A MENSAGEM NAO POSSUI UM IpTO!");
				}
			} else {
				System.out.println("[ERROR] A MENSAGEM NAO POSSUI UM OBJECT!");
			}
		} catch (JMSException e) {
			e.printStackTrace();
		}
	}
	
	private boolean pingIP(String ip) {
		try {
			Runtime runtime = Runtime.getRuntime();
			Process process = runtime.exec("ping -n 1 " + ip);
			InputStream is = process.getInputStream();
			InputStream es = process.getErrorStream();
			String input = processStream(is);
			String error = processStream(es);
			int code = process.waitFor();
			if (code != 0) {
				return false;
			}
			if (error.length() > 0) {
				return false;
			}
			if (input.contains("Host de destino inacess")) {
				return false;
			}
			return true;
		} catch (IOException | InterruptedException e) {
			return false;
		}
	}
	
	private String processStream(InputStream is) {
		StringBuilder input = new StringBuilder();
		try (Scanner scanner = new Scanner(is)) {
			while (scanner.hasNextLine()) {
				input.append(scanner.nextLine()).append("\n");
			}
		}
		return input.toString();
	}
	
	private boolean saveIP(String ip, boolean status) {
		IpStatus ipStatus = new IpStatus();
		
		ipStatus.setIp(ip);
		ipStatus.setStatus((status ? "Ativo" : "Inativo"));
		
		ipStatusDAO.insert(ipStatus);
		System.out.println("Salvando no BD. IP: " + ip + " | Status: " + (status ? "Ativo" : "Inativo"));
		return true;
	}

}
