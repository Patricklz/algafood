package com.algaworks.algafood.domain.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class FormaPagamentoNaoEncontradaException extends EntidadeNaoEncontradaException{
	
	private static final long serialVersionUID = 1L;
	
	public FormaPagamentoNaoEncontradaException(String mensagem) {
		super(mensagem);
	}
	
	public FormaPagamentoNaoEncontradaException(Long id) {
		this(String.format("Não existe um cadastro de forma de pagamento com o código %d", id));
	}

}
