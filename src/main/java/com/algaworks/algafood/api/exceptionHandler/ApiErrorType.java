package com.algaworks.algafood.api.exceptionHandler;

import lombok.Getter;

@Getter
public enum ApiErrorType {

	MENSAGEM_INCOMPREENSIVEL("/mensagem-incompreensivel", "Mensagem incompreensível"),
	ENTIDADE_NAO_ENCONTRADA("/entidade-nao-encontrada", "Entidade não encontrada"),
	ENTIDADE_EM_USO("/entidade-em-uso", "Entidade em uso");
	
	private String title;
	private String uri;
	
	ApiErrorType(String path, String title) {
		this.uri = "https://algafood.com.br" + path;
		this.title = title;
	}
}
