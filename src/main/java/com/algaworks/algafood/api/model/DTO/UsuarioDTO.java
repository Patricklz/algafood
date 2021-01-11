package com.algaworks.algafood.api.model.DTO;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UsuarioDTO {
	
	@NotNull
	Long id;
	
	@NotBlank
	private String nome;
	
	@Email
	private String email;
	
	

}
