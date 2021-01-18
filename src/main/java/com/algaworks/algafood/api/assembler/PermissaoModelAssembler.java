package com.algaworks.algafood.api.assembler;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.api.model.DTO.PermissaoDTO;
import com.algaworks.algafood.domain.model.Permissao;

@Component
public class PermissaoModelAssembler {
	
	@Autowired
	private ModelMapper modelMapper;
	
	public PermissaoDTO toDTO(Permissao permissao) {
		
		return modelMapper.map(permissao, PermissaoDTO.class);
		
	}
	
	public List<PermissaoDTO> toCollectDTO(Collection<Permissao> permissoes) {
		
		return permissoes.stream().map(permissao -> modelMapper.map(permissao, PermissaoDTO.class)).collect(Collectors.toList());
	}

}
