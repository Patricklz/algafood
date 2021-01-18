package com.algaworks.algafood.domain.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.algaworks.algafood.domain.model.Produto;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Long>{

	//@Query("select p from Produto p where p.restaurante.id = :id")
	List<Produto> findAllByRestauranteId(Long id);
	Optional<Produto> findByRestauranteIdAndId(Long restauranteId, Long produtoId);
	

}
