package com.e2r.transportes.model;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.util.StringUtils;

import com.e2r.transportes.validation.SKU;

@Entity
@Table(name="cerveja")
public class Cerveja implements Serializable {
	
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long codigo;
	
	@SKU
	@NotBlank(message="sku é obrigatório")
	private String sku;
	
	@NotBlank(message="nome é obrigatório")
	private String nome;
	
	@NotBlank(message="descrição é obrigatório")
	@Size(min=1, max=50, message="o tamanho da descrição deve estar entre 1 e 50")
	private String descricao;
	
	@NotNull(message="valor é obrigatório")
	@DecimalMin("1.00")
	@DecimalMax(value="1000.00", message="o valor da cerveja não pode ser maior que 1.000,00")
	private BigDecimal valor;
	
	@NotNull(message="comissão é obrigatório")
	@DecimalMax(value="100.0", message="o valor da comissão de ser menor ou igual a 100")
	private BigDecimal comissao;
	
	@NotNull(message="teor alcoolico é obrigatório")
	@DecimalMax(value="100.0", message="o teor alcoolico de ser menor ou igual a 100")
	@Column(name="teor_alcoolico")
	private BigDecimal teorAlcoolico;
	
	@NotNull(message="quantidade em estoque é obrigatório")
	@Max(value=9999, message="a quantidade em estoque deve ser menor que 9.999")
	@Column(name="quantidade_estoque")
	private Integer quantidadeEstoque;
	
	@NotNull(message="origem é obrigatório")
	@Enumerated(EnumType.STRING)
	private Origem origem;
	
	@NotNull(message="sabor é obrigatorio")
	@Enumerated(EnumType.STRING)
	private Sabor sabor;
	
	@NotNull(message = "O estilo é obrigatório")
	@ManyToOne
	@JoinColumn(name="codigo_estilo")
	private Estilo estilo;
	
	private String foto;
	
	@Column(name="content_type")
	private String contentType;
	
	@PrePersist
	@PreUpdate
	private void PrePestistUpDate() {
		sku = sku.toUpperCase();
	}
	
	public String getSku() {
		return sku;
	}
	public void setSku(String sku) {
		this.sku = sku;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public Long getCodigo() {
		return codigo;
	}
	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}
	public BigDecimal getValor() {
		return valor;
	}
	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}
	public BigDecimal getTeorAlcoolico() {
		return teorAlcoolico;
	}
	public void setTeorAlcoolico(BigDecimal teorAlcoolico) {
		this.teorAlcoolico = teorAlcoolico;
	}
	public Integer getQuantidadeEstoque() {
		return quantidadeEstoque;
	}
	public void setQuantidadeEstoque(Integer quantidadeEstoque) {
		this.quantidadeEstoque = quantidadeEstoque;
	}
	public Origem getOrigem() {
		return origem;
	}
	public void setOrigem(Origem origem) {
		this.origem = origem;
	}
	public Sabor getSabor() {
		return sabor;
	}
	public void setSabor(Sabor sabor) {
		this.sabor = sabor;
	}
	public Estilo getEstilo() {
		return estilo;
	}
	public void setEstilo(Estilo estilo) {
		this.estilo = estilo;
	}	
	public BigDecimal getComissao() {
		return comissao;
	}
	public void setComissao(BigDecimal comissao) {
		this.comissao = comissao;
	}
	
	
	public String getFoto() {
		return foto;
	}

	public void setFoto(String foto) {
		this.foto = foto;
	}
	
	
	public String getContentType() {
		return contentType;
	}
	
	public String getFotoOuMock() {
		return !StringUtils.isEmpty(foto) ? foto : "cerveja-mock.png";
	}
	

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((codigo == null) ? 0 : codigo.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Cerveja other = (Cerveja) obj;
		if (codigo == null) {
			if (other.codigo != null)
				return false;
		} else if (!codigo.equals(other.codigo))
			return false;
		return true;
	}
		

}
