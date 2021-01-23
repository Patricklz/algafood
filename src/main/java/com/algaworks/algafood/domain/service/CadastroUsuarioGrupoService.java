package com.algaworks.algafood.domain.service;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.algaworks.algafood.domain.model.Grupo;
import com.algaworks.algafood.domain.model.Permissao;
import com.algaworks.algafood.domain.model.Usuario;
import com.algaworks.algafood.domain.repository.UsuarioGrupoRepository;

@Service
public class CadastroUsuarioGrupoService {
	
	@Autowired
	private UsuarioGrupoRepository usuarioGrupoRepository;
	
	@Autowired
	private CadastroUsuarioService CadastroUsuarioService;
	
	@Autowired
	private CadastroGrupoService cadastroGrupoService;
	
	
	@Transactional
	public Set<Grupo> buscarUsuarioGrupos(Long usuarioId) {
		
		Usuario usuario = CadastroUsuarioService.buscarOuFalhar(usuarioId);
		
		Set<Grupo> grupos = usuario.getGrupos();
		
		return grupos;
	}
	
	@Transactional
	public Grupo associarUsuarioGrupo(Long usuarioId, Long grupoId) {
		
		Usuario usuario = CadastroUsuarioService.buscarOuFalhar(usuarioId);
		Set<Grupo> grupos = usuario.getGrupos();

		Grupo grupo = cadastroGrupoService.buscarOuFalhar(grupoId);
		
		
		grupos.add(grupo);

		
		
		return usuarioGrupoRepository.save(grupo);
	}
	
	@Transactional
	public void desassociarUsuarioGrupo(Long usuarioId, Long grupoId) {
		
		Usuario usuario = CadastroUsuarioService.buscarOuFalhar(usuarioId);
		Set<Grupo> grupos = usuario.getGrupos();

		Grupo grupo = cadastroGrupoService.buscarOuFalhar(grupoId);
		
		grupos.remove(grupo);

	}
	

	

	


}
