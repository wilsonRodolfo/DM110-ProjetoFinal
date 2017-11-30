package br.inatel.dm110.projetoFinal.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

@Entity
@SequenceGenerator(name = "seq_ipStatus", sequenceName = "seq_ipStatus", allocationSize = 1)
public class IpStatus {

	@Id
	@GeneratedValue(generator = "seq_ipStatus", strategy = GenerationType.SEQUENCE)
	private Integer id;

	private String ip;
	private String status;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}
