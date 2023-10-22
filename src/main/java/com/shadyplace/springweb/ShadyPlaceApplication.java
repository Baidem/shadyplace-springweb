package com.shadyplace.springweb;

import com.shadyplace.springweb.services.articleBlog.UploadImageService;
import jakarta.annotation.Resource;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ShadyPlaceApplication implements CommandLineRunner {

	@Resource
	UploadImageService uploadImageService;

	public static void main(String[] args) {
		SpringApplication.run(ShadyPlaceApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		this.uploadImageService.init();
	}
}
