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

import com.algaworks.algafood.api.assembler.GrupoInputDisassembler;
import com.algaworks.algafood.api.assembler.GrupoModelAssembler;
import com.algaworks.algafood.api.model.DTO.CidadeDTO;
import com.algaworks.algafood.api.model.DTO.GrupoDTO;
import com.algaworks.algafood.api.model.input.CidadeDTOInput;
import com.algaworks.algafood.api.model.input.GrupoDTOInput;
import com.algaworks.algafood.domain.exception.EstadoNaoEncontradoException;
import com.algaworks.algafood.domain.exception.NegocioException;
import com.algaworks.algafood.domain.model.Cidade;
import com.algaworks.algafood.domain.model.Grupo;
import com.algaworks.algafood.domain.repository.GrupoRepository;
import com.algaworks.algafood.domain.service.CadastroGrupoService;

@RestController
@RequestMapping(value = "/grupos")
public class GrupoController {

	@Autowired
	private GrupoRepository grupoRepository;
	
	@Autowired
	private CadastroGrupoService cadastroGrupoService;
	
	@Autowired
	private GrupoModelAssembler grupoModelAssembler;
	
	@Autowired
	private GrupoInputDisassembler grupoInputDisassembler;
	
	@GetMapping
	public List<GrupoDTO> listar() {
		List<Grupo> grupos = grupoRepository.findAll();
		
		return grupoModelAssembler.toCollectDTO(grupos);
	}
	
	@GetMapping("/{id}")
	public GrupoDTO buscar(@PathVariable Long id) {
		Grupo grupo = cadastroGrupoService.buscarOuFalhar(id);
		
		return grupoModelAssembler.toDTO(grupo);
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public GrupoDTO adicionar(@RequestBody @Valid GrupoDTOInput grupoDTOInput) {
		try {
			Grupo grupo = grupoInputDisassembler.DTOToModel(grupoDTOInput);
			
			cadastroGrupoService.salvar(grupo);
			
			return grupoModelAssembler.toDTO(grupo);
		} catch (EstadoNaoEncontradoException e) {
			throw new NegocioException(e.getMessage(), e);
		}
	}
	
	@PutMapping("/{id}")
	public GrupoDTO atualizar(@PathVariable Long id, @RequestBody @Valid GrupoDTOInput grupoDTOInput) {
		try {
			Grupo grupoAtual = cadastroGrupoService.buscarOuFalhar(id);
			
			grupoInputDisassembler.copyDTOToModel(grupoDTOInput, grupoAtual);
			
			grupoAtual = cadastroGrupoService.salvar(grupoAtual);
			
			return grupoModelAssembler.toDTO(grupoAtual);
		} catch (EstadoNaoEncontradoException e) {
			throw new NegocioException(e.getMessage(), e);
		}
	}
	
	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Long id) {
		cadastroGrupoService.excluir(id);	
	}
}
