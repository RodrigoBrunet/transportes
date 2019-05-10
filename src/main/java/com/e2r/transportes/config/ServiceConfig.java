package com.e2r.transportes.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import com.e2r.transportes.service.CadastroCervejaService;
import com.e2r.transportes.storage.FotoStorage;
import com.e2r.transportes.storage.FotoStorageLocal;

@Configuration
@ComponentScan(basePackageClasses=CadastroCervejaService.class)
public class ServiceConfig {
	
	@Bean
	public FotoStorage fotoStorage() {
		
		return new FotoStorageLocal();
		
	}

}
