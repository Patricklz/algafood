package com.algaworks.algafood.api.assembler;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.api.model.input.CidadeDTOInput;
import com.algaworks.algafood.domain.model.Cidade;

@Component
public class CidadeInputDisassembler {
	
	@Autowired
	private ModelMapper modelMapper;
	
	@PersistenceContext
	private EntityManager entityManager;
	
	public Cidade DTOToModel(CidadeDTOInput cidadeDTOInput) {
		return modelMapper.map(cidadeDTOInput, Cidade.class);
	}
	
	public void copyDTOToModel(CidadeDTOInput cidadeDTOInput, Cidade cidade) {
		entityManager.detach(cidade.getEstado());
		modelMapper.map(cidadeDTOInput, cidade);
	}
	
	

}
