package br.com.wex;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class WexApplication {

	public static void main(String[] args) {
		SpringApplication.run(WexApplication.class, args);
	}

}
