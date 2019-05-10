package com.e2r.transportes.model;

public enum StatusVenda {
	
	ORCAMENTO("Orcamento"),
	EMITIDA("Emitida"),
	CANCELADA("Cancelada");
	
	private String descricao;
	
	StatusVenda(String descricao){
		this.descricao=descricao;
	}

	public String getDescricao() {
		return descricao;
	}
	
}
