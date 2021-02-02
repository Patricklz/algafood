package com.algaworks.algafood.api.controller;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.api.assembler.UsuarioModelAssembler;
import com.algaworks.algafood.api.model.DTO.UsuarioDTO;
import com.algaworks.algafood.domain.model.Usuario;
import com.algaworks.algafood.domain.service.CadastroRestauranteResponsavelService;

@RestController
@RequestMapping(value = "/restaurantes/{restauranteId}/responsaveis", produces = MediaType.APPLICATION_JSON_VALUE)
public class RestauranteResponsavelController {


	@Autowired
	private CadastroRestauranteResponsavelService cadastroRestauranteResponsavelService;
	
	@Autowired
	private UsuarioModelAssembler usuarioModelAssembler;


	@GetMapping
	public List<UsuarioDTO> listar(@PathVariable Long restauranteId) {
		Set<Usuario> usuarios = cadastroRestauranteResponsavelService.listar(restauranteId);
		return usuarioModelAssembler.toCollectDTO(usuarios);
	}
	
	@PutMapping("/{responsavelId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void associar(@PathVariable Long restauranteId, @PathVariable Long responsavelId) {
		cadastroRestauranteResponsavelService.associar(restauranteId, responsavelId);
		
	}
	
	@DeleteMapping("/{responsavelId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void desassociar(@PathVariable Long restauranteId, @PathVariable Long responsavelId) {
		
		cadastroRestauranteResponsavelService.desassociar(restauranteId, responsavelId);
		
	}
	


	
		
	
}
