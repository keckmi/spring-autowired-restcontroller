package com.example.springautowiredrestcontroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
public class SpringAutowiredRestcontrollerApplication {


	public interface SaySomethingService {
		public String saySomething();
	}


	@Component
	public class SayHelloService implements SaySomethingService {

		@Override
		public String saySomething() {
			return "Hello World!";
		}
	}

	@Configuration
	public class SaySomethingConfiguration {
		@Bean
		@Primary
		public SaySomethingConfigurableService saySomethingConfigurableService() {
			SaySomethingConfigurableService saySomethingConfigurableService = new SaySomethingConfigurableService();
			saySomethingConfigurableService.setWhatToSay("Goodbye");
			return saySomethingConfigurableService;
		}
	}

	public class SaySomethingConfigurableService implements SaySomethingService {
		private String whatToSay = "";

		@Override
		public String saySomething() {
			return whatToSay;
		}

		public String getWhatToSay() {
			return whatToSay;
		}
		public void setWhatToSay(String whatToSay) {
			this.whatToSay = whatToSay;
		}
	}

	@RestController
	public class SaySomethingController {
		@Autowired
		SaySomethingService saySomethingService;

		@GetMapping("/")
		public String home() {
			return saySomethingService.saySomething();
		}
	}

	public static void main(String[] args) {
		ConfigurableApplicationContext applicationContext = SpringApplication.run(SpringAutowiredRestcontrollerApplication.class, args);
		SaySomethingService saySomethingService = applicationContext.getBean(SaySomethingService.class);
		System.out.println(saySomethingService.saySomething());
	}

}
