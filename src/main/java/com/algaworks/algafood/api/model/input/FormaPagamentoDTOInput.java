package com.algaworks.algafood.api.model.input;

import javax.validation.constraints.NotBlank;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FormaPagamentoDTOInput {
	
	@NotBlank
	private String descricao;

}
