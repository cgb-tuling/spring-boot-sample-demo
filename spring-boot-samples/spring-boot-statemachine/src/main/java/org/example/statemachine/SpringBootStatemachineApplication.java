package org.example.statemachine;

import org.example.statemachine.run.StartupRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

/**
 * @author MSI-NB
 */
@SpringBootApplication
public class SpringBootStatemachineApplication {

	@Bean
	public StartupRunner startupRunner() {
		return new StartupRunner();
	}

	public static void main(String[] args) {
		SpringApplication.run(SpringBootStatemachineApplication.class, args);
	}

}

