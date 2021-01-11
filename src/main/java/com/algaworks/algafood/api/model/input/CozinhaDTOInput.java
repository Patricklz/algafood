package com.algaworks.algafood.api.model.input;

import javax.validation.constraints.NotBlank;

import com.sun.istack.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CozinhaDTOInput {

	
	@NotBlank
	String nome;
	
}
