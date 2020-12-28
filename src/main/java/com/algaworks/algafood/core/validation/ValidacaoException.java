package com.algaworks.algafood.core.validation;

import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ResponseStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
@ResponseStatus(value = HttpStatus.BAD_REQUEST) 
public class ValidacaoException extends RuntimeException{
	
	private static final long serialVersionUID = 1L;
	
	private BindingResult bindingResult;

}
