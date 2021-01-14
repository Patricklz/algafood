package com.algaworks.algafood.domain.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.algaworks.algafood.api.model.input.UsuarioSenhaDTOInput;
import com.algaworks.algafood.domain.exception.GrupoNaoEncontradoException;
import com.algaworks.algafood.domain.exception.NegocioException;
import com.algaworks.algafood.domain.exception.UsuarioNaoEncontradoException;
import com.algaworks.algafood.domain.model.Usuario;
import com.algaworks.algafood.domain.repository.UsuarioRepository;

@Service
public class CadastroUsuarioService {

	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Transactional
	public Usuario salvar(Usuario usuario) {
		usuarioRepository.detach(usuario);
		
		Optional<Usuario> usuarioExistente = usuarioRepository.findeByEmail(usuario.getEmail());
		
		if(usuarioExistente.isPresent() && !usuarioExistente.get().equals(usuario)) {
			throw new NegocioException(String.format("Já existe um usuário cadastrado com o e-mail", usuario.getEmail()));
		}
		
		return usuarioRepository.save(usuario);
	}
	
	@Transactional
	public void alterarSenha(Long id, UsuarioSenhaDTOInput usuarioSenhaDTOInput) {
		
		Usuario usuario = buscarOuFalhar(id);
		if (usuario.senhaNaoCoincideCom(usuarioSenhaDTOInput.getSenhaAtual())) {
			throw new NegocioException("Senha atual informada não coincide com a senha do usuário.");
		}
		usuario.setSenha(usuarioSenhaDTOInput.getSenhaNova());
		usuarioRepository.save(usuario);
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
