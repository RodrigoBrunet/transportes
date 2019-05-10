package com.e2r.transportes.storage;

import org.springframework.web.context.request.async.DeferredResult;
import org.springframework.web.multipart.MultipartFile;

import com.e2r.transportes.dto.FotoDTO;

public class FotoStorageRunnable implements Runnable{

	private MultipartFile[] files;
	private DeferredResult<FotoDTO> resultado;
	private FotoStorage fotoStorage;
	
	public FotoStorageRunnable(MultipartFile[] files, DeferredResult<FotoDTO> resultado, FotoStorage fotoStorage) {
		this.files = files;
		this.resultado = resultado;
		this.fotoStorage = fotoStorage;
	}
	
	@Override
	public void run() {
				
		String novoNome = this.fotoStorage.salvarTemporariamente(files);
		String nomeFoto = novoNome;
		String contentType = files[0].getContentType();
		resultado.setResult(new FotoDTO(nomeFoto, contentType));
		
	}
	
	
}
