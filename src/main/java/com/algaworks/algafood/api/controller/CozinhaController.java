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

import com.algaworks.algafood.api.assembler.CozinhaInputDisassembler;
import com.algaworks.algafood.api.assembler.CozinhaModelAssembler;
import com.algaworks.algafood.api.model.DTO.CozinhaDTO;
import com.algaworks.algafood.api.model.input.CozinhaDTOInput;
import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.repository.CozinhaRepository;
import com.algaworks.algafood.domain.service.CadastroCozinhaService;

@RestController
@RequestMapping(value = "/cozinhas", produces = MediaType.APPLICATION_JSON_VALUE)
public class CozinhaController {

	@Autowired
	private CozinhaRepository cozinhaRepository;

	@Autowired
	private CadastroCozinhaService cadastroCozinhaService;
	
	@Autowired
	private CozinhaModelAssembler cozinhaDTOAssembler;
	
	@Autowired
	private CozinhaInputDisassembler cozinhaDTODissembler;

	@GetMapping
	public List<CozinhaDTO> listar() {
		return cozinhaDTOAssembler.toCollectDTO(cozinhaRepository.findAll());
	}

	@GetMapping("/{id}")
	public CozinhaDTO buscar(@PathVariable Long id) {
		return cozinhaDTOAssembler.toDto(cadastroCozinhaService.buscarOuFalhar(id));
		
		

	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public CozinhaDTO adicionar(@RequestBody @Valid CozinhaDTOInput CozinhaDTOInput) {
		Cozinha cozinha = cozinhaDTODissembler.dtoToModel(CozinhaDTOInput);
		
		return cozinhaDTOAssembler.toDto(cadastroCozinhaService.salvar(cozinha));
	}

	@PutMapping("/{id}")
	public CozinhaDTO atualizar(@PathVariable Long id, @RequestBody @Valid CozinhaDTOInput cozinhaDTOInput) {
		
		Cozinha cozinhaAtual = cadastroCozinhaService.buscarOuFalhar(id);
		
		cadastroCozinhaService.salvar(cozinhaAtual);
		
		cozinhaDTODissembler.copyDTOToModel(cozinhaDTOInput, cozinhaAtual);
		


		return cozinhaDTOAssembler.toDto(cozinhaAtual);

		

	}

	@DeleteMapping("/{id}")
	public void remover(@PathVariable Long id) {
			cadastroCozinhaService.excluir(id);
	}
	

			

}
