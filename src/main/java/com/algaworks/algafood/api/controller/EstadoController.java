package com.algaworks.algafood.api.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.domain.repository.EstadoRepository;

@RestController
@RequestMapping("/estados")
public class EstadoController {
	
	@Autowired
	private EstadoRepository estadoRepository;
	
	@GetMapping
	public List<EstadoDTO> listar() {
		var estados = estadoRepository.findAll();
		List<EstadoDTO> retorno = new ArrayList<EstadoDTO>();
		
		for (var estado : estados) {
			var estadoDTO = new EstadoDTO();
			estadoDTO.setId(estado.getId());
			estadoDTO.setNome(estado.getNome());
			
			retorno.add(estadoDTO);
			
		}
		
		return retorno;
	}
}
