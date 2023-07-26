package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.example.demo.config.TwilioConfig;
import com.twilio.Twilio;

import jakarta.annotation.PostConstruct;

@SpringBootApplication
public class TwilioPocApplication {
	
	@Autowired
	 private TwilioConfig twilioConfig;
	 
	@PostConstruct
	 public void initTwilio() {
		 Twilio.init(twilioConfig.getAccount_sid(), twilioConfig.getAuth_token());
	 }

	public static void main(String[] args) {
		SpringApplication.run(TwilioPocApplication.class, args);
	}

}
