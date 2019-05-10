package com.e2r.transportes.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.e2r.transportes.controller.page.PageWrapper;
import com.e2r.transportes.dto.CervejaDTO;
import com.e2r.transportes.model.Cerveja;
import com.e2r.transportes.model.Origem;
import com.e2r.transportes.model.Sabor;
import com.e2r.transportes.repository.Cervejas;
import com.e2r.transportes.repository.Estilos;
import com.e2r.transportes.repository.filter.CervejaFilter;
import com.e2r.transportes.service.CadastroCervejaService;

@Controller
@RequestMapping("/cervejas")
public class CervejaController {

	@Autowired
	private Estilos estilos;
	
	@Autowired
	private CadastroCervejaService cadastroCervejaService;
	
	@Autowired
	private Cervejas cervejas;
	
	@RequestMapping("/novo")
	public ModelAndView novo(Cerveja cerveja) {
		ModelAndView mv = new ModelAndView("cerveja/CadastroCerveja");
		mv.addObject("sabores", Sabor.values());
		mv.addObject("estilos", estilos.findAll());
		mv.addObject("origens", Origem.values());
		
		return mv;
	}
	@RequestMapping(value = "/novo", method = RequestMethod.POST)
	public ModelAndView Cadastrar(@Valid Cerveja cerveja, BindingResult result, Model model, RedirectAttributes atributes) {
		if(result.hasErrors()) {
					throw new RuntimeException();
			//return novo(cerveja);
		
		}
		cadastroCervejaService.salvar(cerveja);
		atributes.addFlashAttribute("mensagem", "cerveja salva com sucesso!!!");
		return new ModelAndView("redirect:/cervejas/novo");
		
	}
	@GetMapping
	public ModelAndView pesquisar(CervejaFilter cervejaFilter, BindingResult result, @PageableDefault(size=2) 
	Pageable pageable, HttpServletRequest httpServletRequest) {
		ModelAndView mv = new ModelAndView("cerveja/PesquisaCerveja");
		mv.addObject("estilos", estilos.findAll());
		mv.addObject("sabores", Sabor.values());
		mv.addObject("origens", Origem.values());
		
		PageWrapper<Cerveja> paginaWrapper = new PageWrapper<>( cervejas.filtrar(cervejaFilter, pageable)
				,httpServletRequest);
		mv.addObject("pagina", paginaWrapper);
		return mv;
	}
	
	@RequestMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody List<CervejaDTO> pesquisar(String skuOuNome){
		return cervejas.porSkuOuNome(skuOuNome);
	}

}
