package com.e2r.transportes.controller;



import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.e2r.transportes.controller.page.PageWrapper;
import com.e2r.transportes.model.Estilo;
import com.e2r.transportes.repository.Estilos;
import com.e2r.transportes.repository.filter.EstiloFilter;
import com.e2r.transportes.service.CadastroEstiloService;
import com.e2r.transportes.service.exception.NomeEstiloJaCadastradoException;


@Controller
@RequestMapping("/estilos")
public class EstiloController {
	
	@Autowired
	private CadastroEstiloService cadastroEstiloService;
	
	@Autowired
	private Estilos estilos;
	
	
	
	@RequestMapping("/novo")
	public ModelAndView novo(Estilo estilo) {
		return new ModelAndView("estilo/CadastroEstilo") ;
	}

	@RequestMapping(value="/novo", method = RequestMethod.POST)
	public ModelAndView cadastrar(@Valid Estilo estilo, BindingResult result, RedirectAttributes attributes) {
		if(result.hasErrors()) {
			return novo(estilo);
		}
		
		try {
		cadastroEstiloService.salvar(estilo);
		}catch (NomeEstiloJaCadastradoException e) {
			result.rejectValue("nome", e.getMessage(), e.getMessage());
			return novo(estilo);
		}
		attributes.addFlashAttribute("mensagem", "Estilo salvo com sucesso");
		return new ModelAndView("redirect:/estilos/novo");
	}
	
	@PostMapping
	public @ResponseBody ResponseEntity<?> salvar(@RequestBody @Valid Estilo estilo, BindingResult result) {
		if (result.hasErrors()) {
			return ResponseEntity.badRequest().body(result.getFieldError("nome").getDefaultMessage());
		}
		
		try {
			estilo = cadastroEstiloService.salvar(estilo);
		} catch (NomeEstiloJaCadastradoException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
		
		return ResponseEntity.ok(estilo);
	}
	@GetMapping
	public ModelAndView pesquisar(EstiloFilter estiloFilter, BindingResult result, 
			@PageableDefault(size = 4)Pageable pageable, HttpServletRequest request) {
		
		ModelAndView mv = new ModelAndView("estilo/PesquisaEstilo");
		PageWrapper<Estilo> paginaEstilo = new PageWrapper<>(estilos.filtrar(estiloFilter, pageable), request);
		mv.addObject("pagina", paginaEstilo);
		
		return mv;
	}
}