package br.com.claro.BoxTV;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class BoxTvApplication {

	public static void main(String[] args) {
		SpringApplication.run(BoxTvApplication.class, args);
	}

}
