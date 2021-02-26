package com.algaworks.algafood.api.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.api.assembler.ProdutoModelAssembler;
import com.algaworks.algafood.api.model.DTO.ProdutoDTO;
import com.algaworks.algafood.api.model.input.ProdutoDTOInput;
import com.algaworks.algafood.domain.model.Produto;
import com.algaworks.algafood.domain.service.CadastroProdutoService;

@RestController
@RequestMapping(value = "/restaurantes/{restauranteId}/produtos", produces = MediaType.APPLICATION_JSON_VALUE)
public class RestauranteProdutoController {


	@Autowired
	private CadastroProdutoService cadastroProdutoService;
	
	@Autowired
	private ProdutoModelAssembler produtoModelAssembler;


	@GetMapping
	public List<ProdutoDTO> listar(@PathVariable Long restauranteId, @RequestParam(required = false) boolean incluirInativos) {
		
		if(incluirInativos) {
			List<Produto> produtos = cadastroProdutoService.buscarProdutosAtivosRestaurante(restauranteId);
			return produtoModelAssembler.toCollectDTO(produtos);
			}
		
		
			List<Produto> produtos = cadastroProdutoService.buscarProdutosRestaurante(restauranteId);
		
		return produtoModelAssembler.toCollectDTO(produtos);
	}
	
	@GetMapping("/{produtoId}")
	public ProdutoDTO buscar(@PathVariable Long restauranteId,  @PathVariable Long produtoId) {
		Produto produto = cadastroProdutoService.buscarProdutoRestaurante(restauranteId, produtoId);
		
		
		return produtoModelAssembler.toDTO(produto);
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public ProdutoDTO adicionar(@PathVariable Long restauranteId,  @RequestBody ProdutoDTOInput produtoDTOInput) {
		Produto produto = cadastroProdutoService.adicionarProdutoRestaurante(restauranteId, produtoDTOInput);
		
		
		return produtoModelAssembler.toDTO(produto);
	}
	
	
	@PutMapping("/{produtoId}")
	public ProdutoDTO atualizar(@PathVariable Long restauranteId, @PathVariable Long produtoId,  @Valid @RequestBody ProdutoDTOInput produtoDTOInput) {
		Produto produto = cadastroProdutoService.atualizarProdutoRestaurante(restauranteId, produtoId, produtoDTOInput);
		
		
		return produtoModelAssembler.toDTO(produto);
	}
	
//	@DeleteMapping("/{idFormaPagamento}")
//	@ResponseStatus(HttpStatus.NO_CONTENT)
//	public void desassociar(@PathVariable Long id, @PathVariable Long idFormaPagamento) {
//		cadastroRestauranteService.desassociarFormaPagamento(id, idFormaPagamento);
//		
//	}
//	
//	@PutMapping("/{idFormaPagamento}")
//	@ResponseStatus(HttpStatus.NO_CONTENT)
//	public void associar(@PathVariable Long id, @PathVariable Long idFormaPagamento) {
//		cadastroRestauranteService.associarFormaPagamento(id, idFormaPagamento);
//		
//	}

	
		
	
}
