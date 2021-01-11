package com.algaworks.algafood.api.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.api.assembler.CidadeInputDisassembler;
import com.algaworks.algafood.api.assembler.CidadeModelAssembler;
import com.algaworks.algafood.api.model.DTO.CidadeDTO;
import com.algaworks.algafood.api.model.input.CidadeDTOInput;
import com.algaworks.algafood.domain.exception.EstadoNaoEncontradoException;
import com.algaworks.algafood.domain.exception.NegocioException;
import com.algaworks.algafood.domain.model.Cidade;
import com.algaworks.algafood.domain.repository.CidadeRepository;
import com.algaworks.algafood.domain.service.CadastroCidadeService;

@RestController
@RequestMapping(value = "/cidades")
public class CidadeController {

	@Autowired
	private CidadeRepository cidadeRepository;
	
	@Autowired
	private CadastroCidadeService cadastroCidadeService;
	
	@Autowired
	private CidadeModelAssembler cidadeModelAssembler;
	
	@Autowired
	private CidadeInputDisassembler cidadeInputDisassembler;
	
	@GetMapping
	public List<CidadeDTO> listar() {
		List<Cidade> todasCidades = cidadeRepository.findAll();
		
		return cidadeModelAssembler.toCollectDTO(todasCidades);
	}
	
	@GetMapping("/{id}")
	public CidadeDTO buscar(@PathVariable Long id) {
		Cidade cidade = cadastroCidadeService.buscarOuFalhar(id);
		
		return cidadeModelAssembler.toDTO(cidade);
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public CidadeDTO adicionar(@RequestBody @Valid CidadeDTOInput cidadeDTOInput) {
		try {
			Cidade cidade = cidadeInputDisassembler.DTOToModel(cidadeDTOInput);
			
			cidade = cadastroCidadeService.salvar(cidade);
			
			return cidadeModelAssembler.toDTO(cidade);
		} catch (EstadoNaoEncontradoException e) {
			throw new NegocioException(e.getMessage(), e);
		}
	}
	
	@PutMapping("/{id}")
	public CidadeDTO atualizar(@PathVariable Long id, @RequestBody @Valid CidadeDTOInput cidadeDTOInput) {
		try {
			Cidade cidadeAtual = cadastroCidadeService.buscarOuFalhar(id);
			
			cidadeInputDisassembler.copyDTOToModel(cidadeDTOInput, cidadeAtual);
			
			cidadeAtual = cadastroCidadeService.salvar(cidadeAtual);
			
			return cidadeModelAssembler.toDTO(cidadeAtual);
		} catch (EstadoNaoEncontradoException e) {
			throw new NegocioException(e.getMessage(), e);
		}
	}
	
	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Long id) {
		cadastroCidadeService.excluir(id);	
	}
}
