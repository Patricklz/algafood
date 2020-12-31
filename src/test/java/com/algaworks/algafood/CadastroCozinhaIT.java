package com.algaworks.algafood;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import javax.validation.ConstraintViolationException;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.algaworks.algafood.domain.exception.CozinhaNaoEncontradaException;
import com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.service.CadastroCozinhaService;

@SpringBootTest
class CadastroCozinhaIT {

	@Autowired
	CadastroCozinhaService cadastroCozinha;
	
	@Test
	public void WhenCadastroCozinhaComDadosCorretos_ThenDeveAtribuirId() {
		//cenário
		Cozinha novaCozinha = new Cozinha();
		novaCozinha.setNome("Chinesa");
		
		//ação
		novaCozinha = cadastroCozinha.salvar(novaCozinha);
		
		//validação
		assertThat(novaCozinha).isNotNull();
		assertThat(novaCozinha.getId()).isNotNull();
	}
	
	@Test
	public void deveFalhar_QuandoCadastrarCozinhaSemNome() {
		assertThrows(ConstraintViolationException.class, () -> {
			Cozinha novaCozinha = new Cozinha();
			novaCozinha.setNome(null);
			novaCozinha = cadastroCozinha.salvar(novaCozinha);
		});
		
	}
	
	@Test
	public void deveFalhar_quandoExcluirCozinhaEmUso() {
		assertThrows(EntidadeEmUsoException.class, () -> {
			cadastroCozinha.excluir(1L);
			

		});
	}
	
	public void deveFalhar_quandoExcluirCozinhaInexistente() {
		assertThrows(CozinhaNaoEncontradaException.class, () -> {
			cadastroCozinha.excluir(100L);
			
		});
	}
	
	

}
