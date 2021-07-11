package com.algaworks.algafood.domain.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.algaworks.algafood.domain.model.FotoProduto;
import com.algaworks.algafood.domain.model.Produto;
import com.algaworks.algafood.domain.model.Restaurante;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Long>, ProdutoRepositoryQueries{

	//@Query("select p from Produto p where p.restaurante.id = :id")
	List<Produto> findAllByRestauranteId(Long id);
	Optional<Produto> findByRestauranteIdAndId(Long restauranteId, Long produtoId);
	
	
	@Query("from Produto p where p.ativo = true and p.restaurante = :restaurante" )
	List<Produto> findAtivosByRestaurante(Restaurante restaurante);
	

	@Query("select f from FotoProduto f join f.produto p "
			+ "where p.restaurante.id = :restauranteId and f.produto.id = :produtoId")
	Optional<FotoProduto> findFotoById(Long restauranteId, Long produtoId);
	
	
}
