package com.algaworks.algafood.domain.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.algaworks.algafood.api.assembler.ProdutoInputDisassembler;
import com.algaworks.algafood.api.model.DTO.ProdutoDTO;
import com.algaworks.algafood.api.model.input.ProdutoDTOInput;
import com.algaworks.algafood.domain.exception.ProdutoNaoEncontradoException;
import com.algaworks.algafood.domain.model.Produto;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.repository.ProdutoRepository;

@Service
public class CadastroProdutoService {

	
	@Autowired
	private ProdutoRepository produtoRepository;
	
	@Autowired
	private CadastroRestauranteService cadastroRestauranteService;
	
	@Autowired
	private ProdutoInputDisassembler produtoInputDisassembler;
	
	@Transactional
	public List<Produto> buscarProdutosRestaurante(Long restauranteId) {

		Restaurante restaurante = cadastroRestauranteService.buscarOuFalhar(restauranteId);
		
		List<Produto> produtos = produtoRepository.findAllByRestauranteId(restaurante.getId());
		
		return produtos;
	}
	
	@Transactional
	public List<Produto> buscarProdutosAtivosRestaurante(Long restauranteId) {

		Restaurante restaurante = cadastroRestauranteService.buscarOuFalhar(restauranteId);
		
		
		List<Produto> produtos = produtoRepository.findAtivosByRestaurante(restaurante);
		
		return produtos;
	}
	
	
	@Transactional
	public Produto buscarProdutoRestaurante(Long restauranteId, Long produtoId) {

		Restaurante restaurante = cadastroRestauranteService.buscarOuFalhar(restauranteId);
		
		Produto produto = buscarOuFalhar(restaurante.getId(), produtoId);
				
		
		return produto;
	}
	
	
	@Transactional
	public Produto adicionarProdutoRestaurante(Long restauranteId,  ProdutoDTOInput produtoDTOInput) {

		Restaurante restaurante = cadastroRestauranteService.buscarOuFalhar(restauranteId);

		Produto produto = produtoInputDisassembler.DTOToModel(produtoDTOInput);
		produto.setRestaurante(restaurante);
		
		
		return produtoRepository.save(produto);
	}
	
	@Transactional
	public Produto atualizarProdutoRestaurante(Long restauranteId, Long produtoId,  ProdutoDTOInput produtoDTOInput) {

		cadastroRestauranteService.buscarOuFalhar(restauranteId);
		
		buscarOuFalhar(restauranteId, produtoId);

		Produto produto = produtoRepository.findById(produtoId).get();
		
				
		produtoInputDisassembler.copyDTOToModel(produtoDTOInput, produto);

		
		
		return produtoRepository.save(produto);
	}
	
	
	public Produto buscarOuFalhar(Long restauranteId, Long produtoId) {
		Produto produto = produtoRepository.findByRestauranteIdAndId(restauranteId, produtoId).orElseThrow(() -> new ProdutoNaoEncontradoException(restauranteId, produtoId)); 
		
		return produto;
	}
	
	
	
//	@Transactional
//	public void alterarSenha(Long id, UsuarioSenhaDTOInput usuarioSenhaDTOInput) {
//		
//		Usuario usuario = buscarOuFalhar(id);
//		if (usuario.senhaNaoCoincideCom(usuarioSenhaDTOInput.getSenhaAtual())) {
//			throw new NegocioException("Senha atual informada não coincide com a senha do usuário.");
//		}
//		usuario.setSenha(usuarioSenhaDTOInput.getSenhaNova());
//		usuarioRepository.save(usuario);
//	}
//	
//	
//	public Usuario buscarOuFalhar(Long id) {
//		Usuario usuario = usuarioRepository.findById(id).orElseThrow(() -> new UsuarioNaoEncontradoException(id)); 
//		
//		return usuario;
//	}
//	
//	
//	@Transactional
//	public void excluir(Long id) {
//
//		try {
//			usuarioRepository.deleteById(id);
//			usuarioRepository.flush();
//			
//		}catch(EmptyResultDataAccessException e) {
//			throw new GrupoNaoEncontradoException(id);
//			
//		} 
//	}

}
