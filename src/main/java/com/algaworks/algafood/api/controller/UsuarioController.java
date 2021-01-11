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

import com.algaworks.algafood.api.assembler.UsuarioInputDisassembler;
import com.algaworks.algafood.api.assembler.UsuarioModelAssembler;
import com.algaworks.algafood.api.model.DTO.GrupoDTO;
import com.algaworks.algafood.api.model.DTO.UsuarioDTO;
import com.algaworks.algafood.api.model.input.GrupoDTOInput;
import com.algaworks.algafood.api.model.input.UsuarioCadastrarDTOInput;
import com.algaworks.algafood.api.model.input.UsuarioEmailDTOInput;
import com.algaworks.algafood.domain.exception.EstadoNaoEncontradoException;
import com.algaworks.algafood.domain.exception.NegocioException;
import com.algaworks.algafood.domain.model.Grupo;
import com.algaworks.algafood.domain.model.Usuario;
import com.algaworks.algafood.domain.repository.UsuarioRepository;
import com.algaworks.algafood.domain.service.CadastroUsuarioService;

@RestController
@RequestMapping(value = "/usuarios")
public class UsuarioController {

	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	private CadastroUsuarioService cadastroUsuarioService;
	
	@Autowired
	private UsuarioModelAssembler usuarioModelAssembler;
	
	@Autowired
	private UsuarioInputDisassembler usuarioInputDisassembler;
	
	@GetMapping
	public List<UsuarioDTO> listar() {
		List<Usuario> usuarios = usuarioRepository.findAll();
		
		return usuarioModelAssembler.toCollectDTO(usuarios);
	}
	
	@GetMapping("/{id}")
	public UsuarioDTO buscar(@PathVariable Long id) {
		Usuario usuario = cadastroUsuarioService.buscarOuFalhar(id);
		
		return usuarioModelAssembler.toDTO(usuario);
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public UsuarioDTO adicionar(@RequestBody @Valid UsuarioCadastrarDTOInput usuarioCadastrarDTOInput) {
		try {
			Usuario usuario = usuarioInputDisassembler.DTOToCadastrarModel(usuarioCadastrarDTOInput);
			
			cadastroUsuarioService.salvar(usuario);
			
			return usuarioModelAssembler.toDTO(usuario);
		} catch (EstadoNaoEncontradoException e) {
			throw new NegocioException(e.getMessage(), e);
		}
	}
	
	@PutMapping("/{id}")
	public UsuarioDTO atualizar(@PathVariable Long id, @RequestBody @Valid UsuarioEmailDTOInput usuarioEmailDTOInput) {
		try {
			Usuario usuarioEmail = cadastroUsuarioService.buscarOuFalhar(id);
			
			usuarioInputDisassembler.copyDTOToEmailModel(usuarioEmailDTOInput, usuarioEmail);
			
			usuarioEmail = cadastroUsuarioService.salvar(usuarioEmail);
			
			return usuarioModelAssembler.toDTO(usuarioEmail);
		} catch (EstadoNaoEncontradoException e) {
			throw new NegocioException(e.getMessage(), e);
		}
	}
	
//	@DeleteMapping("/{id}")
//	@ResponseStatus(HttpStatus.NO_CONTENT)
//	public void remover(@PathVariable Long id) {
//		cadastroGrupoService.excluir(id);	
//	}
}
