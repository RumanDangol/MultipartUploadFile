package com.example.multipart.storage;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

@Service
public class StorageService {
	
	Logger log = LoggerFactory.getLogger(this.getClass().getName());
	
	private final Path rootLocation = Paths.get("upload-dir");
	
	public void store(MultipartFile file) {
		try {
			Files.copy(file.getInputStream(), this.rootLocation.resolve(file.getOriginalFilename()));
			
		} catch (Exception e) {
			throw new RuntimeException("FAIL !");
		}
	}
	
	public Resource loadFile(String filename) {
		
		try {
			Path file = rootLocation.resolve(filename);
			Resource resource = new UrlResource(file.toUri());
			if(resource.exists() || resource.isReadable()) {
				return resource;
			}else {
				throw new RuntimeException("Fail ");
			}
		} catch (Exception e) {
			throw new RuntimeException("Fail !! ");
		}
	}
	
	 public void deleteAll() {
	        FileSystemUtils.deleteRecursively(rootLocation.toFile());
	    }
	public void init() {
		try {
			
			Files.createDirectory(rootLocation);
		} catch (Exception e) {
			throw new RuntimeException("could not initialize storage !! ");
		}
	}
	

}
