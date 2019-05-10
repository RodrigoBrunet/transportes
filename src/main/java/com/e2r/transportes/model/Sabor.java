package com.e2r.transportes.model;

public enum Sabor {

	ADOCICADA("Adocicada"),
	AMARGA("Amarga"),
	FRUTADA("Frutada"),
	FORTE("Forte"),
	SUAVE("Suave");
	
	private String descricao;
	
	Sabor(String descricao){
		this.descricao=descricao;
	}
	
	public String getDescricao() {
		return descricao;
	}
}

