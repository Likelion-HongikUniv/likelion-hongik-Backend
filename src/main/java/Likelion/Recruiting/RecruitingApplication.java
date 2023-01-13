package Likelion.Recruiting;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;
import java.util.Date;
import java.util.TimeZone;

@SpringBootApplication
public class RecruitingApplication {

	@PostConstruct
	public void started(){
		TimeZone.setDefault(TimeZone.getTimeZone("Asia/Seoul"));
		System.out.println("현재 시각은 "+new Date() +"입니다");
	}
	public static void main(String[] args) {

		SpringApplication.run(RecruitingApplication.class, args);
	}

}
