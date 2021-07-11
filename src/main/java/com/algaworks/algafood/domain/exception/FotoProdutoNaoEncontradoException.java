package com.algaworks.algafood.domain.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class FotoProdutoNaoEncontradoException extends EntidadeNaoEncontradaException {
	
	private static final long serialVersionUID = 1L;
	
    public FotoProdutoNaoEncontradoException (String mensagem) {
        super(mensagem);
    }
    
	public FotoProdutoNaoEncontradoException(Long restauranteId, Long produtoId) {
		super(String.format("Não existe uma foto para o restaurante id nº %d produto id nº %d ", restauranteId, produtoId));
	}

}
