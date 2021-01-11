package com.algaworks.algafood.api.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.api.assembler.FormaPagamentoInputDisassembler;
import com.algaworks.algafood.api.assembler.FormaPagamentoModelAssembler;
import com.algaworks.algafood.api.assembler.RestauranteInputDisassembler;
import com.algaworks.algafood.api.assembler.RestauranteModelAssembler;
import com.algaworks.algafood.api.model.DTO.FormaPagamentoDTO;
import com.algaworks.algafood.api.model.DTO.RestauranteDTO;
import com.algaworks.algafood.api.model.input.FormaPagamentoDTOInput;
import com.algaworks.algafood.api.model.input.RestauranteDTOInput;
import com.algaworks.algafood.domain.exception.CozinhaNaoEncontradaException;
import com.algaworks.algafood.domain.exception.NegocioException;
import com.algaworks.algafood.domain.model.FormaPagamento;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.repository.FormaPagamentoRepository;
import com.algaworks.algafood.domain.repository.RestauranteRepository;
import com.algaworks.algafood.domain.service.CadastroFormaPagamentoService;
import com.algaworks.algafood.domain.service.CadastroRestauranteService;

@RestController
@RequestMapping(value = "/formaspagamentos", produces = MediaType.APPLICATION_JSON_VALUE)
public class FormaPagamentoController {

	@Autowired
	private FormaPagamentoRepository formaPagamentoRepository;

	@Autowired
	private CadastroFormaPagamentoService cadastroFormaPagamentoService;
	
	@Autowired
	private FormaPagamentoModelAssembler formaPagamentoModelAssembler;
	
	@Autowired
	private FormaPagamentoInputDisassembler formaPagamentoInputDissembler;
	


	@GetMapping
	public List<FormaPagamentoDTO> listar() {
		return formaPagamentoModelAssembler.toCollectDTO(formaPagamentoRepository.findAll());
	}

	@GetMapping("/{id}")
	public FormaPagamentoDTO buscar(@PathVariable Long id) {
		
		FormaPagamento formaPagamento = cadastroFormaPagamentoService.buscarOuFalhar(id);
		
		return formaPagamentoModelAssembler.toDTO(formaPagamento);

	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public FormaPagamentoDTO adicionar(@RequestBody @Valid FormaPagamentoDTOInput formaPagamentoDTOInput) {
		try {
			
			FormaPagamento formaPagamento = formaPagamentoInputDissembler.dtoToModel(formaPagamentoDTOInput);
			
			return formaPagamentoModelAssembler.toDTO(formaPagamentoRepository.save(formaPagamento));
		}catch(CozinhaNaoEncontradaException e) {
			throw new NegocioException(e.getMessage(), e);
		}

	}
	

	@PutMapping("/{id}")
	public FormaPagamentoDTO atualizar(@PathVariable Long id, @RequestBody @Valid FormaPagamentoDTOInput formaPagamentoDTOInput) {
		try {
			FormaPagamento formaPagamentoAtual = cadastroFormaPagamentoService.buscarOuFalhar(id);
				

			formaPagamentoInputDissembler.copyDTOToModel(formaPagamentoDTOInput, formaPagamentoAtual);
			formaPagamentoAtual = cadastroFormaPagamentoService.salvar(formaPagamentoAtual);
			
			return formaPagamentoModelAssembler.toDTO(formaPagamentoAtual);
			}catch(CozinhaNaoEncontradaException e) {
				throw new NegocioException(e.getMessage(), e);
			}
			
		
	}

	
	@DeleteMapping("/{id}")
	public void remover(@PathVariable Long id) {
		cadastroFormaPagamentoService.excluir(id);
	}
		
	
}
