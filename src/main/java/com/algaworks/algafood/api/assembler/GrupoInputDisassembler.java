package com.algaworks.algafood.api.assembler;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.api.model.input.CidadeDTOInput;
import com.algaworks.algafood.api.model.input.GrupoDTOInput;
import com.algaworks.algafood.domain.model.Cidade;
import com.algaworks.algafood.domain.model.Grupo;

@Component
public class GrupoInputDisassembler {
	
	@Autowired
	private ModelMapper modelMapper;
	
	@PersistenceContext
	private EntityManager entityManager;
	
	public Grupo DTOToModel(GrupoDTOInput grupoDTOInput) {
		return modelMapper.map(grupoDTOInput, Grupo.class);
	}
	
	public void copyDTOToModel(GrupoDTOInput grupoDTOInput, Grupo grupo) {

		modelMapper.map(grupoDTOInput, grupo);
	}
	
	

}
