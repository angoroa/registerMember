package org.example.member;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan("org.example.member.entity")
public class MemberApplication {

	public static void main(String[] args) {SpringApplication.run(MemberApplication.class, args);
	}

}
