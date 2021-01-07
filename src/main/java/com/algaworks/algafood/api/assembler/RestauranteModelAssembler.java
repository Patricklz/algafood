package com.algaworks.algafood.api.assembler;

import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.api.model.DTO.RestauranteDTO;
import com.algaworks.algafood.api.model.DTO.RestauranteDTOInput;
import com.algaworks.algafood.domain.model.Restaurante;

@Component
public class RestauranteModelAssembler {
	
	@Autowired
	private ModelMapper modelMapper;
	
	
	public RestauranteDTO toDTO(Restaurante restaurante) {
		return modelMapper.map(restaurante, RestauranteDTO.class);
	}

	
	public List<RestauranteDTO> toCollectDTO(List<Restaurante> restaurantes) {
		
		return restaurantes.stream().map(restaurante -> modelMapper.map(restaurante, RestauranteDTO.class)).collect(Collectors.toList());
	}

}
