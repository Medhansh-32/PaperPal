package com.example.PaperPal;

import com.example.PaperPal.service.ExamFileService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.MongoTransactionManager;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

@EnableTransactionManagement
@SpringBootApplication
@EnableAsync
public class PaperPalApplication {

	private ExamFileService examFileService;
	public PaperPalApplication(ExamFileService examFileService) {
		this.examFileService = examFileService;
	}

	public static void main(String[] args) {
		SpringApplication.run(PaperPalApplication.class, args);
		System.out.println();
		System.out.println();
		System.out.println("Running....");

	}
	@Bean
	public PlatformTransactionManager add(MongoDatabaseFactory dbFactory){
		return new MongoTransactionManager(dbFactory);
	}
	@Bean
	public RestTemplate restTemplate(){
		return new RestTemplate();
	}
	@Bean
	public WebClient webClient(){
		return WebClient.builder().build();
	}





}
