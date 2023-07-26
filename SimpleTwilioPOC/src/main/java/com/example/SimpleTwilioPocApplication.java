package com.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.example.config.TwilioConfig;
import com.twilio.Twilio;

import jakarta.annotation.PostConstruct;

@SpringBootApplication
public class SimpleTwilioPocApplication {

	public static void main(String[] args) {
		SpringApplication.run(SimpleTwilioPocApplication.class, args);
	}

	@Autowired
	private TwilioConfig twilioConfig;

	@PostConstruct
	public void initTwilio() {
		Twilio.init(twilioConfig.getAccount_sid(), twilioConfig.getAuth_token());
	}
}
