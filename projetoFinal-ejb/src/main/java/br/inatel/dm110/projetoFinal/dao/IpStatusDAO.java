package br.inatel.dm110.projetoFinal.dao;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

import br.inatel.dm110.projetoFinal.entities.IpStatus;

@Stateless
public class IpStatusDAO {

	@PersistenceContext(unitName = "projetoFinal")
	private EntityManager em;
	
	public void insert(IpStatus ipStatus) {
		em.persist(ipStatus);
	}
	
	public String getStatus(String ip) {
		String ret;
		
		try {
			ret = em.createQuery("SELECT status FROM IpStatus WHERE ip = '" + ip + "'", String.class).getSingleResult();
		} catch (NoResultException e) {
			ret = "Ip não encontrado. Ip Received: " + ip;
		}
		
		return ret;
	}
}
