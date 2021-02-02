package com.algaworks.algafood.domain.service;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.algaworks.algafood.domain.exception.PermissaoNaoEncontradoException;
import com.algaworks.algafood.domain.model.Grupo;
import com.algaworks.algafood.domain.model.Permissao;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.model.Usuario;
import com.algaworks.algafood.domain.repository.RestauranteRepository;

@Service
public class CadastroRestauranteResponsavelService {
	
    private static final String MSG_FORMA_PAGAMENTO_EM_USO 
    = "Permissão de código %d não pode ser removida, pois está em uso";
	
	@Autowired
	private CadastroRestauranteService cadastroRestauranteService;
	
	@Autowired
	private CadastroUsuarioService cadastroUsuarioService;
	
	@Autowired
	private RestauranteRepository restauranteRepository;
	
	
	@Transactional
	public Set<Usuario> listar(Long restauranteId) {
		
		Restaurante restaurante = cadastroRestauranteService.buscarOuFalhar(restauranteId);
		Set<Usuario> usuarios = restaurante.getUsuarios();
		
		return usuarios;
	}
	
	
	@Transactional
	public void associar(Long restauranteId, Long responsavelId) {
		
		Restaurante restaurante = cadastroRestauranteService.buscarOuFalhar(restauranteId);
		Usuario usuario = cadastroUsuarioService.buscarOuFalhar(responsavelId);
		
		Set<Usuario> usuarios = restaurante.getUsuarios();

		usuarios.add(usuario);
		
		
		 restauranteRepository.save(restaurante);
	}
	
	
	
	@Transactional
	public void desassociar(Long restauranteId, Long responsavelId) {
		
		Restaurante restaurante = cadastroRestauranteService.buscarOuFalhar(restauranteId);
		Usuario usuario = cadastroUsuarioService.buscarOuFalhar(responsavelId);
		
		Set<Usuario> usuarios = restaurante.getUsuarios();
		
		usuarios.remove(usuario);
				
	}
	


}
