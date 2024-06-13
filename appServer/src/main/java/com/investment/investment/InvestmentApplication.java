package com.investment.investment;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@RestController
public class InvestmentApplication {

    @RequestMapping("/")
	public static void main(String[] args) {
		SpringApplication.run(InvestmentApplication.class, args);
	}

}
