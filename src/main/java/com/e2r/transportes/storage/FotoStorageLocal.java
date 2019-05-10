package com.e2r.transportes.storage;
import static java.nio.file.FileSystems.getDefault;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.UUID;

import org.springframework.web.multipart.MultipartFile;

import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.name.Rename;

public class FotoStorageLocal implements FotoStorage {
	 
	private Path local;
	private Path localTemporario;
	
	public FotoStorageLocal() {
		this(getDefault().getPath(".tranportesfotos"));
		
	}
	
	public FotoStorageLocal(Path path) {
		this.local = path;
		criarPastas();
		
	}
	
	@Override
	public void salvar(String foto) {
		try {
			Files.move(this.localTemporario.resolve(foto), this.local.resolve(foto));
		} catch (IOException e) {
			throw new RuntimeException("erro movendo foto para destino final", e);
		}
		
		try {
			Thumbnails.of(this.local.resolve(foto).toString()).size(40, 68).toFiles(Rename.PREFIX_DOT_THUMBNAIL);
		} catch (IOException e) {
			throw new RuntimeException("erro redimensionando a foto", e);			
		}
	}
	
	@Override
	public byte[] recuperar(String nome) {
		
		try {
			return Files.readAllBytes(this.local.resolve(nome));
		} catch (IOException e) {
			throw new RuntimeException("erro lendo foto", e);
		}
		
	}
	
	private void criarPastas() {
		try {
			Files.createDirectories(this.local);
			this.localTemporario = getDefault().getPath(this.local.toString(), "temp");
			Files.createDirectories(this.localTemporario);
		}catch(IOException e) {
			throw new RuntimeException("erro ao criar pasta para salvar foto", e);
		}
	}
	
	@Override
	public byte[] recuperarFotoTemporaria(String nome) {
		
		try {
			return Files.readAllBytes(this.localTemporario.resolve(nome));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			throw new RuntimeException("erro lendo foto temporaria", e);
		}
	}
	
	@Override
	public String salvarTemporariamente(MultipartFile[] files)  {
		String novoNome = null;
		if(files != null && files.length>0) {
			MultipartFile arquivo = files[0];
			novoNome = renomearArquivo(arquivo.getOriginalFilename());
			try {
				arquivo.transferTo(new File(this.localTemporario.toAbsolutePath().toString() + getDefault().getSeparator()
						+ novoNome));	
			}catch (IOException e) {
				throw new RuntimeException("erro salvando foto na pasta temporária", e);
			}
			
			
		}
	
		return novoNome;
	}
	
	public String renomearArquivo(String nomeOriginal) {
		String novoNome = UUID.randomUUID().toString() + "_" + nomeOriginal;
		
		return novoNome;
	}




}
