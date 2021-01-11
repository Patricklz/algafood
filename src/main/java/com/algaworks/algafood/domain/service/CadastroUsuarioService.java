package com.algaworks.algafood.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.algaworks.algafood.domain.exception.GrupoNaoEncontradoException;
import com.algaworks.algafood.domain.exception.UsuarioNaoEncontradoException;
import com.algaworks.algafood.domain.model.Usuario;
import com.algaworks.algafood.domain.repository.UsuarioRepository;

@Service
public class CadastroUsuarioService {

	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Transactional
	public Usuario salvar(Usuario usuario) {

		return usuarioRepository.save(usuario);
	}
	
	
	public Usuario buscarOuFalhar(Long id) {
		Usuario usuario = usuarioRepository.findById(id).orElseThrow(() -> new UsuarioNaoEncontradoException(id)); 
		
		return usuario;
	}
	
	@Transactional
	public void excluir(Long id) {

		try {
			usuarioRepository.deleteById(id);
			usuarioRepository.flush();
			
		}catch(EmptyResultDataAccessException e) {
			throw new GrupoNaoEncontradoException(id);
			
		} 
	}

}
