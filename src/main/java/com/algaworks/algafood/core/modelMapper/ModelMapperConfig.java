package com.algaworks.algafood.core.modelMapper;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.algaworks.algafood.api.model.DTO.EnderecoDTO;
import com.algaworks.algafood.domain.model.Endereco;

@Configuration
public class ModelMapperConfig {
	
	@Bean
	public ModelMapper modelMapper() {
		ModelMapper modelMapper = new ModelMapper();
		
		var enderecoToenderecoDTOTypeMap = modelMapper.createTypeMap(Endereco.class, EnderecoDTO.class);
		enderecoToenderecoDTOTypeMap.<String>addMapping(
				enderecoSrc -> enderecoSrc.getCidade().getEstado().getNome(),
				(EnderecoDTOdest, value) -> EnderecoDTOdest.getCidade().setEstado(value));
		
		
		return modelMapper;
	}

}
