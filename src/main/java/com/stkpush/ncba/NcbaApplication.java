package com.stkpush.ncba;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class NcbaApplication {

	public static void main(String[] args) {
		SpringApplication.run(NcbaApplication.class, args);
	}

}
