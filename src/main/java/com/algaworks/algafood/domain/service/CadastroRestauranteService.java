package com.algaworks.algafood.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.algaworks.algafood.domain.exception.RestauranteNaoEncontradoException;
import com.algaworks.algafood.domain.model.Cidade;
import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.repository.RestauranteRepository;

@Service
public class CadastroRestauranteService {


	@Autowired
	private RestauranteRepository restauranteRepository;
	
	@Autowired
	private CadastroCozinhaService cadastroCozinhaService;
	
	@Autowired
	private CadastroCidadeService cadastroCidadeService;
	
	@Transactional
	public Restaurante salvar(Restaurante restaurante) {
		Long cozinhaId = restaurante.getCozinha().getId();
		Long cidadeId = restaurante.getEndereco().getCidade().getId();
		
		Cozinha cozinha = cadastroCozinhaService.buscarOuFalhar(cozinhaId);
		Cidade cidade = cadastroCidadeService.buscarOuFalhar(cidadeId);

		restaurante.setCozinha(cozinha);
		restaurante.getEndereco().setCidade(cidade);
		
		return restauranteRepository.save(restaurante);
	}
	
	@Transactional
	public void ativar (Long id) {
		Restaurante restauranteAtual = buscarOuFalhar(id);
		
		restauranteAtual.ativar();
		
		
	}
	
	@Transactional
	public void inativar (Long id) {
		Restaurante restauranteAtual = buscarOuFalhar(id);
		
		restauranteAtual.inativar();
		
		
	}
	
	public Restaurante buscarOuFalhar(Long id) {
		Restaurante restaurante = restauranteRepository.findById(id).orElseThrow(() -> new RestauranteNaoEncontradoException(id)); 
		
		return restaurante;
	}
	
	@Transactional
	public void excluir(Long id) {

		try {
			restauranteRepository.deleteById(id);
			restauranteRepository.flush();
			
		}catch(EmptyResultDataAccessException e) {
			throw new RestauranteNaoEncontradoException(id);
			
		} 
	}
}
