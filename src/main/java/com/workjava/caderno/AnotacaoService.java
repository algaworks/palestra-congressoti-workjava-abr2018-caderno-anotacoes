package com.workjava.caderno;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.workjava.caderno.model.Anotacao;
import com.workjava.caderno.respository.AnotacaoRepository;

@Service
public class AnotacaoService {
	
	@Autowired
	private AnotacaoRepository repository;
	
	public Anotacao atualizar(Long id, Anotacao anotacao) {
		Anotacao anotacaoSalva = repository.getOne(id);
		
		BeanUtils.copyProperties(anotacao, anotacaoSalva, "id", "cadastro");
		
		return repository.save(anotacaoSalva);
	}
}
