package com.json.awaaz;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;

import com.json.awaaz.controller.SkillController;

public class Runner implements CommandLineRunner {
	
	@Autowired
	private SkillController skillController;
	
	@Override
	public void run(String... args) throws Exception {
		    		
            skillController.outputAtConsole();
      
	}
}
