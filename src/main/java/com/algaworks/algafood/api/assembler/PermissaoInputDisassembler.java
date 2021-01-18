package com.algaworks.algafood.api.assembler;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.api.model.input.PermissaoDTOInput;
import com.algaworks.algafood.domain.model.Permissao;

@Component
public class PermissaoInputDisassembler {
	
	@Autowired
	private ModelMapper modelMapper;
	
	@PersistenceContext
	private EntityManager entityManager;
	
	public Permissao DTOToModel(PermissaoDTOInput permissaoDTOInput) {
		return modelMapper.map(permissaoDTOInput, Permissao.class);
	}
	
	public void copyDTOToModel(PermissaoDTOInput permissaoDTOInput, Permissao permissao) {

		modelMapper.map(permissaoDTOInput, permissao);
	}
	
	

}
