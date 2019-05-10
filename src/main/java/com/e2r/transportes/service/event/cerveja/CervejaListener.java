package com.e2r.transportes.service.event.cerveja;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import com.e2r.transportes.storage.FotoStorage;

@Component
public class CervejaListener {

		@Autowired
		private FotoStorage fotoStorage;
		
		@EventListener(condition = "#evento.temFoto()")
		public void cervejaSalva(CervejaSalvaEvent evento) {
			fotoStorage.salvar(evento.getCerveja().getFoto());
		}
}
