package com.algaworks.algafood.api.assembler;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.api.model.input.RestauranteDTOInput;
import com.algaworks.algafood.domain.model.Cidade;
import com.algaworks.algafood.domain.model.Restaurante;

@Component
public class RestauranteInputDisassembler {
	
	@Autowired
	private ModelMapper modelMapper;
	
	@PersistenceContext
	private EntityManager entityManager;
	
	public Restaurante dtoToModel(RestauranteDTOInput restauranteDTOInput) {
		return modelMapper.map(restauranteDTOInput, Restaurante.class);
	}
	
	
	public void copyDtoToModel(RestauranteDTOInput restauranteDTOInput, Restaurante restaurante) {
		//restaurante.setCozinha(new Cozinha());
		
		entityManager.detach(restaurante.getCozinha());
		
		if(restaurante.getEndereco() != null) {
		//entityManager.detach(restaurante.getEndereco().getCidade().getEstado());

		restaurante.getEndereco().setCidade(new Cidade());
		}
		
		modelMapper.map(restauranteDTOInput, restaurante);
	}

}
