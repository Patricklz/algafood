package com.algaworks.algafood.api.controller;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.api.assembler.UsuarioGrupoInputDisassembler;
import com.algaworks.algafood.api.assembler.UsuarioGrupoModelAssembler;
import com.algaworks.algafood.api.model.DTO.GrupoDTO;
import com.algaworks.algafood.domain.exception.EstadoNaoEncontradoException;
import com.algaworks.algafood.domain.exception.NegocioException;
import com.algaworks.algafood.domain.model.Grupo;
import com.algaworks.algafood.domain.service.CadastroUsuarioGrupoService;

@RestController
@RequestMapping(value = "/usuarios/{usuarioId}/grupos")
public class UsuarioGrupoController {

	
	@Autowired
	private CadastroUsuarioGrupoService cadastroUsuarioGrupoPermissaoService;
	
	@Autowired
	private UsuarioGrupoModelAssembler usuarioGrupoModelAssembler;
	
	@Autowired
	private UsuarioGrupoInputDisassembler usuarioGrupoInputDisassembler;
	
	@GetMapping
	public List<GrupoDTO> listarGruposUsuario(@PathVariable Long usuarioId) {
		Set<Grupo> grupos = cadastroUsuarioGrupoPermissaoService.buscarUsuarioGrupos(usuarioId);
		
		return usuarioGrupoModelAssembler.toCollectDTO(grupos);
	}
	
	
	@PutMapping("{grupoId}")
	@ResponseStatus(HttpStatus.CREATED)
	public GrupoDTO associarGrupoPermissoesUsuario(@PathVariable Long usuarioId, @PathVariable Long grupoId) {
		try {
			Grupo grupo = cadastroUsuarioGrupoPermissaoService.associarUsuarioGrupo(usuarioId, grupoId);
			
			
			
			return usuarioGrupoModelAssembler.toDTO(grupo);
		} catch (EstadoNaoEncontradoException e) {
			throw new NegocioException(e.getMessage(), e);
		}
	}
	
	
	@DeleteMapping("{grupoId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void desassociarUsuarioGrupo(@PathVariable Long usuarioId, @PathVariable Long grupoId) {
			cadastroUsuarioGrupoPermissaoService.desassociarUsuarioGrupo(usuarioId, grupoId);
			
	}
	
	

}
