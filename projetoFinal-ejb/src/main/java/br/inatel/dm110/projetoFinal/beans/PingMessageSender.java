package br.inatel.dm110.projetoFinal.beans;

import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.Queue;
import javax.jms.Session;

import br.inatel.dm110.projetoFinal.api.IpTO;

@Stateless
public class PingMessageSender {
	
	@Resource(lookup = "java:/ConnectionFactory")
	private ConnectionFactory connectionFactory;
	
	@Resource(lookup = "java:/jms/queue/dm110projetofinalqueue")
	private Queue queue;
	
	public void sendIps(IpTO ips) {
		try ( 
				Connection connection = connectionFactory.createConnection();
				Session session = connection.createSession();
				MessageProducer producer = session.createProducer(queue);
		) {
			ObjectMessage message = session.createObjectMessage(ips);
			System.out.println("Producer send ips...");
			producer.send(message);
		} catch (JMSException e) {
			throw new RuntimeException(e);
		}
	}

}
