package com.algaworks.algafood.api.model.DTO;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.algaworks.algafood.domain.model.Restaurante;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProdutoDTO {
	

	private Long id;
	

	private String nome;


	private String descricao;
	

	private BigDecimal preco;
	

	private boolean ativo;
	

}
