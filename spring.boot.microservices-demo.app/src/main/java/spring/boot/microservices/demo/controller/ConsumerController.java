package spring.boot.microservices.demo.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import spring.boot.microservices.demo.models.Greeting;

@RestController
@RequestMapping("/api/consume")
public class ConsumerController {
	
	public static final Logger log = LoggerFactory.getLogger(ConsumerController.class);
	
	@Autowired
	private RestTemplate restTemplate;
	
	
	@GetMapping("/greeting")
	public ResponseEntity<Greeting> consumeGreeting(@RequestParam(value = "name", defaultValue = "World") String name)
	{
		Greeting greeting = restTemplate.getForObject("http://localhost:8087/api/greeting?name={name}", Greeting.class, name);
		
		if(greeting == null)
		{
			log.warn("Instance is null");
			return new ResponseEntity<Greeting>(HttpStatus.NO_CONTENT);
		}
		
		log.info(greeting.toString());
		return new ResponseEntity<Greeting>(greeting,HttpStatus.OK);
	}
	
	
	
	

}
