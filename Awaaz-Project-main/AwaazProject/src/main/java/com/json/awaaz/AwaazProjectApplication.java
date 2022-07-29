package com.json.awaaz;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.json.awaaz.controller.SkillController;

@SpringBootApplication
public class AwaazProjectApplication {

	public static void main(String[] args) {
		SpringApplication.run(AwaazProjectApplication.class, args);
	}
	@Bean
	public Runner getRunner() {
		return new Runner();
	}
	
	@Bean
	public SkillController getSkill() {
		return new SkillController();
	}

}
