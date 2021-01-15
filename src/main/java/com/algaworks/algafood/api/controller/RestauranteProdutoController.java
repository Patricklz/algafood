package com.algaworks.algafood.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.api.assembler.FormaPagamentoModelAssembler;
import com.algaworks.algafood.api.model.DTO.FormaPagamentoDTO;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.service.CadastroRestauranteService;

@RestController
@RequestMapping(value = "/restaurantes/{id}/produtos", produces = MediaType.APPLICATION_JSON_VALUE)
public class RestauranteProdutoController {


	@Autowired
	private CadastroRestauranteService cadastroRestauranteService;
	
	@Autowired
	private FormaPagamentoModelAssembler formaPagamentoModelAssembler;


	@GetMapping
	public List<FormaPagamentoDTO> listar(@PathVariable Long id) {
		Restaurante restaurante = cadastroRestauranteService.buscarOuFalhar(id);
		return formaPagamentoModelAssembler.toCollectDTO(restaurante.getFormasPagamento());
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
