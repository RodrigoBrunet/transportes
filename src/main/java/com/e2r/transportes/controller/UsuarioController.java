package com.e2r.transportes.controller;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.e2r.transportes.controller.page.PageWrapper;
import com.e2r.transportes.model.Usuario;
import com.e2r.transportes.repository.Grupos;
import com.e2r.transportes.repository.Usuarios;
import com.e2r.transportes.repository.filter.UsuarioFilter;
import com.e2r.transportes.service.CadastroUsuarioService;
import com.e2r.transportes.service.StatusUsuario;
import com.e2r.transportes.service.exception.EmailUsuarioJaCadastradoException;
import com.e2r.transportes.service.exception.SenhaObrigatorioUsuarioException;

@RequestMapping("/usuarios")
@Controller
public class UsuarioController {
	
	@Autowired
	private Grupos grupos;
	
	@Autowired
	private CadastroUsuarioService cadastroUsuarioService;
	
	@Autowired
	private Usuarios usuarios;
	
	@RequestMapping("/novo")
	public ModelAndView novo(Usuario usuario) {
		ModelAndView mv =  new ModelAndView("usuario/CadastroUsuario");
		mv.addObject("grupos", grupos.findAll());
		return mv ;
	}
	@PostMapping("/novo")
	public ModelAndView salvar(@Valid Usuario usuario, BindingResult result, RedirectAttributes attributes) {
		if(result.hasErrors()) {
			return novo(usuario);
		}
		
	
		try {
		cadastroUsuarioService.salvar(usuario);
	}catch(EmailUsuarioJaCadastradoException e){
		result.rejectValue("email", e.getMessage(), e.getMessage());
		return novo(usuario);
	}catch(SenhaObrigatorioUsuarioException e) {
		result.rejectValue("senha", e.getMessage(), e.getMessage());
		return novo(usuario);
	}
				
		attributes.addFlashAttribute("mensagem", "Usuário salvo com sucesso");
		return new ModelAndView("redirect:/usuarios/novo");
   }

	@GetMapping
	public ModelAndView pesquisar(UsuarioFilter usuarioFilter,
			@PageableDefault(size=3) Pageable pageable, HttpServletRequest httpServletRequest) {
		ModelAndView mv = new ModelAndView("/usuario/PesquisaUsuario");
		mv.addObject("grupos", grupos.findAll());
		
		PageWrapper<Usuario> paginaWrapper = new PageWrapper<>(usuarios.filtrar(usuarioFilter, pageable)
				,httpServletRequest);
		mv.addObject("pagina", paginaWrapper);
		return mv;
	}
	
	@PutMapping("/status")
	@ResponseStatus(HttpStatus.OK)
	public void atualizarStatus(@RequestParam("codigos[]") Long[] codigos, @RequestParam("status")StatusUsuario statusUsuario) {
		cadastroUsuarioService.alterarStatus(codigos, statusUsuario);
	}


}