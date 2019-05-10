package com.e2r.transportes.service.event.cerveja;

import org.springframework.util.StringUtils;

import com.e2r.transportes.model.Cerveja;



public class CervejaSalvaEvent {
	
	private Cerveja cerveja;

	public CervejaSalvaEvent(Cerveja cerveja) {
		this.cerveja = cerveja;
	}

	public Cerveja getCerveja() {
		return cerveja;
	}

	public void setCerveja(Cerveja cerveja) {
		this.cerveja = cerveja;
	}
	
	public boolean temFoto() {
		return !StringUtils.isEmpty(cerveja.getFoto());
	}
	

}
