package com.algaworks.algafood.api.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.SmartValidator;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.api.assembler.GenericDTOAssembler;
import com.algaworks.algafood.api.assembler.GenericDTODissembler;
import com.algaworks.algafood.api.model.DTO.RestauranteDTO;
import com.algaworks.algafood.api.model.DTO.RestauranteDTOInput;
import com.algaworks.algafood.domain.exception.CozinhaNaoEncontradaException;
import com.algaworks.algafood.domain.exception.NegocioException;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.repository.RestauranteRepository;
import com.algaworks.algafood.domain.service.CadastroRestauranteService;

@RestController
@RequestMapping(value = "/restaurantes", produces = MediaType.APPLICATION_JSON_VALUE)
public class RestauranteController {

	@Autowired
	private RestauranteRepository restauranteRepository;

	@Autowired
	private CadastroRestauranteService cadastroRestauranteService;
	
	@Autowired
	private SmartValidator validator;
	
	@Autowired
	private GenericDTOAssembler<Restaurante, RestauranteDTO> genericDTOAssembler;
	
	@Autowired
	private GenericDTODissembler<RestauranteDTOInput, Restaurante> genericDTODissembler;

	@GetMapping
	public List<RestauranteDTO> listar() {
		return genericDTOAssembler.toCollectDTO(restauranteRepository.findAll(), RestauranteDTO.class);
	}

	@GetMapping("/{id}")
	public RestauranteDTO buscar(@PathVariable Long id) {
		
		Restaurante restaurante = cadastroRestauranteService.buscarOuFalhar(id);
		
		return genericDTOAssembler.toDTO(restaurante, RestauranteDTO.class);

	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public RestauranteDTO adicionar(@RequestBody @Valid RestauranteDTOInput restauranteDTOInput) {
		try {
			
			Restaurante restaurante = genericDTODissembler.dtoToModel(restauranteDTOInput, Restaurante.class);
			
			return genericDTOAssembler.toDTO(cadastroRestauranteService.salvar(restaurante), RestauranteDTO.class);
		}catch(CozinhaNaoEncontradaException e) {
			throw new NegocioException(e.getMessage(), e);
		}

	}
	

	@PutMapping("/{id}")
	public RestauranteDTO atualizar(@PathVariable Long id, @RequestBody @Valid RestauranteDTOInput restauranteDTOInput) {
		try {
			Restaurante restauranteAtual = cadastroRestauranteService.buscarOuFalhar(id);
				
			
			genericDTODissembler.dtoToModel(restauranteDTOInput, Restaurante.class);
			restauranteAtual =cadastroRestauranteService.salvar(restauranteAtual);
			
			
			
			return genericDTOAssembler.toDTO(restauranteAtual, RestauranteDTO.class);
			}catch(CozinhaNaoEncontradaException e) {
				throw new NegocioException(e.getMessage(), e);
			}
			
		
	}
	
//	@PatchMapping("/{id}")
//	public RestauranteDTO atualizacaoParcial(@PathVariable Long id, @RequestBody Map<String, Object> campos, HttpServletRequest request) {
//		
//		Restaurante restauranteAtual = cadastroRestauranteService.buscarOuFalhar(id);
//		 
//		merge(campos, restauranteAtual, request);
//		
//		validate(restauranteAtual, "restaurante");
//		
//		return atualizar(id, restauranteDTOAssembler.toDTO(restauranteAtual));
//	}
//
//	
//	private void validate(Restaurante restaurante, String objectName) {
//		BeanPropertyBindingResult bindingResult = new BeanPropertyBindingResult(restaurante, objectName);
//		
//		validator.validate(restaurante, bindingResult);
//		
//		if (bindingResult.hasErrors()) {
//			throw new ValidacaoException(bindingResult);
//		}
//		
//	}
//
//	private void merge(Map<String, Object> camposOrigem, Restaurante restauranteDestino, HttpServletRequest request) {
//		ServletServerHttpRequest serverHttpRequest = new ServletServerHttpRequest(request);
//		
//		try {
//		ObjectMapper objectMapper = new ObjectMapper();
//		objectMapper.configure(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES, true);
//		
//		Restaurante restauranteOrigem = objectMapper.convertValue(camposOrigem, Restaurante.class);
//		
//		camposOrigem.forEach((nomePropriedade, valorPropriedade) -> {
//			Field field = ReflectionUtils.findField(Restaurante.class, nomePropriedade);
//			field.setAccessible(true);
//			
//			Object novoValor = ReflectionUtils.getField(field, restauranteOrigem);
//			
//			ReflectionUtils.setField(field, restauranteDestino, novoValor);
//		});
//		} catch (IllegalArgumentException e) {
//			Throwable rootCause = ExceptionUtils.getRootCause(e);
//			throw new HttpMessageNotReadableException(e.getMessage(), rootCause, serverHttpRequest);
//		}
//	}
	
	@DeleteMapping("/{id}")
	public void remover(@PathVariable Long id) {
			cadastroRestauranteService.excluir(id);
	}
		
	
}
