package com.algaworks.algafood.domain.service;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.algaworks.algafood.domain.exception.PermissaoNaoEncontradoException;
import com.algaworks.algafood.domain.model.Grupo;
import com.algaworks.algafood.domain.model.Permissao;
import com.algaworks.algafood.domain.repository.PermissaoRepository;

@Service
public class CadastroPermissaoService {
	
    private static final String MSG_FORMA_PAGAMENTO_EM_USO 
    = "Permissão de código %d não pode ser removida, pois está em uso";
	
	@Autowired
	private PermissaoRepository permissaoRepository;
	
	@Autowired
	private CadastroGrupoService cadastroGrupoService;
	
	
	@Transactional
	public Set<Permissao> listar(Long grupoId) {
		
		Grupo grupo = cadastroGrupoService.buscarOuFalhar(grupoId);
		Set<Permissao> permissoes = grupo.getPermissoes();
		
		return permissoes;
	}
	
	
	@Transactional
	public Permissao associar(Long grupoId, Long permissaoId) {
		
		Grupo grupo = cadastroGrupoService.buscarOuFalhar(grupoId);
		Set<Permissao> permissoes = grupo.getPermissoes();
		Permissao permissao = buscarOuFalhar(permissaoId);
		
		permissoes.add(permissao);
		
		
		return permissaoRepository.save(permissao);
	}
	
	@Transactional
	public Permissao desassociar(Long grupoId, Long permissaoId) {
		
		Grupo grupo = cadastroGrupoService.buscarOuFalhar(grupoId);
		Set<Permissao> permissoes = grupo.getPermissoes();
		Permissao permissao = buscarOuFalhar(permissaoId);
		
		permissoes.remove(permissao);
		
		
		return permissaoRepository.save(permissao);
	}
	
	public Permissao buscarOuFalhar(Long id) {
		Permissao permissao = permissaoRepository.findById(id).orElseThrow(() -> new PermissaoNaoEncontradoException(id)); 
		
		return permissao;
	}
	
	
	

//	
//	@Transactional
//	public void excluir(Long id) {
//
//		try {
//			formaPagamentoRepository.deleteById(id);
//			formaPagamentoRepository.flush();
//			
//		}catch(EmptyResultDataAccessException e) {
//			throw new FormaPagamentoNaoEncontradaException(id);
//			
//		}
//		catch (DataIntegrityViolationException e) {
//            throw new EntidadeEmUsoException(
//                    String.format(MSG_FORMA_PAGAMENTO_EM_USO, id));
//            }
//	}
//
//	public FormaPagamento buscarOuFalhar(Long id) {
//		FormaPagamento formaPagamento = formaPagamentoRepository.findById(id).orElseThrow(() -> new FormaPagamentoNaoEncontradaException(id)); 
//		
//		return formaPagamento;
//	}
	

}
