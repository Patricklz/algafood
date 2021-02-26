package com.algaworks.algafood.core.modelMapper;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.algaworks.algafood.api.model.DTO.EnderecoDTO;
import com.algaworks.algafood.api.model.input.ItemPedidoDTOInput;
import com.algaworks.algafood.domain.model.Endereco;
import com.algaworks.algafood.domain.model.ItemPedido;

@Configuration
public class ModelMapperConfig {
	
	@Bean
	public ModelMapper modelMapper() {
		ModelMapper modelMapper = new ModelMapper();
		
		var enderecoToenderecoDTOTypeMap = modelMapper.createTypeMap(Endereco.class, EnderecoDTO.class);
		enderecoToenderecoDTOTypeMap.<String>addMapping(
				enderecoSrc -> enderecoSrc.getCidade().getEstado().getNome(),
				(EnderecoDTOdest, value) -> EnderecoDTOdest.getCidade().setEstado(value));		
		
		modelMapper.createTypeMap(ItemPedidoDTOInput.class, ItemPedido.class)
	    .addMappings(mapper -> mapper.skip(ItemPedido::setId)); 
		
		return modelMapper;
	}

}
