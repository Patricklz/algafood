package com.algaworks.algafood.api.exceptionHandler;

import lombok.Getter;

@Getter
public enum ApiErrorType {

	MENSAGEM_INCOMPREENSIVEL("/mensagem-incompreensivel", "Mensagem incompreensível"),
	RECURSO_NAO_ENCONTRADO("/recurso-nao-encontrado", "Recurso não encontrado"),
	ENTIDADE_EM_USO("/entidade-em-uso", "Entidade em uso"),
	PARAMETRO_INVALIDO("/parametro-invalido", "Parâmetro inválido"),
	ERRO_DE_SISTEMA("/erro-no-sistema", "Erro de sistema.");
	
	private String title;
	private String uri;
	
	ApiErrorType(String path, String title) {
		this.uri = "https://algafood.com.br" + path;
		this.title = title;
	}
}
