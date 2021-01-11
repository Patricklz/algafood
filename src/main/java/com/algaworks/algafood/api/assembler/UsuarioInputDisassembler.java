package com.algaworks.algafood.api.assembler;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.api.model.DTO.UsuarioDTO;
import com.algaworks.algafood.api.model.input.CidadeDTOInput;
import com.algaworks.algafood.api.model.input.GrupoDTOInput;
import com.algaworks.algafood.api.model.input.UsuarioCadastrarDTOInput;
import com.algaworks.algafood.api.model.input.UsuarioEmailDTOInput;
import com.algaworks.algafood.api.model.input.UsuarioSenhaDTOInput;
import com.algaworks.algafood.domain.model.Cidade;
import com.algaworks.algafood.domain.model.Grupo;
import com.algaworks.algafood.domain.model.Usuario;

@Component
public class UsuarioInputDisassembler {
	
	@Autowired
	private ModelMapper modelMapper;
	
	@PersistenceContext
	private EntityManager entityManager;
	
	public Usuario DTOToModel(UsuarioDTO usuarioDTO) {
		return modelMapper.map(usuarioDTO, Usuario.class);
	}
	
	public void copyDTOToModel(UsuarioDTO usuarioDTO, Usuario usuario) {

		modelMapper.map(usuarioDTO, usuario);
	}
	
	public Usuario DTOToCadastrarModel(UsuarioCadastrarDTOInput usuarioCadastrarDTOInput) {
		return modelMapper.map(usuarioCadastrarDTOInput, Usuario.class);
	}
	
	public void copyDTOToCadastrarModel(UsuarioCadastrarDTOInput usuarioCadastrarDTOInput, Usuario usuario) {

		modelMapper.map(usuarioCadastrarDTOInput, usuario);
	}
	
	
	public Usuario DTOToEmailModel(UsuarioEmailDTOInput usuarioEmailDTOInput) {
		return modelMapper.map(usuarioEmailDTOInput, Usuario.class);
	}
	
	public void copyDTOToEmailModel(UsuarioEmailDTOInput usuarioEmailDTOInput, Usuario usuario) {

		modelMapper.map(usuarioEmailDTOInput, usuario);
	}
	
	public Usuario DTOToSenhaModel(UsuarioSenhaDTOInput usuarioSenhaDTOInput) {
		return modelMapper.map(usuarioSenhaDTOInput, Usuario.class);
	}
	
	public void copyDTOToSenhaModel(UsuarioSenhaDTOInput usuarioSenhaDTOInput, Usuario usuario) {

		modelMapper.map(usuarioSenhaDTOInput, usuario);
	}
	
	
	

}
