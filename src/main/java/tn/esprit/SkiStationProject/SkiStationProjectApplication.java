package tn.esprit.SkiStationProject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class SkiStationProjectApplication {

    public static void main(String[] args) {
        SpringApplication.run(SkiStationProjectApplication.class, args);
    }

}
