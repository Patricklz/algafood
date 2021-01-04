package com.algaworks.algafood.api.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.api.model.DTO.RestauranteDTOInput;
import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.model.Restaurante;

@Component
public class RestauranteDTODissembler {
	
	@Autowired
	private ModelMapper modelMapper;
	
	public Restaurante DTOToRestaurante(RestauranteDTOInput restauranteDTOInput) {
		return modelMapper.map(restauranteDTOInput, Restaurante.class);
	}
	
	
	public Restaurante DTOToRestaurante(RestauranteDTOInput restauranteDTOInput, Restaurante restaurante) {
		restaurante.setCozinha(new Cozinha());
		
		return modelMapper.map(restauranteDTOInput, Restaurante.class);
	}


}
