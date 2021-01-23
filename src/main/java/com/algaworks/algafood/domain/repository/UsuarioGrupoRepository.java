package com.algaworks.algafood.domain.repository;

import org.springframework.stereotype.Repository;

import com.algaworks.algafood.domain.model.Grupo;

@Repository
public interface UsuarioGrupoRepository extends CustomJpaRepository<Grupo, Long>{
	
//	List<Grupo> findAllByGruposByUsuarioId(Long grupoId, Long usuarioId);

}
