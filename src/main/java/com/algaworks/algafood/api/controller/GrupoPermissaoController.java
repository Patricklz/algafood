package com.algaworks.algafood.api.controller;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.api.assembler.PermissaoInputDisassembler;
import com.algaworks.algafood.api.assembler.PermissaoModelAssembler;
import com.algaworks.algafood.api.model.DTO.PermissaoDTO;
import com.algaworks.algafood.domain.model.Permissao;
import com.algaworks.algafood.domain.service.CadastroPermissaoService;

@RestController
@RequestMapping(value = "/grupos/{grupoId}/permissoes")
public class GrupoPermissaoController {

	
	@Autowired
	private CadastroPermissaoService cadastroPermissaoService;
	
	@Autowired
	private PermissaoModelAssembler permissaoModelAssembler;
	
	@Autowired
	private PermissaoInputDisassembler permissaoInputDisassembler;
	
	@GetMapping
	public List<PermissaoDTO> listar(@PathVariable Long grupoId) {
		Set<Permissao> permissoes = cadastroPermissaoService.listar(grupoId);
		
		return permissaoModelAssembler.toCollectDTO(permissoes);
	}
	
	
	@PostMapping("/{permissaoId}")
	@ResponseStatus(HttpStatus.CREATED)
	public PermissaoDTO associar(@PathVariable Long grupoId, @PathVariable Long permissaoId) {
			
			Permissao permissao = cadastroPermissaoService.associar(grupoId, permissaoId);
			
			return permissaoModelAssembler.toDTO(permissao);

	}

	
	@DeleteMapping("/{permissaoId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void desassociar(@PathVariable Long grupoId, @PathVariable Long permissaoId) {
		Permissao permissao = cadastroPermissaoService.desassociar(grupoId, permissaoId);	
	}
	
	
}
