package com.algaworks.algafood.api.assembler;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.api.model.DTO.GrupoDTO;
import com.algaworks.algafood.domain.model.Grupo;

@Component
public class UsuarioGrupoInputDisassembler {
	
	@Autowired
	private ModelMapper modelMapper;
	
	@PersistenceContext
	private EntityManager entityManager;
	
	public Grupo DTOToModel(GrupoDTO grupoDTO) {
		return modelMapper.map(grupoDTO, Grupo.class);
	}
	
	public void copyDTOToModel(GrupoDTO grupoDTO, Grupo grupo) {

		modelMapper.map(grupoDTO, grupo);
	}
	

	
	
	

}
