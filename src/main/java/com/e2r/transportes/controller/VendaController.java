package com.e2r.transportes.controller;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.e2r.transportes.model.Cerveja;
import com.e2r.transportes.model.Venda;
import com.e2r.transportes.repository.Cervejas;
import com.e2r.transportes.security.UsuarioSistema;
import com.e2r.transportes.service.CadastroVendaService;
import com.e2r.transportes.session.TabelaItensSession;

@Controller
@RequestMapping("/vendas")
public class VendaController {
	
	@Autowired
	private Cervejas cervejas;
	
	@Autowired
	private TabelaItensSession tabelaItens;
	
	@Autowired
	private CadastroVendaService cadastroVendaService;
	
	@GetMapping("/nova")
	public ModelAndView nova(Venda venda) {
		ModelAndView mv = new ModelAndView("venda/CadastroVenda");
		venda.setUuid(UUID.randomUUID().toString());
		return mv ;
	}
	
	@PostMapping("/nova")
	public ModelAndView salvar(Venda venda, RedirectAttributes attributes, @AuthenticationPrincipal UsuarioSistema usuarioSistema) {
		
		venda.setUsuario(usuarioSistema.getUsuario());
		venda.setItens(tabelaItens.getItens(venda.getUuid()));
		
		cadastroVendaService.salvar(venda);
		attributes.addFlashAttribute("mensagem", "Venda salva com sucesso");
		return new ModelAndView("redirect:/vendas/nova");
	}
	
	@PostMapping("/item")
	public ModelAndView adicionarItem(Long codigoCerveja, String uuid) {
		Cerveja cerveja = cervejas.findOne(codigoCerveja);
		tabelaItens.adicionarItem(uuid, cerveja, 1);
		return mvTabelaItensVenda(uuid);
	}
	
	@PutMapping("/item/{codigoCerveja}")
	public ModelAndView alterarQuantidadeItem(@PathVariable Long codigoCerveja, Integer quantidade, String uuid) {
		Cerveja cerveja = cervejas.findOne(codigoCerveja);
		tabelaItens.alterarQuantidadeItens(uuid, cerveja, quantidade);
		return mvTabelaItensVenda(uuid);
	}
	
	@DeleteMapping("/item/{uuid}/{codigoCerveja}")
	public ModelAndView excluirItem(@PathVariable Long codigoCerveja, @PathVariable String uuid) {
		Cerveja cerveja = cervejas.findOne(codigoCerveja);
		tabelaItens.excluirItem(uuid, cerveja);
		return mvTabelaItensVenda(uuid);
		
	}
	
	private ModelAndView mvTabelaItensVenda(String uuid) {
		ModelAndView mv = new ModelAndView("venda/TabelaItensVenda");
		mv.addObject("itens", tabelaItens.getItens(uuid));
		mv.addObject("valorTotal", tabelaItens.getValorTotal(uuid));
		return mv;
	}
	
	

}
