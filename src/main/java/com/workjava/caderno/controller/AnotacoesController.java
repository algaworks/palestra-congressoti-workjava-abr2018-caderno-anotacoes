package com.workjava.caderno.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.workjava.caderno.AnotacaoService;
import com.workjava.caderno.model.Anotacao;
import com.workjava.caderno.respository.AnotacaoRepository;

@Controller
@RequestMapping("/anotacoes")
public class AnotacoesController {
	
	@Autowired
	private AnotacaoRepository repository;
	
	@Autowired
	private AnotacaoService service;
	
	@PostMapping("/{id}/editar")
	public ModelAndView atualizar(@PathVariable Long id,
			@Valid Anotacao anotacao, 
			BindingResult bindingResult,
			RedirectAttributes redirectAttributes) {
		if (bindingResult.hasErrors()) {
			return nova(anotacao);
		}
		
		service.atualizar(id, anotacao);
		
		redirectAttributes.addFlashAttribute("mensagem", "Anotação atualizada com sucesso!");
		
		return new ModelAndView("redirect:/anotacoes/nova");
	}
	
	@GetMapping("/{id}/editar")
	public ModelAndView editar(@PathVariable Long id) {
		return nova(repository.getOne(id));
	}
	
	@PostMapping("/nova")
	public ModelAndView criar(@Valid Anotacao anotacao, BindingResult bindingResult,
			RedirectAttributes redirectAttributes) {
		if (bindingResult.hasErrors()) {
			return nova(anotacao);
		}
		
		repository.save(anotacao);
		
		redirectAttributes.addFlashAttribute("mensagem", "Anotação criada com sucesso!");
		
		return new ModelAndView("redirect:/anotacoes/nova");
	}
	
	@GetMapping("/nova")
	public ModelAndView nova(Anotacao anotacao) {
		ModelAndView mv = new ModelAndView("anotacoes/anotacoes-cadastro");
		mv.addObject("anotacao", anotacao);
		return mv;
	}
	
	@GetMapping
	public ModelAndView listar() {
		ModelAndView mv = new ModelAndView("anotacoes/anotacoes-lista");
		mv.addObject("anotacoes", repository.findAll());
		return mv;
	}
}
