package org.example.user_profile;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class UserProfileApplication {

	public static void main(String[] args) {

		System.setProperty("aws.java.v1.disableDeprecationAnnouncement", "true");

		SpringApplication.run(UserProfileApplication.class, args);
	}

}
