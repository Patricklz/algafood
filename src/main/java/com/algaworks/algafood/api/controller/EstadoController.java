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

import com.algaworks.algafood.api.assembler.EstadoInputDisassembler;
import com.algaworks.algafood.api.assembler.EstadoModelAssembler;
import com.algaworks.algafood.api.model.DTO.EstadoDTO;
import com.algaworks.algafood.api.model.input.EstadoDTOInput;
import com.algaworks.algafood.domain.model.Estado;
import com.algaworks.algafood.domain.repository.EstadoRepository;
import com.algaworks.algafood.domain.service.CadastroEstadoService;

@RestController
@RequestMapping("/estados")
public class EstadoController {
	
	@Autowired
	private EstadoRepository estadoRepository;
	
	@Autowired
	private CadastroEstadoService cadastroEstado;
	
	@Autowired
	private EstadoModelAssembler estadoModelAssembler;
	
	@Autowired
	private EstadoInputDisassembler estadoInputDisassembler;
	
	@GetMapping
	public List<EstadoDTO> listar() {
		List<Estado> todosEstados = estadoRepository.findAll();
		
		return estadoModelAssembler.toCollectDTO(todosEstados);
	}
	
	@GetMapping("/{id}")
	public EstadoDTO buscar(@PathVariable Long id) {
		Estado estado = cadastroEstado.buscarOuFalhar(id);
		
		return estadoModelAssembler.toDTO(estado);
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public EstadoDTO adicionar(@RequestBody @Valid EstadoDTOInput estadoDTOInput) {
		Estado estado = estadoInputDisassembler.DTOToModel(estadoDTOInput);
		
		estado = cadastroEstado.salvar(estado);
		
		return estadoModelAssembler.toDTO(estado);
	}
	
	@PutMapping("/{id}")
	public EstadoDTO atualizar(@PathVariable Long id, @RequestBody @Valid EstadoDTOInput estadoDTOInput) {
		Estado estadoAtual = cadastroEstado.buscarOuFalhar(id);
		
		estadoInputDisassembler.copyDTOToModel(estadoDTOInput, estadoAtual);
		
		estadoAtual = cadastroEstado.salvar(estadoAtual);
		
		return estadoModelAssembler.toDTO(estadoAtual);
	}
	
	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Long id) {
		cadastroEstado.excluir(id);	
	}
}
