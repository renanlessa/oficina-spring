package com.lessa.vinhos.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.lessa.vinhos.model.TipoVinho;
import com.lessa.vinhos.model.Vinho;
import com.lessa.vinhos.repository.VinhoRepository;

@Controller
@RequestMapping("/vinhos")
public class VinhosController {
	
	@Autowired
	private VinhoRepository repository;
	
	@DeleteMapping("/{id}")
	public String remover(@PathVariable Long id, RedirectAttributes attributes) {
		repository.deleteById(id);
		attributes.addFlashAttribute("mensagem", "Vinho removido com sucesso!");
		return "redirect:/vinhos";
	}
	
	@GetMapping
	public ModelAndView listar() {
		ModelAndView view = new ModelAndView("vinhos/lista-vinhos");
		view.addObject("vinhos", repository.findAll());
		return view;
	}
	
	@GetMapping("/novo")
	public ModelAndView novo(Vinho vinho) {
		ModelAndView view = new ModelAndView("vinhos/cadastro-vinho");
		view.addObject("vinho", vinho);
		view.addObject("tipos", TipoVinho.values());
		return view;
	}
	
	@GetMapping("/{id}")
	public ModelAndView editar(@PathVariable Long id) {
		return this.novo(repository.findById(id).get());
	}
	
	@PostMapping("/novo")
	public ModelAndView salvar(@Valid Vinho vinho, BindingResult result, RedirectAttributes attributes) {
		
		if(result.hasErrors()) {
			return this.novo(vinho);
		}
		
		repository.save(vinho);
		
		attributes.addFlashAttribute("mensagem", "Vinho salvo com sucesso");
		
		return new ModelAndView("redirect:/vinhos/novo");
	}
}